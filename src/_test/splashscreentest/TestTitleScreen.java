package _test.splashscreentest;

import control.ScreenManager;
import utilities.Debug;
import utilities.DebugEnabler;
import view.renderengine.GameScreen;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

public class TestTitleScreen extends GameScreen {
    //region <Variables>
    private ScreenState previousState = null; //TODO: Remove after testing
    //endregion

    //region <Construction and Initialization>
    public TestTitleScreen(ScreenManager screenManager) {
        super(screenManager);
    }

    @Override
    protected void initializeLayers() {
        rederableLayers.add(new CopyOnWriteArrayList<>());
    }

    @Override
    public void loadContent(){
        try {
            Debug.success(DebugEnabler.GAME_SCREEN_LOG,"Loading Content");
            BufferedImage background = ImageIO.read(getClass().getResource("/assets/TitleScreenBackground.png"));
            rederableLayers.get(0).add(new ImageContainer(0,0, background, 0));

            BufferedImage cover = ImageIO.read(getClass().getResource("/assets/TitleScreenCover.png"));
            rederableLayers.get(0).add(new ImageContainer(0,-720, cover, 3));

            BufferedImage title = ImageIO.read(getClass().getResource("/assets/Title.png"));
            rederableLayers.get(0).add(new ImageContainer(350,75, title, 2));

        } catch(IOException e)  {
            Debug.error(DebugEnabler.GAME_SCREEN_LOG,"Error: " + e.getMessage());
        }
    }
    //endregion

    //region <Update>
    @Override
    public void updateTransitionOn() {
        if(rederableLayers.get(0).get(1).getY() < -240)
            rederableLayers.get(0).get(1).setY(rederableLayers.get(0).get(1).getY() + 2);
        else
            currentState = ScreenState.Active;
    }

    @Override
    public void updateTransitionOff() {
        exiting = true;
        Debug.success(DebugEnabler.GAME_SCREEN_LOG,"Constructing TestMainMenuScreen"); //ToDO: Remove after test
        screenManager.addScreen(new TestMainMenuScreen(screenManager));
    }

    @Override
    public void hiddenUpdate() {

    }

    public void update(){
        if(currentState != previousState){
            previousState = currentState;
            Debug.log(DebugEnabler.GAME_SCREEN_LOG,"SplashScreen-CurrentState: " + currentState.name());
        }

        super.update();
        switch(currentState) {
            case Active: currentState = ScreenState.TransitionOff; break;
            case Hidden: Debug.log(DebugEnabler.GAME_SCREEN_LOG,"State is hidden"); break;//TODO: handle hidden state
            case TransitionOff: break;
            case TransitionOn: break;
            default: Debug.error(DebugEnabler.GAME_SCREEN_LOG,"Unknown screen state");
        }
    }
    //endregion

    //region <Support Functions>
    @Override
    public void handleClickEvent(int x, int y) {}
    //endregion
}
