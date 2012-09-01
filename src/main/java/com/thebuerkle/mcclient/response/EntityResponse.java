package com.thebuerkle.mcclient.response;

import com.google.common.base.Objects;
import com.thebuerkle.mcclient.model.DataType;

import org.apache.mina.core.buffer.IoBuffer;

public class EntityResponse extends Response {

    public static final int ID = 0x1E;

    public static final DataType[] ENCODING = new DataType[] {
        DataType.mc_int
    };

    public final int eid;

    public EntityResponse(IoBuffer in) {
        this.eid = mc_int(in);
    }

    @Override()
    public int getId() {
        return ID;
    }

    @Override()
    public String toString() {
        return Objects.toStringHelper(this).
            add("Entity ID", eid).
            toString();
    }
}
