package gameobjects.renderables.LevelTiles;

import gameobjects.renderables.ImageContainer;
import gamescreens.DrawLayer;

public class FloorTile extends ImageContainer {
    public static final int SIZE = 150;

    public FloorTile(int x, int y, String path) {
        super(x, y, path, DrawLayer.Scenery);
        width = SIZE;
        height = SIZE;
    }

    @Override
    public void update() {

    }
}