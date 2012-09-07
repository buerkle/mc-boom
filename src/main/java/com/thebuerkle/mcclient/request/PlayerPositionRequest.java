package com.thebuerkle.mcclient.request;

import com.thebuerkle.mcclient.model.Vec3;

import org.apache.mina.core.buffer.IoBuffer;

public class PlayerPositionRequest extends Request {

    private final Vec3 _position;
    private final double _stance;
    private final boolean _onGround;

    public PlayerPositionRequest(Vec3 position, double stance, boolean onGround) {
        super(0x0B);

        _position = position;
        _stance = stance;
        _onGround = onGround;
    }

    @Override()
    public int getSize() {
        return 33;
    }

    @Override()
    public void write(IoBuffer out) {
        mc_double(out, _position.x);
        mc_double(out, _position.y);
        mc_double(out, _stance);
        mc_double(out, _position.z);
        mc_bool(out, _onGround);
    }
}
