package com.thebuerkle.mcboom.packet;

import com.google.common.base.Objects;
import com.thebuerkle.mcboom.Buffers;
import com.thebuerkle.mcboom.DataType;

import org.jboss.netty.buffer.ChannelBuffer;

public class LoginRequestPacket extends Packet {

    public static final int ID = LOGIN_REQUEST;

    public static final DataType[] ENCODING = new DataType[] {
        DataType.mc_int,
        DataType.mc_string,
        DataType.mc_byte,
        DataType.mc_byte,
        DataType.mc_byte,
        DataType.mc_byte,
        DataType.mc_byte
    };

    public final int eid;
    public final String levelType;
    public final int mode;
    public final int dimension;
    public final int difficulty;
    public final int maxPlayers;

    public LoginRequestPacket(ChannelBuffer in) {
        super(ID);

        this.eid = Buffers.mc_int(in);
        this.levelType = Buffers.mc_string(in);
        this.mode = Buffers.mc_byte(in);
        this.dimension = Buffers.mc_byte(in);
        this.difficulty = Buffers.mc_byte(in);
        Buffers.mc_byte(in); // not used
        this.maxPlayers = Buffers.mc_byte(in);
    }

    @Override()
    public String toString() {
        return Objects.toStringHelper(this)
            .add("ID", id)
            .add("Entity ID", eid)
            .add("Level type", levelType)
            .add("Game mode", mode)
            .add("Dimension", dimension)
            .add("Difficulty", difficulty)
            .add("Maximum players", maxPlayers)
            .toString();
    }
}
