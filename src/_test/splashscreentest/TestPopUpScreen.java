package _test.splashscreentest;

import control.ScreenManager;
import utilities.Log;
import view.renderengine.GameScreen;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class TestPopUpScreen extends GameScreen {

    //TODO: Currently popups are draw over the screen beneath, it may be necessary to have a popup replace the contents of the screen

    public TestPopUpScreen(ScreenManager screenManager) {
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
            BufferedImage popup = ImageIO.read(getClass().getResource("/assets/TestPopup.png"));
            foregroundLayer.add(new ImageContainer(0,0, popup, 2));

            BufferedImage button2 = ImageIO.read(getClass().getResource("/assets/TestButton2.png"));
            foregroundLayer.add(new TestButton2(900,500, button2, 0));

        } catch(IOException e)  {
            Log.logError("Error: " + e.getMessage());
        }
    }
}
