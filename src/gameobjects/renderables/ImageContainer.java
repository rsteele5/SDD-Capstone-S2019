package gameobjects.renderables;

import gameobjects.renderables.RenderableObject;

import java.awt.image.BufferedImage;

public class ImageContainer extends RenderableObject {

    public ImageContainer() {
        super();
    }

    public ImageContainer(int x, int y) {
        super(x, y);
    }

    public ImageContainer(int x, int y, BufferedImage currentImage, int drawLayer) {
        super(x, y, currentImage, drawLayer);
    }

    @Override
    public void update() {

    }

}
