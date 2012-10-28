package com.thebuerkle.mcclient.model;

public class Chunk {
    private final IntVec3 _position;
    private final int _offset;
    private final byte[] _data;
    private final boolean _addData;
    private final boolean _biome;

    public Chunk(IntVec3 position, int offset, byte[] data, boolean addData, boolean biome) {
        _position = position;
        _offset = offset;
        _data = data;
        _addData = addData;
        _biome = biome;
    }

    public IntVec3 getPosition() {
        return _position;
    }

    public boolean contains(int x, int y, int z) {
        return(x >= _position.x && x < _position.x + 16
               && y >= _position.y && y < _position.y + 16
               && z >= _position.z && z < _position.z + 16);
    }

    public int blockType(int x, int y, int z) {
        int cx = x % 16;
        int cy = y % 16;
        int cz = z % 16;
        return _data[indexOf(cx, cy, cz) + _offset];
    }

    public int indexOf(int cx, int cy, int cz) {
        return cx + (cz * 16) + (cy * 16 * 16);
    }
}
