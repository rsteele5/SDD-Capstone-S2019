package _test.splashscreentest;

import control.ScreenManager;
import model.gameobjects.ImageContainer;
import utilities.Debug;
import utilities.DebugEnabler;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

public class TestGameplayScreen extends view.screens.GameScreen {

    //region <Construction and Initialization>
    public TestGameplayScreen(ScreenManager screenManager) {
        super(screenManager);
        name = "TestGameplayScreen";
        loadingScreenRequired = true;
    }

    @Override
    protected void initializeLayers() {
        renderableLayers.add(new CopyOnWriteArrayList<>());
        renderableLayers.add(new CopyOnWriteArrayList<>());
        renderableLayers.add(new CopyOnWriteArrayList<>());
    }

    @Override
    protected void loadContent() {
        try {
            Debug.success(DebugEnabler.GAME_SCREEN_LOG,name+"-Loading Content");

            int dataLoaded = 0;
            int totalDataToLoad = 350;
            screenManager.initializeLoadingScreen(totalDataToLoad);

            for(int i = 0; i < 50; i++) {
                BufferedImage backhoes = ImageIO.read(getClass().getResource("/assets/testAssets/largeImage.png"));
                renderableLayers.get(0).add(new ImageContainer(0,0, backhoes, 0));
                ++dataLoaded;
                screenManager.updateLoadingScreen(dataLoaded);
            }

            for(int i = 0; i < 100; i++) {
                BufferedImage midtown = ImageIO.read(getClass().getResource("/assets/testAssets/TestGameplayScreen.png"));
                renderableLayers.get(1).add(new ImageContainer(0,0, midtown, 1));
                ++dataLoaded;
                screenManager.updateLoadingScreen(dataLoaded);
            }


            for(int i = 0; i < 100; i++) {
                BufferedImage foreclosed = ImageIO.read(getClass().getResource("/assets/testAssets/TestGameplayScreen1.png"));
                renderableLayers.get(2).add(new ImageContainer(0,0, foreclosed, 2));
                ++dataLoaded;
                screenManager.updateLoadingScreen(dataLoaded);
            }


            for(int i = 0; i < 100; i++) {
                BufferedImage foretold = ImageIO.read(getClass().getResource("/assets/testAssets/TestGameplayScreen2.png"));
                renderableLayers.get(2).add(new ImageContainer(0,0, foretold, 2));
                ++dataLoaded;
                screenManager.updateLoadingScreen(dataLoaded);
            }


            Debug.success(DebugEnabler.GAME_SCREEN_LOG,name+"-Loaded Success");
        } catch(IOException e)  {
            Debug.error(DebugEnabler.GAME_SCREEN_LOG,"Error: " + e.getMessage());
        }
    }
    //endregion

    @Override
    public void updateTransitionOn() {
        currentState = ScreenState.Active;
    }

    @Override
    public void updateTransitionOff() {
        exiting = true;
    }

    @Override
    public void hiddenUpdate() {

    }

    @Override
    protected void activeUpdate() {

    }

    @Override
    public void handleClickEvent(int x, int y) {

    }
}
