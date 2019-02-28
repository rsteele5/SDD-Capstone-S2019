package gameobjects.renderables.house;

import gameobjects.renderables.RenderableObject;
import gamescreens.DrawLayer;

import java.awt.*;
import java.util.ArrayList;

public class Room extends RenderableObject {

    ArrayList<ArrayList<HouseTile>> tiles;

    //private ArrayList<Boundary> boundaries;

    public Room(int x, int y) {
        super(x, y, "", DrawLayer.Scenery);
        tiles = new ArrayList<>();
    }

    public Room(int x, int y, int rows, int cols) {
        super(x, y, "", DrawLayer.Scenery);


    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics2D graphics) {
        for(ArrayList<HouseTile> row : tiles){
            for(HouseTile tile : row){
                tile.draw(graphics);
            }
        }
    }

    @Override
    public void load() {
        for(ArrayList<HouseTile> row : tiles){
            for(HouseTile tile : row){
                tile.load();
            }
        }
    }
}
