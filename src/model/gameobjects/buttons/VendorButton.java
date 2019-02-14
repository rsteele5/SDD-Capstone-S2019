package model.gameobjects.buttons;

import control.ScreenManager;
import utilities.Debug;
import utilities.DebugEnabler;
import view.screens.GameScreen;
import view.screens.VendorScreen;

import java.awt.image.BufferedImage;

public class VendorButton extends Button {

    public VendorButton(int x, int y, BufferedImage image, int drawLayer) {
        super(x, y, image, drawLayer);
    }

    @Override
    public void onClick(ScreenManager screenManager) {
        Debug.success(DebugEnabler.BUTTON_LOG,"Clicked a vendor Button");
        screenManager.addScreen(new VendorScreen(screenManager));

    }

}
