package _test.vendorTest;

import control.ScreenManager;
import model.gameobjects.buttons.Button;
import utilities.Debug;
import utilities.DebugEnabler;
import view.screens.VendorScreen;

import java.awt.image.BufferedImage;

public class VendorTestButton extends Button {

    public VendorTestButton(int x, int y, BufferedImage image, int drawLayer) {
        super(x, y, image, drawLayer);
    }

    @Override
    public void onClick(ScreenManager screenManager) {
        Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Vendor Button");
        screenManager.addScreen(new VendorScreen(screenManager));
    }
}
