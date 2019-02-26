package gameobjects.renderables;

import main.utilities.Debug;

import java.awt.*;

public class DialogBox extends TextBox{

    private int wordCount;
    private String textToDisplay[];

    public DialogBox(int x, int y, int width, int height, String text) {
        super(x, y, width, height, text);
    }

    public DialogBox(int x, int y, int width, int height, String text, Font font, Color color) {
        super(x, y, width, height, text, font, color);
    }

    @Override
    public void draw(Graphics2D graphics) {
        if(displayText == ""){
            //Debug.drawRect(true, graphics, new Rectangle2D.Double(x,y,(double)width, (double) height));
            graphics.setFont(font);
            graphics.setColor(color);
            String newLine = "";
            displayText = "";
            if(text != "") {
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
            textToDisplay = displayText.split(" ");
        }
        for(String string: textToDisplay){
            Debug.log(true, string);
        }

        int fontHeight = graphics.getFontMetrics().getHeight();
        int fontAscent = graphics.getFontMetrics().getAscent();
        int row = 0;
        for(int i = 0; i < wordCount; i++){
            if(row < height){
                graphics.drawString(textToDisplay[i] + " ", x, y + row + fontAscent);
                row += fontHeight;
        }

        }
        if(wordCount < textToDisplay.length) {
            wordCount++;
        }
    }
}
