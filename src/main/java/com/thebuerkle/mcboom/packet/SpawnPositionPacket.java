package com.thebuerkle.mcboom.packet;

import com.google.common.base.Objects;
import com.thebuerkle.mcboom.Buffers;
import com.thebuerkle.mcboom.DataType;

import org.jboss.netty.buffer.ChannelBuffer;

public class SpawnPositionPacket extends Packet {

    public static final int ID = Packet.SPAWN_POSITION;

    public static final DataType[] ENCODING = new DataType[] {
        DataType.mc_int,
        DataType.mc_int,
        DataType.mc_int
    };

    public final int x;
    public final int y;
    public final int z;

    public SpawnPositionPacket(ChannelBuffer in) {
        super(ID);
        this.x = Buffers.mc_int(in);
        this.y = Buffers.mc_int(in);
        this.z = Buffers.mc_int(in);
    }

    @Override()
    public String toString() {
        return Objects.toStringHelper(this).
            add("x", x).
            add("y", y).
            add("z", z).
            toString();
    }
}
