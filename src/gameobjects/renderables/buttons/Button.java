package gameobjects.renderables.buttons;

import gamescreens.DrawLayer;
import gamescreens.ScreenManager;
import gameobjects.renderables.RenderableObject;
import java.awt.image.BufferedImage;
import java.util.function.Consumer;

public class Button extends RenderableObject {

    public Button(int x, int y) {
        super(x, y);
    }

    public Button(int x, int y, String imagePath, DrawLayer drawLayer) {
        super(x, y, drawLayer);
        this.imagePath = imagePath;
    }

    public Button(int x, int y, DrawLayer drawLayer, Consumer<ScreenManager> handleOnClick) {
        super(x, y, drawLayer);
        onClick = handleOnClick;
    }

    public Button(int x, int y, String imagePath, DrawLayer drawLayer, Consumer<ScreenManager> handleOnClick) {
        super(x, y, imagePath, drawLayer);
        onClick = handleOnClick;
    }

    public Button(int x, int y, String imagePath, DrawLayer drawLayer, float alpha, Consumer<ScreenManager> handleOnClick) {
        super(x, y, imagePath, drawLayer);
        onClick = handleOnClick;
    }

    @Override
    public void update() { }

    public Consumer<ScreenManager> onClick;
}
