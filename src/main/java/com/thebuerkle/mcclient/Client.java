package com.thebuerkle.mcclient;

import com.thebuerkle.mcclient.model.IntVec3;
import com.thebuerkle.mcclient.model.Player;
import com.thebuerkle.mcclient.model.Vec3;
import com.thebuerkle.mcclient.request.*;
import com.thebuerkle.mcclient.response.*;

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

public class Client {

    public static final String SESSION_KEY = Client.class.getName();

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

    private Player _player;

    public Client(ScheduledExecutorService executor, String user, String host, int port) {
        _executor = executor;
        _user = user;
        _host = host;
        _port = port;
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

        ConnectFuture future = connector.connect(new InetSocketAddress(_host, _port));
        future.awaitUninterruptibly();

        if (!future.isConnected()) {
            return;
        }

        session = future.getSession();
        session.getConfig().setUseReadOperation(true);
        session.setAttribute(SESSION_KEY, this);
        session.write(new HandshakeRequest(39, _user, _host, _port));

        _session = session;

        System.err.println("--- wait for close");
/*      session.getCloseFuture().awaitUninterruptibly();*/

    }

    public void onMessageReceived(Response response) {
/*      System.err.println("Response received: " + response.getClass().getSimpleName() + ": " + response);*/
        int id = response.getId();

        if (EncryptionKeyRequestResponse.ID == id) {
            EncryptionKeyRequestResponse rsp = (EncryptionKeyRequestResponse) response;

            _session.write(new EncryptionKeyResponseRequest(rsp.key, _secret, rsp.token));
        }
        else if (EncryptionKeyResponseResponse.ID == id) {
            _session.getFilterChain().addFirst("encryption", new EncryptionFilter(_secret));

            _session.write(new ClientStatusRequest(0));
        }
        else if (KeepAliveResponse.ID == id) {
            _session.write(new KeepAliveRequest(((KeepAliveResponse) response).id));
        }
        else if (LoginRequestResponse.ID == id) {
            final LoginRequestResponse login = (LoginRequestResponse) response;

            _executor.submit(new Runnable() {
                                 public void run() {
                                     _player = new Player(login.eid);
                                     System.err.println("Login: " + login.eid);
                                 }
                             });
        }
        else if (PlayerPositionAndLookResponse.ID == id) {
            final PlayerPositionAndLookResponse rsp = (PlayerPositionAndLookResponse) response;

            System.err.println("Player position and look: " + rsp);

            if (_running.compareAndSet(false, true)) {
                _executor.execute(new Runnable() {
                                      public void run() {
/*                                        System.err.println("---- set position: " + rsp.position*/
/*                                             + ": " + rsp.onGround);                           */
                                          _player.setPosition(rsp.position);

                                          _executor.schedule(new TickRunnable(),
                                                             TICK_MS,
                                                             TimeUnit.MILLISECONDS);
                                      }
                                  });
            }
            else {
                _executor.execute(new Runnable() {
                                      public void run() {
                                          _player.setPosition(rsp.position);
                                          _session.write(new PlayerPositionRequest(rsp.position, rsp.position.y + Player.HEIGHT, _player.isOnGround()));
                                      }
                                  });
            }

        }
        else if (DisconnectResponse.ID == id) {
            System.err.println("Disconnected: " + ((DisconnectResponse) response).reason);
        }
    }

    public void disconnect() {
        _running.set(false);
    }

    private class TickRunnable implements Runnable {
        @Override()
        public void run() {
            Vec3 position = _player.getPosition();
            double velocityY = _player.getVelocityY();
            double y = position.y;
            double x = position.x;
            double z = position.z;

            if (_player.isOnGround()) {
                position.x += _random.nextDouble() - 0.5;
                position.z += _random.nextDouble() - 0.5;
            }
            else {
                velocityY -= 0.08;
                velocityY *= 0.98;

                y = position.y + velocityY;

                // Works only for flat world until chunk data is parsed
                if (y < 4.0) {
                    y = 4.0;
                    velocityY = 0.0;
                    _player.setOnGround(true);
                }
                _player.setVelocityY(velocityY);
                _player.setPosition(position.x, y, position.z);
            }

            _session.write(new PlayerPositionRequest(position, position.y + Player.HEIGHT, _player.isOnGround()));

            if (_running.get()) {
                _executor.schedule(this, TICK_MS, TimeUnit.MILLISECONDS);
            }
        }
    }
}

