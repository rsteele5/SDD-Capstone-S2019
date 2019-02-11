package control;


import control.listeners.MouseController;
import control.physics.PhysicsEngine;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.TimeUnit;

public class GameEngine implements Runnable {

    private final int FRAMES_PER_SECOND = 60;
    private int frameCounter = 0;

    private PhysicsEngine physicsEngine;
    private RenderEngine renderEngine;
    private HouseGenerator houseGenerator;
    private CharacterGenerator characterGenerator;

    private InputManager inputManager;

    public GameEngine(){
        houseGenerator = new HouseGenerator();
        physicsEngine = new PhysicsEngine();
        characterGenerator = new CharacterGenerator();

        inputManager = new InputManager();
        renderEngine = new RenderEngine();
        renderEngine.addMouseListener(new MouseController());
    }

    public void initializeWindow(JFrame gameWindow){
        Container contentPane = gameWindow.getContentPane();
        contentPane.add(renderEngine);
    }

    @Override
    public void run() {
        //TODO: stuff
        while(true){
            frameCounter++;
            //Debug.log("Frame: " + frameCounter);
            long startTime = System.currentTimeMillis();

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
                //Debug.logWarning("FPS below 60! - current FPS: " + 1000 / (endTime - startTime) );
            }
        }
    }
}
