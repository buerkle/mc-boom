package com.thebuerkle.mcclient.response;

import com.google.common.base.Objects;
import com.thebuerkle.mcclient.model.DataType;

import org.apache.mina.core.buffer.IoBuffer;

public class IncrementStatisticResponse extends Response {

    public static final int ID = 0xC8;

    public static final DataType[] ENCODING = new DataType[] {
        DataType.mc_int,
        DataType.mc_byte
    };

    public final int id;
    public final int amount;

    public IncrementStatisticResponse(IoBuffer in) {
        this.id = mc_int(in);
        this.amount = mc_byte(in);
    }

    @Override()
    public int getId() {
        return ID;
    }

    @Override()
    public String toString() {
        return Objects.toStringHelper(this).
            add("Statistic ID", id).
            add("Amount", amount).
            toString();
    }
}
