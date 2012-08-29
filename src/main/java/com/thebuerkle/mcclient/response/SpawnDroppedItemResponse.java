package com.thebuerkle.mcclient.response;

import com.google.common.base.Objects;
import com.thebuerkle.mcclient.model.DataType;
import com.thebuerkle.mcclient.model.IntVec3;

import org.apache.mina.core.buffer.IoBuffer;

public class SpawnDroppedItemResponse extends Response {

    public static final int ID = 0x15;

    public static final DataType[] ENCODING = new DataType[] {
        DataType.mc_int,
        DataType.mc_short,
        DataType.mc_byte,
        DataType.mc_short,
        DataType.mc_int,
        DataType.mc_int,
        DataType.mc_int,
        DataType.mc_byte,
        DataType.mc_byte,
        DataType.mc_byte,
    };

    public final int eid;
    public final short item;
    public final int count;
    public final short data;
    public final IntVec3 position;
    public final int rotation;
    public final int pitch;
    public final int roll;

    public SpawnDroppedItemResponse(IoBuffer in) {
        this.eid = mc_int(in);
        this.item = mc_short(in);
        this.count = mc_byte(in);
        this.data = mc_short(in);
        this.position = new IntVec3(mc_int(in), mc_int(in), mc_int(in));
        this.rotation = mc_byte(in);
        this.pitch = mc_byte(in);
        this.roll = mc_byte(in);
    }

    @Override()
    public int getId() {
        return ID;
    }

    @Override()
    public String toString() {
        return Objects.toStringHelper(this).
            add("Entity ID", eid).
            add("Item ID", item).
            add("Item count", count).
            add("Data", data).
            add("Position", position).
            add("Rotation", rotation).
            add("Pitch", pitch).
            add("Roll", roll).
            toString();
    }
}
