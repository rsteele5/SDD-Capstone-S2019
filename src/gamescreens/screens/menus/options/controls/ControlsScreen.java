package gamescreens.screens.menus.options.controls;

import static gameengine.GameSettings.*;
import static gameengine.GameSettings.InputMethod.*;

import gameobjects.renderables.TextBox;
import gamescreens.DrawLayer;
import gamescreens.GameScreen;
import gamescreens.ScreenManager;
import gameobjects.renderables.ImageContainer;
import gameobjects.renderables.buttons.Button;
import main.utilities.Debug;
import main.utilities.DebugEnabler;

import java.awt.*;


public class ControlsScreen extends GameScreen {

    private static InputMethod inputSetting;
    private InputMethod exitSetting;
    private TextBox controlsText;

    //region <Variables>
    private final int X_INIT_BUTTON = 64;
    private final int Y_INIT_BUTTON = 576;
    private final int WIDTH_BUTTON = 256;
    private final int X_BUFFER = 48;
    //endregion

    //region <Construction and Initialization>
    public ControlsScreen(ScreenManager screenManager) {
        super(screenManager, "ControlsScreen", true);
        inputSetting = screenManager.getGameSettings().getInputMethod();
        exitSetting = inputSetting;
    }

    @Override
    protected void initializeScreen() {

        controlsText = new TextBox(X_INIT_BUTTON+X_BUFFER, Y_INIT_BUTTON,
                300,
                150,
                screenManager.getGameSettings().getInputMethod().name(),
                new Font("NoScary", Font.PLAIN, 60),
                Color.WHITE);

        addObject(controlsText);

        //Create buttons
        addObject(new Button(X_INIT_BUTTON, Y_INIT_BUTTON, "/assets/buttons/Button-LeftArrow.png", DrawLayer.Entity,
                (GameScreen) -> {
                    Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Left Arrow");
                    if (exitSetting == KeyBoard) {
                        exitSetting = GamePad;
                        controlsText.setText(exitSetting.name());
                    } else {
                        exitSetting = KeyBoard;
                        controlsText.setText(exitSetting.name());
                    }
                }));

        addObject(new Button(X_INIT_BUTTON + X_BUFFER + WIDTH_BUTTON, Y_INIT_BUTTON, "/assets/buttons/Button-RightArrow.png", DrawLayer.Entity,
                (GameScreen) -> {
                    Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Right Arrow");
                    if (exitSetting == KeyBoard) {
                        exitSetting = GamePad;
                        controlsText.setText(exitSetting.name());
                    } else {
                        exitSetting = KeyBoard;
                        controlsText.setText(exitSetting.name());
                    }
                }));

        addObject(new Button(X_INIT_BUTTON + 2 * (X_BUFFER + WIDTH_BUTTON), Y_INIT_BUTTON,
                "/assets/buttons/Button-Confirm.png",
                DrawLayer.Entity,
                (GameScreen) -> {
                    Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Confirm");
                    this.setScreenState(ScreenState.TransitionOff);
                    inputSetting = exitSetting;
                    screenManager.getGameSettings().setIputMethod(inputSetting);
                }));

        addObject(new Button(X_INIT_BUTTON + 3 * (X_BUFFER + WIDTH_BUTTON),
                Y_INIT_BUTTON,
                "/assets/buttons/Button-Back.png",
                DrawLayer.Entity,
                (GameScreen) -> {
                    Debug.success(DebugEnabler.BUTTON_LOG, "Clicked Button - Back");
                    if (!exitSetting.equals(inputSetting)) {
                        screenManager.addScreen(new ConfirmControlsPopup(screenManager, this));
                    } else {
                        setScreenState(ScreenState.TransitionOff);
                    }
                }));

        //Create Background on layer 0
        addObject(new ImageContainer(0, 0, "/assets/backgrounds/BG-ControlsMenu.png", DrawLayer.Background));
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
