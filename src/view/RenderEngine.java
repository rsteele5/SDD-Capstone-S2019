package view;


import javax.swing.JPanel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.CopyOnWriteArrayList;

public class RenderEngine extends JPanel {

    // size of the canvas - determined at runtime once rendered
    public static int width;
    public static int height;

    // off screen rendering
    private Graphics2D g2;
    private BufferedImage dbImage = null;
    GraphicsConfiguration graphicsConfig;

    CopyOnWriteArrayList<GameScreen> gameScreens;

    public RenderEngine(CopyOnWriteArrayList<Integer> house){
        gameScreens = new CopyOnWriteArrayList<>();
        gameScreens.add(new TitleScreen());
        gameScreens.add(new OverworldScreen(house));
        graphicsConfig = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        this.setVisible(true);
    }

    public void render() {
        width = getSize().width;
        height = getSize().height;
        System.out.println(width + " " + height);
        if (dbImage == null) {
            if(width > 0 && height > 0){
                //Creates an off-screen drawable image to be used for double buffering
                dbImage = graphicsConfig.createCompatibleImage(width, height, Transparency.OPAQUE);
                if (dbImage == null) {
                    System.out.println("Critical Error: dbImage is null");
                    System.exit(1);
                } else {
                    g2 = (Graphics2D) dbImage.getGraphics();
                }
                g2.clearRect(0, 0, width, height);
                g2.setBackground(Color.BLACK);

                /*
                    Add game object rendering here
                 */

                printScreen();
            }
        }
    }

    public void printScreen() {
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
}
