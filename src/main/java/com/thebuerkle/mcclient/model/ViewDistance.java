package com.thebuerkle.mcclient.model;

public enum ViewDistance {
    Far(0), Normal(1), Short(2), Tiny(3);

    public static ViewDistance fromValue(int v) {
        switch (v) {
        case 0: return Far;
        case 1: return Normal;
        case 2: return Short;
        case 3: return Tiny;
        }
        throw new IllegalArgumentException("Unknown view distance: " + v);
    }

    private final int _value;

    private ViewDistance(int value) {
        _value = value;
    }

    public int getValue() {
        return _value;
    }
}
