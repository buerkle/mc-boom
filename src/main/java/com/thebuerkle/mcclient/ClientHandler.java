package com.thebuerkle.mcclient;

import com.thebuerkle.mcclient.response.Response;

import java.net.InetSocketAddress;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

public class ClientHandler extends IoHandlerAdapter {

    @Override()
    public void sessionOpened(IoSession session) {
        System.err.println("------ open");
/*      Client client = client(session);*/
/*      client.onConnect(session);      */
/*          packethandshake handshake = new PacketHandshake(39, _user, _host, _port);*/
/*          handshake.write(_buf);                                                   */
/*          session.write(_buf);                                                     */


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
