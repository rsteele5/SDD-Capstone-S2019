package _test.splashscreentest;

import control.ScreenManager;
import utilities.Log;
import view.renderengine.GameScreen;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class TestTitleScreen extends GameScreen {

    public TestTitleScreen(ScreenManager screenManager) {
        super(screenManager);
    }

    @Override
    public void updateTransitionOn() {

    }

    @Override
    public void updateTransitionOff() {

    }

    @Override
    public void loadContent(){
        try {
            Log.logSuccess("Loading Content");
            BufferedImage background = ImageIO.read(getClass().getResource("/assets/TitleScreenBackground.png"));
            foregroundLayer.add(new ImageContainer(0,0, background, 0));

            BufferedImage cover = ImageIO.read(getClass().getResource("/assets/TitleScreenCover.png"));
            foregroundLayer.add(new ImageContainer(0,-720, cover, 3));

            BufferedImage title = ImageIO.read(getClass().getResource("/assets/Title.png"));
            foregroundLayer.add(new ImageContainer(350,75, title, 2));

        } catch(IOException e)  {
            Log.logError("Error: " + e.getMessage());
        }
    }

    public void update(){
        super.update();
        if(foregroundLayer.get(1).getY() < -240) {
            foregroundLayer.get(1).setY(foregroundLayer.get(1).getY() + 2);
        } else {
            screenManager.addScreen(new TestMainMenuScreen(screenManager));
            isExiting = true;
            screenManager.removeScreen(this);
        }
    }
}
