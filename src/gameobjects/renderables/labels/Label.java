package gameobjects.renderables.labels;

import gameobjects.renderables.RenderableObject;
import gamescreens.DrawLayer;

import java.awt.image.BufferedImage;

public class Label extends RenderableObject {

    public boolean isActive;

    public Label(int x, int y) {
        super(x, y);
    }

    public Label(int x, int y, String imagePath, DrawLayer drawLayer, boolean isActive) {
        super(x, y, imagePath, drawLayer);
        this.isActive = isActive;
    }

    @Override
    public void update() {

    }

    public void setActive(boolean activeState) {
        isActive = activeState;
    }
}
