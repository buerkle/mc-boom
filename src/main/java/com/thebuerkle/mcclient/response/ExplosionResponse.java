package com.thebuerkle.mcclient.response;

import com.google.common.base.Objects;
import com.thebuerkle.mcclient.model.DataType;
import com.thebuerkle.mcclient.model.Vec3;

import org.apache.mina.core.buffer.IoBuffer;

public class ExplosionResponse extends Response {

    public static final int ID = 0x3C;

    public static final DataType[] ENCODING = new DataType[] {
        DataType.mc_double,
            DataType.mc_double,
            DataType.mc_double,
            DataType.mc_float,
            DataType.mc_bytecoordarray,
            DataType.mc_float,
            DataType.mc_float,
            DataType.mc_float
    };

    public final Vec3 position;
    public final float radius;
    public final byte[] records;

    public ExplosionResponse(IoBuffer in) {
        this.position = new Vec3(mc_double(in), mc_double(in), mc_double(in));
        this.radius = mc_float(in);
        this.records = mc_bytecoordarray(in);

        float unknown1 = mc_float(in);
        float unknown2 = mc_float(in);
        float unknown3 = mc_float(in);
    }

    @Override()
    public int getId() {
        return ID;
    }

    @Override()
    public String toString() {
        return Objects.toStringHelper(this).
            add("Position", position).
            add("Radius", radius).
            add("Record count", records.length).
            toString();
    }
}
