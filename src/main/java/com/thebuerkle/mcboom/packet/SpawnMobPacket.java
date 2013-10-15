package com.thebuerkle.mcboom.packet;

import com.google.common.base.Objects;
import com.thebuerkle.mcboom.Buffers;
import com.thebuerkle.mcboom.DataType;

import org.jboss.netty.buffer.ChannelBuffer;

public class SpawnMobPacket extends Packet {

    public static final int ID = Packet.SPAWN_MOB;

    public static final DataType[] ENCODING = new DataType[] {
        DataType.mc_int,
        DataType.mc_byte,
        DataType.mc_fixed_point,
        DataType.mc_fixed_point,
        DataType.mc_fixed_point,
        DataType.mc_byte,
        DataType.mc_byte,
        DataType.mc_byte,
        DataType.mc_short,
        DataType.mc_short,
        DataType.mc_short,
        DataType.mc_metadata
    };

    public final int eid;
    public final int type;
    public final double x;
    public final double y;
    public final double z;
    public final int yaw;
    public final int pitch;
    public final int headYaw;
    public final int vx;
    public final int vy;
    public final int vz;
    public final byte[] metadata;

    public SpawnMobPacket(ChannelBuffer in) {
        super(ID);
        this.eid = Buffers.mc_int(in);
        this.type = Buffers.mc_byte(in);
        this.x = Buffers.mc_fixed_point(in);
        this.y = Buffers.mc_fixed_point(in);
        this.z = Buffers.mc_fixed_point(in);
        this.yaw = Buffers.mc_byte(in);
        this.pitch = Buffers.mc_byte(in);
        this.headYaw = Buffers.mc_byte(in);
        this.vx = Buffers.mc_short(in);
        this.vy = Buffers.mc_short(in);
        this.vz = Buffers.mc_short(in);
        this.metadata = Buffers.mc_metadata(in);
    }

    public String toString() {
        return Objects.toStringHelper(this)
            .add("Entity ID", eid)
            .add("Type", type)
            .add("x", x)
            .add("y", y)
            .add("z", z)
            .add("Yaw", yaw)
            .add("Pitch", pitch)
            .add("Head yaw", headYaw)
            .add("vx", vx)
            .add("vy", vy)
            .add("vz", vz)
            .add("Metadata", metadata.length)
            .toString();
    }
}
