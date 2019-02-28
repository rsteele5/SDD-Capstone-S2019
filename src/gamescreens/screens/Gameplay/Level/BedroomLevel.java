package gamescreens.screens.Gameplay.Level;

import gameengine.GameEngine;
import gameobjects.renderables.ImageContainer;
import gameobjects.renderables.LevelTiles.FloorTile;
import gamescreens.DrawLayer;
import gamescreens.GameScreen;
import gamescreens.ScreenManager;
import gamescreens.containers.GridContainer;

public class BedroomLevel implements LevelBuilder {
    private Level level;

    public BedroomLevel() {
        this.level = new Level();
    }


    @Override
    public ImageContainer buildBackground() {
        return new ImageContainer(0, 0, "/assets/backgrounds/BG-Level.png", DrawLayer.Background);
    }

    public void buildTerrain(GameScreen gameScreen) {
        //This is where the instruction for how to procedurally generate a level would go
        GridContainer floorTileContainer = new GridContainer(gameScreen, 1, 8, FloorTile.SIZE, FloorTile.SIZE,40, Level.Y_INIT_BUTTON,0);
        FloorTile floorTile;
        for (int i = 0; i < 8; i++) {
            floorTile = new FloorTile(0,0,"/assets/levelObjects/WoodTile1.png");
            gameScreen.kinematics.add(floorTile);
            floorTileContainer.addAt(floorTile, 0, i);
        }
    }

    @Override
    public void buildPlayer(GameScreen gameScreen) {
        GameEngine.players.get(0).addToScreen(gameScreen, true);
    }

    @Override
    public void buildEnemies() {

    }

}
