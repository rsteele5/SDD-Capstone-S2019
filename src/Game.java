import control.GameEngine;

public class Game {
    public static GameEngine gameEngine;

    public static void main(String[] args) {
        gameEngine = new GameEngine();

        new Thread(gameEngine).start();
    }
}
