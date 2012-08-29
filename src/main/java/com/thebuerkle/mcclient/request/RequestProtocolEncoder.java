package com.thebuerkle.mcclient.request;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

public class RequestProtocolEncoder implements ProtocolEncoder {

    private static final String BUFFER_KEY = RequestProtocolEncoder.class.getName() + ".BUFFER";

    @Override()
    public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {
        if (message instanceof Request == false) {
            return;
        }

        Request request = (Request) message;
        IoBuffer buf = (IoBuffer) session.getAttribute(BUFFER_KEY);

        if (buf == null) {
            buf = IoBuffer.allocate(512);
            buf.setAutoExpand(true);
            session.setAttribute(BUFFER_KEY, buf);
        }

        buf.clear();
        buf.put((byte) request.getId());
        request.write(buf);
        buf.flip();
        out.write(buf);
    }

    @Override()
    public void dispose(IoSession session) {
        session.removeAttribute(BUFFER_KEY);
    }
}

