package com.thebuerkle.mcclient.response;

import com.google.common.base.Objects;
import com.thebuerkle.mcclient.model.DataType;
import com.thebuerkle.mcclient.model.IntVec3;

import org.apache.mina.core.buffer.IoBuffer;

public class EntityEffectResponse extends Response {

    public static final int ID = 0x29;

    public static final DataType[] ENCODING = new DataType[] {
        DataType.mc_int,
        DataType.mc_byte,
        DataType.mc_byte,
        DataType.mc_short
    };

    public final int eid;
    public final int effectId;
    public final int amplifier;
    public final short duration;

    public EntityEffectResponse(IoBuffer in) {
        this.eid = mc_int(in);
        this.effectId = mc_byte(in);
        this.amplifier = mc_byte(in);
        this.duration = mc_short(in);
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
            add("Amplifier", amplifier).
            add("Duration", duration).
            toString();
    }
}
