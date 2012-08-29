package com.thebuerkle.mcclient.response;

import com.google.common.base.Objects;
import com.thebuerkle.mcclient.model.DataType;
import com.thebuerkle.mcclient.model.IntVec3;

import org.apache.mina.core.buffer.IoBuffer;

public class UpdateSignResponse extends Response {

    public static final int ID = 0x82;

    public static final DataType[] ENCODING = new DataType[] {
        DataType.mc_int,
        DataType.mc_short,
        DataType.mc_int,
        DataType.mc_string,
        DataType.mc_string,
        DataType.mc_string,
        DataType.mc_string
    };

    public final IntVec3 position;
    public final String text1;
    public final String text2;
    public final String text3;
    public final String text4;

    public UpdateSignResponse(IoBuffer in) {
        this.position = new IntVec3(mc_int(in), mc_short(in), mc_int(in));
        this.text1 = mc_string(in);
        this.text2 = mc_string(in);
        this.text3 = mc_string(in);
        this.text4 = mc_string(in);
    }

    @Override()
    public int getId() {
        return ID;
    }

    @Override()
    public String toString() {
        return Objects.toStringHelper(this).
            add("Position", position).
            add("Text1", text1).
            add("Text2", text1).
            add("Text3", text1).
            add("Text4", text1).
            toString();
    }
}
