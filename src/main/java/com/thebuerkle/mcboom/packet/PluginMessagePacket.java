package com.thebuerkle.mcboom.packet;

import com.google.common.base.Objects;
import com.thebuerkle.mcboom.Buffers;
import com.thebuerkle.mcboom.DataType;

import org.jboss.netty.buffer.ChannelBuffer;

public class PluginMessagePacket extends Packet {

    public static final int ID = PLUGIN_MESSAGE;

    public static final DataType[] ENCODING = new DataType[] {
            DataType.mc_string,
            DataType.mc_bytearray_2
    };

    public final String channel;
    public final byte[] data;

    public PluginMessagePacket(ChannelBuffer in) {
        super(ID);

        this.channel = Buffers.mc_string(in);
        this.data = Buffers.mc_bytearray_2(in);
    }

    @Override()
    public String toString() {
        return Objects.toStringHelper(this).
            add("ID", id).
            add("Channel", channel).
            add("Data", data.length).
            toString();
    }
}
