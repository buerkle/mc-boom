package com.thebuerkle.mcclient.response;

import com.google.common.base.Objects;
import com.thebuerkle.mcclient.model.DataType;
import com.thebuerkle.mcclient.model.IntVec3;

import org.apache.mina.core.buffer.IoBuffer;

public class ThunderboltResponse extends Response {

    public static final int ID = 0x47;

    public static final DataType[] ENCODING = new DataType[] {
        DataType.mc_int,
        DataType.mc_bool,
        DataType.mc_int,
        DataType.mc_int,
        DataType.mc_int
    };

    public final int eid;
    public final boolean unknown;
    public final IntVec3 position;

    public ThunderboltResponse(IoBuffer in) {
        this.eid = mc_int(in);
        this.unknown = mc_bool(in);
        this.position = new IntVec3(mc_int(in), mc_int(in), mc_int(in));
    }

    @Override()
    public int getId() {
        return ID;
    }

    @Override()
    public String toString() {
        return Objects.toStringHelper(this).
            add("Entity ID", eid).
            add("Position", position).
            toString();
    }
}
