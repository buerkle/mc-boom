package com.thebuerkle.mcboom.packet;

import com.google.common.base.Objects;
import com.thebuerkle.mcboom.Buffers;
import com.thebuerkle.mcboom.DataType;

import org.jboss.netty.buffer.ChannelBuffer;

public class ChangeGameStatePacket extends Packet {

    public static final int ID = CHANGE_GAME_STATE;

    public static final DataType[] ENCODING = new DataType[] {
        DataType.mc_byte,
        DataType.mc_byte
    };

    public final byte reason;
    public final byte mode;

    public ChangeGameStatePacket(ChannelBuffer in) {
        super(ID);

        this.reason = Buffers.mc_byte(in);
        this.mode = Buffers.mc_byte(in);
    }

    @Override()
    public String toString() {
        return Objects.toStringHelper(this)
            .add("ID", id)
            .add("Reason", reason)
            .add("Mode", mode)
            .toString();
    }
}
