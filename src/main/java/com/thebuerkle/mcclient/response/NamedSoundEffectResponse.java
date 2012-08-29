package com.thebuerkle.mcclient.response;

import com.google.common.base.Objects;

import com.thebuerkle.mcclient.model.DataType;
import com.thebuerkle.mcclient.model.IntVec3;

import org.apache.mina.core.buffer.IoBuffer;

public class NamedSoundEffectResponse extends Response {

    public static final int ID = 0x3E;

    public static final DataType[] ENCODING = new DataType[] {
        DataType.mc_string,
        DataType.mc_int,
        DataType.mc_int,
        DataType.mc_int,
        DataType.mc_float,
        DataType.mc_byte
    };

    public final String name;
    public final IntVec3 position;
    public final float volume;
    public final int pitch;

    public NamedSoundEffectResponse(IoBuffer in) {
        this.name = mc_string(in);
        this.position = new IntVec3(mc_int(in), mc_int(in), mc_int(in));
        this.volume = mc_float(in);
        this.pitch = mc_byte(in);
    }

    @Override()
    public int getId() {
        return ID;
    }

    @Override()
    public String toString() {
        return Objects.toStringHelper(this).
            add("Name", name).
            add("Position", position).
            add("Volume", volume).
            add("Pitch", pitch).
            toString();
    }
}
