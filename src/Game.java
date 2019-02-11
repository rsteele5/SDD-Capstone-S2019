import control.GameEngine;
import utilities.Debug;
import utilities.DebugEnabler;
import view.renderengine.window.GameWindow;

import javax.swing.JFrame;

public class Game {

    private static GameEngine gameEngine;

    //TODO: Make Adjustable
    public static int WIN_WIDTH = 1280;
    public static int WIN_HEIGHT = 720;


    public static void main(String[] args) {
        Debug.startLog();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            Debug.endLog();
        }));
        if(!DebugEnabler.LOGGING_ACTIVE){

        }

        //Initialize and display the renderable portion
        JFrame gameWindow = new GameWindow();
        gameWindow.setTitle("Nightbears");
        gameWindow.setSize(WIN_WIDTH, WIN_HEIGHT);
        gameWindow.setLocation(320,20);
        gameWindow.setResizable(false);
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Uncomment these to make the game full screen
        //gameWindow.setExtendedState(JFrame.MAXIMIZED_BOTH);
        gameEngine = new GameEngine();
        //Attach the RenderEngine to the window
        gameEngine.initializeWindow(gameWindow);
        gameWindow.setUndecorated(true);
        gameWindow.setVisible(true);
        new Thread(gameEngine).start();

    }
}
