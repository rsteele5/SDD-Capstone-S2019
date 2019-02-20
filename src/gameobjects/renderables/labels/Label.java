package gameobjects.renderables.labels;

import gameobjects.renderables.RenderableObject;
import java.awt.image.BufferedImage;

public class Label extends RenderableObject {

    public boolean isActive;

    public Label(int x, int y) {
        super(x, y);
    }

    public Label(int x, int y, BufferedImage image, int drawLayer, boolean isActive) {
        super(x, y, image, drawLayer);
        this.isActive = isActive;
    }

    @Override
    public void update() {

    }

    public void setActive(boolean activeState) {
        isActive = activeState;
    }
}
