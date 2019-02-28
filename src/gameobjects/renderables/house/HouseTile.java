package gameobjects.renderables.house;

import gameobjects.renderables.ImageContainer;
import gamescreens.DrawLayer;


public class HouseTile extends ImageContainer {

    public static final int SIZE = 500;

    public HouseTile(int x, int y, String path) {
        super(x, y, path, DrawLayer.Scenery);
        width = SIZE;
        height = SIZE;
    }

    @Override
    public void update() {

    }
}
