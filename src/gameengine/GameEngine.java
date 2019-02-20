package gameengine;


import input.listeners.MouseController;
import gameengine.physics.PhysicsEngine;
import gameengine.rendering.RenderEngine;
import gamescreens.ScreenManager;
import main.utilities.Debug;
import main.utilities.DebugEnabler;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.TimeUnit;

public class GameEngine implements Runnable {

    private final int FRAMES_PER_SECOND = 60;
    private int frameCounter = 0;

    private ScreenManager screenManager;
    private PhysicsEngine physicsEngine;
    private RenderEngine renderEngine;


    public GameEngine(){
        screenManager = new ScreenManager();

        renderEngine = new RenderEngine(screenManager);
        physicsEngine = new PhysicsEngine(screenManager);
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
            long startTime = System.currentTimeMillis();

            //TODO: Proposed changes
            /* Game engine gets the data from the screen manager then sends it to the
             * physics engine. After its run its course then we send it to the renderEngine
             * So the call would look like:
             * Data data = screenManager.getData();
             * physicsEngine.update(data);
             * renderEngine.draw(data);
             * We just decoupled the screenManager from both the phys engine and the renderer
             * The game data is the fuel to make the engine run lol
             */

            if(screenManager.getScreenData() != null) {
                //Update
                //physicsEngine.update();
                //Render
                screenManager.update();
                renderEngine.draw();
            }

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
