package com.thebuerkle.mcboom.packet;

import com.google.common.base.Objects;
import com.thebuerkle.mcboom.Buffers;
import com.thebuerkle.mcboom.DataType;

import org.jboss.netty.buffer.ChannelBuffer;

public class KeepAlivePacket extends Packet {

    public static final int ID = 0x00;

    public static final DataType[] ENCODING = new DataType[] {
        DataType.mc_int
    };

    public final int id;

    public KeepAlivePacket(ChannelBuffer in) {
        super(ID);
        this.id = Buffers.mc_int(in);
    }

    @Override()
    public void write(ChannelBuffer out) {
        out.writeInt(0);
    }

    @Override()
    public String toString() {
        return Objects.toStringHelper(this)
            .add("ID", id)
            .toString();
    }

}
