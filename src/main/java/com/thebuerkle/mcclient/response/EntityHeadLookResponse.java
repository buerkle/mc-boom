package com.thebuerkle.mcclient.response;

import com.google.common.base.Objects;
import com.thebuerkle.mcclient.model.DataType;

import org.apache.mina.core.buffer.IoBuffer;

public class EntityHeadLookResponse extends Response {

    public static final int ID = 0x23;

    public static final DataType[] ENCODING = new DataType[] {
        DataType.mc_int,
        DataType.mc_unsigned_byte
    };

    public final int eid;
    public final int yaw;

    public EntityHeadLookResponse(IoBuffer in) {
        this.eid = mc_int(in);
        this.yaw = mc_unsigned_byte(in);
    }

    @Override()
    public int getId() {
        return ID;
    }

    @Override()
    public String toString() {
        return Objects.toStringHelper(this).
            add("Entity ID", eid).
            add("Yaw", yaw).
            toString();
    }
}
