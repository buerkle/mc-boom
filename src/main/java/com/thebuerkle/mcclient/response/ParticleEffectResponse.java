package com.thebuerkle.mcclient.response;

import com.google.common.base.Objects;
import com.thebuerkle.mcclient.model.DataType;
import com.thebuerkle.mcclient.model.IntVec3;

import org.apache.mina.core.buffer.IoBuffer;

public class ParticleEffectResponse extends Response {

    public static final int ID = 0x3D;

    public static final DataType[] ENCODING = new DataType[] {
        DataType.mc_int,
        DataType.mc_int,
        DataType.mc_byte,
        DataType.mc_int,
        DataType.mc_int,
        DataType.mc_bool
    };

    public final int id;
    public final IntVec3 position;
    public final int data;
    public final boolean noVolumeDecrease;

    public ParticleEffectResponse(IoBuffer in) {
        this.id = mc_int(in);
        this.position = new IntVec3(mc_int(in), mc_byte(in), mc_int(in));
        this.data = mc_int(in);
        this.noVolumeDecrease = mc_bool(in);
    }

    @Override()
    public int getId() {
        return ID;
    }

    @Override()
    public String toString() {
        return Objects.toStringHelper(this).
            add("ID", id).
            add("Position", position).
            add("Data", data).
            toString();
    }
}
