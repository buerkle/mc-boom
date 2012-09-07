package com.thebuerkle.mcclient.request;

import org.apache.mina.core.buffer.IoBuffer;

public class KeepAliveRequest extends Request {

    private final int _id;

    public KeepAliveRequest(int id) {
        super(0x00);

        _id = id;
    }

    @Override()
    public int getSize() {
        return 4;
    }

    @Override()
    public void write(IoBuffer out) {
        mc_int(out, _id);
    }
}
