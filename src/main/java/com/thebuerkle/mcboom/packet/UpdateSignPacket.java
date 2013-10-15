package com.thebuerkle.mcboom.packet;

import com.google.common.base.Objects;
import com.google.common.base.Objects.ToStringHelper;
import com.thebuerkle.mcboom.Buffers;
import com.thebuerkle.mcboom.DataType;

import org.jboss.netty.buffer.ChannelBuffer;

public class UpdateSignPacket extends Packet {

    public static final int ID = UPDATE_SIGN;

    public static final DataType[] ENCODING = new DataType[] {
        DataType.mc_int,
        DataType.mc_short,
        DataType.mc_int,
        DataType.mc_string,
        DataType.mc_string,
        DataType.mc_string,
        DataType.mc_string
    };

    public final int x;
    public final int y;
    public final int z;
    public final String[] lines = new String[4];

    public UpdateSignPacket(ChannelBuffer in) {
        super(ID);
        this.x = Buffers.mc_int(in);
        this.y = Buffers.mc_short(in);
        this.z = Buffers.mc_int(in);

        for (int i = 0; i < lines.length; i++) {
            lines[i] = Buffers.mc_string(in);
        }
    }

    @Override()
    public String toString() {
        ToStringHelper result = Objects.toStringHelper(this)
            .add("x", x)
            .add("y", y)
            .add("z", z);

        for (int i = 0; i < lines.length; i++) {
            result.add("line" + (i+1), lines[i]);
        }
        return result.toString();
    }
}
