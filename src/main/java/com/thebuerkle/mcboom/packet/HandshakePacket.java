package com.thebuerkle.mcboom.packet;

import com.google.common.base.Objects;
import com.thebuerkle.mcboom.Buffers;
import com.thebuerkle.mcboom.DataType;

import org.jboss.netty.buffer.ChannelBuffer;

public class HandshakePacket extends Packet {

    public static final int ID = HANDSHAKE;

    public static final int PROTOCOL_VERSION = 78;

    public static final DataType[] ENCODING = new DataType[] {
        DataType.mc_string
    };

    public final String host;
    public final int port;
    public final String username;

    public HandshakePacket(String host, int port, String username) {
        super(ID);

        this.username = username;
        this.host = host;
        this.port = port;
    }

    @Override()
    public int size() {
        return 1 + mc_string_length(this.username) + mc_string_length(this.host) + 4;
    }

    @Override()
    public void write(ChannelBuffer out) {
        Buffers.mc_byte(out, PROTOCOL_VERSION);
        Buffers.mc_string(out, this.username);
        Buffers.mc_string(out, this.host);
        Buffers.mc_int(out, this.port);
    }

    @Override()
    public String toString() {
        return Objects.toStringHelper(this)
            .add("ID", id)
            .add("host", host)
            .add("port", port)
            .add("username", username)
            .toString();
    }
}
