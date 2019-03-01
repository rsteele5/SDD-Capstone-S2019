package gamescreens.screens;

import gameobjects.GameObject;
import gameobjects.renderables.DialogBox;
import gameobjects.renderables.ImageContainer;
import gameobjects.renderables.buttons.Button;
import gamescreens.DrawLayer;
import gamescreens.GameScreen;
import gamescreens.ScreenManager;
import main.utilities.Debug;
import main.utilities.DebugEnabler;

import java.awt.*;

public class VendorDialogBoxScreen extends GameScreen {
    private final String welcome = "Hey Teddy! Would you like to come in and check out my new wares?";


    public VendorDialogBoxScreen(ScreenManager screenManager) {
        super(screenManager, "VendorDialogBoxScreen", true, 550, 250);
    }



    @Override
    protected void initializeScreen() {

        ImageContainer cover = new ImageContainer(0,0, "/assets/backgrounds/BG-VendorDialog.png", DrawLayer.Background);
        cover.setSize(375, 180);
        cover.setAlpha(1f);
        cover.addToScreen(this, true);

        DialogBox diagBox = new DialogBox(10, 10, 355, 160, welcome,
                new Font("NoScary", Font.PLAIN, 40), Color.WHITE);
        diagBox.addToScreen(this, true);

        Button button = new Button(100,140,
                "/assets/buttons/Button-Yes.png",
                DrawLayer.Entity,
                () ->{
                    Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - VendorDialog Yes");
                    this.exiting = true;
                    screenManager.addScreen(new VendorScreen(screenManager));
                });
        button.setSize(75,30);
        button.addToScreen(this,true);

        button = new Button(225,140,
                "/assets/buttons/Button-No.png",
                DrawLayer.Entity,
                () ->{
                    Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - VendorDialog No");
                    this.setScreenState(ScreenState.TransitionOff);
                });
        button.setSize(75,30);
        button.addToScreen(this,true);
    }


}
