package com.thebuerkle.mcclient.response;

import com.thebuerkle.mcclient.model.DataType;
import com.thebuerkle.mcclient.model.EntityMetadata;
import com.thebuerkle.mcclient.model.Slot;

import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.List;

import org.apache.mina.core.buffer.IoBuffer;

public abstract class Response {

    private static final CharsetDecoder UTF_16_DECODER = Charset.forName("UTF-16BE").newDecoder();

    abstract public int getId();

    protected static boolean mc_bool(IoBuffer in) {
        return in.get() == 1 ? true : false;
    }

    protected static int mc_byte(IoBuffer in) {
        return in.get();
    }

    protected static int mc_unsigned_byte(IoBuffer in) {
        return in.getUnsigned();
    }

    protected static byte[] mc_bytearray_2(IoBuffer in) {
        int len = in.getUnsignedShort();
        byte[] v = new byte[len];
        in.get(v);
        return v;
    }

    protected static byte[] mc_bytearray_4(IoBuffer in) {
        int len = in.getInt();
        byte[] v = new byte[len];
        in.get(v);
        return v;
    }

    protected static double mc_double(IoBuffer in) {
        return in.getDouble();
    }

    protected static float mc_float(IoBuffer in) {
        return in.getFloat();
    }

    protected static int mc_int(IoBuffer in) {
        return in.getInt();
    }

    protected static int[] mc_intarray_1(IoBuffer in) {
        int len = in.get();
        int[] v = new int[len];
        for (int i = 0; i < len; i++) {
            v[i] = in.getInt();
        }
        return v;
    }

    protected static long mc_long(IoBuffer in) {
        return in.getLong();
    }

    protected static List<EntityMetadata> mc_metadata(IoBuffer in) {
        return EntityMetadata.decode(in);
    }

    protected static short mc_short(IoBuffer in) {
        return in.getShort();
    }

    protected static Slot mc_slot(IoBuffer in) {
        return Slot.decode(in);
    }

    protected static Slot[] mc_slotarray(IoBuffer in) {
        int len = in.getShort();
        Slot[] slots = new Slot[len];
        for (int i = 0; i < len; ++i) {
            slots[i] = Slot.decode(in);
        }
        return slots;
    }

    public static String mc_string(IoBuffer in) {
        int len = in.getShort() * 2;
        try {
            return in.getString(len, UTF_16_DECODER);
        }
        catch (CharacterCodingException e) {
            throw new RuntimeException(e);
        }
    }
}
