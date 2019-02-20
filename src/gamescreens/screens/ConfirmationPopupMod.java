package gamescreens.screens;

import gameengine.rendering.RenderEngine;
import gamescreens.GameScreen;
import gamescreens.ScreenManager;
import gameobjects.renderables.ImageContainer;
import gameobjects.renderables.RenderableObject;
import gameobjects.renderables.buttons.Button;
import main.utilities.Debug;
import main.utilities.DebugEnabler;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

public class ConfirmationPopupMod extends GameScreen {

    protected CopyOnWriteArrayList<Button> buttons = new CopyOnWriteArrayList<>();
    private final int X_INIT_BUTTON = 427;
    private final int Y_INIT_BUTTON = 400;
    private final int X_BUFFER = 142;
    private boolean goBack = false;
    private String bgImage;
    private char screen;

    public ConfirmationPopupMod(ScreenManager screenManager, String bgImage, char screen) {
        super(screenManager);
        name = "ConfirmationPopupMod";
        exclusivePopup = true;
        this.bgImage = bgImage;
        this.screen = screen;
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
     * Loads the contents of this main.Game Screen.
     */
    @Override
    protected void loadContent() {
        try {
            BufferedImage background = RenderEngine.convertToARGB(ImageIO.read(getClass()
                    .getResource(bgImage)));
            BufferedImage yesButtonIMG = RenderEngine.convertToARGB(ImageIO.read(getClass()
                    .getResource("/assets/buttons/Button-Yes.png")));
            BufferedImage noButtonIMG = RenderEngine.convertToARGB(ImageIO.read(getClass()
                    .getResource("/assets/buttons/Button-No.png")));

            buttons.add(new Button(X_INIT_BUTTON,Y_INIT_BUTTON, yesButtonIMG, 1,
                    (screenManager) ->{
                        Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - Yes");
                        //Yes is continue to new screen.
                        //TODO: Make this not shit
                        //Solo Mode
                        if(screen == 's')  screenManager.addScreen(new TempSoloScreen(screenManager));
                        if(screen == 'c')  screenManager.addScreen(new TempCoopScreen(screenManager));

                    }));
            buttons.add(new Button(X_INIT_BUTTON + noButtonIMG.getWidth() + X_BUFFER,Y_INIT_BUTTON, noButtonIMG, 1,
                    (screenManager) ->{
                        Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - No");
                        //No is stay here.
                        //TODO: Make this not shit
                        goBack = false;
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
    protected void transitionOn() {
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
    protected void transitionOff() {
        float alpha = renderableLayers.get(0).get(0).getAlpha();
        if(alpha > 0.055f){
            for(CopyOnWriteArrayList<RenderableObject> layer : renderableLayers)
                for(RenderableObject renderable : layer)
                    renderable.setAlpha(alpha - 0.05f);
        } else {
            if(goBack)
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
