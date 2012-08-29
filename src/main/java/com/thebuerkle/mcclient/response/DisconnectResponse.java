package com.thebuerkle.mcclient.response;

import com.google.common.base.Objects;
import com.thebuerkle.mcclient.model.DataType;

import org.apache.mina.core.buffer.IoBuffer;

public class DisconnectResponse extends Response {

    public static final int ID = 0xFF;

    public static final DataType[] ENCODING = new DataType[] {
        DataType.mc_string
    };

    public final String reason;

    public DisconnectResponse(IoBuffer in) {
        this.reason = mc_string(in);
    }

    @Override()
    public int getId() {
        return ID;
    }

    @Override()
    public String toString() {
        return Objects.toStringHelper(this).
            add("Reason", reason).
            toString();
    }
}
