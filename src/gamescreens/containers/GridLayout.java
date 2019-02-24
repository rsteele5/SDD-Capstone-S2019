package gamescreens.containers;

import gameobjects.renderables.RenderableObject;
import gamescreens.GameScreen;
import gamescreens.ScreenManager;
import main.Game;

import java.util.ArrayList;

public class GridLayout<T> extends GameScreen {

    protected int rows;
    protected int cols;
    protected GameScreen parentScreen;

    public GridLayout(ScreenManager screenManager, GameScreen parentScreen, int rows, int cols) {
        super(screenManager, "GridLayout", false, 0, 0);
        this.rows = rows;
        this.cols = cols;
        this.parentScreen = parentScreen;
    }

    public GridLayout(ScreenManager screenManager, GameScreen parentScreen, int rows, int cols, int xPos, int yPos) {
        this(screenManager,parentScreen, rows, cols);
        this.x = xPos;
        this.y = yPos;
    }

    /**
     * Initializes all of the stuff you want on your screen
     */
    @Override
    protected void initializeScreen() {

    }

    @Override
    protected void activeUpdate() {
        
    }

    public void add(RenderableObject rederable){
        addObject(rederable);
        //TODO: format
    }
    public void addAt(RenderableObject rederable, int row, int col){
        addObject(rederable);
        //TODO: format to the row and col
    }




}
