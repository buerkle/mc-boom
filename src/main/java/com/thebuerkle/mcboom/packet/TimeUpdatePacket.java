package com.thebuerkle.mcboom.packet;

import com.google.common.base.Objects;
import com.thebuerkle.mcboom.Buffers;
import com.thebuerkle.mcboom.DataType;

import org.jboss.netty.buffer.ChannelBuffer;

public class TimeUpdatePacket extends Packet {

    public static final int ID = TIME_UPDATE;

    public static final DataType[] ENCODING = new DataType[] {
        DataType.mc_long,
        DataType.mc_long
    };

    public final long ageOfWorld;
    public final long timeOfDay;

    public TimeUpdatePacket(ChannelBuffer in) {
        super(ID);
        this.ageOfWorld = Buffers.mc_long(in);
        this.timeOfDay = Buffers.mc_long(in);
    }

    @Override()
    public String toString() {
        return Objects.toStringHelper(this)
            .add("ID", id)
            .add("Age of the world", ageOfWorld)
            .add("Time of day", timeOfDay)
            .toString();
    }
}
