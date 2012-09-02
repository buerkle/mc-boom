package com.thebuerkle.mcclient;

import com.thebuerkle.mcclient.response.Response;

import java.net.InetSocketAddress;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

public class ClientHandler extends IoHandlerAdapter {

    private final ClientListener _listener;

    public ClientHandler(ClientListener listener) {
        _listener = listener;
    }

    @Override()
    public void sessionOpened(IoSession session) {
        Client client = Client.get(session);
        if (_listener != null) {
            _listener.onConnect(client);
        }
    }

    public void sessionClosed(IoSession session) throws Exception {
        Client client = Client.get(session);
        System.err.println("Session closed: " + client.getUser());
        if (_listener != null) {
            _listener.onDisconnect(client);
        }
    }

    @Override()
    public void messageReceived(IoSession session, Object msg) {
        Client client = Client.get(session);
        client.onMessageReceived((Response) msg);
    }

    @Override()
    public void exceptionCaught(IoSession session, Throwable cause) {
        cause.printStackTrace();
        session.close();
    }
}
