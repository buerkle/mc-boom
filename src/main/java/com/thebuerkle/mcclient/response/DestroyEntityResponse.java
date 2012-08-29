package com.thebuerkle.mcclient.response;

import com.google.common.base.Objects;
import com.thebuerkle.mcclient.model.DataType;

import org.apache.mina.core.buffer.IoBuffer;

public class DestroyEntityResponse extends Response {

    public static final int ID = 0x1D;

    public static final DataType[] ENCODING = new DataType[] {
        DataType.mc_intarray_1
    };

    public final int[] eids;

    public DestroyEntityResponse(IoBuffer in) {
        this.eids = mc_intarray_1(in);
    }

    @Override()
    public int getId() {
        return ID;
    }

    @Override()
    public String toString() {
        return Objects.toStringHelper(this).
            add("Count", eids.length).
            toString();
    }
}
