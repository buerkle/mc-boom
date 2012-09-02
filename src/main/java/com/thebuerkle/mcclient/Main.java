package com.thebuerkle.mcclient;

import com.thebuerkle.mcclient.request.RequestProtocolEncoder;
import com.thebuerkle.mcclient.response.ResponseProtocolDecoder;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.SocketSessionConfig;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

public class Main {

    public static void main(String[] args) {
        new Main().run();
    }

    public void run() {
        final int port = 25565;
        final String host = "localhost";

        final NioSocketConnector connector = new NioSocketConnector();
        SocketSessionConfig cfg = connector.getSessionConfig();
        cfg.setTrafficClass(24); // IPTOS_THROUGHPUT & IPTOS_LOWDELAY

        final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

        ClientListener listener = new ClientListener() {
            @Override()
            public void onConnect(Client client) {

            }

            @Override()
            public void onDisconnect(Client client) {
                System.err.println("Client disconnected: " + client.getUser() + ". Reconnect");
                Client reconnect = new Client(executor, client.getUser(), host, port);
                reconnect.connect(connector);
            }
        };
        connector.setHandler(new ClientHandler(listener));

        connector.getFilterChain().addLast("RequestProtocolCodec",
                                           new ProtocolCodecFilter(new RequestProtocolEncoder(), new ResponseProtocolDecoder()));

        for (int i = 0; i < 50; i++) {
            Client client = new Client(executor, "__steve" + i, host, port);
            client.connect(connector);
        }
/*      connector.dispose();*/
    }
}
