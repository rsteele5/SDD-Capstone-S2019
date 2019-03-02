package gamescreens.screens.Gameplay.Level;

import gameobjects.renderables.ImageContainer;
import gamescreens.GameScreen;

public interface Level {
    void buildBackground(GameScreen gameScreen);
    void buildTerrain(GameScreen gameScreen);
    void buildPlayer(GameScreen gameScreen);
    void buildEnemies(GameScreen gameScreen);
}
