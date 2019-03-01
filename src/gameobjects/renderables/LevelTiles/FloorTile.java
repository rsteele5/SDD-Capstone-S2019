package gameobjects.renderables.LevelTiles;

import gameengine.physics.Kinematic;
import gameengine.physics.PhysicsVector;
import gameobjects.renderables.ImageContainer;
import gamescreens.DrawLayer;

import java.awt.*;

public class FloorTile extends ImageContainer implements Kinematic {
    public static final int SIZE = 150;

    public FloorTile(int x, int y, String path) {
        super(x, y, path, DrawLayer.Entity);
        width = SIZE;
        height = SIZE;
    }

    @Override
    public void update() {

    }

    @Override
    public PhysicsVector getVelocity() {
        return new PhysicsVector(0,0);
    }

    @Override
    public void setVelocity(PhysicsVector pv) {
    }

    @Override
    public PhysicsVector getAcceleration() {
        return new PhysicsVector(0,0);
    }

    @Override
    public void setAcceleration(PhysicsVector pv) {
    }

    @Override
    public Rectangle getHitbox() {
        return new Rectangle(x, y, width, height);

    }
}
