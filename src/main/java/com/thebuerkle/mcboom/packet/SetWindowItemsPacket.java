package com.thebuerkle.mcboom.packet;

import com.google.common.base.Objects;
import com.thebuerkle.mcboom.Buffers;
import com.thebuerkle.mcboom.DataType;

import org.jboss.netty.buffer.ChannelBuffer;

public class SetWindowItemsPacket extends Packet {

    public static final int ID = SET_WINDOW_ITEMS;

    public static final DataType[] ENCODING = new DataType[] {
        DataType.mc_byte,
        DataType.mc_slotarray
    };

    public final int window;
    public final byte[] slots;

    public SetWindowItemsPacket(ChannelBuffer in) {
        super(ID);
        this.window = Buffers.mc_byte(in);
        this.slots = Buffers.mc_slotarray(in);
    }

    @Override()
    public String toString() {
        return Objects.toStringHelper(this)
            .add("Window ID", window)
            .add("Slots", slots.length)
            .toString();
    }
}
