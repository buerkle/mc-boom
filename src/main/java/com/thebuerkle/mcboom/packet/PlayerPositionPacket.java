package com.thebuerkle.mcboom.packet;

import com.thebuerkle.mcboom.Buffers;

import org.jboss.netty.buffer.ChannelBuffer;

public class PlayerPositionPacket extends Packet {

    public static final int ID = PLAYER_POSITION;

    public final double x;
    public final double y;
    public final double z;
    public final double stance;
    public final boolean onGround;

    public PlayerPositionPacket(double x, double y, double z, double stance, boolean onGround) {
        super(ID);
        this.x = x;
        this.y = y;
        this.z = z;
        this.stance = stance;
        this.onGround = onGround;
    }

    @Override()
    public int size() {
        return 33;
    }

    @Override()
    public void write(ChannelBuffer out) {
        Buffers.mc_double(out, x);
        Buffers.mc_double(out, y);
        Buffers.mc_double(out, stance);
        Buffers.mc_double(out, z);
        Buffers.mc_bool(out, onGround);
    }
}
