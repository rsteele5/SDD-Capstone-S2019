package gameobjects.renderables.house;

import gameobjects.renderables.ImageContainer;
import gamescreens.DrawLayer;

public class FloorTile extends ImageContainer {
    public static final int SIZE = 250;

    public FloorTile(int x, int y, String path) {
        super(x, y, path, DrawLayer.Scenery);
        width = SIZE;
        height = SIZE;
    }

    @Override
    public void update() {

    }
}
