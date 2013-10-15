package com.thebuerkle.mcboom.packet;

import com.google.common.base.Objects;
import com.thebuerkle.mcboom.Buffers;
import com.thebuerkle.mcboom.DataType;

import org.jboss.netty.buffer.ChannelBuffer;

public class ExplosionPacket extends Packet {

    public static final int ID = EXPLOSION;

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

    public final double x;
    public final double y;
    public final double z;
    public final float radius;
    public final byte[] records;
    public final float vx;
    public final float vy;
    public final float vz;

    public ExplosionPacket(ChannelBuffer in) {
        super(ID);
        this.x = Buffers.mc_double(in);
        this.y = Buffers.mc_double(in);
        this.z = Buffers.mc_double(in);
        this.radius = Buffers.mc_float(in);
        this.records = Buffers.mc_bytecoordarray(in);
        this.vx = Buffers.mc_float(in);
        this.vy = Buffers.mc_float(in);
        this.vz = Buffers.mc_float(in);
    }

    @Override()
    public String toString() {
        return Objects.toStringHelper(this)
            .add("x", x)
            .add("y", y)
            .add("z", z)
            .add("radius", radius)
            .add("records", records.length)
            .add("vx", vx)
            .add("vy", vy)
            .add("vz", vz)
            .toString();
    }
}
