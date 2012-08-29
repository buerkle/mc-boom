package com.thebuerkle.mcclient.model;

import com.google.common.base.Objects;

public class Vec3 {
    public double x;
    public double y;
    public double z;

    public Vec3(IntVec3 o) {
        this(o.x, o.y, o.z);
    }

    public Vec3(double x, double y, double z) {
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

