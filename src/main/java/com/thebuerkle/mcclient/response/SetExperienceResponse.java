package com.thebuerkle.mcclient.response;

import com.google.common.base.Objects;

import com.thebuerkle.mcclient.model.DataType;

import org.apache.mina.core.buffer.IoBuffer;

public class SetExperienceResponse extends Response {

    public static final int ID = 0x2B;

    public static final DataType[] ENCODING = new DataType[] {
        DataType.mc_float,
            DataType.mc_short,
            DataType.mc_short
    };

    public final float experinceBar;
    public final short level;
    public final short totalExperience;

    public SetExperienceResponse(IoBuffer in) {
        this.experinceBar = mc_float(in);
        this.level = mc_short(in);
        this.totalExperience = mc_short(in);
    }

    @Override()
    public int getId() {
        return ID;
    }

    @Override()
    public String toString() {
        return Objects.toStringHelper(this).
            add("Experience bar", experinceBar).
            add("Level", level).
            add("Total experience", totalExperience).
            toString();
    }
}
