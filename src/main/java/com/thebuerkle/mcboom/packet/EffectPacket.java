package com.thebuerkle.mcboom.packet;

import com.google.common.base.Objects;
import com.thebuerkle.mcboom.Buffers;
import com.thebuerkle.mcboom.DataType;

import org.jboss.netty.buffer.ChannelBuffer;

public class EffectPacket extends Packet {

    public static final int ID = EFFECT;

    public static final DataType[] ENCODING = new DataType[] {
        DataType.mc_int,
        DataType.mc_int,
        DataType.mc_byte,
        DataType.mc_int,
        DataType.mc_int,
        DataType.mc_bool
    };

    public final int id;
    public final int x;
    public final int y;
    public final int z;
    public final int data;
    public final boolean noVolumeDecrease;

    public EffectPacket(ChannelBuffer in) {
        super(ID);
        this.id = Buffers.mc_int(in);
        this.x = Buffers.mc_int(in);
        this.y = Buffers.mc_byte(in);
        this.z = Buffers.mc_int(in);
        this.data = Buffers.mc_int(in);
        this.noVolumeDecrease = Buffers.mc_bool(in);
    }

    @Override()
    public String toString() {
        return Objects.toStringHelper(this)
            .add("id", id)
            .add("x", x)
            .add("y", y)
            .add("z", z)
            .add("data", data)
            .toString();
    }
}
