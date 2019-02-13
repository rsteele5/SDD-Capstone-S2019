package view.screens;

import control.ScreenManager;
import model.gameobjects.ImageContainer;
import utilities.Debug;
import utilities.DebugEnabler;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

public class TitleScreen extends view.screens.GameScreen {
    //region <Variables>
    private ScreenState previousState = null; //TODO: Remove after testing
    //endregion

    //region <Construction and Initialization>
    public TitleScreen(ScreenManager screenManager) {
        super(screenManager);
        name = "TitleScreen";
        overlay = true;
    }

    @Override
    protected void initializeLayers() {
        renderableLayers.add(new CopyOnWriteArrayList<>());
    }

    @Override
    public void loadContent(){
        try {
            Debug.success(DebugEnabler.GAME_SCREEN_LOG,name+"Loading Content");

            BufferedImage background = ImageIO.read(getClass().getResource("/assets/TitleScreenBackground.png"));
            renderableLayers.get(0).add(new ImageContainer(0,0, background, 0));

            BufferedImage cover = ImageIO.read(getClass().getResource("/assets/TitleScreenCover.png"));
            renderableLayers.get(0).add(new ImageContainer(0,-720, cover, 3));

            BufferedImage title = ImageIO.read(getClass().getResource("/assets/Title.png"));
            renderableLayers.get(0).add(new ImageContainer(350,75, title, 2));

            Debug.success(DebugEnabler.GAME_SCREEN_LOG,name+"Loaded Success");
        } catch(IOException e)  {
            Debug.error(DebugEnabler.GAME_SCREEN_LOG,"Error: " + e.getMessage());
        }
    }
    //endregion

    //region <Update>
    @Override
    public void updateTransitionOn() {
        if(renderableLayers.get(0).get(1).getY() < -240)
            renderableLayers.get(0).get(1).setY(renderableLayers.get(0).get(1).getY() + 2);
        else
            currentState = ScreenState.Active;
    }

    @Override
    public void updateTransitionOff() {
        Debug.success(DebugEnabler.GAME_SCREEN_LOG,"Constructing MainMenuScreen");
        screenManager.addScreen(new MainMenuScreen(screenManager));
    }

    @Override
    public void hiddenUpdate() {
        Debug.log(DebugEnabler.GAME_SCREEN_LOG,name+"-CurrentState: Hidden"); //TODO: handle hidden state
        if(!screenManager.coveredByLoading(this))
            exiting = true;
    }

    @Override
    protected void activeUpdate() {
        currentState = ScreenState.TransitionOff;
    }
    //endregion

    //region <Getters and Setters>
    @Override
    public boolean isActive(){
        return true;
    }
    //endregion

    //region <Support Functions>
    @Override
    public void handleClickEvent(int x, int y) {
        screenManager.addScreen(new MainMenuScreen(screenManager));
    }
    //endregion
}
