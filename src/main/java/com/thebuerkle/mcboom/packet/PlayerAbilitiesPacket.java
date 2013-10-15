package com.thebuerkle.mcboom.packet;

import com.google.common.base.Objects;
import com.thebuerkle.mcboom.Buffers;
import com.thebuerkle.mcboom.DataType;

import org.jboss.netty.buffer.ChannelBuffer;

public class PlayerAbilitiesPacket extends Packet {

    public static final int ID = PLAYER_ABILITIES;

    public static final DataType[] ENCODING = new DataType[] {
        DataType.mc_byte,
        DataType.mc_float,
        DataType.mc_float
    };

    public final int flags;
    public final float flyingVelocity;
    public final float walkingVelocity;

    public PlayerAbilitiesPacket(ChannelBuffer in) {
        super(ID);
        this.flags = Buffers.mc_byte(in);
        this.flyingVelocity = Buffers.mc_float(in);
        this.walkingVelocity = Buffers.mc_float(in);
    }

    @Override()
    public String toString() {
        return Objects.toStringHelper(this).
            add("ID", id).
            add("Flags", flags).
            add("Flying velocity", flyingVelocity).
            add("Walking velocity", walkingVelocity).
            toString();
    }
}
