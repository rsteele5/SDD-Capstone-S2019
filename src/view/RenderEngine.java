package view;


import javax.swing.JPanel;
import java.awt.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class RenderEngine extends JPanel {

    // size of the canvas - determined at runtime once rendered
    public static int width;
    public static int height;

    // off screen rendering
    private Graphics2D g2;
    private Image dbImage = null;

    CopyOnWriteArrayList<GameScreen> gameScreens;

    public RenderEngine(CopyOnWriteArrayList<Integer> house){
        gameScreens = new CopyOnWriteArrayList<>();
        gameScreens.add(new TitleScreen());
        gameScreens.add(new OverworldScreen(house));
    }

    public void gameRender() {
        width = getSize().width;
        height = getSize().height;

        if (dbImage == null) {
            //Creates an off-screen drawable image to be used for double buffering
            dbImage = createImage(width, height);
            if (dbImage == null) {
                System.out.println("Critical Error: dbImage is null");
                System.exit(1);
            } else {
                g2 = (Graphics2D) dbImage.getGraphics();
            }
        }

        g2.clearRect(0, 0, width, height);
        g2.setBackground(Color.BLACK);
    }
}
