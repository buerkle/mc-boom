package com.thebuerkle.mcclient.model;
import com.google.common.base.Objects;

public class IntVec3 {
    public int x;
    public int y;
    public int z;

    public IntVec3(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override()
    public String toString() {
        return Objects.toStringHelper(this).
            add("X", x).
            add("Y", y).
            add("Z", z).
            toString();
    }
}
