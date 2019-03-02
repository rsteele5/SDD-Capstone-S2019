package gamescreens.screens.gameplay.level;

import gameobjects.renderables.ImageContainer;
import gamescreens.GameScreen;

public interface LevelBuilder {
    ImageContainer buildBackground();
    void buildTerrain(GameScreen gameScreen);
    void buildPlayer(GameScreen gameScreen);
    void buildEnemies();
}
