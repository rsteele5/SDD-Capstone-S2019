package view.screens;

import control.RenderEngine;
import control.ScreenManager;
import model.gameobjects.ImageContainer;
import model.gameobjects.RenderableObject;
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

            BufferedImage logo = RenderEngine.convertToARGB(ImageIO.read(getClass().getResource("/assets/backgrounds/BG-TeamLogo.png")));
            renderableLayers.get(0).add(new ImageContainer(0,0, logo, 0));

            BufferedImage skipMsg = RenderEngine.convertToARGB(ImageIO.read(getClass().getResource("/assets/text/TXT-SkipMsg.png")));
            renderableLayers.get(0).add(new ImageContainer(10,10, skipMsg, 0));

            BufferedImage coverImg = RenderEngine.convertToARGB(ImageIO.read(getClass().getResource("/assets/backgrounds/BG-BlackCover.png")));
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
        if(!screenManager.coveredByLoading(this))
            exiting = true;
    }

    @Override
    protected void activeUpdate() {
        currentState = ScreenState.TransitionOff;
    }
    //endregion

    //region <Render>
    @Override
    public void draw(Graphics2D graphics) {
        for (CopyOnWriteArrayList<RenderableObject> layer : renderableLayers) {
            for (RenderableObject gameObject : layer) {
                gameObject.draw(graphics);
            }
        }
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
        Debug.log(DebugEnabler.GAME_SCREEN_LOG, "Clicked the splash screen");
        exiting = true;
        screenManager.addScreen(new TitleScreen(screenManager));
    }
    //endregion
}
