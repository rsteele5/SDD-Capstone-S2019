package gameobjects.renderables;

import gamescreens.DrawLayer;
import main.utilities.AssetLoader;
import main.utilities.Debug;

import java.awt.*;
import java.awt.geom.Rectangle2D;


public class TextBox extends RenderableObject {

    private String text;
    private String displayText;
    private Font font;
    private Color color;
    private Graphics graphics;

    public TextBox(int x, int y, int width, int height, String text) {
        super(x,y);
        this.width = width;
        this.height = height;
        this.text = text;
        this.drawLayer = DrawLayer.Effects;
        font = new Font("arial", Font.PLAIN, 12);
        color = Color.WHITE;
        displayText = "";
    }

    public TextBox(int x, int y, int width, int height, String text, Font font, Color color) {
        this(x,y,width,height, text);
        this.font = font;
        this.color = color;
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Graphics2D graphics) {
        Debug.drawRect(true, graphics, new Rectangle2D.Double(x,y,(double)width, (double) height));
        graphics.setFont(font);
        graphics.setColor(color);
        String newLine = "";
        String displayText = "";
        if(text != "") {
            for (String line : text.split("\n")) {
                Debug.error(true, "Line is -> " + line);
                for (String word : line.split(" ")) {
                    Debug.warning(true, "Word is -> " + word);
                    if (graphics.getFontMetrics().stringWidth(newLine + word) < width) {
                        newLine = newLine.concat(word + " ");
                    } else {
                        //newLine = newLine.concat("\n" + word);
                        displayText = displayText.concat(newLine + "\n" + word);
                        newLine = "";
                        //Debug.warning(true, "New line is -> " + line);
                    }
                }
                displayText = displayText.concat(newLine + "\n");
                newLine = "";
            }
        }

        Debug.success(true, "Text to display -> " + displayText);

        int fontHeight = graphics.getFontMetrics().getHeight();
        int fontAscent = graphics.getFontMetrics().getAscent();
        int row = 0;
        String text = displayText;
        for (String line: text.split("\n")) {
            if(row < height){
                graphics.drawString(line, x, y + row + fontAscent);
                row += fontHeight;
            }
        }
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public void load(){
        if(imagePath != ""){
            image = AssetLoader.load(imagePath);
            if(width != 0 && height != 0) {
                setSize(width, height);
            } else {
                setSize(image.getWidth(), image.getHeight());
            }
        }
    }
}
