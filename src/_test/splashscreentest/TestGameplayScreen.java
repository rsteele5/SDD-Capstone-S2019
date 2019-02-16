package _test.splashscreentest;

import control.RenderEngine;
import control.ScreenManager;
import model.gameobjects.ImageContainer;
import model.gameobjects.buttons.Button;
import utilities.Debug;
import utilities.DebugEnabler;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

public class TestGameplayScreen extends view.screens.GameScreen {

    private final int X_INIT_BUTTON = 64;
    private final int Y_INIT_BUTTON = 576;
    protected CopyOnWriteArrayList<Button> buttons = new CopyOnWriteArrayList<>();

    //region <Construction and Initialization>
    public TestGameplayScreen(ScreenManager screenManager) {
        super(screenManager);
        name = "TestGameplayScreen";
        loadingScreenRequired = true;
        exclusivePopup = true;
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
            int totalDataToLoad = 351;
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

            BufferedImage mainMenuButtonIMG = RenderEngine.convertToARGB(ImageIO.read(getClass()
                    .getResource("/assets/buttons/Button-Back.png")));
            buttons.add(new Button(X_INIT_BUTTON,Y_INIT_BUTTON, mainMenuButtonIMG, 1,
                    (screenManager) ->{
                        Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - Main Menu");
                        this.setScreenState(ScreenState.TransitionOff);
                    }));
            renderableLayers.get(2).addAll(buttons);
            ++dataLoaded;
            screenManager.updateLoadingScreen(dataLoaded);

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
        for(Button butt: buttons) {
            if(butt.getBoundingBox().contains(x,y)) {
                butt.onClick.accept(screenManager);
                return;
            }
        }
    }
}
