package com.thebuerkle.mcboom.packet;

import com.google.common.base.Objects;
import com.thebuerkle.mcboom.Buffers;
import com.thebuerkle.mcboom.DataType;

import org.jboss.netty.buffer.ChannelBuffer;

public class AttachEntityPacket extends Packet {

    public static final int ID = ATTACH_ENTITY;

    public static final DataType[] ENCODING = new DataType[] {
        DataType.mc_int,
        DataType.mc_int,
        DataType.mc_byte
    };

    public final int eid;
    public final int vehicle;
    public final boolean leash;

    public AttachEntityPacket(ChannelBuffer in) {
        super(ID);
        this.eid = Buffers.mc_int(in);
        this.vehicle = Buffers.mc_int(in);
        this.leash = Buffers.mc_byte(in) == 1;
    }

    @Override()
    public String toString() {
        return Objects.toStringHelper(this)
            .add("eid", eid)
            .add("vehicle id", vehicle)
            .add("leash", leash)
            .toString();
    }
}
