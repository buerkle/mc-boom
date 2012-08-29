package com.thebuerkle.mcclient.model;

public enum Difficulty {
    Peaceful(0), Easy(1), Normal(2), Hard(3);

    public static Difficulty fromValue(int v) {
        switch (v) {
        case 0: return Peaceful;
        case 1: return Easy;
        case 2: return Normal;
        case 3: return Hard;
        }
        throw new IllegalArgumentException("Unknown difficulty: " + v);
    }

    private final int _value;

    private Difficulty(int value) {
        _value = value;
    }

    public int getValue() {
        return _value;
    }
}
