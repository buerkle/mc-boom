package com.thebuerkle.mcclient.response;

import com.google.common.base.Objects;
import com.thebuerkle.mcclient.model.DataType;
import com.thebuerkle.mcclient.model.EntityMetadata;

import java.util.List;

import org.apache.mina.core.buffer.IoBuffer;

public class EntityMetadataResponse extends Response {

    public static final int ID = 0x28;

    public static final DataType[] ENCODING = new DataType[] {
        DataType.mc_int,
        DataType.mc_metadata
    };

    public final int eid;
    public final List<EntityMetadata> metadata;

    public EntityMetadataResponse(IoBuffer in) {
        this.eid = mc_int(in);
        this.metadata = mc_metadata(in);
    }

    @Override()
    public int getId() {
        return ID;
    }

    @Override()
    public String toString() {
        return Objects.toStringHelper(this).
            add("Entity ID", eid).
            add("Metadata", metadata).
            toString();
    }
}
