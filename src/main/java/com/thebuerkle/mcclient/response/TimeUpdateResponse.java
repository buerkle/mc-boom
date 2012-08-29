package com.thebuerkle.mcclient.response;

import com.thebuerkle.mcclient.model.DataType;

import com.google.common.base.Objects;

import org.apache.mina.core.buffer.IoBuffer;

public class TimeUpdateResponse extends Response {

    public static final int ID = 0x04;

    public static final DataType[] ENCODING = new DataType[] {
        DataType.mc_long,
    };

    public final long time;

    public TimeUpdateResponse(IoBuffer in) {
        time = mc_long(in);
    }

    @Override()
    public int getId() {
        return ID;
    }

    @Override()
    public String toString() {
        return Objects.toStringHelper(this).
            add("Time", time).
            toString();
    }
}
