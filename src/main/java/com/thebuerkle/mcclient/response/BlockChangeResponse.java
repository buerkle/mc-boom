package com.thebuerkle.mcclient.response;

import com.google.common.base.Objects;
import com.thebuerkle.mcclient.model.DataType;
import com.thebuerkle.mcclient.model.IntVec3;

import org.apache.mina.core.buffer.IoBuffer;

public class BlockChangeResponse extends Response {

    public static final int ID = 0x35;

    public static final DataType[] ENCODING = new DataType[] {
        DataType.mc_int,
        DataType.mc_byte,
        DataType.mc_int,
        DataType.mc_short,
        DataType.mc_byte
    };

    public final IntVec3 coord;
    public final short type;
    public final int metadata;

    public BlockChangeResponse(IoBuffer in) {
        this.coord = new IntVec3(mc_int(in), mc_byte(in), mc_int(in));
        this.type = mc_short(in);
        this.metadata = mc_byte(in);
    }

    @Override()
    public int getId() {
        return ID;
    }

    @Override()
    public String toString() {
        return Objects.toStringHelper(this).
            add("Coordinate", coord).
            add("Type", type).
            add("Metadata", metadata).
            toString();
    }
}
