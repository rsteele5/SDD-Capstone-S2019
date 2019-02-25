package gamescreens.screens;

import gamescreens.DrawLayer;
import gamescreens.GameScreen;
import gamescreens.ScreenManager;
import gameobjects.renderables.ImageContainer;
import gamescreens.screens.menus.MainMenuScreen;
import main.utilities.Debug;
import main.utilities.DebugEnabler;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class TitleScreen extends GameScreen {
    //region <Variables>
    private ScreenState previousState = null; //TODO: Remove after testing
    //endregion
    private boolean musicStart = false;
    //region <Construction and Initialization>
    public TitleScreen(ScreenManager screenManager, String name) {
        super(screenManager,name);
    }

    /**
     * Initializes all of the stuff you want on your screen
     */
    @Override
    protected void initializeScreen() {
        ImageContainer image;
        image = new ImageContainer(0,0, "/assets/backgrounds/BG-BlackCover.png", DrawLayer.Background);
        image.addToScreen(this,true);
        image = new ImageContainer(0,-720, "/assets/backgrounds/BG-TitleScreenCover.png", DrawLayer.Background);
        image.addToScreen(this,true);
        image = new ImageContainer(350,75, "/assets/backgrounds/BG-Title.png", DrawLayer.Background);
        image.addToScreen(this,true);
        image = new ImageContainer(575,660, "/assets/text/TXT-SkipMsg.png", DrawLayer.Background);
        image.addToScreen(this,true);
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
        if(renderables.get(1).getY() < -240)
            renderables.get(1).setY(renderables.get(1).getY() + 2);
        else
            currentState = ScreenState.Active;
    }

    @Override
    public void transitionOff() {
        Debug.success(DebugEnabler.GAME_SCREEN_LOG,"Constructing MainMenuScreen");
        screenManager.addScreen(new MainMenuScreen(screenManager));
        exiting = true;
    }

    @Override
    public void hiddenUpdate() {
        Debug.log(DebugEnabler.GAME_SCREEN_LOG,name+"-CurrentState: Hidden"); //TODO: handle hidden state
            exiting = true;
    }

    @Override
    protected void activeUpdate() {
        currentState = ScreenState.TransitionOff;
    }

    //endregion

    //region <Support Functions>
    @Override
    public boolean handleClickEvent(int x, int y) {
        screenManager.addScreen(new MainMenuScreen(screenManager));
        return true;
    }
    //endregion
}
