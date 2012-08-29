package com.thebuerkle.mcclient.model;

public enum LevelType {
    Default("default"), Flat("flat"), LargeBiomes("largeBiomes");

    public static LevelType fromValue(String v) {
        if (Default.getValue().equals(v)) {
            return Default;
        }
        else if (Flat.getValue().equals(v)) {
            return Flat;
        }
        else if (LargeBiomes.getValue().equals(v)) {
            return LargeBiomes;
        }
        throw new IllegalArgumentException("Unknown level type: " + v);
    }

    private final String _value;

    private LevelType(String value) {
        _value = value;
    }

    public String getValue() {
        return _value;
    }
}
