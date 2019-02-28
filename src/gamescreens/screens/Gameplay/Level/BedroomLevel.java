package gamescreens.screens.Gameplay.Level;

import gameobjects.renderables.ImageContainer;
import gameobjects.renderables.LevelTiles.FloorTile;
import gamescreens.DrawLayer;

public class BedroomLevel implements LevelBuilder {
    private Level level;

    public BedroomLevel() {
        this.level = new Level();
    }


    @Override
    public ImageContainer buildBackground() {
        return new ImageContainer(0, 0, "/assets/backgrounds/BG-Level.png", DrawLayer.Background);
    }

    @Override
    public ImageContainer buildTerrain() {
        //This is where the instruction for how to procedurally generate a level would go
        //GridContainer floorTiles = new GridContainer(this, 1, 5, FloorTile.SIZE, FloorTile.SIZE,0, Level.Y_INIT_BUTTON,0);
        return new ImageContainer(0,0,"",DrawLayer.Scenery);
    }

    @Override
    public void buildPlayer() {

    }

    @Override
    public void buildEnemies() {

    }

}
