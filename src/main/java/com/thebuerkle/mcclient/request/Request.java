package com.thebuerkle.mcclient.request;

import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

import org.apache.mina.core.buffer.IoBuffer;

public abstract class Request {
    private static final CharsetEncoder UTF_16_ENCODER = Charset.forName("UTF-16BE").newEncoder();

    public final int id;

    public Request(int id) {
        this.id = id;
    }

    abstract void write(IoBuffer out);

    public int getId() {
        return this.id;
    }

    protected void mc_bool(IoBuffer out, boolean v) {
        mc_byte(out, v ? 1 : 0);
    }

    protected void mc_byte(IoBuffer out, int v) {
        out.put((byte) v);
    }

    protected void mc_bytearray_2(IoBuffer out, byte[] v) {
        out.putShort((short) v.length);
        out.put(v);
    }

    protected void mc_double(IoBuffer out, double v) {
        out.putDouble(v);
    }

    protected void mc_float(IoBuffer out, float v) {
        out.putFloat(v);
    }

    protected void mc_int(IoBuffer out, int v) {
        out.putInt(v);
    }

    protected void mc_long(IoBuffer out, long v) {
        out.putLong(v);
    }

    protected void mc_short(IoBuffer out, int v) {
        out.putShort((short) v);
    }

    protected void mc_string(IoBuffer out, String v) {
        try {
            out.putShort((short) v.length());
            out.putString(v, UTF_16_ENCODER);
        }
        catch (CharacterCodingException e) {
            throw new RuntimeException(e);
        }
    }
}
