package model.gameobjects;

import control.physics.Kinematic;

public class Enemy extends DynamicObject implements Kinematic {

    public Enemy(int x, int y) {
        super(x, y);
    }

    @Override
    public void update() {

    }
}
