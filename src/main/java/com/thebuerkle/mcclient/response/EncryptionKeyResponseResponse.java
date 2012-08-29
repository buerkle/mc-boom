package com.thebuerkle.mcclient.response;

import com.thebuerkle.mcclient.model.DataType;

import org.apache.mina.core.buffer.IoBuffer;

public class EncryptionKeyResponseResponse extends Response {

    public static final int ID = 0xFC;

    public static final DataType[] ENCODING = new DataType[] {
        DataType.mc_bytearray_2,
            DataType.mc_bytearray_2
    };

    public final byte[] secret;
    public final byte[] token;

    public EncryptionKeyResponseResponse(IoBuffer in) {
        this.secret = mc_bytearray_2(in);
        this.token = mc_bytearray_2(in);
    }

    @Override()
    public int getId() {
        return ID;
    }
}
