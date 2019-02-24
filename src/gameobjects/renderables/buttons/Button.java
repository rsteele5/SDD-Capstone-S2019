package gameobjects.renderables.buttons;

import gamescreens.DrawLayer;
import gamescreens.ScreenManager;
import gameobjects.renderables.RenderableObject;
import gameobjects.Clickable;
import main.utilities.Debug;

import java.util.function.Consumer;

public class Button extends RenderableObject implements Clickable<ScreenManager> {

    public Button(int x, int y, String imagePath, DrawLayer drawLayer) {
        super(x, y, imagePath, drawLayer);
    }

    public Button(int x, int y, String imagePath, DrawLayer drawLayer, Consumer<ScreenManager> handleOnClick) {
        this(x, y, imagePath, drawLayer);
        onClick = handleOnClick;
    }

    @Override
    public void update() { }

    public Consumer<ScreenManager> onClick;

    @Override
    public void onClick(ScreenManager screenManager) {
        onClick.accept(screenManager);
    }

    @Override
    public void setOnClick(Consumer<ScreenManager> onClick) {
        this.onClick = onClick;
    }

    @Override
    public boolean contains(int x, int y) {
        return getBoundingBox().contains(x,y);
    }
}
