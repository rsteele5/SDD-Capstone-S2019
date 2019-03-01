package gameengine.physics;

import gameengine.GameEngine;
import gameobjects.Player;
import gamescreens.ScreenManager;

public class OverworldEngine {
    ScreenManager screenManager;
    Player p1;
    public OverworldEngine(ScreenManager myScreenManager){

        screenManager = myScreenManager;
    }
    public void update() {
        p1 = GameEngine.players.get(0);
        p1.setX((int) (p1.getX() + p1.getVelocity().x));
        p1.setY((int) (p1.getY() + p1.getVelocity().y));
    }
}
