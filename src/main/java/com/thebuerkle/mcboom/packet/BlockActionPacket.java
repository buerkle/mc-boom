package com.thebuerkle.mcboom.packet;

import com.google.common.base.Objects;
import com.thebuerkle.mcboom.Buffers;
import com.thebuerkle.mcboom.DataType;

import org.jboss.netty.buffer.ChannelBuffer;

public class BlockActionPacket extends Packet {

    public static final int ID = BLOCK_ACTION;

    public static final DataType[] ENCODING = new DataType[] {
        DataType.mc_int,
        DataType.mc_short,
        DataType.mc_int,
        DataType.mc_byte,
        DataType.mc_byte,
        DataType.mc_short
    };

    public final int x;
    public final int y;
    public final int z;
    public final int byte1;
    public final int byte2;
    public final short block;

    public BlockActionPacket(ChannelBuffer in) {
        super(ID);
        this.x = Buffers.mc_int(in);
        this.y = Buffers.mc_short(in);
        this.z = Buffers.mc_int(in);
        this.byte1 = Buffers.mc_byte(in);
        this.byte2 = Buffers.mc_byte(in);
        this.block = Buffers.mc_short(in);
    }

    @Override()
    public String toString() {
        return Objects.toStringHelper(this)
            .add("x", x)
            .add("y", y)
            .add("z", z)
            .add("byte1", byte1)
            .add("byte2", byte2)
            .add("block", block)
            .toString();
    }
}
