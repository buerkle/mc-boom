package com.thebuerkle.mcclient.response;

import com.google.common.base.Objects;
import com.thebuerkle.mcclient.model.DataType;
import com.thebuerkle.mcclient.model.IntVec3;

import org.apache.mina.core.buffer.IoBuffer;

public class UpdateTileEntityResponse extends Response {

    public static final int ID = 0x84;

    public static final DataType[] ENCODING = new DataType[] {
        DataType.mc_int,
        DataType.mc_short,
        DataType.mc_int,
        DataType.mc_byte,
        DataType.mc_bytearray_2
    };

    public final IntVec3 position;
    public final int action;
    public final byte[] data;

    public UpdateTileEntityResponse(IoBuffer in) {
        this.position = new IntVec3(mc_int(in), mc_short(in), mc_int(in));
        this.action = mc_byte(in);
        this.data = mc_bytearray_2(in);
    }

    @Override()
    public int getId() {
        return ID;
    }

    @Override()
    public String toString() {
        return Objects.toStringHelper(this).
            add("Position", position).
            add("Action", action).
            add("Data length", data.length).
            toString();
    }
}
