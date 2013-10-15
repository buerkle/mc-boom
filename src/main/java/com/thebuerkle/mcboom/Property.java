package com.thebuerkle.mcboom;

import com.google.common.base.Objects;

import java.util.List;

public class Property {

    public final String key;
    public final double value;
    public List<Modifier> attributes;

    public Property(String key, double value, List<Modifier> attributes) {
        this.key = key;
        this.value = value;
        this.attributes = attributes;
    }

    @Override()
    public String toString() {
        return Objects.toStringHelper(this)
            .add("key", key)
            .add("value", value)
            .add("attributes", attributes)
            .toString();
    }
}
