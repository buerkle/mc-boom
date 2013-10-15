package com.thebuerkle.mcboom.packet;

import com.google.common.base.Objects;
import com.thebuerkle.mcboom.Buffers;
import com.thebuerkle.mcboom.DataType;

import org.jboss.netty.buffer.ChannelBuffer;

public class EntityVelocityPacket extends Packet {

    public static final int ID = ENTITY_VELOCITY;

    public static final DataType[] ENCODING = new DataType[] {
        DataType.mc_int,
        DataType.mc_short,
        DataType.mc_short,
        DataType.mc_short
    };

    public final int eid;
    public final int vx;
    public final int vy;
    public final int vz;

    public EntityVelocityPacket(ChannelBuffer in) {
        super(ID);
        this.eid = Buffers.mc_int(in);
        this.vx = Buffers.mc_short(in);
        this.vy = Buffers.mc_short(in);
        this.vz = Buffers.mc_short(in);
    }

    @Override()
    public String toString() {
        return Objects.toStringHelper(this)
            .add("Entity ID", eid)
            .add("vx", vx)
            .add("vy", vy)
            .add("vz", vz)
            .toString();
    }
}
