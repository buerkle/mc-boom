package com.thebuerkle.mcclient.model;
import javax.swing.text.Position;

import gnu.trove.map.hash.TLongObjectHashMap;

public class World {

    private static long key(IntVec3 position) {
        return key(position.x, position.z);
    }

    private static long key(int x, int z) {
        return (x << 32) | (z & 0XFFFFFFFFL);
    }

    private static long fromWorldToKey(int x, int z) {
        return key(x - (x % 16), z - (z % 16));
    }

    private final TLongObjectHashMap<Region> _regions = new TLongObjectHashMap<Region>();

    public void add(Chunk chunk) {
        IntVec3 position = chunk.getPosition();
        long key = key(position);
        Region region = _regions.get(key);

        if (region == null) {
            region = new Region(position);
            _regions.put(key, region);

            System.err.println("Add region: " + region);
        }
        region.add(chunk);
    }

    public void remove(Chunk chunk) {
    }

    public int blockType(Vec3 position) {
        int x = (int) position.x;
        int y = (int) position.y;
        int z = (int) position.z;

        Chunk chunk = fromWorld(x, y, z);
        if (chunk != null) {
            System.err.println("Found chunk: " + chunk);
            return chunk.blockType(x, y, z);
        }
        return -1;
    }

    private int toRegionCoord(int value) {
        return value - (value % 16);
    }

    private Chunk fromWorld(int x, int y, int z) {
        Region region = _regions.get(fromWorldToKey(x, z));
        if (region != null) {
            System.err.println("Found region: " + x + ", " + z);
            return region.get(y);
        }
        return null;
    }
}
