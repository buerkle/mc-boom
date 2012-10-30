package com.thebuerkle.mcclient.response;

import com.google.common.base.Objects;

import com.thebuerkle.mcclient.model.DataType;

import org.apache.mina.core.buffer.IoBuffer;

public class MapChunkBulkResponse extends Response {

    public static final int ID = 0x38;

    public static final DataType[] ENCODING = new DataType[] {
        DataType.mc_chunk_bulk
    };

    public final short count;
    public final byte[] chunks;
    public final byte[] metadata;

    public MapChunkBulkResponse(IoBuffer in) {
        this.count = mc_short(in);
        this.chunks = mc_bytearray_4(in);
        this.metadata = mc_bytearray(in, 12 * this.count);
    }

    @Override()
    public int getId() {
        return ID;
    }

    @Override()
    public String toString() {
        return Objects.toStringHelper(this).
            add("Chunk count", this.count).
            add("Chunk data length", chunks.length).
            toString();
    }
}
