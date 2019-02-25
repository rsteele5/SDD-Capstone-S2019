package gamescreens.containers;

import gameobjects.renderables.RenderableObject;
import gamescreens.GameScreen;
import gamescreens.ScreenManager;
import main.utilities.Debug;
import main.utilities.DebugEnabler;

import java.awt.geom.Rectangle2D;

public class GridLayoutContainer extends GameScreen {

    protected GameScreen parentScreen;
    protected int rows;
    protected int cols;
    protected int padding = 5;
    protected int width;    //(size of item + padding) * rows = height
    protected int height;   //(size of item + padding) * cols = width





    public GridLayoutContainer(ScreenManager screenManager, GameScreen parentScreen, int rows, int cols) {
        super(screenManager, "GridLayoutContainer", false, parentScreen.getX(), parentScreen.getY());
        this.rows = rows;
        this.cols = cols;
        this.parentScreen = parentScreen;
    }

    public GridLayoutContainer(ScreenManager screenManager, GameScreen parentScreen, int rows, int cols, int xPos, int yPos) {
        this(screenManager,parentScreen, rows, cols);
        this.x = parentScreen.getX() + xPos;
        this.y = parentScreen.getY() + yPos;
    }


    /**
     * Initializes all of the stuff you want on your screen
     */
    @Override
    protected void initializeScreen() { }

    public void setPosition(int xPos, int yPos) {
        x = parentScreen.getX() + xPos;
        y = parentScreen.getY() + yPos;
    }

    @Override
    protected void activeUpdate() {
        if(!parentScreen.isLoading())
            super.activeUpdate();
    }

    public void organize(){
        //renderables
        if(!renderables.isEmpty()) {

            // Set Width and Height base on renderables bounding box
            Rectangle2D rect = renderables.get(0).getBoundingBox();
            width = (((int)rect.getWidth()) + (padding*2)) * cols;
            height = (((int)rect.getHeight()) + (padding*2)) * rows;

            for(RenderableObject renderable : renderables){
                //renderable.setX
            }
        } else {
            Debug.warning(DebugEnabler.GAME_SCREEN_LOG, name + "- trying to organize nothing");
        }
    }
}
