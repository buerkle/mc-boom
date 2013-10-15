package com.thebuerkle.mcboom.packet;

import com.google.common.base.Objects;
import com.thebuerkle.mcboom.Buffers;
import com.thebuerkle.mcboom.DataType;

import org.jboss.netty.buffer.ChannelBuffer;

public class SpawnNamedEntityPacket extends Packet {

    public static final int ID = SPAWN_NAMED_ENTITY;

    public static final DataType[] ENCODING = new DataType[] {
        DataType.mc_int,
        DataType.mc_string,
        DataType.mc_fixed_point,
        DataType.mc_fixed_point,
        DataType.mc_fixed_point,
        DataType.mc_byte,
        DataType.mc_byte,
        DataType.mc_short,
        DataType.mc_metadata
    };

    public final int eid;
    public final String name;
    public final double x;
    public final double y;
    public final double z;
    public final int yaw;
    public final int pitch;
    public final short item;
    public final byte[] metadata;

    public SpawnNamedEntityPacket(ChannelBuffer in) {
        super(ID);
        this.eid = Buffers.mc_int(in);
        this.name = Buffers.mc_string(in);
        this.x = Buffers.mc_fixed_point(in);
        this.y = Buffers.mc_fixed_point(in);
        this.z = Buffers.mc_fixed_point(in);
        this.yaw = Buffers.mc_byte(in);
        this.pitch = Buffers.mc_byte(in);
        this.item = Buffers.mc_short(in);
        this.metadata = Buffers.mc_metadata(in);
    }

    @Override()
    public String toString() {
        return Objects.toStringHelper(this)
            .add("eid", eid)
            .add("name", name)
            .add("x", x)
            .add("y", y)
            .add("z", z)
            .add("yaw", yaw)
            .add("pitch", pitch)
            .add("item", item)
            .add("metadata", metadata)
            .toString();
    }
}
