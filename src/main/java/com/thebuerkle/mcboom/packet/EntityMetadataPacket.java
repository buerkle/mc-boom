package com.thebuerkle.mcboom.packet;

import com.google.common.base.Objects;
import com.thebuerkle.mcboom.Buffers;
import com.thebuerkle.mcboom.DataType;

import org.jboss.netty.buffer.ChannelBuffer;

public class EntityMetadataPacket extends Packet {

    public static final int ID = ENTITY_METADATA;

    public static final DataType[] ENCODING = new DataType[] {
        DataType.mc_int,
        DataType.mc_metadata
    };

    public final int eid;
    public final byte[] metadata;

    public EntityMetadataPacket(ChannelBuffer in) {
        super(ID);
        this.eid = Buffers.mc_int(in);
        this.metadata = Buffers.mc_metadata(in);
    }

    @Override()
    public String toString() {
        return Objects.toStringHelper(this)
            .add("eid", eid)
            .add("metadata", metadata.length)
            .toString();
    }
}
