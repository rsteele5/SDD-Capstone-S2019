package gameobjects.renderables.buttons;

import gamescreens.ScreenManager;
import gameobjects.renderables.RenderableObject;
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

    @Override
    public void update() { }

    public Consumer<ScreenManager> onClick;
}
