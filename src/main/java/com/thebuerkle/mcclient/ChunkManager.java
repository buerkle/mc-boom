package com.thebuerkle.mcclient;

import com.google.common.util.concurrent.AbstractExecutionThreadService;
import com.thebuerkle.mcclient.model.Chunk;
import com.thebuerkle.mcclient.model.IntVec3;
import com.thebuerkle.mcclient.response.ChunkDataResponse;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

public class ChunkManager extends AbstractExecutionThreadService {

    // Block type array, 4096
    // Block metadata array, 2048
    // Block light array, 2048
    // Sky light array, 2048
    // Add array, 2048
    private final static int SECTION_SIZE = 4096 + 2048 * 4;

    private final static int BLOCK_DATA_SIZE = 16 * 16 * 16;

    private final static int BIOME_SIZE = 256;

    private final static Item STOP = new Item(null, null);

    private final BlockingQueue<Item> _queue = new LinkedBlockingQueue<Item>();

    private final Inflater _inflater = new Inflater();

    public void submit(Callback callback, ChunkDataResponse response) {
        if (!isRunning()) {
            return;
        }

        try {
            _queue.put(new Item(callback, response));
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override()
    protected void triggerShutdown() {
        _queue.offer(STOP);
    }

    @Override()
    protected void run() {
        Item item = null;

        try {
            while ((item = _queue.take()) != STOP) {
                ChunkDataResponse response = item.response;
                int sections = 0;
                int size = Integer.bitCount(response.primaryBitMap) * SECTION_SIZE;

                if (response.groundUp) {
                    size += BIOME_SIZE;
                }

                byte[] data = new byte[size];

                _inflater.reset();
                _inflater.setInput(response.chunk);
                int result = _inflater.inflate(data);

                int x = response.x * 16;
                int z = response.z * 16;
                int offset = 0;

                for (int y = 0; y < 16; y++) {
                    IntVec3 position = new IntVec3(x, y * 16, z);

                    if ((response.primaryBitMap & (1 << y)) != 0) {
                        boolean addData = ((response.addBitMap & (1 << y)) != 0);
                        boolean biome = response.groundUp;

                        if (response.primaryBitMap > 1) {
                            System.err.println("offset: " + offset);
                        }
                        item.callback.onChunkLoad(new Chunk(position, offset, data, addData, biome));

                        offset += BLOCK_DATA_SIZE;
                        if (biome && y == 0) {
/*                          offset += BIOME_SIZE;*/
                        }
                    }
                }

/*              System.err.println(response.x * 16 + ", " + response.z * 16);*/
/*              System.err.println("Primary bit map: " + Integer.toBinaryString(response.primaryBitMap));*/
/*              System.err.println("Result: " + response.x + ", " + response.z + ": " + result*/
/*                  + ", " + data.length                                                      */
/*                  + ", " + response.primaryBitMap);                                         */
            }
        }
        catch (DataFormatException e) {
            e.printStackTrace();
        }
        catch (InterruptedException e) {
        }
    }

    public static interface Callback {
        void onChunkLoad(Chunk chunk);
    }

    private static class Item {
        public final ChunkDataResponse response;
        public final Callback callback;

        public Item(Callback callback, ChunkDataResponse response) {
            this.callback = callback;
            this.response = response;
        }
    }
}
