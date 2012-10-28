package com.thebuerkle.mcclient.model;

public class Player {

    public static final double HEIGHT = 1.62;

    private final int _id;

    private final World _world;

    private Vec3 _position;
    private Vec3 _velocity = new Vec3();
    private boolean _onGround;
    private float _stance;

    public Player(int id) {
        _id = id;
        _world = new World();
    }

    public World getWorld() {
        return _world;
    }

    public int getId() {
        return _id;
    }

    public void setOnGround(boolean v) {
        _onGround = v;
    }

    public boolean isOnGround() {
        return _onGround;
    }

    public Vec3 getPosition() {
        return _position;
    }

    public Vec3 getVelocity() {
        return _velocity;
    }

    public double getVelocityY() {
        return _velocity.y;
    }

    public void setVelocityY(double y) {
        _velocity.y = y;
    }

    public void setPosition(Vec3 position) {
        _position = position;
    }

    public void setPosition(double x, double y, double z) {
        _position.x = x;
        _position.y = y;
        _position.z = z;
    }
}

