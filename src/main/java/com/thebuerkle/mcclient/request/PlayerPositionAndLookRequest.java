package com.thebuerkle.mcclient.request;

import com.google.common.base.Objects;
import com.thebuerkle.mcclient.model.DataType;
import com.thebuerkle.mcclient.model.Vec3;

import org.apache.mina.core.buffer.IoBuffer;

public class PlayerPositionAndLookRequest extends Request {

    public static final int ID = 0x0D;

    private static final DataType[] ENCODING = new DataType[] {
        DataType.mc_double,
            DataType.mc_double,
            DataType.mc_double,
            DataType.mc_double,
            DataType.mc_float,
            DataType.mc_float,
            DataType.mc_bool
    };

    private final Vec3 _position;
    private final double _stance;
    private final float _yaw;
    private final float _pitch;
    private final boolean _onGround;

    public PlayerPositionAndLookRequest(Vec3 position, double stance, float yaw,
                                        float pitch, boolean onGround) {
        super(0x0D);

        _position = position;
        _stance = stance;
        _yaw = yaw;
        _pitch = pitch;
        _onGround = onGround;
    }

    @Override()
    public void write(IoBuffer out) {
        mc_double(out, _position.x);
        mc_double(out, _position.y);
        mc_double(out, _stance);
        mc_double(out, _position.z);
        mc_float(out, _yaw);
        mc_float(out, _pitch);
        mc_bool(out, _onGround);
    }


    @Override()
    public int getId() {
        return ID;
    }
}
