package com.thebuerkle.mcclient.response;

import com.google.common.base.Objects;
import com.thebuerkle.mcclient.model.DataType;
import com.thebuerkle.mcclient.model.IntVec3;

import org.apache.mina.core.buffer.IoBuffer;

public class BlockActionResponse extends Response {

    public static final int ID = 0x36;

    public static final DataType[] ENCODING = new DataType[] {
        DataType.mc_int,
        DataType.mc_short,
        DataType.mc_int,
        DataType.mc_byte,
        DataType.mc_byte,
        DataType.mc_short
    };

    public final IntVec3 coord;
    public final int byte1;
    public final int byte2;
    public final short id;

    public BlockActionResponse(IoBuffer in) {
        this.coord = new IntVec3(mc_int(in), mc_short(in), mc_int(in));
        this.byte1 = mc_byte(in);
        this.byte2 = mc_byte(in);
        this.id = mc_short(in);
    }

    @Override()
    public int getId() {
        return ID;
    }

    @Override()
    public String toString() {
        return Objects.toStringHelper(this).
            add("Coordinate", coord).
            add("Byte1", byte1).
            add("Byte2", byte2).
            add("Block ID", id).
            toString();
    }
}
