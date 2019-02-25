package gamescreens.containers;

import gameobjects.renderables.RenderableObject;
import gamescreens.GameScreen;
import gamescreens.ScreenManager;
import main.utilities.Debug;
import main.utilities.DebugEnabler;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class GridLayoutContainer extends GameScreen {

    protected GameScreen parentScreen;
    protected int rows;
    protected int cols;
    protected int padding;
    protected int width;    //(size of item + padding*2) * rows = height
    protected int height;   //(size of item + padding*2) * cols = width
    protected static boolean postLoadCheck = true;

    protected ArrayList<RenderableObject[]> renderableGrid;



    public GridLayoutContainer(ScreenManager screenManager, GameScreen parentScreen, int rows, int cols) {
        super(screenManager, "GridLayoutContainer", false, parentScreen.getX(), parentScreen.getY());
        this.rows = rows;
        this.cols = cols;
        this.parentScreen = parentScreen;
        parentScreen.coverWith(this);
        // Setup grid
        padding = 5;
        width = 0;
        height = 0;
        renderableGrid = new ArrayList<>();
        for(int row = 0; row < rows; row++)
            renderableGrid.add(new RenderableObject[cols]);
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
        if(!parentScreen.isLoading()){
            if(postLoadCheck) {
                postLoadSetup();
                postLoadCheck = false;
                //TODO: Find a home for this but do it here for now
                organizeStandardGrid();
            }
            else super.activeUpdate();
        }
    }

    //TODO: Testing size function
    @Override
    protected void drawLayers(Graphics2D graphics) {
        super.drawLayers(graphics);
        Debug.drawRect(DebugEnabler.GAME_SCREEN_LOG, graphics, getBoundingBox());
    }

    //TODO: figure out where this shit goes ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    //SetsUp variables post Load
    public void postLoadSetup(){

        /* everything should be loaded. If there is anything
        in the loadables array, clear the array*/
        loadables.clear();
        if(!renderables.isEmpty()) {
            // Set Width and Height base on renderables bounding box
            width = (renderables.get(0).getWidth() + padding) * cols + padding;
            height = (renderables.get(0).getHeight() + padding) * rows + padding;
        }
    }

    //populates renderableGrid into standard grid
    public void organizeStandardGrid(){

        if(!renderables.isEmpty()) {
            for(int row = 0; row < rows; row++){
                for(int col = 0; col < cols; col++){
                    if(row*rows + col < renderables.size())
                        addAt(renderables.get(row*rows + col), row, col);
                    else return;
                }
            }
        } else {
            Debug.warning(DebugEnabler.GAME_SCREEN_LOG,
                    name + "- trying to organize into standard grid without renderables");
        }
    }

    public void addAt(RenderableObject renderable, int row, int col){
        if((row >= 0 && col >= 0) && (row < rows && col < cols)) {
            if(renderable != null) {
                //Set the position of the renderable
                renderable.setPosition(x + padding + (renderable.getWidth() + padding)* col
                                      ,y + padding + (renderable.getHeight() + padding)* row);
                renderableGrid.get(row)[col] = renderable;
            }else {
                Debug.error(DebugEnabler.GAME_SCREEN_LOG, name + "- addAt() was passed null");
            }
        } else{
            Debug.warning(DebugEnabler.GAME_SCREEN_LOG,
                    name + "- Current Range: row: 0-" + (rows-1) + ", col: 0-" + (cols-1));
            Debug.error(DebugEnabler.GAME_SCREEN_LOG,
                    name + "- addAt( row: " + row + ", col: " + col + ") is out of bounds");
        }
    }


    public Rectangle2D getBoundingBox() {
        return new Rectangle2D.Double(x, y, width, height);
    }
    

    //TODO: figure out where this shit goes ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

}
