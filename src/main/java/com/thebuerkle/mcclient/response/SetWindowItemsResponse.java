package com.thebuerkle.mcclient.response;

import com.google.common.base.Objects;

import com.thebuerkle.mcclient.model.DataType;
import com.thebuerkle.mcclient.model.Slot;

import org.apache.mina.core.buffer.IoBuffer;

public class SetWindowItemsResponse extends Response {

    public static final int ID = 0x68;

    public static final DataType[] ENCODING = new DataType[] {
        DataType.mc_byte,
        DataType.mc_slotarray
    };

    public final int window;
    public final Slot[] slots;

    public SetWindowItemsResponse(IoBuffer in) {
        this.window = mc_byte(in);
        this.slots = mc_slotarray(in);
    }

    @Override()
    public int getId() {
        return ID;
    }

    @Override()
    public String toString() {
        return Objects.toStringHelper(this).
            add("Window ID", window).
            add("Slots", slots.length).
            toString();
    }
}
