package com.thebuerkle.mcboom.packet;

import com.google.common.base.Objects;
import com.thebuerkle.mcboom.Buffers;
import com.thebuerkle.mcboom.DataType;

import org.jboss.netty.buffer.ChannelBuffer;

public class KickPacket extends Packet {

    public static final int ID = KICK;

    public static final DataType[] ENCODING = new DataType[] {
        DataType.mc_string
    };

    public final String reason;

    public KickPacket(ChannelBuffer in) {
        super(ID);

        this.reason = Buffers.mc_string(in);
    }

    @Override()
    public int size() {
        return 2 + 2*this.reason.length();
    }

    @Override()
    public String toString() {
        return Objects.toStringHelper(this).
            add("ID", id).
            add("Reason", reason).
            toString();
    }

}
