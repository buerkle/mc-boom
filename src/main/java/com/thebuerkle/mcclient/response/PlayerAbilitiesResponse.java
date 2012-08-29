package com.thebuerkle.mcclient.response;

import com.google.common.base.Objects;

import com.thebuerkle.mcclient.model.DataType;

import org.apache.mina.core.buffer.IoBuffer;

public class PlayerAbilitiesResponse extends Response {

    public static final int ID = 0xCA;

    public static final DataType[] ENCODING = new DataType[] {
        DataType.mc_byte,
            DataType.mc_byte,
            DataType.mc_byte
    };

    public final boolean god;
    public final boolean flying;
    public final boolean canFly;
    public final boolean creative;
    public final int flyingVelocity;
    public final int walkingVelocity;

    public PlayerAbilitiesResponse(IoBuffer in) {
        int flags = mc_byte(in);

        this.god = (flags & 0x01) == 0x01;
        this.flying = (flags & 0x02) == 0x02;
        this.canFly = (flags & 0x04) == 0x04;
        this.creative = (flags & 0x08) == 0x08;
        this.flyingVelocity = mc_byte(in);
        this.walkingVelocity = mc_byte(in);
    }

    @Override()
    public int getId() {
        return ID;
    }

    @Override()
    public String toString() {
        return Objects.toStringHelper(this).
            add("God mode", god).
            add("Flying", flying).
            add("Can fly", canFly).
            add("Creative", creative).
            add("Flying speed", flyingVelocity).
            add("Walking speed", walkingVelocity).
            toString();
    }
}
