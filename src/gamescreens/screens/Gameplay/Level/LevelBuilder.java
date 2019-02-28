package gamescreens.screens.Gameplay.Level;

import gameobjects.renderables.ImageContainer;

public interface LevelBuilder {
    ImageContainer buildBackground();
    ImageContainer buildTerrain();
    void buildPlayer();
    void buildEnemies();
}
