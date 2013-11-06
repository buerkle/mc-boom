package com.thebuerkle.mcboom.packet;

import com.google.common.base.Objects;
import com.thebuerkle.mcboom.Buffers;
import com.thebuerkle.mcboom.DataType;

import org.jboss.netty.buffer.ChannelBuffer;

public class ChunkDataPacket extends Packet {

    public static final int ID = CHUNK_DATA;

    public static final DataType[] ENCODING = new DataType[] {
        DataType.mc_int,
        DataType.mc_int,
        DataType.mc_bool,
        DataType.mc_short,
        DataType.mc_short,
        DataType.mc_bytearray_4
    };

    public final int x;
    public final int z;
    public final boolean continuous;
    public final short primaryBitmap;
    public final short addBitmap;
    public final byte[] chunk;

    public ChunkDataPacket(ChannelBuffer in) {
        super(ID);
        this.x = Buffers.mc_int(in);
        this.z = Buffers.mc_int(in);
        this.continuous = Buffers.mc_bool(in);
        this.primaryBitmap = Buffers.mc_short(in);
        this.addBitmap = Buffers.mc_short(in);
        this.chunk = Buffers.mc_bytearray_4(in);
    }

    @Override()
    public String toString() {
        return Objects.toStringHelper(this).
            add("X", x).
            add("Z", z).
            add("Continuous", continuous).
            add("Primary bitmap", primaryBitmap).
            add("Add bitmap", addBitmap).
            add("Length", chunk.length).
            toString();
    }
}
