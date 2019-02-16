package view.screens;

import control.RenderEngine;
import control.ScreenManager;
import model.gameobjects.ImageContainer;
import model.gameobjects.RenderableObject;
import model.gameobjects.buttons.Button;
import model.gameobjects.labels.Label;
import utilities.Debug;
import utilities.DebugEnabler;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;


public class ControlsScreen extends GameScreen {
    //region <Variables>
    protected CopyOnWriteArrayList<Button> buttons = new CopyOnWriteArrayList<>();
    protected CopyOnWriteArrayList<Label> labels = new CopyOnWriteArrayList<>();
    private Label currentLabel; //Variable that keeps track of the current label displayed on screen

    private final int X_INIT_BUTTON = 64;
    private final int X_INIT_LABEL = 96;
    private final int Y_INIT_BUTTON = 576;
    private final int X_BUFFER = 48;
    //endregion

    //region <Construction and Initialization>
    public ControlsScreen(ScreenManager screenManager) {
        super(screenManager);
        name = "OptionScreen";
        exclusivePopup = true;
    }

    @Override
    protected void initializeLayers() {
        renderableLayers.add(new CopyOnWriteArrayList<>());
        renderableLayers.add(new CopyOnWriteArrayList<>());
    }

    @Override
    protected void loadContent() {
        try {
            Debug.success(DebugEnabler.GAME_SCREEN_LOG,name+"-Loading Content");

            //RenderableObject object paths
            BufferedImage background = RenderEngine.convertToARGB(ImageIO.read(getClass()
                    .getResource("/assets/backgrounds/BG-ControlsMenu.png")));
            BufferedImage leftArrowButtonIMG = RenderEngine.convertToARGB(ImageIO.read(getClass()
                    .getResource("/assets/buttons/Button-LeftArrow.png")));
            BufferedImage rightArrowButtonIMG = RenderEngine.convertToARGB(ImageIO.read(getClass()
                    .getResource("/assets/buttons/Button-RightArrow.png")));
            BufferedImage confirmButtonIMG = RenderEngine.convertToARGB(ImageIO.read(getClass()
                    .getResource("/assets/buttons/Button-Confirm.png")));
            BufferedImage backButtonIMG = RenderEngine.convertToARGB(ImageIO.read(getClass()
                    .getResource("/assets/buttons/Button-Back.png")));
            BufferedImage keyboardLabelIMG = RenderEngine.convertToARGB(ImageIO.read(getClass()
                    .getResource("/assets/labels/Label-Keyboard.png")));
            BufferedImage gamepadLabelIMG = RenderEngine.convertToARGB(ImageIO.read(getClass()
                    .getResource("/assets/labels/Label-Gamepad.png")));
            //Create labels
            labels.add(new Label(X_INIT_LABEL, Y_INIT_BUTTON, keyboardLabelIMG, 1, true));
            labels.add(new Label(X_INIT_LABEL, Y_INIT_BUTTON, gamepadLabelIMG, 1, false));
            //Create buttons
            buttons.add(new Button(X_INIT_BUTTON,Y_INIT_BUTTON, leftArrowButtonIMG, 1,
                    (screenManager) ->{
                        Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - Left Arrow");
                        renderableLayers.get(currentLabel.getDrawLayer()).remove(currentLabel);
                        currentLabel = labels.get((labels.indexOf(currentLabel) > 0 ? labels.indexOf(currentLabel) - 1
                                : labels.size() - 1));
                        renderableLayers.get(currentLabel.getDrawLayer()).add(currentLabel);
                    }));

            buttons.add(new Button(X_INIT_BUTTON+X_BUFFER+WIDTH_BUTTON,Y_INIT_BUTTON, rightArrowButtonIMG, 1,
                    (screenManager) ->{
                        Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - Right Arrow");
                        renderableLayers.get(currentLabel.getDrawLayer()).remove(currentLabel);
                        currentLabel = labels.get((labels.indexOf(currentLabel) + 1) % labels.size());
                        renderableLayers.get(currentLabel.getDrawLayer()).add(currentLabel);
                    }));

            buttons.add(new Button(X_INIT_BUTTON+2*(X_BUFFER+WIDTH_BUTTON),Y_INIT_BUTTON, confirmButtonIMG, 1,
                    (screenManager) ->{
                        Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - Confirm");
                        //TODO: Save input settings
                        this.setScreenState(ScreenState.TransitionOff);
                    }));

            buttons.add(new Button(X_INIT_BUTTON+3*(X_BUFFER+WIDTH_BUTTON),Y_INIT_BUTTON, backButtonIMG, 1,
                    (screenManager) ->{
                        Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - Back");
                        this.setScreenState(ScreenState.TransitionOff);
                    }));

            //Create Background on layer 0
            renderableLayers.get(0).add(new ImageContainer(0,0, background, 0));
            //renderableLayers.get(0).add(new ImageContainer(0,0, bgCover, 0));

            //Consolidate Renderables
            for(Button butt: buttons)
                renderableLayers.get(butt.getDrawLayer()).add(butt);

            for (Label lab: labels)
                if(lab.isActive) {
                    renderableLayers.get(lab.getDrawLayer()).add(lab);
                    currentLabel = lab;
                }

            //Consolidate GameObjects
            for(CopyOnWriteArrayList<RenderableObject> layer: renderableLayers)
                gameObjects.addAll(layer);

            for(CopyOnWriteArrayList<RenderableObject> layer : renderableLayers)
                for(RenderableObject renderable : layer)
                    renderable.setAlpha(0.0f);

            Debug.success(DebugEnabler.GAME_SCREEN_LOG,name+"-Loaded Success");
        } catch(IOException e)  {
            Debug.error(DebugEnabler.GAME_SCREEN_LOG,"Error: " + e.getMessage());
        }
    }
    //endregion

    @Override
    protected void updateTransitionOn() {
        float alpha = renderableLayers.get(0).get(0).getAlpha();
        if(alpha < 0.9f){
            for(CopyOnWriteArrayList<RenderableObject> layer : renderableLayers)
                for(RenderableObject renderable : layer)
                    renderable.setAlpha(alpha + 0.05f);
        } else {
            for(CopyOnWriteArrayList<RenderableObject> layer : renderableLayers)
                for(RenderableObject renderable : layer)
                    renderable.setAlpha(1.0f);
            currentState = ScreenState.Active;
        }
    }

    @Override
    protected void updateTransitionOff() {
        float alpha = renderableLayers.get(0).get(0).getAlpha();
        if(alpha > 0.055f){
            for(CopyOnWriteArrayList<RenderableObject> layer : renderableLayers)
                for(RenderableObject renderable : layer)
                    renderable.setAlpha(alpha - 0.05f);
        } else {
            exiting = true;
        }
    }

    @Override
    protected void hiddenUpdate() {
        if(!screenManager.coveredByOverlay(this))
            currentState = ScreenState.TransitionOff;
        else
            activeUpdate();
    }

    @Override
    protected void activeUpdate() {
    }

    @Override
    public void handleClickEvent(int x, int y) {
        for(Button butt: buttons) {
            if(butt.getBoundingBox().contains(x,y)) {
                butt.onClick.accept(screenManager);
                return;
            }
        }
    }
}
