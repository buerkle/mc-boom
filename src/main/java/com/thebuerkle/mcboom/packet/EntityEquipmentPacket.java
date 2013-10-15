package com.thebuerkle.mcboom.packet;

import com.google.common.base.Objects;
import com.thebuerkle.mcboom.Buffers;
import com.thebuerkle.mcboom.DataType;

import org.jboss.netty.buffer.ChannelBuffer;

public class EntityEquipmentPacket extends Packet {

    public static final int ID = ENTITY_EQUIPMENT;

    public static final DataType[] ENCODING = new DataType[] {
        DataType.mc_int,
        DataType.mc_short,
        DataType.mc_slot
    };

    public final int eid;
    public final short equipmentSlot;
    public final byte[] slot;

    public EntityEquipmentPacket(ChannelBuffer in) {
        super(ID);
        this.eid = Buffers.mc_int(in);
        this.equipmentSlot = Buffers.mc_short(in);
        this.slot = Buffers.mc_slot(in);
    }

    @Override()
    public String toString() {
        return Objects.toStringHelper(this)
            .add("Entity ID", eid)
            .add("Equipment slot", equipmentSlot)
            .add("Slot", slot.length)
            .toString();
    }
}
