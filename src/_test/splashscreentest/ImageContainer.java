package _test.splashscreentest;

import model.gameobjects.renderable.RenderableObject;

import java.awt.image.BufferedImage;

public class ImageContainer extends RenderableObject {

    public ImageContainer(String imagePath, int drawLayer) {
        super(imagePath, drawLayer);
    }

    public ImageContainer(int x, int y, String imagePath, int drawLayer) {
        super(x,y, imagePath, drawLayer);
    }

    public ImageContainer(int x, int y, String imagePath, BufferedImage image, int drawLayer) {
        super(x,y, imagePath, image, drawLayer);
    }

    public ImageContainer(int x, int y, BufferedImage image, int drawLayer) {
        super(x,y, image, drawLayer);
    }

    @Override
    public void update() {

    }

}
