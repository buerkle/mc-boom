package com.thebuerkle.mcboom.packet;

import com.google.common.base.Objects;
import com.thebuerkle.mcboom.Buffers;
import com.thebuerkle.mcboom.DataType;

import org.jboss.netty.buffer.ChannelBuffer;

public class EntityLookAndMovePacket extends Packet {

    public static final int ID = ENTITY_LOOK_AND_MOVE;

    public static final DataType[] ENCODING = new DataType[] {
        DataType.mc_int,
        DataType.mc_byte,
        DataType.mc_byte,
        DataType.mc_byte,
        DataType.mc_byte,
        DataType.mc_byte
    };

    public final int eid;
    public final double dx;
    public final double dy;
    public final double dz;
    public final int yaw;
    public final int pitch;

    public EntityLookAndMovePacket(ChannelBuffer in) {
        super(ID);
        this.eid = Buffers.mc_int(in);
        this.dx = Buffers.mc_byte(in) / 32.0;
        this.dy = Buffers.mc_byte(in) / 32.0;
        this.dz = Buffers.mc_byte(in) / 32.0;
        this.yaw = Buffers.mc_byte(in);
        this.pitch = Buffers.mc_byte(in);
    }

    @Override()
    public String toString() {
        return Objects.toStringHelper(this)
            .add("Entity ID", eid)
            .add("dx", dx)
            .add("dy", dy)
            .add("dz", dz)
            .add("Yaw", yaw)
            .add("Pitch", pitch)
            .toString();
    }
}
