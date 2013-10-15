package com.thebuerkle.mcboom.packet;

import com.google.common.base.Objects;
import com.thebuerkle.mcboom.Buffers;
import com.thebuerkle.mcboom.DataType;

import org.jboss.netty.buffer.ChannelBuffer;

public class HeldItemChangePacket extends Packet {

    public static final int ID = HELD_ITEM_CHANGE;

    public static final DataType[] ENCODING = new DataType[] {
        DataType.mc_short
    };

    public final short slot;

    public HeldItemChangePacket(ChannelBuffer in) {
        super(ID);
        this.slot = Buffers.mc_short(in);
    }

    @Override()
    public String toString() {
        return Objects.toStringHelper(this)
            .add("ID", id)
            .add("Slot ID", slot)
            .toString();
    }
}
