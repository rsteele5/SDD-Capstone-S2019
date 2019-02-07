import control.GameEngine;
import view.GameWindow;

import javax.swing.JFrame;

public class Game {
    public static GameEngine gameEngine;

    //TODO: Make Adjustable
    public static int WIN_WITDH = 1280;
    public static int WIN_HIEGHT = 720;

    public static void main(String[] args) {
        gameEngine = new GameEngine();

        //Initialize and display the renderable portion
        JFrame gameWindow = new GameWindow();
        gameWindow.setTitle("Nightbears");
        gameWindow.setSize(WIN_WITDH, WIN_HIEGHT);
        gameWindow.setLocation(50,50);
        gameWindow.setResizable(false);
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameWindow.setVisible(true);


        new Thread(gameEngine).start();
    }
}
