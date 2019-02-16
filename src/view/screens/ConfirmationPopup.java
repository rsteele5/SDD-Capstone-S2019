package view.screens;

import control.RenderEngine;
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

public class ConfirmationPopup extends GameScreen {

    protected CopyOnWriteArrayList<Button> buttons = new CopyOnWriteArrayList<>();
    private final int X_INIT_BUTTON = 427;
    private final int Y_INIT_BUTTON = 400;
    private final int X_BUFFER = 142;

    public ConfirmationPopup(ScreenManager screenManager) {
        super(screenManager);
        name = "ConfirmationPopup";
        exclusivePopup = true;
    }

    /**
     * Initializes the renderableLayers array with an arbitrary amount of layers.
     */
    @Override
    protected void initializeLayers() {
        renderableLayers.add(new CopyOnWriteArrayList<>());
        renderableLayers.add(new CopyOnWriteArrayList<>());
    }

    /**
     * Loads the contents of this Game Screen.
     */
    @Override
    protected void loadContent() {
        try {
            BufferedImage background = RenderEngine.convertToARGB(ImageIO.read(getClass()
                    .getResource("/assets/backgrounds/BG-ConfirmationPopup.png")));
            BufferedImage yesButtonIMG = RenderEngine.convertToARGB(ImageIO.read(getClass()
                    .getResource("/assets/buttons/Button-Yes.png")));
            BufferedImage noButtonIMG = RenderEngine.convertToARGB(ImageIO.read(getClass()
                    .getResource("/assets/buttons/Button-No.png")));

            buttons.add(new Button(X_INIT_BUTTON,Y_INIT_BUTTON, yesButtonIMG, 1,
                    (screenManager) ->{
                        Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - Yes");
                        //TODO: Saves to a file?? maybe?
                        this.setScreenState(ScreenState.TransitionOff);

                    }));
            buttons.add(new Button(X_INIT_BUTTON + noButtonIMG.getWidth() + X_BUFFER,Y_INIT_BUTTON, noButtonIMG, 1,
                    (screenManager) ->{
                        Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - No");
                        //TODO: remove changes if any?
                        this.setScreenState(ScreenState.TransitionOff);
                    }));
            //popup background
            renderableLayers.get(0).add(new ImageContainer(0,0, background, 0));

            for(Button butt: buttons)
                renderableLayers.get(butt.getDrawLayer()).add(butt);

            for(CopyOnWriteArrayList<RenderableObject> layer: renderableLayers)
                gameObjects.addAll(layer);

            for(CopyOnWriteArrayList<RenderableObject> layer : renderableLayers)
                for(RenderableObject renderable : layer)
                    renderable.setAlpha(0.0f);

        } catch(IOException e)  {
            Debug.error(DebugEnabler.GAME_SCREEN_LOG,"Error: " + e.getMessage());
        }
    }

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
            screenManager.removeScreen(screenManager.getScreens().get(1));
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
