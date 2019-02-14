package view.screens;

import _test.splashscreentest.TestButton;
import _test.vendorTest.VendorTestButton;
import control.ScreenManager;
import model.gameobjects.ImageContainer;
import model.gameobjects.RenderableObject;
import model.gameobjects.buttons.Button;
import utilities.Debug;
import utilities.DebugEnabler;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

public class MainMenuScreen extends view.screens.GameScreen {

    //region <Variables>
    protected CopyOnWriteArrayList<Button> buttons = new CopyOnWriteArrayList<>();
    //endregion

    //region <Construction and Initialization>
    public MainMenuScreen(ScreenManager screenManager) {
        super(screenManager);
        name = "MainMenuScreen";
    }

    @Override
    protected void initializeLayers() {
        renderableLayers.add(new CopyOnWriteArrayList<>());
        renderableLayers.add(new CopyOnWriteArrayList<>());
    }

    @Override
    public void loadContent(){
        try {
            Debug.success(DebugEnabler.GAME_SCREEN_LOG,name+"-Loading Content");

            //RenderableObject object paths
            BufferedImage background = ImageIO.read(getClass().getResource("/assets/MainMenu.png"));
            BufferedImage button = ImageIO.read(getClass().getResource("/assets/testAssets/TestButton.png"));
            BufferedImage button2 = ImageIO.read(getClass().getResource("/assets/testAssets/TestButton.png"));
            BufferedImage button3 = ImageIO.read(getClass().getResource("/assets/testAssets/TestButton.png"));

            //Create buttons
            buttons.add(new TestButton(360,590, button, 1));
            buttons.add(new TestButton(30,590, button2, 1));
            buttons.add(new VendorTestButton(690, 590, button3, 1));

            //Create all other Renderables
            renderableLayers.get(0).add(new ImageContainer(0,0, background, 0));
            //Consolidate Renderables
            for(Button butt: buttons)
                renderableLayers.get(butt.getDrawLayer()).add(butt);
            //Consolidate GameObjects
            for(CopyOnWriteArrayList<RenderableObject> layer: renderableLayers)
                gameObjects.addAll(layer);

            Debug.success(DebugEnabler.GAME_SCREEN_LOG,name+"-Loaded Success");
        } catch(IOException e)  {
            Debug.error(DebugEnabler.GAME_SCREEN_LOG,"Error: " + e.getMessage());
        }
    }
    //endregion

    //region <Update>
    @Override
    protected void updateTransitionOn() {
        currentState = ScreenState.Active;
    }

    @Override
    protected void updateTransitionOff() {
        exiting = true;
    }

    @Override
    protected void hiddenUpdate() {
        if(!screenManager.coveredByOverlay(this))
            currentState = ScreenState.TransitionOff;
        else
            activeUpdate();
    }

    @Override
    protected void activeUpdate(){

    }
    //endregion

    //region <Support Functions>
    @Override
    public void handleClickEvent(int x, int y) {
        for(Button butt: buttons) {
            if(butt.getBoundingBox().contains(x,y)) {
                butt.onClick(screenManager);
                return;
            }
        }
    }
    //endregion

}
