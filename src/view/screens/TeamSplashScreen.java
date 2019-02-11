package view.screens;

import control.ScreenManager;
import model.gameobjects.ImageContainer;
import model.gameobjects.Renderable;
import utilities.Debug;
import utilities.DebugEnabler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

public class TeamSplashScreen extends view.screens.GameScreen {

    //region <Variables>
    //endregion

    //region <Construction and Initialization>
    public TeamSplashScreen(ScreenManager screenManager) {
        super(screenManager);
        name = "TeamSplashScreen";
    }

    @Override
    protected void initializeLayers() {
        renderableLayers.add(new CopyOnWriteArrayList<>());
    }

    @Override
    public void loadContent(){
        try {
            Debug.success(DebugEnabler.GAME_SCREEN_LOG,name+"-Loading Content");

            BufferedImage logo = ImageIO.read(getClass().getResource("/assets/TeamLogoSplashScreen.png"));
            renderableLayers.get(0).add(new ImageContainer(0,0, logo, 0));

            BufferedImage coverImg = ImageIO.read(getClass().getResource("/assets/TeamLogoSplashScreenCover.png"));
            ImageContainer cover = new ImageContainer(0,0, coverImg, 0);
            cover.setAlpha(1f);
            renderableLayers.get(0).add(cover);

            Debug.success(DebugEnabler.GAME_SCREEN_LOG,name+"-Loaded Success");
        } catch(IOException e)  {
            Debug.error(DebugEnabler.GAME_SCREEN_LOG,"Error: " + e.getMessage());
        }
    }
    //endregion

    //region <Update>
    @Override
    public void updateTransitionOn() {
        float alpha = renderableLayers.get(0).get(1).getAlpha();
        if(alpha > 0.008f) {
            renderableLayers.get(0).get(1).setAlpha(alpha - 0.008f);
            if(renderableLayers.get(0).get(1).getAlpha() <= 0.008f) {
                currentState = ScreenState.Active;
            }
        }
    }

    @Override
    public void updateTransitionOff() {
        float alpha = renderableLayers.get(0).get(1).getAlpha();
        if(alpha < 1f){
            renderableLayers.get(0).get(1).setAlpha(alpha + 0.008f);
        } else {
            exiting = true;
            screenManager.addScreen(new TitleScreen(screenManager));
        }
    }

    @Override
    public void hiddenUpdate() {
        Debug.log(DebugEnabler.GAME_SCREEN_LOG,"State is hidden");
    }

    @Override
    protected void activeUpdate() {
        currentState = ScreenState.TransitionOff;
    }
    //endregion

    //region <Render>
    @Override
    public void draw(Graphics2D graphics) {
        for (CopyOnWriteArrayList<Renderable> layer : renderableLayers) {
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
