package com.thebuerkle.mcclient.response;

import com.google.common.base.Objects;

import com.thebuerkle.mcclient.model.DataType;

import org.apache.mina.core.buffer.IoBuffer;

public class KeepAliveResponse extends Response {

    public static final int ID = 0x00;

    public static final DataType[] ENCODING = new DataType[] {
        DataType.mc_int
    };

    public int id;

    public KeepAliveResponse(IoBuffer in) {
        this.id = mc_int(in);
    }

    @Override()
    public int getId() {
        return ID;
    }

    @Override()
    public String toString() {
        return Objects.toStringHelper(this).
            add("ID", id).
            toString();
    }

}
