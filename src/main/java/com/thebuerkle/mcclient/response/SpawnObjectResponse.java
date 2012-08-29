package com.thebuerkle.mcclient.response;

import com.google.common.base.Objects;
import com.thebuerkle.mcclient.model.DataType;
import com.thebuerkle.mcclient.model.IntVec3;

import org.apache.mina.core.buffer.IoBuffer;

public class SpawnObjectResponse extends Response {

    public static final int ID = 0x17;

    public static final DataType[] ENCODING = new DataType[] {
        DataType.mc_int,
        DataType.mc_byte,
        DataType.mc_int,
        DataType.mc_int,
        DataType.mc_int,
        DataType.mc_speed
    };

    public final int eid;
    public final int type;
    public final IntVec3 position;
    public final int data;
    public final IntVec3 velocity;

    public SpawnObjectResponse(IoBuffer in) {
        this.eid = mc_int(in);
        this.type = mc_byte(in);
        this.position = new IntVec3(mc_int(in), mc_int(in), mc_int(in));
        this.data = mc_int(in);
        if (this.data > 0) {
            this.velocity = new IntVec3(mc_short(in), mc_short(in), mc_short(in));
        }
        else {
            this.velocity = new IntVec3(0, 0, 0);
        }
    }

    @Override()
    public int getId() {
        return ID;
    }

    @Override()
    public String toString() {
        return Objects.toStringHelper(this).
            add("Entity ID", eid).
            add("Type", type).
            add("Position", position).
            add("Data", data).
            add("Velocity", velocity).
            toString();
    }
}
