package model.gameobjects;

import control.physics.Kinematic;

public class Player extends DynamicObject implements Kinematic {

    public Player(int x, int y) {
        super(x, y);
    }

    @Override
    public void update() {

    }
}
