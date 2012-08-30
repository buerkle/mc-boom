package com.thebuerkle.mcclient.response;

import com.google.common.base.Objects;
import com.thebuerkle.mcclient.model.DataType;
import com.thebuerkle.mcclient.model.IntVec3;

import org.apache.mina.core.buffer.IoBuffer;

public class SpawnExperienceOrb extends Response {

    public static final int ID = 0x1A;

    public static final DataType[] ENCODING = new DataType[] {
        DataType.mc_int,
        DataType.mc_int,
        DataType.mc_int,
        DataType.mc_int,
        DataType.mc_short
    };

    public final int eid;
    public final IntVec3 position;
    public final short count;

    public SpawnExperienceOrb(IoBuffer in) {
        this.eid = mc_int(in);
        this.position = new IntVec3(mc_int(in), mc_int(in), mc_int(in));
        this.count = mc_short(in);
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
            add("Count", count).
            toString();
    }
}