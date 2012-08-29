package com.thebuerkle.mcclient.response;

import com.google.common.base.Objects;
import com.thebuerkle.mcclient.model.DataType;

import org.apache.mina.core.buffer.IoBuffer;

public class AttachEntityResponse extends Response {

    public static final int ID = 0x27;

    public static final DataType[] ENCODING = new DataType[] {
        DataType.mc_int,
        DataType.mc_int
    };

    public final int eid;
    public final int vehicle;

    public AttachEntityResponse(IoBuffer in) {
        this.eid = mc_int(in);
        this.vehicle = mc_int(in);
    }

    @Override()
    public int getId() {
        return ID;
    }

    @Override()
    public String toString() {
        return Objects.toStringHelper(this).
            add("Entity ID", eid).
            add("Vehicle ID", vehicle).
            toString();
    }
}
