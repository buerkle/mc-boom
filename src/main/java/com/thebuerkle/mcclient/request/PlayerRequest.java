package com.thebuerkle.mcclient.request;

import com.google.common.base.Objects;

import org.apache.mina.core.buffer.IoBuffer;

public class PlayerRequest extends Request {

    private final boolean _onGround;

    public PlayerRequest(boolean onGround) {
        super(0x0A);

        _onGround = onGround;
    }

    @Override()
    public void write(IoBuffer out) {
        mc_bool(out, _onGround);
    }
}
