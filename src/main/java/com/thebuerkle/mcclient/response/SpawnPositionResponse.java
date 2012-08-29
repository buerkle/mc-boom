package com.thebuerkle.mcclient.response;

import com.google.common.base.Objects;

import com.thebuerkle.mcclient.model.DataType;
import com.thebuerkle.mcclient.model.IntVec3;

import org.apache.mina.core.buffer.IoBuffer;

public class SpawnPositionResponse extends Response {

    public static final int ID = 0x06;

    public static final DataType[] ENCODING = new DataType[] {
        DataType.mc_int,
            DataType.mc_int,
            DataType.mc_int
    };

    public final IntVec3 position;

    public SpawnPositionResponse(IoBuffer in) {
        this.position = new IntVec3(mc_int(in), mc_int(in), mc_int(in));
    }

    @Override()
    public int getId() {
        return ID;
    }

    @Override()
    public String toString() {
        return Objects.toStringHelper(this).
            add("Position", position).
            toString();
    }
}
