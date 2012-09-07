package com.thebuerkle.mcclient.request;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

public class RequestProtocolEncoder implements ProtocolEncoder {

    @Override()
    public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {
        if (message instanceof Request == false) {
            return;
        }

        Request request = (Request) message;
        IoBuffer buf = IoBuffer.allocate(1+request.getSize());

        int p = buf.position();
        int l = buf.limit();
        buf.put((byte) request.getId());
        request.write(buf);
        buf.flip();
        if (buf.remaining() == 0) {
            System.err.println("------  Request: " + request.getId());
            System.err.println("------ Position: " + p);
            System.err.println("------    Limit: " + l);
            System.err.println("------New Position: " + buf.position());
            System.err.println("------   New Limit: " + buf.limit());
        }
        out.write(buf);
    }

    @Override()
    public void dispose(IoSession session) {
    }
}

