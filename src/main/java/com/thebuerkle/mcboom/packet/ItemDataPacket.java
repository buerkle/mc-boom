package com.thebuerkle.mcboom.packet;

import com.google.common.base.Objects;
import com.thebuerkle.mcboom.Buffers;
import com.thebuerkle.mcboom.DataType;

import org.jboss.netty.buffer.ChannelBuffer;

public class ItemDataPacket extends Packet {

    public static final int ID = ITEM_DATA;

    public static final DataType[] ENCODING = new DataType[] {
        DataType.mc_short,
        DataType.mc_short,
        DataType.mc_bytearray_2
    };

    public final int type;
    public final int id;
    public final byte[] data;

    public ItemDataPacket(ChannelBuffer in) {
        super(ID);
        this.type = Buffers.mc_short(in);
        this.id = Buffers.mc_short(in);
        this.data = Buffers.mc_bytearray_2(in);
    }

    @Override()
    public String toString() {
        return Objects.toStringHelper(this)
            .add("type", type)
            .add("id", id)
            .add("data", data.length)
            .toString();
    }
}
