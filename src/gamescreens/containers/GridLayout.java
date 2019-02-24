package gamescreens.containers;

import gamescreens.GameScreen;
import gamescreens.ScreenManager;

import java.util.ArrayList;

public class GridLayout<T> extends GameScreen {

    protected int rows;
    protected int cols;

    public GridLayout(ScreenManager screenManager, int rows, int cols) {
        super(screenManager, "GridLayout", false, 0, 0);
        this.rows = rows;
        this.cols = cols;
    }

    public GridLayout(ScreenManager screenManager, int rows, int cols, int xPos, int yPos) {
        this(screenManager, rows, cols);
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


}
