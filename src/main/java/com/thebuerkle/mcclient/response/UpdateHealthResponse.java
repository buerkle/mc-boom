package com.thebuerkle.mcclient.response;

import com.thebuerkle.mcclient.model.DataType;

import com.google.common.base.Objects;

import org.apache.mina.core.buffer.IoBuffer;

public class UpdateHealthResponse extends Response {

    public static final int ID = 0x08;

    public static final DataType[] ENCODING = new DataType[] {
        DataType.mc_short,
        DataType.mc_short,
        DataType.mc_float
    };

    public final short health;
    public final short food;
    public final float saturation;

    public UpdateHealthResponse(IoBuffer in) {
        this.health = mc_short(in);
        this.food = mc_short(in);
        this.saturation = mc_float(in);
    }

    @Override()
    public int getId() {
        return ID;
    }

    @Override()
    public String toString() {
        return Objects.toStringHelper(this).
            add("Health", health).
            add("Food", food).
            add("Food saturation", saturation).
            toString();
    }
}
