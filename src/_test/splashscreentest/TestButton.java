package _test.splashscreentest;

import control.listeners.Clickable;
import model.gameobjects.buttons.Button;
import utilities.Log;
import view.renderengine.GameScreen;
import view.renderengine.RenderEngine;

import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class TestButton extends Button {

    public TestButton(int x, int y) {
        super(x, y);
    }

    public TestButton(int x, int y, BufferedImage image, int drawLayer) {
        super(x, y, image, drawLayer);
    }

    @Override
    public void onClick(MouseEvent e) {
        Log.logSuccess("Clicked Test Button");
        TestPopUpScreen screen = new TestPopUpScreen(((RenderEngine) e.getSource()).getScreenManager());
        screen.setScreenState(GameScreen.ScreenState.Active);
        ((RenderEngine) e.getSource()).getScreenManager()
                .addScreen(screen);
    }
}
