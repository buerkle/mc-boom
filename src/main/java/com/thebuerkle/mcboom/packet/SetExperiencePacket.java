package com.thebuerkle.mcboom.packet;

import com.google.common.base.Objects;
import com.thebuerkle.mcboom.Buffers;
import com.thebuerkle.mcboom.DataType;

import org.jboss.netty.buffer.ChannelBuffer;

public class SetExperiencePacket extends Packet {

    public static final int ID = SET_EXPERIENCE;

    public static final DataType[] ENCODING = new DataType[] {
        DataType.mc_float,
        DataType.mc_short,
        DataType.mc_short
    };

    public final float experinceBar;
    public final short level;
    public final short totalExperience;

    public SetExperiencePacket(ChannelBuffer in) {
        super(ID);
        this.experinceBar = Buffers.mc_float(in);
        this.level = Buffers.mc_short(in);
        this.totalExperience = Buffers.mc_short(in);
    }

    @Override()
    public String toString() {
        return Objects.toStringHelper(this)
            .add("experience bar", experinceBar)
            .add("level", level)
            .add("total experience", totalExperience)
            .toString();
    }
}
