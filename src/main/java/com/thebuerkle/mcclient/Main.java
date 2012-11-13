package com.thebuerkle.mcclient;

import com.thebuerkle.mcclient.request.RequestProtocolEncoder;
import com.thebuerkle.mcclient.response.ResponseProtocolDecoder;

import java.net.InetSocketAddress;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.polling.AbstractPollingIoConnector;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.SocketSessionConfig;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

public class Main {

    private static final String HOST = "localhost";
    private static final int PORT = 25565;

    public static void main(String[] args) {
        new Main().run();
    }

    private final ChunkManager _chunkManager = new ChunkManager();

    public void run() {
        final NioSocketConnector connector = new NioSocketConnector();
        SocketSessionConfig cfg = connector.getSessionConfig();
        cfg.setTrafficClass(24); // IPTOS_THROUGHPUT & IPTOS_LOWDELAY

        final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

        ReconnectWorker reconnector = new ReconnectWorker(connector, executor);
/*      reconnector.start();*/

        connector.setHandler(new ClientHandler(reconnector));

        connector.getFilterChain().addLast("RequestProtocolCodec",
                                           new ProtocolCodecFilter(new RequestProtocolEncoder(), new ResponseProtocolDecoder()));

        ChunkManager chunkManager = new ChunkManager();
        chunkManager.start();

        for (int i = 0; i < 1; i++) {
            Client client = new Client(executor, "__steve" + i, HOST, PORT, chunkManager);
            client.connect(connector);
        }
/*      connector.dispose();*/
    }

    private class ReconnectWorker implements ClientListener, Runnable {

        private final IoConnector _connector;
        private final ScheduledExecutorService _executor;
        private final BlockingQueue<Client> _reconnects = new LinkedBlockingQueue<Client>();

        ReconnectWorker(IoConnector connector, ScheduledExecutorService executor) {
            _connector = connector;
            _executor = executor;
        }

        @Override()
        public void onConnect(Client client) {

        }

        @Override()
        public void onDisconnect(Client client) {
            System.err.println("Client disconnected: " + client.getUser());
            _reconnects.offer(client);
        }

        public void start() {
            new Thread(this, "ReconnectWorker").start();
        }

        @Override()
        public void run() {
            Client client = null;

            try {
                while ((client = _reconnects.take()) != null) {
                    System.err.println("Reconnect: " + client.getUser());

                    Client reconnect = new Client(_executor, client.getUser(), HOST, PORT, _chunkManager);
                    reconnect.connect(_connector);
                }
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
