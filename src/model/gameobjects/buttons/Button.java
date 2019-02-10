package model.gameobjects.buttons;

import model.gameobjects.Renderable;

import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public abstract class Button extends Renderable {

    public Button(int x, int y) {
        super(x, y);
    }

    public Button(int x, int y, BufferedImage image, int drawLayer) {
        super(x, y, image, drawLayer);
    }

    @Override
    public void update() {

    }

    public abstract void onClick(MouseEvent e);
}
