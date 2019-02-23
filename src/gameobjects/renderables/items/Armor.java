package gameobjects.renderables.items;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Armor extends Item {

    int protection;

    public Armor(){
        super();
        protection = 0;
    }

    public Armor(String imgPath, String name, String type, String description){
        super();
        imagePath = imgPath;
        itemName = name;
        itemType = type;
        itemDescription = description;
        setAttributes();
    }

    public void setAttributes() {
        Random rand = new Random();
        protection = rand.nextInt(maxAttributeValue) + increaseAttributeValue;
    }

    @Override
    public void drawMe(Graphics2D graphics, int x, int y, boolean desc, int fontsize)
    {
        super.drawMe(graphics,x,y,desc,fontsize);
        graphics.setColor(Color.BLACK);
        Font font = new Font("NoScary", Font.BOLD, (fontsize-1));
        graphics.setFont(font);
        graphics.drawString("Protection: " + protection+ "%", x, y + (desc ? 6*(fontsize-1) : 4*(fontsize-1)));
    }

    @Override
    public void update() {

    }
}
