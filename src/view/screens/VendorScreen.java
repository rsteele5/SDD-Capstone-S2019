package view.screens;

import control.ScreenManager;
import model.gameobjects.GameObject;
import model.gameobjects.ImageContainer;
import utilities.Debug;
import utilities.DebugEnabler;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

public class VendorScreen extends GameScreen {
    /** Remove after testing. Create arrays for bear's and vendor's items (identified by image name) **/
    public CopyOnWriteArrayList<String> berInventory;
    public CopyOnWriteArrayList<String> vendorInventory;

    /** Array of x values for bear item boxes **/
    private int [] xValBearItems = {191, 239, 287, 335};

    /** Array of x values for vendor item boxes **/
    private int [] xValVendorItems = {927, 975, 1023, 1071};

    /** Array of y values for bear AND vendor item boxes **/
    private int [] yValItems = {220, 266, 313, 360, 407, 455, 502, 549};

    public VendorScreen(ScreenManager screenManager) {
        super(screenManager);
        name = "VendorScreen";

        /** Remove after testing. Populates inventories with red potion **/

    }

    @Override
    protected void initializeLayers() {
        renderableLayers.add(new CopyOnWriteArrayList<>());
        renderableLayers.add(new CopyOnWriteArrayList<>());
    }

    @Override
    protected void loadContent() {
        try {
            Debug.success(DebugEnabler.GAME_SCREEN_LOG, name + "Loading content");

            BufferedImage background = ImageIO.read(getClass().getResource("/assets/VendorBackground.png"));
            renderableLayers.get(0).add(new ImageContainer(150, 75, background, 0));
            BufferedImage redPotion = ImageIO.read(getClass().getResource("/assets/Items/redPotionSmall.png"));
            renderableLayers.get(1).add(new ImageContainer(927, 549, redPotion, 0));

            Debug.success(DebugEnabler.GAME_SCREEN_LOG, name + "Loaded Success");
        }catch(IOException e) {
            Debug.error(DebugEnabler.GAME_SCREEN_LOG, "Error: " + e.getMessage());
        }
    }

    @Override
    protected void updateTransitionOn() {
        currentState = ScreenState.Active;
    }

    @Override
    protected void updateTransitionOff() {
        exiting = true;
    }

    @Override
    protected void hiddenUpdate() {

    }

    @Override
    protected void activeUpdate() {

    }

    @Override
    public void handleClickEvent(int x, int y) {

    }
}
