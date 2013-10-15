package com.thebuerkle.mcboom.packet;

import com.google.common.base.Objects;
import com.thebuerkle.mcboom.Buffers;
import com.thebuerkle.mcboom.DataType;

import org.jboss.netty.buffer.ChannelBuffer;

public class SpawnObjectPacket extends Packet {

    public static final int ID = SPAWN_OBJECT;

    public static final DataType[] ENCODING = new DataType[] {
        DataType.mc_int,
        DataType.mc_byte,
        DataType.mc_fixed_point,
        DataType.mc_fixed_point,
        DataType.mc_fixed_point,
        DataType.mc_byte,
        DataType.mc_byte,
        DataType.mc_object
    };

    public final int eid;
    public final int type;
    public final double x;
    public final double y;
    public final double z;
    public final float pitch;
    public final float yaw;
    public final int thrower;
    public final int vx;
    public final int vy;
    public final int vz;

    public SpawnObjectPacket(ChannelBuffer in) {
        super(ID);
        this.eid = Buffers.mc_int(in);
        this.type = Buffers.mc_byte(in);
        this.x = Buffers.mc_fixed_point(in);
        this.y = Buffers.mc_fixed_point(in);
        this.z = Buffers.mc_fixed_point(in);
        this.pitch = Buffers.mc_byte(in);
        this.yaw = Buffers.mc_byte(in);
        this.thrower = Buffers.mc_int(in);

        if (this.thrower > 0) {
            this.vx = Buffers.mc_short(in);
            this.vy = Buffers.mc_short(in);
            this.vz = Buffers.mc_short(in);
        }
        else {
            this.vx = this.vy = this.vz = 0;
        }
    }

    @Override()
    public String toString() {
        return Objects.toStringHelper(this)
            .add("Entity ID", eid)
            .add("Type", type)
            .add("x", x)
            .add("y", y)
            .add("z", z)
            .add("Pitch", pitch)
            .add("Yaw", yaw)
            .add("Thrower", thrower)
            .add("vx", vx)
            .add("vy", vy)
            .add("vz", vz)
            .toString();
    }
}
