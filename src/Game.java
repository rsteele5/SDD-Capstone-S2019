import control.GameEngine;
import view.GameWindow;

import javax.swing.JFrame;

public class Game {

    private static GameEngine gameEngine;

    //TODO: Make Adjustable
    public static int WIN_WIDTH = 1280;
    public static int WIN_HEIGHT = 720;


    public static void main(String[] args) {
        gameEngine = new GameEngine();

        //Initialize and display the renderable portion
        JFrame gameWindow = new GameWindow();
        gameWindow.setTitle("Nightbears");
        gameWindow.setSize(WIN_WIDTH, WIN_HEIGHT);
        gameWindow.setLocation(50,50);
        gameWindow.setResizable(false);
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameWindow.setVisible(true);

        //Attach the RenderEngine to the window
        gameEngine.initializeWindow(gameWindow);

        new Thread(gameEngine).start();
    }
}
