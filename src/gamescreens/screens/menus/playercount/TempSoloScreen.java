package gamescreens.screens.menus.playercount;

import gamescreens.DrawLayer;
import gamescreens.GameScreen;
import gamescreens.ScreenManager;
import gameobjects.renderables.ImageContainer;
import gameobjects.renderables.buttons.Button;
import main.utilities.Debug;
import main.utilities.DebugEnabler;

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
        super(screenManager, "TempSoloScreen", true);
        this.covering = covering;
    }

    @Override
    protected void initializeScreen() {
        //Create buttons
        Button button = new Button(X_INIT_BUTTON+3*(X_BUFFER+WIDTH_BUTTON),Y_INIT_BUTTON,
                "/assets/buttons/Button-Back.png",
                DrawLayer.Entity,
                (GameScreen) ->{
                    Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - Back");
                    this.setScreenState(ScreenState.TransitionOff);
                });
        button.addToScreen(this,true);

        //Create Background on layer 0
        ImageContainer image = new ImageContainer(0,0, "/assets/backgrounds/BG-TempSolo.png", DrawLayer.Background);
        image.addToScreen(this,true);
    }

    //endregion
}
