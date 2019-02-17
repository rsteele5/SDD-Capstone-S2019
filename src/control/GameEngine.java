package control;


import control.listeners.MouseController;
import control.physics.PhysicsEngine;
import utilities.Debug;
import utilities.DebugEnabler;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.TimeUnit;

import java.io.*;
import java.net.URL;
import javax.sound.sampled.*;
import javax.swing.*;

public class GameEngine implements Runnable {

    private final int FRAMES_PER_SECOND = 60;
    private int frameCounter = 0;

    private ScreenManager screenManager;
    private PhysicsEngine physicsEngine;
    private RenderEngine renderEngine;
    private HouseGenerator houseGenerator;
    private CharacterGenerator characterGenerator;

    private InputManager inputManager;

    public GameEngine(){
        houseGenerator = new HouseGenerator();
        characterGenerator = new CharacterGenerator();
        screenManager = new ScreenManager();

        inputManager = new InputManager();
        renderEngine = new RenderEngine(screenManager);
        physicsEngine = new PhysicsEngine(screenManager);
        renderEngine.addMouseListener(new MouseController());

    }

    public void initializeWindow(JFrame gameWindow){
        Container contentPane = gameWindow.getContentPane();
        contentPane.add(renderEngine);
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

    @Override
    public void run() {
        //TODO: stuff
        while(true){
            frameCounter++;
            long startTime = System.currentTimeMillis();

            //Update
            if(screenManager.getLevelData() != null) physicsEngine.update();
            //Render
            screenManager.update();
            renderEngine.draw();

            long endTime = System.currentTimeMillis();
            int sleepTime = (int) (1.0 / FRAMES_PER_SECOND * 1000)
                    - (int) (endTime - startTime);

            if (sleepTime >= 0) {
                try {
                    TimeUnit.MILLISECONDS.sleep(sleepTime);
                } catch (InterruptedException e) {

                }
            } else {
                Debug.warning(DebugEnabler.FPS,"FPS below 60! - current FPS: " + 1000 / (endTime - startTime) );
            }

            Debug.flush();
        }
    }
}
