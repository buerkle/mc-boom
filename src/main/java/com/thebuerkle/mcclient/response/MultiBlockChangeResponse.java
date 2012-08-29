package com.thebuerkle.mcclient.response;

import com.google.common.base.Objects;

import com.thebuerkle.mcclient.model.DataType;

import org.apache.mina.core.buffer.IoBuffer;

public class MultiBlockChangeResponse extends Response {

    public static final int ID = 0x34;

    public static final DataType[] ENCODING = new DataType[] {
        DataType.mc_int,
        DataType.mc_int,
        DataType.mc_short,
        DataType.mc_bytearray_4
    };

    public final int x;
    public final int z;
    public final int count;
    public final byte[] data;

    public MultiBlockChangeResponse(IoBuffer in) {
        this.x = mc_int(in);
        this.z = mc_int(in);
        this.count = mc_short(in);
        this.data = mc_bytearray_4(in);
    }

    @Override()
    public int getId() {
        return ID;
    }

    @Override()
    public String toString() {
        return Objects.toStringHelper(this).
            add("X", x).
            add("Z", z).
            add("Count", count).
            add("Data length", data.length).
            toString();
    }
}
