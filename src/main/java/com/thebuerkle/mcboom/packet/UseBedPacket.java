package com.thebuerkle.mcboom.packet;

import com.google.common.base.Objects;
import com.thebuerkle.mcboom.Buffers;
import com.thebuerkle.mcboom.DataType;

import org.jboss.netty.buffer.ChannelBuffer;

public class UseBedPacket extends Packet {

    public static final int ID = USE_BED;

    public static final DataType[] ENCODING = new DataType[] {
        DataType.mc_int,
        DataType.mc_byte,
        DataType.mc_int,
        DataType.mc_byte,
        DataType.mc_int
    };

    public final int eid;
    public final int unknown;
    public final int x;
    public final int y;
    public final int z;

    public UseBedPacket(ChannelBuffer in) {
        super(ID);
        this.eid = Buffers.mc_int(in);
        this.unknown = Buffers.mc_byte(in);
        this.x = Buffers.mc_int(in);
        this.y = Buffers.mc_byte(in);
        this.z = Buffers.mc_int(in);
    }

    @Override()
    public String toString() {
        return Objects.toStringHelper(this)
            .add("eid", id)
            .add("unknown", unknown)
            .add("x", x)
            .add("y", y)
            .add("z", z)
            .toString();
    }
}
