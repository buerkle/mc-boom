package com.thebuerkle.mcboom.packet;

import com.google.common.base.Objects;
import com.thebuerkle.mcboom.Buffers;
import com.thebuerkle.mcboom.DataType;

import org.jboss.netty.buffer.ChannelBuffer;

public class BlockChangePacket extends Packet {

    public static final int ID = BLOCK_CHANGE;

    public static final DataType[] ENCODING = new DataType[] {
        DataType.mc_int,
        DataType.mc_byte,
        DataType.mc_int,
        DataType.mc_short,
        DataType.mc_byte
    };

    public final int x;
    public final int y;
    public final int z;
    public final short type;
    public final int metadata;

    public BlockChangePacket(ChannelBuffer in) {
        super(ID);
        this.x = Buffers.mc_int(in);
        this.y = Buffers.mc_byte(in);
        this.z = Buffers.mc_int(in);
        this.type = Buffers.mc_short(in);
        this.metadata = Buffers.mc_byte(in);
    }

    @Override()
    public String toString() {
        return Objects.toStringHelper(this)
            .add("x", x)
            .add("y", y)
            .add("z", z)
            .add("type", type)
            .add("metadata", metadata)
            .toString();
    }
}
