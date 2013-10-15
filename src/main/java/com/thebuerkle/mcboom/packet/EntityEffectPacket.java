package com.thebuerkle.mcboom.packet;

import com.google.common.base.Objects;
import com.thebuerkle.mcboom.Buffers;
import com.thebuerkle.mcboom.DataType;

import org.jboss.netty.buffer.ChannelBuffer;

public class EntityEffectPacket extends Packet {

    public static final int ID = ENTITY_EFFECT;

    public static final DataType[] ENCODING = new DataType[] {
        DataType.mc_int,
        DataType.mc_byte,
        DataType.mc_byte,
        DataType.mc_short
    };

    public final int eid;
    public final int effect;
    public final int amplifier;
    public final short duration;

    public EntityEffectPacket(ChannelBuffer in) {
        super(ID);
        this.eid = Buffers.mc_int(in);
        this.effect = Buffers.mc_byte(in);
        this.amplifier = Buffers.mc_byte(in);
        this.duration = Buffers.mc_short(in);
    }

    @Override()
    public String toString() {
        return Objects.toStringHelper(this)
            .add("eid", eid)
            .add("effect", effect)
            .add("amplifier", amplifier)
            .add("duration", duration)
            .toString();
    }
}
