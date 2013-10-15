package com.thebuerkle.mcboom.packet;

import com.google.common.base.Objects;
import com.thebuerkle.mcboom.Buffers;
import com.thebuerkle.mcboom.DataType;

import org.jboss.netty.buffer.ChannelBuffer;

public class UpdateTileEntityPacket extends Packet {

    public static final int ID = UPDATE_TILE_ENTITY;

    public static final DataType[] ENCODING = new DataType[] {
        DataType.mc_int,
        DataType.mc_short,
        DataType.mc_int,
        DataType.mc_byte,
        DataType.mc_bytearray_2
    };

    public final int x;
    public final int y;
    public final int z;
    public final int action;
    public final byte[] data;

    public UpdateTileEntityPacket(ChannelBuffer in) {
        super(ID);
        this.x = Buffers.mc_int(in);
        this.y = Buffers.mc_short(in);
        this.z = Buffers.mc_int(in);
        this.action = Buffers.mc_byte(in);
        this.data = Buffers.mc_bytearray_2(in);
    }

    @Override()
    public String toString() {
        return Objects.toStringHelper(this)
            .add("x", x)
            .add("y", y)
            .add("z", z)
            .add("Action", action)
            .add("Data length", data.length)
            .toString();
    }
}
