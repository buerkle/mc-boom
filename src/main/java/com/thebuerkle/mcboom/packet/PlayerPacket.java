package com.thebuerkle.mcboom.packet;

import com.google.common.base.Objects;
import com.thebuerkle.mcboom.Buffers;
import com.thebuerkle.mcboom.DataType;

import org.jboss.netty.buffer.ChannelBuffer;

public class PlayerPacket extends Packet {

    public static final int ID = PLAYER;

    public static final DataType[] ENCODING = new DataType[] {
        DataType.mc_bool
    };

    public final boolean ground;

    public PlayerPacket(ChannelBuffer in) {
        super(ID);
        this.ground = Buffers.mc_bool(in);
    }

    @Override()
    public String toString() {
        return Objects.toStringHelper(this)
            .add("ID", id)
            .add("On Ground", ground)
            .toString();
    }
}
