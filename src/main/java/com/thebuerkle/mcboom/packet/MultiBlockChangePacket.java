package com.thebuerkle.mcboom.packet;

import com.google.common.base.Objects;
import com.thebuerkle.mcboom.Buffers;
import com.thebuerkle.mcboom.DataType;

import org.jboss.netty.buffer.ChannelBuffer;

public class MultiBlockChangePacket extends Packet {

    public static final int ID = MULTI_BLOCK_CHANGE;

    public static final DataType[] ENCODING = new DataType[] {
        DataType.mc_int,
        DataType.mc_int,
        DataType.mc_short,
        DataType.mc_bytearray_4
    };

    public final int x;
    public final int z;
    public final int count;
    public final byte[] data;

    public MultiBlockChangePacket(ChannelBuffer in) {
        super(ID);
        this.x = Buffers.mc_int(in);
        this.z = Buffers.mc_int(in);
        this.count = Buffers.mc_short(in);
        this.data = Buffers.mc_bytearray_4(in);
    }

    @Override()
    public String toString() {
        return Objects.toStringHelper(this)
            .add("x", x)
            .add("z", z)
            .add("count", count)
            .add("data length", data.length)
            .toString();
    }
}
