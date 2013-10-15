package com.thebuerkle.mcboom.packet;

import com.google.common.base.Objects;
import com.thebuerkle.mcboom.Buffers;
import com.thebuerkle.mcboom.DataType;

import org.jboss.netty.buffer.ChannelBuffer;

public class RemoveEntityEffectPacket extends Packet {

    public static final int ID = REMOVE_ENTITY_EFFECT;

    public static final DataType[] ENCODING = new DataType[] {
        DataType.mc_int,
        DataType.mc_byte
    };

    public final int eid;
    public final int effect;

    public RemoveEntityEffectPacket(ChannelBuffer in) {
        super(ID);
        this.eid = Buffers.mc_int(in);
        this.effect = Buffers.mc_byte(in);
    }

    @Override()
    public String toString() {
        return Objects.toStringHelper(this)
            .add("eid", eid)
            .add("effect", effect)
            .toString();
    }
}
