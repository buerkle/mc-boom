package com.thebuerkle.mcclient.response;

import com.google.common.base.Objects;
import com.thebuerkle.mcclient.model.DataType;

import org.apache.mina.core.buffer.IoBuffer;

public class CollectItemResponse extends Response {

    public static final int ID = 0x16;

    public static final DataType[] ENCODING = new DataType[] {
        DataType.mc_int,
        DataType.mc_int
    };

    public final int collected;
    public final int collector;

    public CollectItemResponse(IoBuffer in) {
        this.collected = mc_int(in);
        this.collector = mc_int(in);
    }

    @Override()
    public int getId() {
        return ID;
    }

    @Override()
    public String toString() {
        return Objects.toStringHelper(this).
            add("Collected EID", collected).
            add("Collector EID", collector).
            toString();
    }
}
