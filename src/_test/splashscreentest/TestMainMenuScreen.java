package _test.splashscreentest;

import control.ScreenManager;
import utilities.Log;
import view.renderengine.GameScreen;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class TestMainMenuScreen extends GameScreen {

    public TestMainMenuScreen(ScreenManager screenManager) {
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

            BufferedImage background = ImageIO.read(getClass().getResource("/assets/MainMenu.png"));
            foregroundLayer.add(new ImageContainer(0,0, background, 0));

            BufferedImage button = ImageIO.read(getClass().getResource("/assets/TestButton.png"));
            foregroundLayer.add(new TestButton(360,590, button, 0));

            BufferedImage button2 = ImageIO.read(getClass().getResource("/assets/TestButton.png"));
            foregroundLayer.add(new TestButton(30,590, button2, 0));
            currentState = ScreenState.Active;

            Log.logSuccess("Loading Finished");
        } catch(IOException e)  {
            Log.logError("Error: " + e.getMessage());
        }
    }

    public void update(){
        if(foregroundLayer.get(1).getY() < -240) {

        } else {

        }
    }

}
