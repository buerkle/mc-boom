package com.thebuerkle.mcclient.response;

import com.google.common.base.Objects;

import com.thebuerkle.mcclient.model.DataType;
import com.thebuerkle.mcclient.model.GameMode;

import org.apache.mina.core.buffer.IoBuffer;

public class ChangeGameStateResponse extends Response {

    public static final int ID = 0x46;

    public static final DataType[] ENCODING = new DataType[] {
        DataType.mc_byte,
        DataType.mc_byte
    };

    public final int reason;
    public final GameMode mode;

    public ChangeGameStateResponse(IoBuffer in) {
        reason = mc_byte(in);
        mode = GameMode.fromValue(mc_byte(in));
    }

    @Override()
    public int getId() {
        return ID;
    }

    @Override()
    public String toString() {
        return Objects.toStringHelper(this).
            add("Reason", reason).
            add("Game mode", mode).
            toString();
    }
}
