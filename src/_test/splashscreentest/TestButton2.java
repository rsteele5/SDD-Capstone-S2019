package _test.splashscreentest;

import control.ScreenManager;
import model.gameobjects.buttons.Button;
import utilities.Log;
import view.renderengine.GameScreen;
import view.renderengine.RenderEngine;

import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class TestButton2 extends Button {

    public TestButton2(int x, int y, BufferedImage image, int drawLayer) {
        super(x, y, image, drawLayer);
    }

    @Override
    public void onClick(MouseEvent e) {
        ScreenManager screenManager = ((RenderEngine) e.getSource()).getScreenManager();
        screenManager.removeScreen(screenManager.getScreens().get(screenManager.getScreens().size() - 1));
        TestTitleScreen screen = new TestTitleScreen(((RenderEngine) e.getSource()).getScreenManager());
        screen.setScreenState(GameScreen.ScreenState.Active);
        ((RenderEngine) e.getSource()).getScreenManager()
                .addScreen(screen);
    }
}
