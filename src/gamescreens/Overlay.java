package gamescreens;

public abstract class Overlay extends GameScreen{

    protected GameScreen parentScreen;

    public Overlay(ScreenManager screenManager, GameScreen parentScreen, String name, int xPos, int yPos, float screenAlpha) {
        super(screenManager, "Overlay-" + name, false, xPos, yPos, screenAlpha);
        this.parentScreen = parentScreen;
    }
}
