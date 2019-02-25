package gamescreens.screens.menus.playercount;

import gamescreens.DrawLayer;
import gamescreens.GameScreen;
import gamescreens.ScreenManager;
import gameobjects.renderables.ImageContainer;
import gameobjects.renderables.buttons.Button;
import main.utilities.Debug;
import main.utilities.DebugEnabler;

import java.util.concurrent.CopyOnWriteArrayList;

public class ConfirmSoloPopup extends GameScreen {

    protected CopyOnWriteArrayList<Button> buttons = new CopyOnWriteArrayList<>();
    private final int X_INIT_BUTTON = 427;
    private final int Y_INIT_BUTTON = 400;
    private final int X_BUFFER = 142;
    private final int WIDTH_BUTTON = 142;
    private boolean goBack = false;
    private float alphaTransition = 0.0f;
    private GameScreen covering;
    private String bgImage;
    private char screen;

    public ConfirmSoloPopup(ScreenManager screenManager, GameScreen covering) {
        super(screenManager, "ConfirmSoloPopup");
        isExclusive = true;
        this.covering = covering;
    }

    @Override
    protected void initializeScreen() {

        //popup background
        ImageContainer background = (new ImageContainer(0,0, "/assets/backgrounds/BG-ConfirmSoloPopup.png", DrawLayer.Background));
        background.addToScreen(this, true);

        Button button;
        button = new Button(X_INIT_BUTTON,Y_INIT_BUTTON,
                "/assets/buttons/Button-Yes.png",
                DrawLayer.Entity,
                (GameScreen) ->{
                    Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - Yes");
                    //Yes is continue to new screen.
                    //TODO: Make this not shit
                    //Solo Mode
                    screenManager.addScreen(new TempSoloScreen(screenManager, this));
                    setScreenState(ScreenState.TransitionOff);
                });
        button.addToScreen(this, true);


        button = new Button(X_INIT_BUTTON + WIDTH_BUTTON + X_BUFFER,Y_INIT_BUTTON,
                "/assets/buttons/Button-No.png",
                DrawLayer.Entity,
                (GameScreen) ->{
                    Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - No");
                    //No is stay here.
                    //TODO: Make this not shit
                    goBack = false;
                    this.setScreenState(ScreenState.TransitionOff);
                });
        button.addToScreen(this, true);
    }

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