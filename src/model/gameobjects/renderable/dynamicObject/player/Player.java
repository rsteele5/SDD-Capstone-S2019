package model.gameobjects.renderable.dynamicObject.player;

import control.physics.Kinematic;
import model.gameobjects.renderable.RenderableObject;
import model.gameobjects.renderable.dynamicObject.DynamicObject;

import java.awt.image.BufferedImage;

public class Player extends RenderableObject implements Kinematic, DynamicObject {

    public Player(String imagePath, int drawLayer) {
        super(imagePath, drawLayer);
    }

    public Player(int x, int y, String imagePath, int drawLayer) {
        super(x,y, imagePath, drawLayer);
    }

    public Player(int x, int y, String imagePath, BufferedImage image, int drawLayer) {
        super(x,y, imagePath, image, drawLayer);
    }

    @Override
    public void update() {

    }

    @Override
    public void setState(Object o) {

    }

    @Override
    public Object getState() {
        return null;
    }

    @Override
    public void nextState() {

    }
}
