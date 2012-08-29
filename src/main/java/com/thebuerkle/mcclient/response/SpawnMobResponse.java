package com.thebuerkle.mcclient.response;

import com.google.common.base.Objects;

import com.thebuerkle.mcclient.model.DataType;
import com.thebuerkle.mcclient.model.EntityMetadata;
import com.thebuerkle.mcclient.model.IntVec3;

import java.util.List;

import org.apache.mina.core.buffer.IoBuffer;

public class SpawnMobResponse extends Response {

    public static final int ID = 0x18;

    public static final DataType[] ENCODING = new DataType[] {
        DataType.mc_int,
        DataType.mc_byte,
        DataType.mc_int,
        DataType.mc_int,
        DataType.mc_int,
        DataType.mc_byte,
        DataType.mc_byte,
        DataType.mc_byte,
        DataType.mc_short,
        DataType.mc_short,
        DataType.mc_short,
        DataType.mc_metadata
    };

    public final int eid;
    public final int type;
    public final IntVec3 position;
    public final int yaw;
    public final int pitch;
    public final int headYaw;
    public final IntVec3 velocity;
    public final List<EntityMetadata> metadata;

    public SpawnMobResponse(IoBuffer in) {
        this.eid = mc_int(in);
        this.type = mc_byte(in);
        this.position = new IntVec3(mc_int(in), mc_int(in), mc_int(in));
        this.yaw = mc_byte(in);
        this.pitch = mc_byte(in);
        this.headYaw = mc_byte(in);
        this.velocity = new IntVec3(mc_short(in), mc_short(in), mc_short(in));
        this.metadata = EntityMetadata.decode(in);
    }

    @Override()
    public int getId() {
        return ID;
    }

    public String toString() {
        return Objects.toStringHelper(this).
            add("Entity ID", eid).
            add("Type", type).
            add("Position", position).
            add("Yaw", yaw).
            add("Pitch", pitch).
            add("Head yaw", headYaw).
            add("Velocity", velocity).
            add("Metadata", metadata.size()).
            toString();
    }
}
