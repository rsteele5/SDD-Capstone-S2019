package model.gameobjects;

import model.gameobjects.GameObject;
import model.gameobjects.Renderable;

import java.awt.image.BufferedImage;

public class ImageContainer extends Renderable {

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
