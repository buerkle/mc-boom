package com.thebuerkle.mcboom.packet;

import com.google.common.base.Objects;
import com.thebuerkle.mcboom.Buffers;
import com.thebuerkle.mcboom.DataType;

import org.jboss.netty.buffer.ChannelBuffer;

public class PlayerListItemPacket extends Packet {

    public static final int ID = PLAYER_LIST_ITEM;

    public static final DataType[] ENCODING = new DataType[] {
        DataType.mc_string,
        DataType.mc_bool,
        DataType.mc_short
    };

    public final String name;
    public final boolean online;
    public final short ping;

    public PlayerListItemPacket(ChannelBuffer in) {
        super(ID);
        this.name = Buffers.mc_string(in);
        this.online = Buffers.mc_bool(in);
        this.ping = Buffers.mc_short(in);
    }

    @Override()
    public String toString() {
        return Objects.toStringHelper(this)
            .add("ID", id)
            .add("Player name", name)
            .add("Online", online)
            .add("Ping", ping)
            .toString();
    }
}
