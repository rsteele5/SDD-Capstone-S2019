package model.gameobjects.labels;

import model.gameobjects.RenderableObject;
import java.awt.image.BufferedImage;

public class Label extends RenderableObject {

    public Label(int x, int y) {
        super(x, y);
    }

    public Label(int x, int y, BufferedImage image, int drawLayer) {
        super(x, y, image, drawLayer);
    }

    @Override
    public void update() {

    }
}
