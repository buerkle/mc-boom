package com.thebuerkle.mcboom.packet;

import com.google.common.base.Objects;
import com.thebuerkle.mcboom.Buffers;
import com.thebuerkle.mcboom.DataType;

import org.jboss.netty.buffer.ChannelBuffer;

public class SetSlotPacket extends Packet {

    public static final int ID = SET_SLOT;

    public static final DataType[] ENCODING = new DataType[] {
        DataType.mc_byte,
        DataType.mc_short,
        DataType.mc_slot
    };

    public final int window;
    public final short id;
    public final byte[] slot;

    public SetSlotPacket(ChannelBuffer in) {
        super(ID);
        this.window = Buffers.mc_byte(in);
        this.id = Buffers.mc_short(in);
        this.slot = Buffers.mc_slot(in);
    }

    @Override()
    public String toString() {
        return Objects.toStringHelper(this)
            .add("ID", id)
            .add("Window ID", window)
            .add("Slot ID", id)
            .toString();
    }
}
