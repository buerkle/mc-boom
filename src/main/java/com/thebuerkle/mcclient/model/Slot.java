package com.thebuerkle.mcclient.model;

import com.google.common.base.Objects;

import org.apache.mina.core.buffer.IoBuffer;

public class Slot {
    public static int size(IoBuffer in, int position) {
        int remaining = in.limit() - position;

        if (remaining < 2) {
            return -1; // no room for id
        }

        int id = in.getShort(position);
        if (id == -1) {
            return 2;
        }

        if (remaining < 7) {
            return -1; // ID, count, damage, len
        }

        int len = in.getShort(position+5);
        if (len == -1) {
            return 7;
        }
        if (remaining < 7 + len) {
            return -1;
        }
        return 7 + len;
    }

    public static Slot decode(IoBuffer in) {
        int id = in.getShort();

        if (id == -1) {
            return EMPTY_SLOT;
        }

        int itemCount = in.get();
        short damage = in.getShort();
        int len = in.getShort();
        byte[] data = null;
        if (len >= 0) {
            data = new byte[len];
            in.get(data);
        }

        return new Slot(id, itemCount, damage, data);
    }

    private static final Slot EMPTY_SLOT = new Slot(-1, 0, (short) -1, new byte[0]);

    private int _id;
    private int _itemCount;
    private short _damage;
    private byte[] _data;

    public Slot(int id, int itemCount, short damage, byte[] data) {
        _id = id;
        _itemCount = itemCount;
        _damage = damage;
        _data = data;
    }

    public String toString() {
        return Objects.toStringHelper(this).
            add("ID", _id).
            add("Item count", _itemCount).
            add("Damage", _damage).
            add("Data length", (_data == null ? 0 : _data.length)).
            toString();
    }
}


