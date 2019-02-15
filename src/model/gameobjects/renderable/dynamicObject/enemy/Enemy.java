package model.gameobjects.renderable.dynamicObject.enemy;

import control.physics.Kinematic;
import model.gameobjects.renderable.RenderableObject;
import model.gameobjects.renderable.dynamicObject.DynamicObject;

import java.awt.image.BufferedImage;

public class Enemy extends RenderableObject implements Kinematic {

    public Enemy(String imagePath, int drawLayer) {
        super(imagePath, drawLayer);
    }

    public Enemy(int x, int y, String imagePath, int drawLayer) {
        super(x, y, imagePath, drawLayer);
    }

    public Enemy(int x, int y, String imagePath, BufferedImage image, int drawLayer) {
        super(x, y, imagePath, image, drawLayer);
    }

    @Override
    public void update() {

    }
}
