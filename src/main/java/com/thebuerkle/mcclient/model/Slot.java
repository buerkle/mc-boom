package com.thebuerkle.mcclient.model;

import com.google.common.base.Objects;

import org.apache.mina.core.buffer.IoBuffer;

public class Slot {
    public static int size(IoBuffer in, int position) {
        int remaining = in.limit() - position;

        if (remaining >= 2) {
            int id = in.getShort(position);

            if (id == -1) {
                return 2;
            }
            else if (remaining >= 7) {
                int len = in.getShort(position+5);
                if (len >= remaining - 7) {
                    return 7 + len;
                }
            }
        }
        return -1;
    }

    public static Slot decode(IoBuffer in) {
        int id = in.getShort();

        if (id == -1) {
            return EMPTY_SLOT;
        }

        int itemCount = in.get();
        int damage = in.getShort();
        byte[] data = new byte[in.getShort()];

        in.get(data);

        return new Slot(id, itemCount, damage, data);
    }

    private static final Slot EMPTY_SLOT = new Slot(-1, -1, -1, new byte[0]);

    private int _id;
    private int _itemCount;
    private int _damage;
    private byte[] _data;

    public Slot(int id, int itemCount, int damage, byte[] data) {
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
            add("Data length", _data.length).
            toString();
    }
}


