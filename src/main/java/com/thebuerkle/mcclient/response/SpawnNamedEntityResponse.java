package com.thebuerkle.mcclient.response;

import com.google.common.base.Objects;
import com.thebuerkle.mcclient.model.DataType;
import com.thebuerkle.mcclient.model.EntityMetadata;
import com.thebuerkle.mcclient.model.IntVec3;

import java.util.List;

import org.apache.mina.core.buffer.IoBuffer;

public class SpawnNamedEntityResponse extends Response {

    public static final int ID = 0x14;

    public static final DataType[] ENCODING = new DataType[] {
        DataType.mc_int,
        DataType.mc_string,
        DataType.mc_int,
        DataType.mc_int,
        DataType.mc_int,
        DataType.mc_byte,
        DataType.mc_byte,
        DataType.mc_short,
        DataType.mc_metadata
    };

    public final int eid;
    public final String name;
    public final IntVec3 position;
    public final int yaw;
    public final int pitch;
    public final short item;
    public final List<EntityMetadata> metadata;

    public SpawnNamedEntityResponse(IoBuffer in) {
        this.eid = mc_int(in);
        this.name = mc_string(in);
        this.position = new IntVec3(mc_int(in), mc_int(in), mc_int(in));
        this.yaw = mc_byte(in);
        this.pitch = mc_byte(in);
        this.item = mc_short(in);
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
            add("Name", name).
            add("Position", position).
            add("Yaw", yaw).
            add("Pitch", pitch).
            add("Item", item).
            add("Metadata", metadata).
            toString();
    }
}
