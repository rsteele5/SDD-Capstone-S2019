package model.gameobjects.renderable.dynamicObject.enemy.floatingeye;

import model.gameobjects.renderable.dynamicObject.DynamicObject;
import model.gameobjects.renderable.dynamicObject.enemy.Enemy;
import utilities.Debug;
import utilities.DebugEnabler;

import java.awt.image.BufferedImage;

public class FloatingEye extends Enemy implements DynamicObject<String> {

    public FloatingEye(String imagePath, int drawLayer) {
        super(imagePath, drawLayer);
    }

    public FloatingEye(int x, int y, String imagePath, int drawLayer) {
        super(x, y, imagePath, drawLayer);
    }

    public FloatingEye(int x, int y, String imagePath, BufferedImage image, int drawLayer) {
        super(x, y, imagePath, image, drawLayer);
    }

    @Override
    public void setState(String o) {

    }

    @Override
    public String getState() {
        return null;
    }

    @Override
    public void nextState() {

    }

    @Override
    public void update() {
        Debug.success(DebugEnabler.UPDATE_LOG, "FloatingEye -> I am being updated!");
    }
}
