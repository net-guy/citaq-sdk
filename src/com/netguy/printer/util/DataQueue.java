package com.netguy.printer.util;

import java.util.LinkedList;

public class DataQueue {
    
    // Tag for logging.
    private static final String TAG = "DataQueue";
    
    // LinkedList to hold the queue of byte arrays.
    private final LinkedList<byte[]> list = new LinkedList<>();

    /**
     * Clears the queue.
     */
    public void clear() {
        list.clear();
    }

    /**
     * Checks if the queue is empty.
     * @return True if the queue is empty, otherwise false.
     */
    public boolean isEmpty() {
        return list.isEmpty();
    }

    /**
     * Adds an element to the end of the queue.
     * 
     * @param cmd            The data to add to the queue.
     * @param needSubpackage Indicates whether the data should be broken into sub-packages.
     * @param len            Length of each sub-package.
     */
    public void enqueue(byte[] cmd, boolean needSubpackage, int len) {
        synchronized(list) {
            if (needSubpackage) {
                int offset = 0;
                int total = cmd.length;

                while ((offset + len) <= total) {
                    byte[] printText = new byte[len];
                    System.arraycopy(cmd, offset, printText, 0, len);
                    list.addLast(printText);
                    offset += len;
                }
                
                if (offset < total) {
                    byte[] printText = new byte[total - offset];
                    System.arraycopy(cmd, offset, printText, 0, total - offset);
                    list.addLast(printText);
                }		
            } else {
                list.addLast(cmd);
            }
        } 
    }

    /**
     * Removes and returns the first element of the queue.
     * 
     * @return The first element in the queue or null if the queue is empty.
     */
    public byte[] dequeue() {
        synchronized(list) {
            if (!list.isEmpty()) {
                // Optional: log the current queue length.
                // Log.i(TAG, "Queue length: " + getLength());
                
                return list.removeFirst();
            }
        }
        return null;
    }

    /**
     * Retrieves the number of elements in the queue.
     * 
     * @return The size of the queue.
     */
    public int getLength() {
        return list.size();
    }

    /**
     * Peeks the first element of the queue without removing it.
     * 
     * @return The first element in the queue.
     */
    public byte[] peek() {
        return list.getFirst();
    }
}
