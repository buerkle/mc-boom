package com.thebuerkle.mcboom.packet;

import com.thebuerkle.mcboom.Buffers;

import org.jboss.netty.buffer.ChannelBuffer;

public class ServerListPingPacket extends Packet {

    private static final String MESSAGE = "MC|PingHost";

    public final int protocol;
    public final String host;
    public final int port;

    public ServerListPingPacket(int protocol, String host, int port) {
        super(SERVER_LIST_PING);

        this.protocol = protocol;
        this.host = host;
        this.port = port;
    }

    @Override()
    public int size() {
        return 16 + 2*MESSAGE.length() + 2*this.host.length();
    }

    @Override()
    public void write(ChannelBuffer out) {
        Buffers.mc_byte(out, 1);
        Buffers.mc_int(out, 0xFA);
        Buffers.mc_string(out, MESSAGE);
        Buffers.mc_short(out, 7 + 2 * this.host.length());
        Buffers.mc_byte(out, this.protocol);
        Buffers.mc_string(out, this.host);
        Buffers.mc_int(out, this.port);
    }
}
