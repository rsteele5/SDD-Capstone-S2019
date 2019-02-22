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
import java.util.concurrent.CopyOnWriteArrayList;


public class GraphicsScreen extends GameScreen {
    //region <Variables>
    protected CopyOnWriteArrayList<Button> buttons = new CopyOnWriteArrayList<>();
    protected CopyOnWriteArrayList<Label> labels;
    private Label currentLabel; //Variable that keeps track of the current label displayed on screen

    private final int X_INIT_BUTTON = 64;
    private final int Y_INIT_BUTTON = 576;
    private final int WIDTH_BUTTON = 256;
    private float alphaTransition = 0.0f;
    private final int X_BUFFER = 48;
    //endregion

    //region <Construction and Initialization>
    public GraphicsScreen(ScreenManager screenManager, CopyOnWriteArrayList<Label> controlLabels) {
        super(screenManager, "ControlsScreen");
        labels = controlLabels;
        isExclusive = true;
    }
    @Override
    protected void initializeScreen() {
        //Create buttons
        addObject(new Button(X_INIT_BUTTON,Y_INIT_BUTTON,
                "/assets/buttons/Button-LeftArrow.png",
                DrawLayer.Entity,
                (screenManager) ->{
                    Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - Left Arrow");
                    entityLayer.remove(currentLabel);
                    currentLabel = labels.get((labels.indexOf(currentLabel) > 0 ? labels.indexOf(currentLabel) - 1
                            : labels.size() - 1));
                    entityLayer.add(currentLabel);
                }));

        addObject(new Button(X_INIT_BUTTON+X_BUFFER+WIDTH_BUTTON,Y_INIT_BUTTON,
                "/assets/buttons/Button-RightArrow.png",
                DrawLayer.Entity,
                (screenManager) ->{
                    Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - Right Arrow");
                    entityLayer.remove(currentLabel);
                    currentLabel = labels.get((labels.indexOf(currentLabel) + 1) % labels.size());
                    entityLayer.add(currentLabel);
                }));

        addObject(new Button(X_INIT_BUTTON+2*(X_BUFFER+WIDTH_BUTTON),Y_INIT_BUTTON,
                "/assets/buttons/Button-Confirm.png",
                DrawLayer.Entity,
                (screenManager) ->{
                    Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - Confirm");
                    for (Label lab : labels) {
                        if (lab == currentLabel)
                            lab.setActive(true);
                        else
                            lab.setActive(false);
                    }
                    this.setScreenState(ScreenState.TransitionOff);
                }));

        addObject(new Button(X_INIT_BUTTON+3*(X_BUFFER+WIDTH_BUTTON),Y_INIT_BUTTON,
                "/assets/buttons/Button-Back.png",
                DrawLayer.Entity,
                (screenManager) ->{
                    Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - Back");
                    //screenManager.addScreen(new ConfirmationPopup(screenManager, "/assets/backgrounds/BG-ConfirmationPopup.png"));
                    //this.setScreenState(ScreenState.TransitionOff);
                }));

        //Create Background on layer 0
        addObject(new ImageContainer(0,0, "/assets/backgrounds/BG-GraphicsMenu.png", DrawLayer.Background));
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
    protected void activeUpdate() {
        //Set all labels alpha equal to one, even if they aren't being rendered at the time
        for(RenderableObject renderable : entityLayer)
            renderable.setAlpha(1.0f);
    }
}
