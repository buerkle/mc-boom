package com.thebuerkle.mcclient.response;

import com.google.common.base.Objects;
import com.thebuerkle.mcclient.model.DataType;
import com.thebuerkle.mcclient.model.IntVec3;

import org.apache.mina.core.buffer.IoBuffer;

public class SpawnPaintingResponse extends Response {

    public static final int ID = 0x19;

    public static final DataType[] ENCODING = new DataType[] {
        DataType.mc_int,
        DataType.mc_string,
        DataType.mc_int,
        DataType.mc_int,
        DataType.mc_int,
        DataType.mc_int
    };

    public final int eid;
    public final String title;
    public final IntVec3 position;
    public final int direction;

    public SpawnPaintingResponse(IoBuffer in) {
        this.eid = mc_int(in);
        this.title = mc_string(in);
        this.position = new IntVec3(mc_int(in), mc_int(in), mc_int(in));
        this.direction = mc_int(in);
    }

    @Override()
    public int getId() {
        return ID;
    }

    @Override()
    public String toString() {
        return Objects.toStringHelper(this).
            add("Entity ID", eid).
            add("Title", title).
            add("Position", position).
            add("Direction", direction).
            toString();
    }
}
