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


public class DevScreen extends GameScreen {
    //region <Variables>
    protected CopyOnWriteArrayList<Button> buttons = new CopyOnWriteArrayList<>();

    private final int X_INIT_BUTTON = 64;
    private final int Y_INIT_BUTTON = 576;
    private final int X_BUFFER = 64;
    //endregion

    //region <Construction and Initialization>
    public DevScreen(ScreenManager screenManager) {
        super(screenManager);
        name = "DevScreen";
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
                    .getResource("/assets/backgrounds/BG-DevMenu.png")));
            BufferedImage bgCover = RenderEngine.convertToARGB(ImageIO.read(getClass()
                    .getResource("/assets/backgrounds/BG-BlackCover.png")));
            BufferedImage loadingButtonIMG = RenderEngine.convertToARGB(ImageIO.read(getClass()
                    .getResource("/assets/buttons/Button-Loading.png")));
            BufferedImage physicsButtonIMG = RenderEngine.convertToARGB(ImageIO.read(getClass()
                    .getResource("/assets/buttons/Button-Physics.png")));
            BufferedImage inventoryButtonIMG = RenderEngine.convertToARGB(ImageIO.read(getClass()
                    .getResource("/assets/buttons/Button-Inventory.png")));
            BufferedImage mainMenuButtonIMG = RenderEngine.convertToARGB(ImageIO.read(getClass()
                    .getResource("/assets/buttons/Button-MainMenu.png")));
            //Create buttons
            buttons.add(new Button(X_INIT_BUTTON,Y_INIT_BUTTON, loadingButtonIMG, 1,
                    (screenManager) ->{
                        Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - Loading");
                        //TODO: Add Loading Screen
                    }));

            buttons.add(new Button(X_INIT_BUTTON+X_BUFFER+WIDTH_BUTTON,Y_INIT_BUTTON, physicsButtonIMG, 1,
                    (screenManager) ->{
                        Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - Physics");
                        //TODO: Add Physics Test Screen
                    }));

            buttons.add(new Button(X_INIT_BUTTON+2*(X_BUFFER+WIDTH_BUTTON),Y_INIT_BUTTON, inventoryButtonIMG, 1,
                    (screenManager) ->{
                        Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - Inventory");
                        //TODO: Add Inventory Screen
                    }));

            buttons.add(new Button(X_INIT_BUTTON+3*(X_BUFFER+WIDTH_BUTTON),Y_INIT_BUTTON, mainMenuButtonIMG, 1,
                    (screenManager) ->{
                        Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - Main Menu");
                        this.setScreenState(ScreenState.TransitionOff);
                    }));

            //Create Background on layer 0
            renderableLayers.get(0).add(new ImageContainer(0,0, background, 0));
            //renderableLayers.get(0).add(new ImageContainer(0,0, bgCover, 0));

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
//        float alpha = renderableLayers.get(0).get(1).getAlpha();
//        if(alpha > 0.055f){
//            renderableLayers.get(0).get(1).setAlpha(alpha - 0.05f);
//        }else
//            currentState = ScreenState.Active;
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
