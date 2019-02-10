package _test.splashscreentest;

import model.gameobjects.GameObject;
import model.gameobjects.Renderable;

import java.awt.image.BufferedImage;

public class ImageContainer extends Renderable {

    public ImageContainer() {
        super();
        x = 0;
        y = 0;
        drawLayer = 0;
    }

    public ImageContainer(int x, int y) {
        this.x = x;
        this.y = y;
        drawLayer = 0;
    }

    @Override
    public void update() {

    }

    public ImageContainer(int x, int y, BufferedImage currentImage, int drawLayer) {
        this.x = x;
        this.y = y;
        this.currentImage = currentImage;
        this.drawLayer = drawLayer;
    }

}
