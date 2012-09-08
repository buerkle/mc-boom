package com.thebuerkle.mcclient.response;

import com.google.common.base.Objects;
import com.thebuerkle.mcclient.model.DataType;
import com.thebuerkle.mcclient.model.IntVec3;

import org.apache.mina.core.buffer.IoBuffer;

public class RemoveEntityEffectResponse extends Response {

    public static final int ID = 0x2A;

    public static final DataType[] ENCODING = new DataType[] {
        DataType.mc_int,
        DataType.mc_byte
    };

    public final int eid;
    public final int effectId;

    public RemoveEntityEffectResponse(IoBuffer in) {
        this.eid = mc_int(in);
        this.effectId = mc_byte(in);
    }

    @Override()
    public int getId() {
        return ID;
    }

    @Override()
    public String toString() {
        return Objects.toStringHelper(this).
            add("Entity ID", eid).
            add("Effect ID", effectId).
            toString();
    }
}
