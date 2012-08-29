package com.thebuerkle.mcclient.response;

import com.google.common.base.Objects;

import com.thebuerkle.mcclient.model.DataType;
import com.thebuerkle.mcclient.model.Difficulty;
import com.thebuerkle.mcclient.model.Dimension;
import com.thebuerkle.mcclient.model.GameMode;
import com.thebuerkle.mcclient.model.LevelType;

import org.apache.mina.core.buffer.IoBuffer;

public class LoginRequestResponse extends Response {

    public static final int ID = 0x01;

    public static final DataType[] ENCODING = new DataType[] {
        DataType.mc_int,
            DataType.mc_string,
            DataType.mc_byte,
            DataType.mc_byte,
            DataType.mc_byte,
            DataType.mc_unsigned_byte,
            DataType.mc_unsigned_byte
    };

    public final int eid;
    public final LevelType levelType;
    public final GameMode mode;
    public final Dimension dimension;
    public final Difficulty difficulty;
    public final int maxPlayers;

    public LoginRequestResponse(IoBuffer in) {
        this.eid = mc_int(in);
        this.levelType = LevelType.fromValue(mc_string(in));
        this.mode = GameMode.fromValue(mc_byte(in));
        this.dimension = Dimension.fromValue(mc_byte(in));
        this.difficulty = Difficulty.fromValue(mc_byte(in));
        mc_unsigned_byte(in); // not used
        this.maxPlayers = mc_unsigned_byte(in);
    }

    @Override()
    public int getId() {
        return ID;
    }

    @Override()
    public String toString() {
        return Objects.toStringHelper(this).
            add("Entity ID", eid).
            add("Level type", levelType).
            add("Game mode", mode).
            add("Dimension", dimension).
            add("Difficulty", difficulty).
            add("Maximum players", maxPlayers).
            toString();
    }
}
