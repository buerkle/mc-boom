package com.thebuerkle.mcclient.response;

import com.google.common.base.Objects;

import com.thebuerkle.mcclient.model.DataType;

import org.apache.mina.core.buffer.IoBuffer;

public class PlayerListItemResponse extends Response {

    public static final int ID = 0xC9;

    public static final DataType[] ENCODING = new DataType[] {
        DataType.mc_string,
        DataType.mc_bool,
        DataType.mc_short
    };

    public final String name;
    public final boolean online;
    public final short ping;

    public PlayerListItemResponse(IoBuffer in) {
        this.name = mc_string(in);
        this.online = mc_bool(in);
        this.ping = mc_short(in);
    }

    @Override()
    public int getId() {
        return ID;
    }

    @Override()
    public String toString() {
        return Objects.toStringHelper(this).
            add("Player name", name).
            add("Online", online).
            add("Ping", ping).
            toString();
    }
}
