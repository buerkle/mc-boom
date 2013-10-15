package com.thebuerkle.mcboom;

import com.thebuerkle.mcboom.packet.Packet;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

public class ProtocolEncoder extends OneToOneEncoder {
    @Override()
    protected Object encode(ChannelHandlerContext ctx, Channel channel, Object msg) throws Exception {
        Packet packet = (Packet) msg;

/*      System.err.println("Encode packet: " + Integer.toHexString(packet.id));*/

        ChannelBuffer buf = ChannelBuffers.buffer(1+packet.size());
        buf.writeByte(packet.id);
        packet.write(buf);
        return buf;
    }
}
