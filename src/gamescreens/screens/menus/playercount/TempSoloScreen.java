package gamescreens.screens.menus.playercount;

import gameengine.rendering.RenderEngine;
import gamescreens.DrawLayer;
import gamescreens.GameScreen;
import gamescreens.ScreenManager;
import gameobjects.renderables.ImageContainer;
import gameobjects.renderables.RenderableObject;
import gameobjects.renderables.buttons.Button;
import main.utilities.Debug;
import main.utilities.DebugEnabler;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;


public class TempSoloScreen extends GameScreen {
    //region <Variables>
    protected CopyOnWriteArrayList<Button> buttons = new CopyOnWriteArrayList<>();

    private final int X_INIT_BUTTON = 64;
    private final int Y_INIT_BUTTON = 576;
    private final int WIDTH_BUTTON = 256;
    private final int X_BUFFER = 48;
    private GameScreen covering;
    //endregion

    //region <Construction and Initialization>
    public TempSoloScreen(ScreenManager screenManager, GameScreen covering) {
        super(screenManager, "TempSoloScreen");
        isExclusive = true;
        this.covering = covering;
    }

    @Override
    protected void initializeScreen() {
        //Create buttons
        addObject(new Button(X_INIT_BUTTON+3*(X_BUFFER+WIDTH_BUTTON),Y_INIT_BUTTON,
                "/assets/buttons/Button-Back.png",
                DrawLayer.Entity,
                (screenManager) ->{
                    Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - Back");
                    this.setScreenState(ScreenState.TransitionOff);
                }));

        //Create Background on layer 0
        addObject(new ImageContainer(0,0, "/assets/backgrounds/BG-TempSolo.png", DrawLayer.Background));
    }

    //endregion

    @Override
    protected void transitionOn() {
        defaultTransitionOn();
    }

    @Override
    protected void transitionOff() {
        exiting = true;
    }

    @Override
    protected void hiddenUpdate() {
//        if(!screenManager.coveredByOverlay(this))
//            currentState = ScreenState.TransitionOff;
//        else
//            activeUpdate();
    }

    @Override
    protected void activeUpdate() {
    }
}
