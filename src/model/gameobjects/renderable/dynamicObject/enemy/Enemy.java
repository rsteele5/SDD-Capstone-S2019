package model.gameobjects.enemy;

import control.physics.Kinematic;
import model.gameobjects.DynamicObject;

public class Enemy extends DynamicObject implements Kinematic {

    public Enemy(int x, int y) {
        super(x, y);
    }

    @Override
    public void update() {

    }
}
