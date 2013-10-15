package com.thebuerkle.mcboom.packet;

import com.google.common.base.Objects;
import com.thebuerkle.mcboom.Buffers;
import com.thebuerkle.mcboom.DataType;

import org.jboss.netty.buffer.ChannelBuffer;

public class SpawnExperienceOrbPacket extends Packet {

    public static final int ID = SPAWN_EXPERIENCE_ORB;

    public static final DataType[] ENCODING = new DataType[] {
        DataType.mc_int,
        DataType.mc_fixed_point,
        DataType.mc_fixed_point,
        DataType.mc_fixed_point,
        DataType.mc_short
    };

    public final int eid;
    public final double x;
    public final double y;
    public final double z;
    public final short count;

    public SpawnExperienceOrbPacket(ChannelBuffer in) {
        super(ID);
        this.eid = Buffers.mc_int(in);
        this.x = Buffers.mc_fixed_point(in);
        this.y = Buffers.mc_fixed_point(in);
        this.z = Buffers.mc_fixed_point(in);
        this.count = Buffers.mc_short(in);
    }

    @Override()
    public String toString() {
        return Objects.toStringHelper(this)
            .add("eid", eid)
            .add("x", x)
            .add("y", y)
            .add("z", z)
            .add("count", count)
            .toString();
    }
}
