package com.thebuerkle.mcboom;

import com.google.common.base.Objects;

import java.util.UUID;

public class Modifier {
    public final UUID id;
    public final double amount;
    public final int operation;

    public Modifier(UUID id, double amount, int operation) {
        this.id = id;
        this.amount = amount;
        this.operation = operation;
    }

    @Override()
    public String toString() {
        return Objects.toStringHelper(this)
            .add("UUID", id)
            .add("Amount", amount)
            .add("Operation", operation)
            .toString();
    }
}
