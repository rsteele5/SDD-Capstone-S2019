package gameobjects.renderables;

import main.utilities.Debug;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;

public class DialogBox extends TextBox{

    private LinkedList<String> stringQueue;

    public DialogBox(int x, int y, int width, int height, String text) {
        super(x, y, width, height, text);
        stringQueue = new LinkedList<>();
    }

    public DialogBox(int x, int y, int width, int height, String text, Font font, Color color) {
        super(x, y, width, height, text, font, color);
        stringQueue = new LinkedList<>();
    }

    private void parseString(Graphics2D graphics) {
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

    private void setUpStringQueue(){
        //Create string queue to pop our split strings into display Text
        for(String word: displayText.split(" ")) {
            for(String letter: word.split("")){
                stringQueue.add(letter);
            }
            stringQueue.add(" ");
        }
        displayText = stringQueue.pop();
    }

    @Override
    public void draw(Graphics2D graphics) {
        //Debug.drawRect(true, graphics, new Rectangle2D.Double(x,y,(double)width, (double) height));
        if(displayText.equals("")){
            parseString(graphics);
            setUpStringQueue();
        }

        //NOTE: Cant mod on time since we dont have a consistent frame rate
        if(!stringQueue.isEmpty() && System.currentTimeMillis() % 3 == 0) displayText = displayText + stringQueue.pop();
        graphics.setFont(font);
        int fontHeight = graphics.getFontMetrics().getHeight();
        int fontAscent = graphics.getFontMetrics().getAscent();
        int row = 0;
        for (String line: displayText.split("\n")) {
            if(row < height){
                graphics.drawString(line, x, y + row + fontAscent);
                row += fontHeight;
            }
        }
    }

    public void finalize() {

    }
}
