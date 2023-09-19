package com.netguy.printer.util;

// Simplified Bundle parameter callback interface
public interface CallbackUSB {
    void callback(String str, boolean toShow);
    void hasUSB(boolean hasUSB);
}
