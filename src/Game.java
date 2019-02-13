import control.GameEngine;
import utilities.Debug;
import utilities.DebugEnabler;
import view.window.GameWindow;

import javax.swing.JFrame;
import java.awt.*;

public class Game {

    private static GameEngine gameEngine;

    //TODO: Make Adjustable
    public static int WIN_WIDTH = 1280;
    public static int WIN_HEIGHT = 720;


    public static void main(String[] args) {
        Debug.startLog();

        if(!DebugEnabler.LOGGING_ACTIVE){
            Debug.endLog();
        }
        //Initialize and display the renderable portion
        JFrame gameWindow = new GameWindow();
        gameWindow.setTitle("Nightbears");
        gameWindow.setSize(WIN_WIDTH, WIN_HEIGHT);
        gameWindow.setLocation(20,20);
        gameWindow.setResizable(false);
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameWindow.setBackground(Color.BLACK);
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
