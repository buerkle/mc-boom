package com.thebuerkle.mcclient.model;

public enum GameMode {
    Survival(0), Creative(1);

    public static GameMode fromValue(int v) {
        switch (v) {
        case 0: return Survival;
        case 1: return Creative;
        }
        throw new IllegalArgumentException("Unknown game mode: " + v);
    }

    private final int _value;

    private GameMode(int value) {
        _value = value;
    }

    public int getValue() {
        return _value;
    }
}
