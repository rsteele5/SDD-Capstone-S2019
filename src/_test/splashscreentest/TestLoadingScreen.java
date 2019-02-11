package _test.splashscreentest;

import control.ScreenManager;
import utilities.Debug;
import utilities.DebugEnabler;
import view.renderengine.GameScreen;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

public class TestLoadingScreen extends GameScreen {

    public TestLoadingScreen(ScreenManager screenManager) {
        super(screenManager);
        loadContent();
    }

    @Override
    protected void initializeLayers() {
        rederableLayers.add(new CopyOnWriteArrayList<>());
    }

    @Override
    public void loadContent(){
        try {
            Debug.success(DebugEnabler.TEST_LOG && DebugEnabler.GAME_SCREEN_LOG, "Loading Content");
            BufferedImage logo = ImageIO.read(getClass().getResource("/assets/LoadingScreen.png"));
            rederableLayers.get(0).add(new ImageContainer(0,0, logo, 0));

            BufferedImage coverImg = ImageIO.read(getClass().getResource("/assets/LoadingBar.png"));
            ImageContainer cover = new ImageContainer(0,0, coverImg, 0);
            cover.setAlpha(1f);
            rederableLayers.get(0).add(cover);

        } catch(IOException e)  {
            Debug.error(DebugEnabler.GAME_SCREEN_LOG,"Error: " + e.getMessage());
        }
    }

    @Override
    public void updateTransitionOn() {

    }

    @Override
    public void updateTransitionOff() {

    }

    @Override
    public void hiddenUpdate() {

    }

    @Override
    public void update(){

    }

    //region <Support Functions>
    @Override
    public void handleClickEvent(int x, int y) {}
    //endregion
}
