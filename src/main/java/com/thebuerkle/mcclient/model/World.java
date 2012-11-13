package com.thebuerkle.mcclient.model;
import javax.swing.text.Position;

import gnu.trove.map.hash.TLongObjectHashMap;

public class World {

    private static long key(IntVec3 position) {
        return key(position.x, position.z);
    }

    private static long key(Chunk chunk) {
        return key(chunk.getX(), chunk.getZ());
    }

    private static long key(int x, int z) {
        return ((long) x << 32) | (z & 0XFFFFFFFFL);
    }

    private static long fromWorldToKey(int x, int z) {
        return key(x - (x % 16), z - (z % 16));
    }

    private final TLongObjectHashMap<Chunk> _chunks = new TLongObjectHashMap<Chunk>();

    public void add(Chunk chunk) {
        _chunks.put(key(chunk), chunk);
    }

    public void remove(Chunk chunk) {
        _chunks.remove(key(chunk));
    }

    public void blockChange(IntVec3 position, int type, int metadata) {
        Section section = fromWorld(position.x, position.y, position.z);
        if (section != null) {
            section.blockChange(position, type, metadata);
        }
    }

    public int blockType(double x, double y, double z) {
        int ix = (int) x;
        int iy = (int) y;
        int iz = (int) z;
        Section section = fromWorld(ix, iy, iz);
        if (section != null) {
            return section.blockType(ix, iy, iz);
        }
        return -1;
    }

    public int blockType(Vec3 position) {
        return blockType(position.x, position.y, position.z);
    }

    private int toRegionCoord(int value) {
        return value - (value % 16);
    }

    private Section fromWorld(int x, int y, int z) {
        Chunk chunk = _chunks.get(fromWorldToKey(x, z));
        if (chunk != null) {
            return chunk.get(y);
        }
        return null;
    }
}
