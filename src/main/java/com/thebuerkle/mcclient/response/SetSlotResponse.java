package com.thebuerkle.mcclient.response;

import com.google.common.base.Objects;

import com.thebuerkle.mcclient.model.DataType;
import com.thebuerkle.mcclient.model.Slot;

import org.apache.mina.core.buffer.IoBuffer;

public class SetSlotResponse extends Response {

    public static final int ID = 0x67;

    public static final DataType[] ENCODING = new DataType[] {
        DataType.mc_byte,
        DataType.mc_short,
        DataType.mc_slot
    };

    public final int window;
    public final short id;
    public final Slot slot;

    public SetSlotResponse(IoBuffer in) {
        this.window = mc_byte(in);
        this.id = mc_short(in);
        this.slot = Slot.decode(in);
    }

    @Override()
    public int getId() {
        return ID;
    }

    @Override()
    public String toString() {
        return Objects.toStringHelper(this).
            add("Window ID", window).
            add("Slot ID", id).
            toString();
    }
}
