package com.thebuerkle.mcclient.model;
import com.google.common.base.Objects;

/**
 * A 16x16 region of the map. Consists of 16 chunks stacked vertically.
 */
public class Region {

    private final Chunk[] _chunks = new Chunk[16];

    private final int _x;
    private final int _z;

    public Region(IntVec3 position) {
        _x = position.x;
        _z = position.z;
    }

    public int getX() {
        return _x;
    }

    public int getZ() {
        return _z;
    }

    public Chunk get(int y) {
        return _chunks[y / 16];
    }

    public void add(Chunk chunk) {
        _chunks[chunk.getPosition().y / 16] = chunk;
    }

    public void remove(Chunk chunk) {
        _chunks[chunk.getPosition().y / 16] = null;
    }

    @Override()
    public boolean equals(Object o) {
        if (o instanceof Region) {
            Region r = (Region) o;
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
