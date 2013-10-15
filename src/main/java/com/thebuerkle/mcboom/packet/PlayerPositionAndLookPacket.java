package com.thebuerkle.mcboom.packet;

import com.google.common.base.Objects;
import com.thebuerkle.mcboom.Buffers;
import com.thebuerkle.mcboom.DataType;

import org.jboss.netty.buffer.ChannelBuffer;

public class PlayerPositionAndLookPacket extends Packet {

    public static final int ID = PLAYER_POSITION_AND_LOOK;

    public static final DataType[] ENCODING = new DataType[] {
        DataType.mc_double,
        DataType.mc_double,
        DataType.mc_double,
        DataType.mc_double,
        DataType.mc_float,
        DataType.mc_float,
        DataType.mc_bool
    };

    public final double x;
    public final double y;
    public final double z;
    public final float yaw;
    public final float pitch;
    public final double stance;
    public final boolean onGround;

    public PlayerPositionAndLookPacket(ChannelBuffer in) {
        super(ID);
        this.x = Buffers.mc_double(in);
        this.stance = Buffers.mc_double(in);
        this.y = Buffers.mc_double(in);
        this.z = Buffers.mc_double(in);
        this.yaw = Buffers.mc_float(in);
        this.pitch = Buffers.mc_float(in);
        this.onGround = Buffers.mc_bool(in);
    }

    public PlayerPositionAndLookPacket(double x, double y, double z,
                                       double stance, float yaw, float pitch,
                                       boolean onGround) {
        super(ID);

        this.x = x;
        this.y = y;
        this.z = z;
        this.stance = stance;
        this.yaw = yaw;
        this.pitch = pitch;
        this.onGround = onGround;
    }

    @Override()
    public int size() {
        return 41;
    }

    @Override()
    public void write(ChannelBuffer out) {
        Buffers.mc_double(out, x);
        Buffers.mc_double(out, y);
        Buffers.mc_double(out, stance);
        Buffers.mc_double(out, z);

        Buffers.mc_float(out, yaw);
        Buffers.mc_float(out, pitch);
        Buffers.mc_bool(out, onGround);
    }

    @Override()
    public String toString() {
        return Objects.toStringHelper(this)
            .add("ID", id)
            .add("x", x)
            .add("y", y)
            .add("z", z)
            .add("Stance", stance)
            .add("Yaw", yaw)
            .add("Pitch", pitch)
            .add("On ground", onGround)
            .toString();
    }
}
