package _test.splashscreentest;

import control.ScreenManager;
import model.gameobjects.Renderable;
import model.gameobjects.buttons.Button;
import utilities.Debug;
import utilities.DebugEnabler;
import view.renderengine.GameScreen;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

public class TestMainMenuScreen extends GameScreen {

    //region <Variables>
    private ScreenState previousState = null; //TODO: Remove after testing
    //endregion

    //region <Construction and Initialization>
    public TestMainMenuScreen(ScreenManager screenManager) {
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

            BufferedImage background = ImageIO.read(getClass().getResource("/assets/MainMenu.png"));
            rederableLayers.get(0).add(new ImageContainer(0,0, background, 0));

            BufferedImage button = ImageIO.read(getClass().getResource("/assets/TestButton.png"));
            rederableLayers.get(0).add(new TestButton(360,590, button, 0));

            BufferedImage button2 = ImageIO.read(getClass().getResource("/assets/TestButton.png"));
            rederableLayers.get(0).add(new TestButton(30,590, button2, 0));

            Debug.success(DebugEnabler.GAME_SCREEN_LOG,"Loading Finished");
        } catch(IOException e)  {
            Debug.error(DebugEnabler.GAME_SCREEN_LOG,"Error: " + e.getMessage());
        }
    }
    //endregion

    //region <Update>
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

    public void update(){
       super.update();
    }
    //endregion

    //region <Support Functions>
    @Override
    public void handleClickEvent(int x, int y) {
        for (CopyOnWriteArrayList<Renderable> layer : rederableLayers) {
            for (Renderable gameObject : layer) {
                if(gameObject.getBoundingBox().contains(x,y) && gameObject instanceof Button){
                    ((Button)gameObject).onClick(screenManager);
                }
            }
        }
    }
    //endregion

}
