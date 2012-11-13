package com.thebuerkle.mcclient.model;

// A 16x16x16 portion of blocks
public class Section {
    public final static int LENGTH = 16;

    public final static int NUM_BLOCKS = LENGTH * LENGTH * LENGTH;

    private final IntVec3 _position;
    private final int _offset;
    private final byte[] _data;
    private final boolean _addData;
    private final boolean _biome;

    public Section(IntVec3 position, int offset, byte[] data, boolean addData, boolean biome) {
        _position = position;
        _offset = offset;
        _data = data;
        _addData = addData;
        _biome = biome;
    }

    public IntVec3 getPosition() {
        return _position;
    }

    public void blockChange(IntVec3 position, int type, int metadata) {
        _data[indexOf(position.x, position.y, position.z)] = (byte) type;
    }

    public boolean contains(int x, int y, int z) {
        return(x >= _position.x && x < _position.x + LENGTH
               && y >= _position.y && y < _position.y + LENGTH
               && z >= _position.z && z < _position.z + LENGTH);
    }

    public int blockType(int x, int y, int z) {
        int index = indexOf(x, y, z) + _offset;
        if (index < _data.length) {
            return _data[index];
        }
        return -1;
    }

    public int indexOf(int x, int y, int z) {
        int cx = x % LENGTH;
        int cy = y % LENGTH;
        int cz = z % LENGTH;
        return cx + (cz * LENGTH) + (cy * LENGTH * LENGTH);
    }
}
