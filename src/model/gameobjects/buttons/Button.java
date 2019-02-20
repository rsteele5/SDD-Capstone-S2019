package model.gameobjects.buttons;

import control.ScreenManager;
import model.gameobjects.RenderableObject;

import java.awt.image.BufferedImage;
import java.util.function.Consumer;

public class Button extends RenderableObject {

    public Button(int x, int y) {
        super(x, y);
    }

    public Button(int x, int y, BufferedImage image, int drawLayer, Consumer<ScreenManager> handleOnClick) {
        super(x, y, image, drawLayer);
        onClick = handleOnClick;
    }
    public Button(int x, int y, BufferedImage image, int drawLayer) {
        super(x, y, image, drawLayer);
    }

    @Override
    public void update() { }

    public Consumer<ScreenManager> onClick;

    public void setOnClick(Consumer<ScreenManager> onClick) {
        this.onClick = onClick;
    }
}
