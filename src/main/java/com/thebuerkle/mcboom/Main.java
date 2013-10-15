package com.thebuerkle.mcboom;

import com.thebuerkle.mcboom.packet.*;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;

public class Main {
    public static void main(String[] args) throws Exception {
        ChannelFactory factory =
            new NioClientSocketChannelFactory(Executors.newCachedThreadPool(),
                                              Executors.newCachedThreadPool());

        ChannelFuture f = connect(factory, "localhost", 33333, "bob");
        f.await();
    }

    private static ChannelFuture connect(ChannelFactory factory, final String host, final int port, final String user) {
        ClientBootstrap bootstrap = new ClientBootstrap(factory);
        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
                                         @Override()
                                             public ChannelPipeline getPipeline() {
                                             return Channels.pipeline(new ProtocolEncoder(),
                                                                      new ProtocolDecoder(),
                                                                      new ClientHandler(host, port, user));
                                         }
                                     });

        return bootstrap.connect(new InetSocketAddress(host, port));
/*      cf = c.write(new HandshakePacket("buerkle", "localhost", 25565));*/
/*      cf.await();                                                      */
    }
}
