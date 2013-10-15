package com.thebuerkle.mcboom.packet;

import com.google.common.base.Objects;
import com.thebuerkle.mcboom.Buffers;
import com.thebuerkle.mcboom.DataType;

import org.jboss.netty.buffer.ChannelBuffer;

public class ChatMessagePacket extends Packet {

    public static final int ID = CHAT_MESSAGE;

    public static final DataType[] ENCODING = new DataType[] {
        DataType.mc_string
    };

    public final String message;

    public ChatMessagePacket(ChannelBuffer in) {
        super(ID);
        this.message = Buffers.mc_string(in);
    }

    @Override()
    public String toString() {
        return Objects.toStringHelper(this)
            .add("ID", id)
            .add("Message", message)
            .toString();
    }
}
