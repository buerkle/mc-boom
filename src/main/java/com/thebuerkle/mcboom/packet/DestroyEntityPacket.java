package com.thebuerkle.mcboom.packet;

import com.google.common.base.Objects;
import com.thebuerkle.mcboom.Buffers;
import com.thebuerkle.mcboom.DataType;

import org.jboss.netty.buffer.ChannelBuffer;

public class DestroyEntityPacket extends Packet {

    public static final int ID = DESTROY_ENTITY;

    public static final DataType[] ENCODING = new DataType[] {
        DataType.mc_intarray_1
    };

    public final int[] eids;

    public DestroyEntityPacket(ChannelBuffer in) {
        super(ID);
        this.eids = Buffers.mc_intarray_1(in);
    }

    @Override()
    public String toString() {
        return Objects.toStringHelper(this)
            .add("Count", eids.length)
            .toString();
    }
}
