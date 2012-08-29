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
import org.apache.mina.transport.socket.nio.NioSocketConnector;

public class Main {
    public static void main(String[] args) {
        IoConnector connector = new NioSocketConnector();

        connector.setHandler(new ClientHandler());

        connector.getFilterChain().addLast("RequestProtocolCodec",
            new ProtocolCodecFilter(new RequestProtocolEncoder(), new ResponseProtocolDecoder()));

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

        for (int i = 0; i < 1; i++) {
            Client client = new Client(executor, "__steve" + i, "localhost", 25565);
            client.connect(connector);
        }
/*      connector.dispose();*/
    }
}
