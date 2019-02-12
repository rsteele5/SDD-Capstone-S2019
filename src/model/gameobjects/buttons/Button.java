package model.gameobjects.buttons;

import control.ScreenManager;
import model.gameobjects.RenderableObject;

import java.awt.image.BufferedImage;

public abstract class Button extends RenderableObject {

    public Button(int x, int y) {
        super(x, y);
    }

    public Button(int x, int y, BufferedImage image, int drawLayer) {
        super(x, y, image, drawLayer);
    }

    @Override
    public void update() {

    }

    public abstract void onClick(ScreenManager screen);
}
