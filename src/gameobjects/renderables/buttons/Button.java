package gameobjects.renderables.buttons;

import gamescreens.DrawLayer;
import gamescreens.GameScreen;
import gamescreens.ScreenManager;
import gameobjects.renderables.RenderableObject;
import gameobjects.Clickable;
import main.utilities.Debug;

import java.util.function.Consumer;

public class Button extends RenderableObject implements Clickable{
    //public static ButtonType type
    public Button(int x, int y, String imagePath, DrawLayer drawLayer) {
        super(x, y, imagePath, drawLayer);
    }

    public Button(int x, int y, String imagePath, DrawLayer drawLayer, Consumer<GameScreen> handleOnClick) {
        this(x, y, imagePath, drawLayer);
        onClick = handleOnClick;
    }

    @Override
    public void update() { }

    public Consumer<GameScreen> onClick;

    @Override
    public void onClick(GameScreen thing) {
        onClick.accept(thing);
    }

    @Override
    public void setOnClick(Consumer<GameScreen> onClick) {
        this.onClick = onClick;
    }

    @Override
    public boolean contains(int x, int y) {
        return getBoundingBox().contains(x,y);
    }
}
