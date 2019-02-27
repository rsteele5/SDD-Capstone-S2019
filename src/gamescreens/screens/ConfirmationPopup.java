package gamescreens.screens;

import gameobjects.renderables.TextBox;
import gamescreens.DrawLayer;
import gamescreens.GameScreen;
import gamescreens.ScreenManager;
import gameobjects.renderables.ImageContainer;
import gameobjects.renderables.RenderableObject;
import gameobjects.renderables.buttons.Button;
import main.utilities.Action;
import main.utilities.Debug;
import main.utilities.DebugEnabler;

import javax.lang.model.type.NullType;
import java.awt.*;
import java.util.function.Consumer;

public class ConfirmationPopup extends GameScreen {

    private final int X_INIT_BUTTON = 427;
    private final int Y_INIT_BUTTON = 400;
    private final int X_BUFFER = 142;
    private final int WIDTH_BUTTON = 142;
    private TextBox confirmationTextBox;

    private Action onYesBtn;
    private Action onNoBtn;

    public ConfirmationPopup(ScreenManager screenManager, String confirmationMessage, Action onYes) {
        super(screenManager, "ConfirmationPopup", true);
        confirmationTextBox.setText(confirmationMessage);
        onYesBtn = onYes;
    }

    public ConfirmationPopup(ScreenManager screenManager, String confirmationMessage, Action onYes, Action onNo) {
        this(screenManager, confirmationMessage, onYes);
        onNoBtn = onNo;
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
                () ->{
                    Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - Yes");
                    setScreenState(ScreenState.TransitionOff);
                    if(onYesBtn != null){
                        onYesBtn.doIt();
                    }
                    //covering.setScreenState(ScreenState.TransitionOff);
                });
        button.addToScreen(this, true);

        button = new Button(X_INIT_BUTTON + WIDTH_BUTTON + X_BUFFER,Y_INIT_BUTTON,
                "/assets/buttons/Button-No.png",
                DrawLayer.Entity,
                () ->{
                    Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - No");
                    setScreenState(ScreenState.TransitionOff);
                    if(onNoBtn != null){
                        onNoBtn.doIt();
                    }
                });
        button.addToScreen(this, true);
    }
}
