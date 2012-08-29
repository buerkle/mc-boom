package com.thebuerkle.mcclient.response;

import com.google.common.base.Objects;

import com.thebuerkle.mcclient.model.DataType;

import org.apache.mina.core.buffer.IoBuffer;

public class ChatMessageResponse extends Response {

    public static final int ID = 0x03;

    public static final DataType[] ENCODING = new DataType[] {
        DataType.mc_string
    };

    public final String message;

    public ChatMessageResponse(IoBuffer in) {
        this.message = mc_string(in);
    }

    @Override()
    public int getId() {
        return ID;
    }

    @Override()
    public String toString() {
        return Objects.toStringHelper(this).
            add("Message", message).
            toString();
    }
}
