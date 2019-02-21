package gamescreens.screens;

import gameengine.rendering.RenderEngine;
import gamescreens.DrawLayer;
import gamescreens.GameScreen;
import gamescreens.ScreenManager;
import gameobjects.renderables.ImageContainer;
import gameobjects.renderables.RenderableObject;
import gameobjects.renderables.buttons.Button;
import gameobjects.renderables.labels.Label;
import main.utilities.Debug;
import main.utilities.DebugEnabler;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;


public class OptionScreen extends GameScreen {
    //region <Variables>
    private HashMap<String, CopyOnWriteArrayList<Label>> savedLabels;

    private final int X_INIT_BUTTON = 64;
    private final int Y_INIT_BUTTON = 576;
    private final int WIDTH_BUTTON = 256;
    private float alphaTransition = 0.0f;
    private final int X_BUFFER = 48;
    //endregion

    //region <Construction and Initialization>
    public OptionScreen(ScreenManager screenManager, HashMap savedLabels) {
        super(screenManager, "Options Menu");
        this.savedLabels = savedLabels;
        isExclusive = true;
    }
    @Override
    protected void initializeScreen() {

        //Create buttons
        addObject(new Button(X_INIT_BUTTON,Y_INIT_BUTTON,
                "/assets/buttons/Button-Graphics.png",
                DrawLayer.Entity,
                (screenManager) ->{
                    Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - Graphics");
                    if(savedLabels.get("Graphics") == null)
                        Debug.error(DebugEnabler.BUTTON_LOG,"labels are null");
                    screenManager.addScreen(new GraphicsScreen(screenManager, savedLabels.get("Graphics")));
                }));

        addObject(new Button(X_INIT_BUTTON+X_BUFFER+WIDTH_BUTTON,Y_INIT_BUTTON,
                "/assets/buttons/Button-Sound.png",
                DrawLayer.Entity,
                (screenManager) ->{
                    Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - Sound");
                    //TODO: Add Sound Menu
                }));

        addObject(new Button(X_INIT_BUTTON+2*(X_BUFFER+WIDTH_BUTTON),Y_INIT_BUTTON,
                "/assets/buttons/Button-Controls.png",
                DrawLayer.Entity,
                (screenManager) ->{
                    Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - Controls");
                    //screenManager.addScreen(new ControlsScreen(screenManager, savedLabels.get("Controls")));
                }));

        addObject(new Button(X_INIT_BUTTON+3*(X_BUFFER+WIDTH_BUTTON),Y_INIT_BUTTON,
                "/assets/buttons/Button-MainMenu.png",
                DrawLayer.Entity,
                (screenManager) ->{
                    Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - Main Menu");
                    this.setScreenState(ScreenState.TransitionOff);
                }));

        //Create Background on layer 0
        addObject(new ImageContainer(0,0, "/assets/backgrounds/BG-OptionMenu.png", DrawLayer.Background));
    }

    //endregion

    @Override
    protected void transitionOn() {
        float alpha = alphaTransition;
        if(alpha < 0.9f){
            alphaTransition += 0.05f;
            for(RenderableObject renderable : backgroundLayer)
                renderable.setAlpha(alphaTransition);
            for(RenderableObject renderable : sceneryLayer)
                renderable.setAlpha(alphaTransition);
            for(RenderableObject renderable : effectsLayer)
                renderable.setAlpha(alphaTransition);
            for(RenderableObject renderable : entityLayer)
                renderable.setAlpha(alphaTransition);
        } else {
            for(RenderableObject renderable : backgroundLayer)
                renderable.setAlpha(1.0f);
            for(RenderableObject renderable : sceneryLayer)
                renderable.setAlpha(1.0f);
            for(RenderableObject renderable : effectsLayer)
                renderable.setAlpha(1.0f);
            for(RenderableObject renderable : entityLayer)
                renderable.setAlpha(1.0f);
            currentState = ScreenState.Active;
        }
    }

    @Override
    protected void transitionOff() {
        float alpha = alphaTransition;
        if(alpha > 0.055f){
            alphaTransition -= 0.05f;
            for(RenderableObject renderable : backgroundLayer)
                renderable.setAlpha(alphaTransition);
            for(RenderableObject renderable : sceneryLayer)
                renderable.setAlpha(alphaTransition);
            for(RenderableObject renderable : effectsLayer)
                renderable.setAlpha(alphaTransition);
            for(RenderableObject renderable : entityLayer)
                renderable.setAlpha(alphaTransition);
        } else {
            exiting = true;
        }
    }

    @Override
    protected void hiddenUpdate() {
//        if(!screenManager.coveredByOverlay(this))
//            currentState = ScreenState.TransitionOff;
//        else
//            activeUpdate();
    }

    @Override
    protected void activeUpdate() {
    }
}
