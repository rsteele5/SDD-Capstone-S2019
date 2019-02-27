package gameobjects.renderables.house;

import gameobjects.renderables.RenderableObject;
import gamescreens.DrawLayer;

public class HouseTile extends RenderableObject {

    private static String testPath = "/assets/testAssets/square.png";
    private static final int SIZE = 500;


    public HouseTile(int x, int y) {
        super(x, y, testPath, DrawLayer.Scenery);
        width = SIZE;
        height = SIZE;
    }

    @Override
    public void update() {

    }
}
