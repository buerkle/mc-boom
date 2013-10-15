package com.thebuerkle.mcboom;

import com.thebuerkle.mcboom.packet.*;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;

public class ClientHandler extends SimpleChannelUpstreamHandler {

    private final String _host;
    private final int _port;
    private final String _user;

    private volatile Channel _channel;

    private final Object _lock = new Object();

    private PlayerPositionAndLookPacket _position;

    private final ScheduledExecutorService _executor = Executors.newScheduledThreadPool(1);

    public ClientHandler(String host, int port, String user) {
        _host = host;
        _port = port;
        _user = user;
    }

    @Override()
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) {
        _channel = ctx.getChannel();

        ctx.getChannel().write(new HandshakePacket(_host, _port, _user));
    }

    @Override()
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        Packet packet = (Packet) e.getMessage();

/*      System.err.println("Received: " + e.getMessage());*/

        switch (packet.id) {
        case Packet.ENCRYPTION_KEY_REQUEST:
            _channel.write(new ClientStatusPacket(0));
            break;
        case Packet.LOGIN_REQUEST:
            _executor.scheduleAtFixedRate(new Tick(), 100, 50, TimeUnit.MILLISECONDS);
            break;
        case Packet.PLAYER_POSITION_AND_LOOK:
            synchronized (_lock) {
                _position = (PlayerPositionAndLookPacket) packet;
            }
            break;
        }
    }

    @Override()
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
        e.getCause().printStackTrace();
        ctx.getChannel().close();
    }

    private class Tick implements Runnable {
        @Override()
        public void run() {
            try {
                _run();
            }
            catch (RuntimeException e) {
                e.printStackTrace();
            }
        }

        private void _run() {
            if (_channel.isOpen()) {
                PlayerPositionAndLookPacket p;

                synchronized (_lock) {
                    p = _position;
                }
                _channel.write(new PlayerPositionPacket(p.x, p.y, p.z, p.stance, p.onGround));
            }
        }
    }
}
