package view.renderengine;

//TODO: Add javadoc comments

//Java Imports
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Color;
import java.awt.Transparency;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

//Project Imports
import control.ScreenManager;
import utilities.Log;


public class RenderEngine extends JPanel {

    private ScreenManager screenManager;

    // size of the canvas - determined at runtime once rendered
    public static int width;
    public static int height;

    // off screen rendering
    private Graphics2D graphics;
    private BufferedImage dbImage = null;
    GraphicsConfiguration graphicsConfig;


    public RenderEngine() {
        screenManager = new ScreenManager();
        graphicsConfig = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
    }

    public void draw() {

        //TODO: Remove this after testing, update should be called from the physics engine
        for(GameScreen gameScreen: screenManager.getScreens()) {
            gameScreen.update();
        }

        width = getSize().width;
        height = getSize().height;
        if(width > 0 && height > 0) {
            if (dbImage == null) {
                //Creates an off-screen drawable image to be used for double buffering
                dbImage = graphicsConfig.createCompatibleImage(width, height, Transparency.OPAQUE);
                if (dbImage == null) {
                    Log.logError("Critical Error: dbImage is null");
                    System.exit(1);
                } else {
                    graphics = (Graphics2D) dbImage.getGraphics();
                }
                graphics.clearRect(0, 0, width, height);
                graphics.setBackground(Color.BLACK);
            }

            createRenderBuffer(graphics);
            renderBufferToScreen();
            graphics.clearRect(0, 0, width, height);
        }
    }

    public void createRenderBuffer(Graphics2D graphics) {
        for (GameScreen screen : screenManager.getScreens()) {
            if (!screen.isLoading()) {
                screen.draw(graphics);
            }
        }
    }

    public void renderBufferToScreen() {
        Graphics g;
        try {
            g = this.getGraphics();
            if ((g != null) && (dbImage != null)) {
                g.drawImage(dbImage, 0, 0, null);
            }
            Toolkit.getDefaultToolkit().sync();  // sync the display on some systems
            if (g != null) {
                g.dispose();
            }
        } catch (Exception e) {
            System.out.println("Graphics error: " + e);
        }

    }

    public ScreenManager getScreenManager() {
        return screenManager;
    }
}
