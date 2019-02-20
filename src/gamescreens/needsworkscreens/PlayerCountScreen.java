package gamescreens.needsworkscreens;

import gameengine.rendering.RenderEngine;
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


public class PlayerCountScreen extends GameScreen {
    //region <Variables>
    protected CopyOnWriteArrayList<Button> buttons = new CopyOnWriteArrayList<>();
    protected CopyOnWriteArrayList<Label> labels;
    private Label currentLabel; //Variable that keeps track of the current label displayed on screen

    private final int X_INIT_BUTTON = 64;
    private final int Y_INIT_BUTTON = 576;
    private final int X_BUFFER = 48;
    private final int TEDDY_HEIGHT = 200;
    private final int HALF_TEDDY_WIDTH = 50;
    private final int QRTR_BUTTON_WIDTH = 65;

    //endregion

    //region <Construction and Initialization>
    public PlayerCountScreen(ScreenManager screenManager) {
        super(screenManager);
        name = "PlayerCountScreen";
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
                    .getResource("/assets/backgrounds/BG-PlayersMenu.png")));
            BufferedImage soloButtonIMG = RenderEngine.convertToARGB(ImageIO.read(getClass()
                    .getResource("/assets/buttons/Button-Solo.png")));
            BufferedImage coopButtonIMG = RenderEngine.convertToARGB(ImageIO.read(getClass()
                    .getResource("/assets/buttons/Button-Coop.png")));
            BufferedImage backButtonIMG = RenderEngine.convertToARGB(ImageIO.read(getClass()
                    .getResource("/assets/buttons/Button-Back.png")));
            BufferedImage teddyImageIMG = RenderEngine.convertToARGB(ImageIO.read(getClass()
                    .getResource("/assets/Teddy.png")));
            BufferedImage teddy2ImageIMG = RenderEngine.convertToARGB(ImageIO.read(getClass()
                    .getResource("/assets/Teddy2.png")));
            BufferedImage yesButtonIMG = RenderEngine.convertToARGB(ImageIO.read(getClass()
                    .getResource("/assets/buttons/Button-Yes.png")));
            BufferedImage noButtonIMG = RenderEngine.convertToARGB(ImageIO.read(getClass()
                    .getResource("/assets/buttons/Button-No.png")));




            //Create Buttons
            buttons.add(new Button(X_INIT_BUTTON+0*(X_BUFFER+WIDTH_BUTTON),Y_INIT_BUTTON, soloButtonIMG, 1,
                    (screenManager) ->{
                        Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - Solo");
                        screenManager.addScreen(new ConfirmationPopupMod(screenManager,"/assets/backgrounds/BG-ConfirmSoloPopup.png", 's'));
                        this.setScreenState(ScreenState.TransitionOff);
                        //
                        /*Debug.warning(DebugEnabler.GAME_SCREEN_LOG, this.name + "-State: "
                                + this.getScreenState().name()
                                + ", index: " + screenManager.getScreens().indexOf(this));*/
                    }));

            buttons.add(new Button(X_INIT_BUTTON+2*(X_BUFFER+WIDTH_BUTTON),Y_INIT_BUTTON, coopButtonIMG, 1,
                    (screenManager) ->{
                        Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - Coop");
                        screenManager.addScreen(new ConfirmationPopupMod(screenManager,"/assets/backgrounds/BG-ConfirmCoopPopup.png", 'c'));
                        this.setScreenState(ScreenState.TransitionOff);
                        //
                        /*Debug.warning(DebugEnabler.GAME_SCREEN_LOG, this.name + "-State: "
                                + this.getScreenState().name()
                                + ", index: " + screenManager.getScreens().indexOf(this));*/
                    }));
            buttons.add(new Button(X_INIT_BUTTON+3*(X_BUFFER+WIDTH_BUTTON),Y_INIT_BUTTON, backButtonIMG, 1,
                    (screenManager) ->{
                        Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - Back");
                        this.setScreenState(ScreenState.TransitionOff);

                    }));

            /* Create all renderables */
            renderableLayers.get(0).add(new ImageContainer(0,0, background, 0));
            renderableLayers.get(0).add(new ImageContainer(X_INIT_BUTTON + 2*QRTR_BUTTON_WIDTH - HALF_TEDDY_WIDTH, Y_INIT_BUTTON- TEDDY_HEIGHT, teddyImageIMG, 1));
            renderableLayers.get(0).add(new ImageContainer(X_INIT_BUTTON + QRTR_BUTTON_WIDTH - HALF_TEDDY_WIDTH +2*(X_BUFFER+WIDTH_BUTTON), Y_INIT_BUTTON- TEDDY_HEIGHT, teddyImageIMG, 1));
            renderableLayers.get(0).add(new ImageContainer(X_INIT_BUTTON + 3*QRTR_BUTTON_WIDTH - HALF_TEDDY_WIDTH +2*(X_BUFFER+WIDTH_BUTTON), Y_INIT_BUTTON- TEDDY_HEIGHT, teddy2ImageIMG, 1));

            //Consolidate Renderables
            for(Button butt: buttons)
                renderableLayers.get(butt.getDrawLayer()).add(butt);

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
        //Set all labels alpha equal to one, even if they aren't being rendered at the time
        for(CopyOnWriteArrayList<RenderableObject> layer : renderableLayers)
            for(RenderableObject renderable : layer)
                renderable.setAlpha(1.0f);
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
