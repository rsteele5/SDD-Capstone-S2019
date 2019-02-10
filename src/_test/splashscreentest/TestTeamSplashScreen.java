package _test.splashscreentest;

import control.ScreenManager;
import model.gameobjects.Renderable;
import utilities.Log;
import view.renderengine.GameScreen;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class TestTeamSplashScreen extends GameScreen {

    //TODO: Remove after testing
    private ScreenState previousState = null;

    public TestTeamSplashScreen(ScreenManager screenManager) {
        super(screenManager);
    }

    @Override
    public void updateTransitionOn() {
        float alpha = foregroundLayer.get(1).getAlpha();
        if(alpha > 0.008f) {
            foregroundLayer.get(1).setAlpha(alpha - 0.008f);
            if(foregroundLayer.get(1).getAlpha() <= 0.008f) {
                currentState = ScreenState.Active;
            }
        }
    }

    @Override
    public void updateTransitionOff() {
        float alpha = foregroundLayer.get(1).getAlpha();
        if(alpha < 1f){
            foregroundLayer.get(1).setAlpha(alpha + 0.008f);
        } else {
            isExiting = true;
        }
    }

    @Override
    public void loadContent(){
        try {
            Log.logSuccess("Loading Content");
            BufferedImage logo = ImageIO.read(getClass().getResource("/assets/TeamLogoSplashScreen.png"));
            foregroundLayer.add(new ImageContainer(0,0, logo, 0));

            BufferedImage coverImg = ImageIO.read(getClass().getResource("/assets/TeamLogoSplashScreenCover.png"));
            ImageContainer cover = new ImageContainer(0,0, coverImg, 0);
            cover.setAlpha(1f);
            foregroundLayer.add(cover);

        } catch(IOException e)  {
            Log.logError("Error: " + e.getMessage());
        }
    }

    @Override
    public void update() {
        if(currentState != previousState){
            previousState = currentState;
            Log.log("CurrentState: " + currentState.name());
        }

        switch(currentState) {
            case TransitionOn: updateTransitionOn(); break;
            case Active: currentState = ScreenState.TransitionOff; break;
            case TransitionOff: updateTransitionOff(); break;
            case Hidden: Log.log("State is hidden");
            default: Log.logError("Unknown screen state");
        }
    }

    @Override
    public void draw(Graphics2D graphics) {
        for (Renderable gameObject : backgroundLayer) {
            gameObject.draw(graphics);
        }
        for (Renderable gameObject : backgroundMidLayer) {
            gameObject.draw(graphics);
        }
        for (Renderable gameObject : foregroundLayer) {
            gameObject.draw(graphics);
        }
        for (Renderable gameObject : foregroundMidLayer) {
            gameObject.draw(graphics);
        }
    }
}
