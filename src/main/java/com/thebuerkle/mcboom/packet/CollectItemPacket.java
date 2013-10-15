package com.thebuerkle.mcboom.packet;

import com.google.common.base.Objects;
import com.thebuerkle.mcboom.Buffers;
import com.thebuerkle.mcboom.DataType;

import org.jboss.netty.buffer.ChannelBuffer;

public class CollectItemPacket extends Packet {

    public static final int ID = COLLECT_ITEM;

    public static final DataType[] ENCODING = new DataType[] {
        DataType.mc_int,
        DataType.mc_int
    };

    public final int collected;
    public final int collector;

    public CollectItemPacket(ChannelBuffer in) {
        super(ID);
        this.collected = Buffers.mc_int(in);
        this.collector = Buffers.mc_int(in);
    }

    @Override()
    public String toString() {
        return Objects.toStringHelper(this)
            .add("collected eid", collected)
            .add("collector eid", collector)
            .toString();
    }
}
