package gamescreens.screens;

import gameengine.rendering.RenderEngine;
import gamescreens.GameScreen;
import gamescreens.ScreenManager;
import gameobjects.renderables.ImageContainer;
import main.utilities.Debug;
import main.utilities.DebugEnabler;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.CopyOnWriteArrayList;

public class TitleScreen extends GameScreen {
    //region <Variables>
    private ScreenState previousState = null; //TODO: Remove after testing
    //endregion
    private boolean musicStart = false;
    //region <Construction and Initialization>
    public TitleScreen(ScreenManager screenManager) {
        super(screenManager);
        name = "TitleScreen";
        overlay = true;
    }

    @Override
    protected void initializeLayers() {
        renderableLayers.add(new CopyOnWriteArrayList<>());
    }

    @Override
    public void loadContent(){
        try {
            Debug.success(DebugEnabler.GAME_SCREEN_LOG,name+"Loading Content");

            BufferedImage background = RenderEngine.convertToARGB(ImageIO.read(getClass()
                    .getResource("/assets/backgrounds/BG-BlackCover.png")));
            renderableLayers.get(0).add(new ImageContainer(0,0, background, 0));

            BufferedImage cover = RenderEngine.convertToARGB(ImageIO.read(getClass()
                    .getResource("/assets/backgrounds/BG-TitleScreenCover.png")));
            renderableLayers.get(0).add(new ImageContainer(0,-720, cover, 3));

            BufferedImage title = RenderEngine.convertToARGB(ImageIO.read(getClass()
                    .getResource("/assets/backgrounds/BG-Title.png")));
            renderableLayers.get(0).add(new ImageContainer(350,75, title, 2));

            BufferedImage skipImg = RenderEngine.convertToARGB(ImageIO.read(getClass().getResource("/assets/text/TXT-SkipMsg.png")));
            ImageContainer skipMsg = new ImageContainer(575,660, skipImg, 0);
            renderableLayers.get(0).add(skipMsg);

            Debug.success(DebugEnabler.GAME_SCREEN_LOG,name+"Loaded Success");
        } catch(IOException e)  {
            Debug.error(DebugEnabler.GAME_SCREEN_LOG,"Error: " + e.getMessage());
        }
    }
    //endregion

    //region <Update>
    @Override
    public void transitionOn() {
        if(!musicStart) {
            musicStart = true;
            try {
                // Open an audio input stream.
                URL url = this.getClass().getClassLoader().getResource("assets/music/title.wav");
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
                // Get a sound clip resource.
                Clip clip = AudioSystem.getClip();
                // Open audio clip and load samples from the audio input stream.
                clip.open(audioIn);
                clip.start();
            } catch (UnsupportedAudioFileException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }
        }
        if(renderableLayers.get(0).get(1).getY() < -240)
            renderableLayers.get(0).get(1).setY(renderableLayers.get(0).get(1).getY() + 2);
        else
            currentState = ScreenState.Active;
    }

    @Override
    public void transitionOff() {
        Debug.success(DebugEnabler.GAME_SCREEN_LOG,"Constructing MainMenuScreen");
        screenManager.addScreen(new MainMenuScreen(screenManager));
    }

    @Override
    public void hiddenUpdate() {
        Debug.log(DebugEnabler.GAME_SCREEN_LOG,name+"-CurrentState: Hidden"); //TODO: handle hidden state
        if(!screenManager.coveredByLoading(this))
            exiting = true;
    }

    @Override
    protected void activeUpdate() {
        currentState = ScreenState.TransitionOff;
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
        screenManager.addScreen(new MainMenuScreen(screenManager));
    }
    //endregion
}
