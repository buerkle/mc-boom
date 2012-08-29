package com.thebuerkle.mcclient.model;

import com.google.common.base.Objects;
import com.thebuerkle.mcclient.response.Response;

import java.util.ArrayList;
import java.util.List;

import org.apache.mina.core.buffer.IoBuffer;

public class EntityMetadata {

    public static int size(IoBuffer in, int position) {
        int newpos = position;

        while (in.limit() - newpos > 0) {
            byte id = in.get(newpos);
            newpos++;

            if (id == 0x7F) {
                return newpos - position;
            }

            int type = (id & 0xE0) >>> 5;
            int key = id & 0x1F;

            switch (type) {
            case 0: newpos += 1; break; // byte
            case 1: newpos += 2; break; // short
            case 2: newpos += 4; break; // int
            case 3: newpos += 4; break; // float
            case 4:
                if (in.limit() - newpos >= 2) {
                    int len = in.getShort(newpos);
                    newpos += 2 + len;
                }
                break; // string
            case 5: newpos += 5; break; // short, byte, short
            case 6: newpos += 12; break; // int, int, int
            }
        }
        return -1;
    }

    public static List<EntityMetadata> decode(IoBuffer in) {
        byte current = in.get();
        List<EntityMetadata> result = new ArrayList<EntityMetadata>();

        while (current != 0x7F) {
            int type = (current & 0xE0) >>> 5;
            int id = current & 0x1F;
            Object value = null;

            switch (type) {
            case 0: value = in.get(); break;
            case 1: value = in.getShort(); break;
            case 2: value = in.getInt(); break;
            case 3: value = in.getFloat(); break;
            case 4: value = Response.mc_string(in); break;
            case 5:
                value = new Object[]{in.getShort(), in.get(), in.getShort()};
                break;
            case 6:
                value = new Object[]{in.getInt(), in.getInt(), in.getInt()};
                break;
            }
            result.add(new EntityMetadata(id, type, value));

            current = in.get();
        }
        return result;
    }

    private int _id;
    private int _type;
    private Object _value;

    public EntityMetadata(int id, int type, Object value) {
        _id = id;
        _type = type;
        _value = value;
    }

    public String toString() {
        return Objects.toStringHelper(this).
            add("ID", _id).
            add("Type", _type).
            add("Value", _value).
            toString();
    }
}


