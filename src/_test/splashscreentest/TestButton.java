package _test.splashscreentest;

import control.ScreenManager;
import model.gameobjects.buttons.Button;
import utilities.Debug;
import utilities.DebugEnabler;

import java.awt.image.BufferedImage;

public class TestButton extends Button {

    public TestButton(int x, int y) {
        super(x, y);
    }

    public TestButton(int x, int y, BufferedImage image, int drawLayer) {
        super(x, y, image, drawLayer);
    }

    @Override
    public void onClick(ScreenManager screenManager) {
        Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Test Button");
        screenManager.addScreen(new TestGameplayScreen(screenManager)); //TODO: Change to GameplayScreen post test
    }
}
