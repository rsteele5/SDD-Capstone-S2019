package gamescreens.screens.menus;

import gamescreens.DrawLayer;
import gamescreens.GameScreen;
import gamescreens.ScreenManager;
import gameobjects.renderables.ImageContainer;
import gameobjects.renderables.buttons.Button;
import gamescreens.screens.menus.dev.DevScreen;
import gamescreens.screens.menus.options.OptionScreen;
import gamescreens.screens.menus.playercount.PlayerCountScreen;
import main.utilities.Debug;
import main.utilities.DebugEnabler;


public class MainMenuScreen extends GameScreen {

    //region <Variables>
    private final int X_INIT_BUTTON = 64;
    private final int Y_INIT_BUTTON = 576;
    private final int WIDTH_BUTTON = 256;
    private final int X_BUFFER = 48;
    //endregion

    //region <Construction and Initialization>
    public MainMenuScreen(ScreenManager screenManager) {
        super(screenManager, "MainMenuScreen");
    }

    @Override
    protected void initializeScreen() {
        //Create Background on layer 0
        ImageContainer image;
        image = new ImageContainer(0,0, "/assets/backgrounds/BG-MainMenu.png", DrawLayer.Background);
        image.addToScreen(this,true);

        //Create buttons
        Button button;
        button = (new Button(X_INIT_BUTTON,Y_INIT_BUTTON,
                "/assets/buttons/Button-NewGame.png",
                DrawLayer.Entity,
                (GameScreen) ->{
                    Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - New Game");
                    screenManager.addScreen(new PlayerCountScreen(screenManager));
        }));
        button.addToScreen(this,true);

        button = (new Button(X_INIT_BUTTON+X_BUFFER+WIDTH_BUTTON,Y_INIT_BUTTON,
                "/assets/buttons/Button-Options.png",
                DrawLayer.Entity,
                (GameScreen) ->{
                    Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - Options");
                    screenManager.addScreen(new OptionScreen(screenManager));
        }));
        button.addToScreen(this,true);

        button = (new Button(X_INIT_BUTTON+2*(X_BUFFER+WIDTH_BUTTON),Y_INIT_BUTTON,
                "/assets/buttons/Button-Dev.png",
                DrawLayer.Entity,
                (GameScreen) ->{
                    Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - DevMode");
                    screenManager.addScreen(new DevScreen(screenManager));
        }));
        button.addToScreen(this,true);
    }
    //endregion

    //region <Update>
    @Override
    protected void transitionOn() {
        currentState = ScreenState.Active;
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
    protected void activeUpdate(){

    }

    //endregion

    //region <Support Functions>

    //endregion

}
