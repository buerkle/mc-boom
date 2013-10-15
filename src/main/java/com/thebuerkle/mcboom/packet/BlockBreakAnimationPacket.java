package com.thebuerkle.mcboom.packet;

import com.google.common.base.Objects;
import com.thebuerkle.mcboom.Buffers;
import com.thebuerkle.mcboom.DataType;

import org.jboss.netty.buffer.ChannelBuffer;

public class BlockBreakAnimationPacket extends Packet {

    public static final int ID = BLOCK_BREAK_ANIMATION;

    public static final DataType[] ENCODING = new DataType[] {
        DataType.mc_int,
        DataType.mc_int,
        DataType.mc_int,
        DataType.mc_int,
        DataType.mc_byte
    };

    public final int eid;
    public final int x;
    public final int y;
    public final int z;
    public final int stage;

    public BlockBreakAnimationPacket(ChannelBuffer in) {
        super(ID);
        this.eid = Buffers.mc_int(in);
        this.x = Buffers.mc_int(in);
        this.y = Buffers.mc_int(in);
        this.z = Buffers.mc_int(in);
        this.stage = Buffers.mc_byte(in);
    }

    @Override()
    public String toString() {
        return Objects.toStringHelper(this)
            .add("eid", eid)
            .add("x", x)
            .add("y", y)
            .add("z", z)
            .add("destroy stage", stage)
            .toString();
    }
}
