package com.thebuerkle.mcclient;

import com.google.common.util.concurrent.AbstractExecutionThreadService;
import com.thebuerkle.mcclient.response.ChunkDataResponse;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

public class ChunkManager extends AbstractExecutionThreadService {

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
                int size = Integer.bitCount(response.primaryBitMap) * (4096 + 2048*4);

                if (response.groundUp) {
                    size += 256;
                }

                byte[] data = new byte[size];

                _inflater.reset();
                _inflater.setInput(response.chunk);
                int result = _inflater.inflate(data);
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
        void onChunkReady();
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
