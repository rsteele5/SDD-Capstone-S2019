package control;


import view.GameWindow;
import view.RenderEngine;

import javax.swing.*;
import java.awt.*;

public class GameEngine implements Runnable {


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
        renderEngine = new RenderEngine(houseGenerator.getHouse());
    }

    public void initializeWindow(JFrame gameWindow){
        Container contentPane = gameWindow.getContentPane();
        contentPane.add(renderEngine);
    }

    @Override
    public void run() {
        //TODO: stuff
        while(true){
            renderEngine.render();
        }
    }
}
