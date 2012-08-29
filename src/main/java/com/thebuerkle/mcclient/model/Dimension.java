package com.thebuerkle.mcclient.model;

public enum Dimension {
    Overworld(0), Nether(-1), TheEnd(1);

    public static Dimension fromValue(int v) {
        switch (v) {
        case 0: return Overworld;
        case -1: return Nether;
        case 1: return TheEnd;
        }
        throw new IllegalArgumentException("Unknown dimension: " + v);
    }


    private final int _value;

    private Dimension(int value) {
        _value = value;
    }

    public int getValue() {
        return _value;
    }
}
