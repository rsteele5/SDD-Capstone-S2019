package _test.splashscreentest;

import control.ScreenManager;
import model.gameobjects.Renderable;
import utilities.Debug;
import utilities.DebugEnabler;
import view.renderengine.GameScreen;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

public class TestTeamSplashScreen extends GameScreen {
    //region <Variables>
    private ScreenState previousState = null; //TODO: Remove after testing
    //endregion

    //region <Construction and Initialization>
    public TestTeamSplashScreen(ScreenManager screenManager) {
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
            BufferedImage logo = ImageIO.read(getClass().getResource("/assets/TeamLogoSplashScreen.png"));
            rederableLayers.get(0).add(new ImageContainer(0,0, logo, 0));

            BufferedImage coverImg = ImageIO.read(getClass().getResource("/assets/TeamLogoSplashScreenCover.png"));
            ImageContainer cover = new ImageContainer(0,0, coverImg, 0);
            cover.setAlpha(1f);
            rederableLayers.get(0).add(cover);
            Debug.success(DebugEnabler.GAME_SCREEN_LOG,"Loaded Success");
        } catch(IOException e)  {
            Debug.error(DebugEnabler.GAME_SCREEN_LOG,"Error: " + e.getMessage());
        }
    }
    //endregion

    //region <Update>
    @Override
    public void updateTransitionOn() {
        float alpha = rederableLayers.get(0).get(1).getAlpha();
        if(alpha > 0.008f) {
            rederableLayers.get(0).get(1).setAlpha(alpha - 0.008f);
            if(rederableLayers.get(0).get(1).getAlpha() <= 0.008f) {
                currentState = ScreenState.Active;
            }
        }
    }

    @Override
    public void updateTransitionOff() {
        float alpha = rederableLayers.get(0).get(1).getAlpha();
        if(alpha < 1f){
            rederableLayers.get(0).get(1).setAlpha(alpha + 0.008f);
        } else {
            exiting = true;
            screenManager.addScreen(new TestTitleScreen(screenManager));
        }
    }

    @Override
    public void hiddenUpdate() {

    }

    @Override
    public void update() {
        if(currentState != previousState){
            previousState = currentState;
            Debug.log(DebugEnabler.GAME_SCREEN_LOG, "SplashScreen-isLoading: " + isLoading());
            Debug.log(DebugEnabler.GAME_SCREEN_LOG, "SplashScreen-CurrentState: " + currentState.name());
        }

        super.update();
        switch(currentState) {
            case Active: currentState = ScreenState.TransitionOff; break;
            case Hidden: Debug.log(DebugEnabler.GAME_SCREEN_LOG,"State is hidden"); break;
            case TransitionOff: break;
            case TransitionOn: break;
            default: Debug.error(DebugEnabler.GAME_SCREEN_LOG,"Unknown screen state");
        }
    }
    //endregion

    //region <Render>
    @Override
    public void draw(Graphics2D graphics) {
        for (CopyOnWriteArrayList<Renderable> layer : rederableLayers) {
            for (Renderable gameObject : layer) {
                gameObject.draw(graphics);
            }
        }
    }
    //endregion

    //region <Support Functions>
    @Override
    public void handleClickEvent(int x, int y) {}
    //endregion
}
