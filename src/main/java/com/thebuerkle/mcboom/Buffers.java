package com.thebuerkle.mcboom;

import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CoderResult;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.util.CharsetUtil;

public final class Buffers {

    public static boolean mc_bool(ChannelBuffer in) {
        return in.readByte() == 1;
    }

    public static void mc_bool(ChannelBuffer out, boolean v) {
        mc_byte(out, v ? 1 : 0);
    }

    public static byte mc_byte(ChannelBuffer in) {
        return in.readByte();
    }

    public static void mc_byte(ChannelBuffer out, int v) {
        out.writeByte(v);
    }

    public static byte[] mc_bytearray_2(ChannelBuffer in) {
        int len = in.readShort();
        byte[] result = new byte[len];
        in.readBytes(result, 0, len);
        return result;
    }

    public static void mc_bytearray_2(ChannelBuffer out, byte[] v) {
        out.writeShort(v.length);
        out.writeBytes(v);
    }

    public static byte[] mc_bytearray_4(ChannelBuffer in) {
        int len = in.readInt();
        byte[] result = new byte[len];
        in.readBytes(result, 0, len);
        return result;
    }

    public static byte[] mc_bytecoordarray(ChannelBuffer in) {
        int len = in.readInt() * 3;
        byte[] result = new byte[len];
        in.readBytes(result, 0, len);
        return result;
    }

    public static byte[] mc_chulk_bulk(ChannelBuffer in) {
        int len = DataType.mc_chunk_bulk.size(in, in.readerIndex());
        byte[] result = new byte[len];
        in.readBytes(result, 0, len);
        return result;
    }

    public static double mc_double(ChannelBuffer in) {
        return in.readDouble();
    }

    public static void mc_double(ChannelBuffer out, double v) {
        out.writeDouble(v);
    }

    public static float mc_float(ChannelBuffer in) {
        return in.readFloat();
    }

    public static void mc_float(ChannelBuffer out, float v) {
        out.writeFloat(v);
    }

    public static double mc_fixed_point(ChannelBuffer in) {
        return in.readInt() / 32.0;
    }

    public static int mc_int(ChannelBuffer in) {
        return in.readInt();
    }

    public static void mc_int(ChannelBuffer out, int v) {
        out.writeInt(v);
    }

    public static int[] mc_intarray_1(ChannelBuffer in) {
        int len = in.readByte();
        int[] v = new int[len];
        for (int i = 0; i < len; i++) {
            v[i] = in.readInt();
        }
        return v;
    }

    public static long mc_long(ChannelBuffer in) {
        return in.readLong();
    }

    public static void mc_long(ChannelBuffer out, long v) {
        out.writeLong(v);
    }

    public static byte[] mc_metadata(ChannelBuffer in) {
        int len = DataType.mc_metadata.size(in, in.readerIndex());
        byte[] result = new byte[len];
        in.readBytes(result, 0, len);
        return result;
    }

    public static List<Property> mc_properties(ChannelBuffer in) {
        int count = in.readInt();
        List<Property> result = new ArrayList<>(count);

        for (int i = 0; i < count; i++) {
            String key = mc_string(in);
            double value = mc_double(in);
            int attrlen = mc_short(in);

            List<Modifier> attrs = new ArrayList<>(attrlen);
            for (int j = 0; j < attrlen; j++) {
                attrs.add(new Modifier(new UUID(mc_long(in), mc_long(in)),
                    mc_double(in),
                    mc_byte(in)));
            }
            result.add(new Property(key, value, attrs));
        }
        return result;
    }

    public static short mc_short(ChannelBuffer in) {
        return in.readShort();
    }

    public static void mc_short(ChannelBuffer out, int v) {
        out.writeShort(v);
    }

    public static byte[] mc_slot(ChannelBuffer in) {
        int len = DataType.mc_slot.size(in, in.readerIndex());
        byte[] result = new byte[len];
        in.readBytes(result, 0, len);
        return result;
    }

    public static byte[] mc_slotarray(ChannelBuffer in) {
        int len = DataType.mc_slotarray.size(in, in.readerIndex());
        byte[] result = new byte[len];
        in.readBytes(result, 0, len);
        return result;
    }

    public static String mc_string(ChannelBuffer in) {
        int len = 2 * in.readShort();
        String result = in.toString(in.readerIndex(), len, CharsetUtil.UTF_16BE);
        in.readerIndex(in.readerIndex() + len);
        return result;
    }

    public static void mc_string(ChannelBuffer out, String v) {
        ChannelBuffer str = ChannelBuffers.copiedBuffer(v, CharsetUtil.UTF_16BE);
        out.writeShort(v.length());
        out.writeBytes(str);
    }
}
