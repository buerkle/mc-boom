package com.thebuerkle.mcboom.packet;

import com.google.common.base.Objects;
import com.thebuerkle.mcboom.Buffers;
import com.thebuerkle.mcboom.DataType;

import org.jboss.netty.buffer.ChannelBuffer;

public class UpdateHealthPacket extends Packet {

    public static final int ID = UPDATE_HEALTH;

    public static final DataType[] ENCODING = new DataType[] {
        DataType.mc_float,
        DataType.mc_short,
        DataType.mc_float
    };

    public final float health;
    public final short food;
    public final float saturation;

    public UpdateHealthPacket(ChannelBuffer in) {
        super(ID);
        this.health = Buffers.mc_float(in);
        this.food = Buffers.mc_short(in);
        this.saturation = Buffers.mc_float(in);
    }

    @Override()
    public String toString() {
        return Objects.toStringHelper(this)
            .add("health", health)
            .add("food", food)
            .add("saturation", saturation)
            .toString();
    }
}
