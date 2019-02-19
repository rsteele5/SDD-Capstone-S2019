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
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;


public class MainMenuScreen extends view.screens.GameScreen {

    //region <Variables>
    protected CopyOnWriteArrayList<Button> buttons = new CopyOnWriteArrayList<>();
    private HashMap<String, CopyOnWriteArrayList<Label>> savedLabels = new HashMap<>();

    private final int X_INIT_BUTTON = 64;
    private final int X_INIT_LABEL = 96;
    private final int Y_INIT_BUTTON = 576;
    private final int X_BUFFER = 48;
    //endregion

    //region <Construction and Initialization>
    public MainMenuScreen(ScreenManager screenManager) {
        super(screenManager);
        name = "MainMenuScreen";
    }

    @Override
    protected void initializeLayers() {
        renderableLayers.add(new CopyOnWriteArrayList<>());
        renderableLayers.add(new CopyOnWriteArrayList<>());
    }

    @Override
    public void loadContent(){
        try {
            Debug.success(DebugEnabler.GAME_SCREEN_LOG,name+"-Loading Content");

            //RenderableObject object paths
            BufferedImage background = RenderEngine.convertToARGB(ImageIO.read(getClass()
                    .getResource("/assets/backgrounds/BG-MainMenu.png")));
            BufferedImage newGameButtonIMG = RenderEngine.convertToARGB(ImageIO.read(getClass()
                    .getResource("/assets/buttons/Button-NewGame.png")));
            BufferedImage optionsButtonIMG = RenderEngine.convertToARGB(ImageIO.read(getClass()
                    .getResource("/assets/buttons/Button-Options.png")));
            BufferedImage devModeButtonIMG = RenderEngine.convertToARGB(ImageIO.read(getClass()
                    .getResource("/assets/buttons/Button-Dev.png")));
            //Create labels
            createMenuLabels();
            //Create buttons
            buttons.add(new Button(X_INIT_BUTTON,Y_INIT_BUTTON, newGameButtonIMG, 1, (screenManager) ->{
                Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - New Game");
                screenManager.addScreen(new PlayerCountScreen(screenManager));
            }));

            //Create labels
            createMenuLabels();

            buttons.add(new Button(X_INIT_BUTTON+X_BUFFER+WIDTH_BUTTON,Y_INIT_BUTTON, optionsButtonIMG, 1, (screenManager) ->{
                Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - Options");
                screenManager.addScreen(new OptionScreen(screenManager, savedLabels));
            }));

            buttons.add(new Button(X_INIT_BUTTON+2*(X_BUFFER+WIDTH_BUTTON),Y_INIT_BUTTON, devModeButtonIMG, 1, (screenManager) ->{
                Debug.success(DebugEnabler.BUTTON_LOG,"Clicked Button - DevMode");
                screenManager.addScreen(new DevScreen(screenManager));
            }));

            //Create Background on layer 0
            renderableLayers.get(0).add(new ImageContainer(0,0, background, 0));

            //Consolidate Renderables
            for(Button butt: buttons)
                renderableLayers.get(butt.getDrawLayer()).add(butt);

            //Consolidate GameObjects
            for(CopyOnWriteArrayList<RenderableObject> layer: renderableLayers)
                gameObjects.addAll(layer);

            Debug.success(DebugEnabler.GAME_SCREEN_LOG,name+"-Loaded Success");
        } catch(IOException e)  {
            Debug.error(DebugEnabler.GAME_SCREEN_LOG,"Error: " + e.getMessage());
        }
    }
    //endregion

    //region <Update>
    @Override
    protected void updateTransitionOn() {
        currentState = ScreenState.Active;
    }

    @Override
    protected void updateTransitionOff() {
        exiting = true;
    }

    @Override
    protected void hiddenUpdate() {
        if(!screenManager.coveredByOverlay(this))
            currentState = ScreenState.TransitionOff;
        else
            activeUpdate();
    }

    @Override
    protected void activeUpdate(){

    }
    //endregion

    //region <Support Functions>
    @Override
    public void handleClickEvent(int x, int y) {
        for(Button butt: buttons) {
            if(butt.getBoundingBox().contains(x,y)) {
                butt.onClick.accept(screenManager);
                return;
            }
        }
    }
    /**
     * NOTE: THIS IS ONLY A TEMPORARY WAY OF SAVING LABELS.*/
    private void createMenuLabels() {
        //Variables
        CopyOnWriteArrayList<Label> controlLabels = new CopyOnWriteArrayList<>();
        CopyOnWriteArrayList<Label> graphicsLabels = new CopyOnWriteArrayList<>();

        //RenderableObject object paths
        try {
            BufferedImage keyboardLabelIMG = RenderEngine.convertToARGB(ImageIO.read(getClass()
                    .getResource("/assets/labels/Label-Keyboard.png")));
            BufferedImage gamepadLabelIMG = RenderEngine.convertToARGB(ImageIO.read(getClass()
                    .getResource("/assets/labels/Label-Gamepad.png")));
            BufferedImage lowLabelIMG = RenderEngine.convertToARGB(ImageIO.read(getClass()
                    .getResource("/assets/labels/Label-Low.png")));
            BufferedImage mediumLabelIMG = RenderEngine.convertToARGB(ImageIO.read(getClass()
                    .getResource("/assets/labels/Label-Medium.png")));
            BufferedImage highLabelIMG = RenderEngine.convertToARGB(ImageIO.read(getClass()
                    .getResource("/assets/labels/Label-High.png")));
            //Add Control Menu labels
            controlLabels.add(new Label(X_INIT_LABEL, Y_INIT_BUTTON, keyboardLabelIMG, 1, true));
            controlLabels.add(new Label(X_INIT_LABEL, Y_INIT_BUTTON, gamepadLabelIMG, 1, false));
            //Add Graphics Menu labels
            graphicsLabels.add(new Label(X_INIT_LABEL, Y_INIT_BUTTON, lowLabelIMG, 1, false));
            graphicsLabels.add(new Label(X_INIT_LABEL, Y_INIT_BUTTON, mediumLabelIMG, 1, true));
            graphicsLabels.add(new Label(X_INIT_LABEL, Y_INIT_BUTTON, highLabelIMG, 1, false));
            //Add dynamic arrays to map
            savedLabels.put("Controls", controlLabels);
            savedLabels.put("Graphics", graphicsLabels);
        } catch (IOException e) {
            Debug.error(DebugEnabler.GAME_SCREEN_LOG,"Error: " + e.getMessage());
        }
    }
    //endregion

}
