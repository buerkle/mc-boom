package com.thebuerkle.mcclient;

import com.thebuerkle.mcclient.ChunkManager.Callback;
import com.thebuerkle.mcclient.model.Chunk;
import com.thebuerkle.mcclient.model.Difficulty;
import com.thebuerkle.mcclient.model.IntVec3;
import com.thebuerkle.mcclient.model.Player;
import com.thebuerkle.mcclient.model.Vec3;
import com.thebuerkle.mcclient.model.ViewDistance;
import com.thebuerkle.mcclient.model.World;
import com.thebuerkle.mcclient.request.*;
import com.thebuerkle.mcclient.response.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.security.InvalidKeyException;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.core.session.IoSessionInitializer;

public class Client implements ChunkManager.Callback {

    public static final String SESSION_KEY = Client.class.getName();

    private static final Method[] HANDLERS = new Method[256];

    static {
        try {
            for (Method method : Client.class.getDeclaredMethods()) {
                Class<?>[] parms = method.getParameterTypes();

                if (parms.length == 1
                    && Response.class.isAssignableFrom(parms[0])
                    && !"onMessageReceived".equals(method.getName())) {

                    Class<?> response = parms[0];
                    int id = response.getField("ID").getInt(null);

                    HANDLERS[id] = method;
                }
            }
        }
        catch (Exception e) {
            throw new Error(e);
        }
    }

    private static final int TICK_MS = 50;

    private final String _host;
    private final int _port;
    private final String _user;

    private volatile IoSession _session;

    // AES key
    private final SecretKey _secret;

    private final Random _random = new Random();

    private final ScheduledExecutorService _executor;

    public static Client get(IoSession session) {
        return(Client) session.getAttribute(SESSION_KEY);
    }

    private final AtomicBoolean _running = new AtomicBoolean();

    private final ChunkManager _chunkManager;

    private Player _player;

    public Client(ScheduledExecutorService executor, String user, String host, int port,
                  ChunkManager chunkManager) {

        _executor = executor;
        _user = user;
        _host = host;
        _port = port;
        _chunkManager = chunkManager;
        _secret = secret();
    }

    public String getUser() {
        return _user;
    }

    private static SecretKey secret() {
        try {
            KeyGenerator gen = KeyGenerator.getInstance("AES");
            gen.init(128);
            return gen.generateKey();
        }
        catch (Exception e) {
            throw new Error(e);
        }
    }

    public void connect(IoConnector connector) {
        IoSession session = _session;

        if (session != null && session.isConnected()) {
            throw new IllegalStateException("Already connected. Disconnect first.");
        }

        ConnectFuture future = connector.connect(new InetSocketAddress(_host, _port),
                                                 new IoSessionInitializer<ConnectFuture>() {
                                                     public void initializeSession(IoSession ios, ConnectFuture f) {
                                                         ios.setAttribute(SESSION_KEY, Client.this);
                                                     }
                                                 });
        future.awaitUninterruptibly();

        if (!future.isConnected()) {
            return;
        }

        session = future.getSession();
        session.getConfig().setUseReadOperation(false);
        session.write(new HandshakeRequest(_user, _host, _port));

        _session = session;
    }

    public void onMessageReceived(Response response) {
//      System.err.println("Response received: " + response.getClass().getSimpleName() + ": " + response);

        int id = response.getId();

        Method handler = HANDLERS[id];

        if (handler != null) {
            try {
                handler.invoke(this, response);
            }
            catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    public void disconnect() {
        _session.close(true);
    }

    public void onDisconnect() {
        _running.set(false);
    }

    @Override()
    public void onChunkLoad(final Chunk chunk) {
        Runnable task = new Runnable() {
            public void run() {
                _player.getWorld().add(chunk);
            }
        };
//      System.err.println("chunk: "+ chunk.getX() + ", " + chunk.getZ());
        _executor.submit(task);
/*      int x = 275;                                                       */
/*      int y = 3;                                                         */
/*      int z = 1589;                                                      */
/*      Vec3 p = _player.getPosition();                                    */
/*                                                                         */
/*      if (chunk.contains(x, y, z)) {                                     */
/*          System.err.println("Found chunk: " + chunk.blockType(x, y, z));*/
/*      }                                                                  */
    }

    // Received after client sends handshake to server
    private void onEncryptionKeyRequestResponse(EncryptionKeyRequestResponse response) {
        _session.write(new EncryptionKeyResponseRequest(response.key, _secret, response.token));
    }

    // Server has turned on encryption, we do the same
    private void onEncryptionKeyResponseResponse(EncryptionKeyResponseResponse response) {
        _session.getFilterChain().addFirst("encryption", new EncryptionFilter(_secret));
        _session.write(new ClientStatusRequest(0));
    }
    private void onLoginRequestResponse(final LoginRequestResponse response) {
        Runnable r = new Runnable() {
            @Override()
            public void run() {
                _player = new Player(response.eid);
                _session.write(new ClientInfoRequest("en_US", ViewDistance.Far,
                                                     0, Difficulty.Normal, true));
            }
        };
        _executor.submit(r);
    }

    private void onKeepAliveResponse(KeepAliveResponse response) {
        _session.write(new KeepAliveRequest(response.id));
    }

    private void onChatMessageResponse(ChatMessageResponse response) {
        Runnable task = new Runnable() {
            public void run() {
                World world = _player.getWorld();
                Vec3 position = _player.getPosition();
                Vec3 under = new Vec3(position.x, position.y-1, position.z);
                System.err.println("Position: " + _player.getPosition() + " -> " + under);
                int block = world.blockType(under);
                /*          int block = world.blockType(new Vec3(x, y, z));*/
                if (block != -1) {
                    System.err.println("Block: " + block);
                }
            }
        };
        String msg = response.message;
        System.err.println(msg);
        if (msg.contains("position")) {
            _executor.submit(task);
        }
    }

    private void onBlockChangeResponse(final BlockChangeResponse response) {
        Runnable task = new Runnable() {
            public void run() {
                _player.getWorld().blockChange(response.position, response.type, response.metadata);
            }
        };
        _executor.submit(task);
    }

    private void onChunkDataResponse(ChunkDataResponse response) {
//      System.err.println("chunk response: " + response);
        _chunkManager.submit(this, response);
    }

    private void onMapChunkBulkResponse(MapChunkBulkResponse response) {
        System.err.println("bulk response: " + response);
        _chunkManager.submit(this, response);
    }

    private void onPlayerPositionAndLookResponse(final PlayerPositionAndLookResponse response) {
        System.err.println("---- set position: " + response.position
                           + ": " + response.onGround
                           + ": " + response.stance);
        if (_running.compareAndSet(false, true)) {
            _executor.execute(new Runnable() {
                                  public void run() {
                                      _player.setPosition(response.position);

                                      _executor.schedule(new Ticker(),
                                                         TICK_MS,
                                                         TimeUnit.MILLISECONDS);
                                  }
                              });
        }
        else {
            _executor.execute(new Runnable() {
                                  public void run() {
                                      _player.setPosition(response.position);
                                      _session.write(new PlayerPositionRequest(response.position, response.position.y + Player.HEIGHT, _player.isOnGround()));
                                  }
                              });
        }
    }

    private void onDisconnectResponse(DisconnectResponse response) {
        System.err.println("Server disconnect: " + response);
    }

    private class Ticker implements Runnable {
        @Override()
        public void run() {
            try {
                runInternal();
            }
            catch (RuntimeException e) {
                e.printStackTrace();
            }

        }

        private void runInternal() {
            Vec3 position = _player.getPosition();
            double velocityY = _player.getVelocityY();
            double y = position.y;
            double x = position.x;
            double z = position.z;
            World world = _player.getWorld();

            if (_player.isOnGround()) {
//              position.x += _random.nextDouble() - 0.5;
//              position.z += Math.abs(_random.nextDouble() - 0.5);
//              System.err.println("on ground");
            }
            else {
                velocityY -= 0.08;
                velocityY *= 0.98;

                //y = position.y + velocityY;

                int block = world.blockType(position.x, position.y-1, position.z);
                System.err.println("block: " + block);
                if (block == 0) {
                    position.y += velocityY;
                    System.err.println("----- Set position: " + position);
                    _player.setPosition(position);
                }
                else if (block != -1) {
                    _player.setOnGround(true);
                }
                // Works only for flat world until chunk data is parsed
//              if (y < 4.0) {
//                  y = 4.0;
//                  velocityY = 0.0;
//                  _player.setOnGround(true);
//              }
//              _player.setVelocityY(velocityY);
//              _player.setPosition(position.x, y, position.z);
            }

//          System.err.println("Player position: " + position);
            _session.write(new PlayerPositionRequest(position, position.y + Player.EYE_LEVEL, _player.isOnGround()));

//          System.err.println("running: " + _running.get());
//          test();
/*          System.err.println("Block: " + _player.getWorld().blockType(position));*/
            if (_running.get()) {
                _executor.schedule(this, TICK_MS, TimeUnit.MILLISECONDS);
            }
        }

        public void test() {
//          int x = 275;
//          int y = 3;
//          int z = 1589;
/*          Vec3 p = _player.getPosition();*/

            World world = _player.getWorld();
            Vec3 position = _player.getPosition();
            Vec3 under = new Vec3(position.x, position.y-1, position.z);
            System.err.println("Position: " + _player.getPosition());
            int block = world.blockType(under);
/*          int block = world.blockType(new Vec3(x, y, z));*/
            if (block != -1) {
                System.err.println("Block: " + block);
            }
/*          if (chunk.contains(x, y, z)) {                                     */
/*              System.err.println("Found chunk: " + chunk.blockType(x, y, z));*/
/*          }                                                                  */

        }
    }
}

