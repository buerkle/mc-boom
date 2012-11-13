package com.thebuerkle.mcclient.model;

import com.google.common.base.Objects;

public class Chunk {
    public final static int WIDTH = 16;
    public final static int LENGTH = 16;
    public final static int HEIGHT = 256;

    public final static int NUM_BLOCKS = LENGTH * LENGTH * HEIGHT;

    public final static int NUM_SECTIONS = 16;

    private final int _x;
    private final int _z;

    private final Section[] _sections;

    public Chunk(int x, int z, Section[] sections) {
        _x = x;
        _z = z;
        _sections = sections;
    }

    public int getX() {
        return _x;
    }

    public int getZ() {
        return _z;
    }

    public Section get(int y) {
        return _sections[y / 16];
    }

    public void add(Section section) {
        _sections[section.getPosition().y / 16] = section;
    }

    public void remove(Section section) {
        _sections[section.getPosition().y / 16] = null;
    }

    @Override()
    public boolean equals(Object o) {
        if (o instanceof Chunk) {
            Chunk r = (Chunk) o;
            return _x == r._x && _z == r._z;
        }
        return false;
    }

    @Override()
    public int hashCode() {
        return 31 * (31 + _x) + _z;
    }

    @Override()
    public String toString() {
        return Objects.toStringHelper(this).
            add("X", _x).
            add("Z", _z).
            toString();
    }
}
