package gamescreens.screens.menus.playercount;

import gamescreens.DrawLayer;
import gamescreens.GameScreen;
import gamescreens.ScreenManager;
import gameobjects.renderables.ImageContainer;
import gameobjects.renderables.buttons.Button;
import main.utilities.Debug;
import main.utilities.DebugEnabler;


public class PlayerCountScreen extends GameScreen {
    //region <Variables>
    private float alphaTransition = 0.0f;

    private final int X_INIT_BUTTON = 64;
    private final int Y_INIT_BUTTON = 576;
    private final int X_BUFFER = 48;
    private final int WIDTH_BUTTON = 256;
    private final int TEDDY_HEIGHT = 200;
    private final int HALF_TEDDY_WIDTH = 50;
    private final int QRTR_BUTTON_WIDTH = 65;

    //endregion

    //region <Construction and Initialization>
    public PlayerCountScreen(ScreenManager screenManager) {
        super(screenManager, "PlayerCountScreen");
        isExclusive = true;
    }

    @Override
    protected void initializeScreen() {
        addObject(new ImageContainer(0,0, "/assets/backgrounds/BG-PlayersMenu.png", DrawLayer.Background));

        //Create Buttons
        addObject(new Button(X_INIT_BUTTON+0*(X_BUFFER+WIDTH_BUTTON),Y_INIT_BUTTON,
                "/assets/buttons/Button-Solo.png",
                DrawLayer.Entity,
                (screenManager) ->{
                    Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - Solo");
                    screenManager.addScreen(new ConfirmSoloPopup(screenManager,this));
                }));

        addObject(new Button(X_INIT_BUTTON+2*(X_BUFFER+WIDTH_BUTTON),Y_INIT_BUTTON,
                "/assets/buttons/Button-Coop.png",
                DrawLayer.Entity,
                (screenManager) ->{
                    Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - Coop");
                    screenManager.addScreen(new ConfirmCoopPopup(screenManager,this));
                }));
        addObject(new Button(X_INIT_BUTTON+3*(X_BUFFER+WIDTH_BUTTON),Y_INIT_BUTTON,
                "/assets/buttons/Button-Back.png",
                DrawLayer.Entity,
                (screenManager) ->{
                    Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - Back");
                    this.setScreenState(ScreenState.TransitionOff);
                }));

        /* Create all renderables */

        addObject(new ImageContainer(X_INIT_BUTTON + 2*QRTR_BUTTON_WIDTH - HALF_TEDDY_WIDTH, Y_INIT_BUTTON- TEDDY_HEIGHT, "/assets/Teddy.png", DrawLayer.Entity));
        addObject(new ImageContainer(X_INIT_BUTTON + QRTR_BUTTON_WIDTH - HALF_TEDDY_WIDTH +2*(X_BUFFER+WIDTH_BUTTON), Y_INIT_BUTTON- TEDDY_HEIGHT, "/assets/Teddy.png", DrawLayer.Entity));
        addObject(new ImageContainer(X_INIT_BUTTON + 3*QRTR_BUTTON_WIDTH - HALF_TEDDY_WIDTH +2*(X_BUFFER+WIDTH_BUTTON), Y_INIT_BUTTON- TEDDY_HEIGHT, "/assets/Teddy2.png", DrawLayer.Entity));
    }
    //endregion

    @Override
    protected void transitionOn() {
        defaultTransitionOn();
    }

    @Override
    protected void transitionOff() {
        defaultTransitionOff();
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
