package com.thebuerkle.mcclient.request;

import org.apache.mina.core.buffer.IoBuffer;

public class ClientStatusRequest extends Request {

    private int _status;

    public ClientStatusRequest(int status) {
        super(0xCD);

        _status = status;
    }

    @Override()
    public void write(IoBuffer out) {
        mc_byte(out, _status);
    }
}
