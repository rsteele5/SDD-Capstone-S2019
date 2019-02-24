package gameobjects.renderables;


import gamescreens.DrawLayer;

import java.awt.image.BufferedImage;

public class ImageContainer extends RenderableObject {

    public ImageContainer(int x, int y, BufferedImage currentImage, DrawLayer drawLayer) {
        super(x, y, currentImage, drawLayer);
    }

    public ImageContainer(int x, int y, String imagePath, DrawLayer drawLayer) {
        super(x, y, imagePath, drawLayer);
    }

    @Override
    public void update() {

    }

}
