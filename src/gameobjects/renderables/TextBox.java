package gameobjects.renderables;

import gamescreens.DrawLayer;
import main.utilities.AssetLoader;

import java.awt.*;


public class TextBox extends RenderableObject {

    protected String text;
    protected String displayText;
    protected Font font;
    protected Color color;

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
        if(displayText.equals("")) parseString(graphics);
        graphics.setFont(font);
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

    private void parseString(Graphics2D graphics) {//Debug.drawRect(true, graphics, new Rectangle2D.Double(x,y,(double)width, (double) height));
        graphics.setFont(font);
        graphics.setColor(color);
        String newLine = "";
        displayText = "";
        if(!text.equals("")) {
            for (String line : text.split("\n")) {
                for (String word : line.split(" ")) {
                    if (graphics.getFontMetrics().stringWidth(newLine + word) < width) {
                        newLine = newLine.concat(word + " ");
                    } else {
                        displayText = displayText.concat(newLine + "\n");
                        newLine = word + " ";
                    }
                }
                displayText = displayText.concat(newLine + "\n");
                newLine = "";
            }
        }
    }

    public void setText(String text) {
        this.text = text;
        displayText = "";
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
