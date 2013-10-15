package com.thebuerkle.mcboom.packet;

import com.google.common.base.Objects;
import com.thebuerkle.mcboom.Buffers;
import com.thebuerkle.mcboom.DataType;

import org.jboss.netty.buffer.ChannelBuffer;

public class MapChunkBulkPacket extends Packet {

    public static final int ID = MAP_CHUNK_BULK;

    public static final DataType[] ENCODING = new DataType[] {
        DataType.mc_chunk_bulk
    };

    public final byte[] data;

    public MapChunkBulkPacket(ChannelBuffer in) {
        super(ID);
        this.data = Buffers.mc_chulk_bulk(in);
    }

    @Override()
    public String toString() {
        return Objects.toStringHelper(this)
            .add("ID", id)
            .add("Data", data.length)
            .toString();
    }
}
