package gamescreens.screens.menus.options;

import gamescreens.DrawLayer;
import gamescreens.GameScreen;
import gamescreens.ScreenManager;
import gameobjects.renderables.ImageContainer;
import gameobjects.renderables.buttons.Button;
import main.utilities.Debug;
import main.utilities.DebugEnabler;


public class OptionScreen extends GameScreen {
    //region <Variables>
    private final int X_INIT_BUTTON = 64;
    private final int Y_INIT_BUTTON = 576;
    private final int WIDTH_BUTTON = 256;
    private float alphaTransition = 0.0f;
    private final int X_BUFFER = 48;
    //endregion

    //region <Construction and Initialization>
    public OptionScreen(ScreenManager screenManager) {
        super(screenManager, "Options Menu", true);
    }
    @Override
    protected void initializeScreen() {

        //Create Background
        ImageContainer image;
        image = (new ImageContainer(0,0, "/assets/backgrounds/BG-OptionMenu.png", DrawLayer.Background));
        image.addToScreen(this,true);

        //Create buttons
        Button button;
        button = new Button(X_INIT_BUTTON,Y_INIT_BUTTON,
                "/assets/buttons/Button-Graphics.png",
                DrawLayer.Entity,
                (GameScreen) ->{
                    Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - Graphics");
                    screenManager.addScreen(new GraphicsScreen(screenManager));
                });
        button.addToScreen(this,true);

        button = new Button(X_INIT_BUTTON+X_BUFFER+WIDTH_BUTTON,Y_INIT_BUTTON,
                "/assets/buttons/Button-Sound.png",
                DrawLayer.Entity,
                (GameScreen) ->{
                    Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - Sound");
                    //TODO: Add Sound Menu
                });
        button.addToScreen(this,true);

        button = new Button(X_INIT_BUTTON+2*(X_BUFFER+WIDTH_BUTTON),Y_INIT_BUTTON,
                "/assets/buttons/Button-Controls.png",
                DrawLayer.Entity,
                (GameScreen) ->{
                    Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - Controls");
                    screenManager.addScreen(new ControlsScreen(screenManager));
                });
        button.addToScreen(this,true);

        button = new Button(X_INIT_BUTTON+3*(X_BUFFER+WIDTH_BUTTON),Y_INIT_BUTTON,
                "/assets/buttons/Button-MainMenu.png",
                DrawLayer.Entity,
                (GameScreen) ->{
                    Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - Main Menu");
                    this.setScreenState(ScreenState.TransitionOff);
                });
        button.addToScreen(this,true);


    }

    //endregion

    @Override
    protected void activeUpdate() {
    }
}