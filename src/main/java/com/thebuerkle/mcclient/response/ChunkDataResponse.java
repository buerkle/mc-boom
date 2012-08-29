package com.thebuerkle.mcclient.response;

import com.google.common.base.Objects;

import com.thebuerkle.mcclient.model.DataType;

import org.apache.mina.core.buffer.IoBuffer;

public class ChunkDataResponse extends Response {

    public static final int ID = 0x33;

    public static final DataType[] ENCODING = new DataType[] {
        DataType.mc_int,
        DataType.mc_int,
        DataType.mc_bool,
        DataType.mc_short,
        DataType.mc_short,
        DataType.mc_bytearray_4
    };

    public final int x;
    public final int z;
    public final boolean groundUp;
    public final short primaryBitMap;
    public final short addBitMap;
    public final byte[] chunk;

    public ChunkDataResponse(IoBuffer in) {
        this.x = mc_int(in);
        this.z = mc_int(in);
        this.groundUp = mc_bool(in);
        this.primaryBitMap = mc_short(in);
        this.addBitMap = mc_short(in);
        this.chunk = mc_bytearray_4(in);
    }

    @Override()
    public int getId() {
        return ID;
    }

    @Override()
    public String toString() {
        return Objects.toStringHelper(this).
            add("X", x).
            add("Z", z).
            add("Ground-up", groundUp).
            add("Primary bitmap", primaryBitMap).
            add("Add bitmap", addBitMap).
            add("Length", chunk.length).
            toString();
    }
}
