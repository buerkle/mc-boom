package com.thebuerkle.mcclient;

import com.thebuerkle.mcclient.model.IntVec3;
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

    private volatile Vec3 _position;
    private volatile double _stance;
    private volatile boolean _onGround;

    private final AtomicBoolean _started = new AtomicBoolean();

    public Client(ScheduledExecutorService executor, String user, String host, int port) {
        _executor = executor;
        _user = user;
        _host = host;
        _port = port;
        _secret = secret();
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
        else if (PlayerPositionAndLookResponse.ID == id) {
            PlayerPositionAndLookResponse rsp = (PlayerPositionAndLookResponse) response;
            _position = rsp.position;
            _stance = rsp.stance;
            _onGround = rsp.onGround;

            System.err.println("Position: " + _position);
            System.err.println("Stance: " + _stance);
            PlayerRequest pr = new PlayerRequest(true);
            _session.write(pr);

            PlayerPositionRequest req =
                new PlayerPositionRequest(_position, _stance, true);

            _session.write(req);

            if (_started.compareAndSet(false, true)) {
                _executor.scheduleAtFixedRate(new Runnable() {
                                                  public void run() {
                                                      double x = (_random.nextDouble()  - 0.5);
                                                      double z = (_random.nextDouble() - 0.5);

                                                      _position = new Vec3(_position.x+x, _position.y, _position.z+z);

/*                                                    System.err.println(_position);*/
                                                      PlayerPositionRequest req =
                                                          new PlayerPositionRequest(_position, _stance, true);

                                                      _session.write(req);
                                                  }
                                              }, 100, 100, TimeUnit.MILLISECONDS);
            }

        }
        else if (DisconnectResponse.ID == id) {
            System.err.println("Disconnected: " + ((DisconnectResponse) response).reason);
        }
    }

    public void disconnect() {
    }
}

