package com.thebuerkle.mcboom.packet;

import com.google.common.base.Objects;
import com.thebuerkle.mcboom.Buffers;
import com.thebuerkle.mcboom.DataType;

import org.jboss.netty.buffer.ChannelBuffer;

public class NamedSoundEffectPacket extends Packet {

    public static final int ID = NAMED_SOUND_EFFECT;

    public static final DataType[] ENCODING = new DataType[] {
        DataType.mc_string,
        DataType.mc_int,
        DataType.mc_int,
        DataType.mc_int,
        DataType.mc_float,
        DataType.mc_byte
    };

    public final String name;
    public final int x;
    public final int y;
    public final int z;
    public final float volume;
    public final int pitch;

    public NamedSoundEffectPacket(ChannelBuffer in) {
        super(ID);
        this.name = Buffers.mc_string(in);
        this.x = Buffers.mc_int(in);
        this.y = Buffers.mc_int(in);
        this.z = Buffers.mc_int(in);
        this.volume = Buffers.mc_float(in);
        this.pitch = Buffers.mc_byte(in);
    }

    @Override()
    public String toString() {
        return Objects.toStringHelper(this)
            .add("name", name)
            .add("x", x)
            .add("y", y)
            .add("z", z)
            .add("volume", volume)
            .add("pitch", pitch)
            .toString();
    }
}
