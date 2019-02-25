package gamescreens.screens;

import gameobjects.renderables.TextBox;
import gamescreens.DrawLayer;
import gamescreens.GameScreen;
import gamescreens.ScreenManager;
import gameobjects.renderables.ImageContainer;
import gameobjects.renderables.RenderableObject;
import gameobjects.renderables.buttons.Button;
import main.utilities.Debug;
import main.utilities.DebugEnabler;

import java.awt.*;

public class ConfirmationPopup extends GameScreen {

    private final int X_INIT_BUTTON = 427;
    private final int Y_INIT_BUTTON = 400;
    private final int X_BUFFER = 142;
    private final int WIDTH_BUTTON = 142;
    private GameScreen covering;
    private TextBox confirmationTextBox;

    public ConfirmationPopup(ScreenManager screenManager, GameScreen covering, String confirmationMessage) {
        super(screenManager, "ConfirmationPopup", true);
        this.covering = covering;
        confirmationTextBox.setText(confirmationMessage);
    }

    @Override
    protected void initializeScreen() {
        //Create Background on layer 0
        ImageContainer imageContainer;

        imageContainer = new ImageContainer(0,0, "/assets/backgrounds/BG-ConfirmationPopup.png", DrawLayer.Background);
        imageContainer.addToScreen(this, true);

        //Text Box
        confirmationTextBox = new TextBox(450, 240, //TODO: change to standard values
                500,
                150,
                screenManager.getGameSettings().getInputMethod().name(),
                new Font("NoScary", Font.PLAIN, 60),
                Color.WHITE);
        confirmationTextBox.addToScreen(this, true);
        //Buttons
        Button button;

        button = new Button(X_INIT_BUTTON,Y_INIT_BUTTON,
                "/assets/buttons/Button-Yes.png",
                DrawLayer.Entity,
                (screenManager) ->{
                    Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - Yes");
                    setScreenState(ScreenState.TransitionOff);
                    covering.setScreenState(ScreenState.TransitionOff);
                });
        button.addToScreen(this, true);

        button = new Button(X_INIT_BUTTON + WIDTH_BUTTON + X_BUFFER,Y_INIT_BUTTON,
                "/assets/buttons/Button-No.png",
                DrawLayer.Entity,
                (screenManager) ->{
                    Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - No");
                    setScreenState(ScreenState.TransitionOff);
                });
        button.addToScreen(this, true);
    }
}
