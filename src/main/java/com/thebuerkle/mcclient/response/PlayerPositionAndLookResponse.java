package com.thebuerkle.mcclient.response;

import com.google.common.base.Objects;

import com.thebuerkle.mcclient.model.DataType;
import com.thebuerkle.mcclient.model.Vec3;

import org.apache.mina.core.buffer.IoBuffer;

public class PlayerPositionAndLookResponse extends Response {

    public static final int ID = 0x0D;

    public static final DataType[] ENCODING = new DataType[] {
        DataType.mc_double,
        DataType.mc_double,
        DataType.mc_double,
        DataType.mc_double,
        DataType.mc_float,
        DataType.mc_float,
        DataType.mc_bool
    };

    public final Vec3 position;
    public final float yaw;
    public final float pitch;
    public final double stance;
    public final boolean onGround;

    public PlayerPositionAndLookResponse(IoBuffer in) {
        double x = mc_double(in);
        double stance = mc_double(in);
        double y = mc_double(in);
        double z = mc_double(in);
        float yaw = mc_float(in);
        float pitch = mc_float(in);
        boolean onGround = mc_bool(in);

        this.position = new Vec3(x, y, z);
        this.yaw = yaw;
        this.pitch = pitch;
        this.stance = stance;
        this.onGround = onGround;
    }

    @Override()
    public int getId() {
        return ID;
    }

    @Override()
    public String toString() {
        return Objects.toStringHelper(this).
            add("Position", position).
            add("Stance", stance).
            add("Yaw", yaw).
            add("Pitch", pitch).
            add("On ground", onGround).
            toString();
    }
}
