package com.thebuerkle.mcclient.response;

import com.google.common.base.Objects;
import com.thebuerkle.mcclient.model.DataType;
import com.thebuerkle.mcclient.model.Slot;

import org.apache.mina.core.buffer.IoBuffer;

public class EntityEquipmentResponse extends Response {

    public static final int ID = 0x05;

    public static final DataType[] ENCODING = new DataType[] {
        DataType.mc_int,
        DataType.mc_short,
        DataType.mc_slot
    };

    public final int eid;
    public final short equipmentSlot;
    public final Slot slot;

    public EntityEquipmentResponse(IoBuffer in) {
        this.eid = mc_int(in);
        this.equipmentSlot = mc_short(in);
        this.slot = mc_slot(in);
    }

    @Override()
    public int getId() {
        return ID;
    }

    @Override()
    public String toString() {
        return Objects.toStringHelper(this).
            add("Entity ID", eid).
            add("Equipment slot", equipmentSlot).
            add("Slot", slot).
            toString();
    }
}
