package gameobjects.renderables.items;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Weapon extends Item {

    int mindamage;
    int maxdamage;
    int critchance;

    public Weapon(){
        super();
        mindamage = 0;
        maxdamage = 0;
        critchance = 0;
    }

    public Weapon(String imgPath, String name, String type, String description){
        imagePath = imgPath;
        itemName = name;
        itemType = type;
        itemDescription = description;
        setAttributes();
    }

    public void setAttributes() {
        Random rand = new Random();
        mindamage = increaseAttributeValue;
        maxdamage = rand.nextInt(maxAttributeValue) + increaseAttributeValue;
        critchance = rand.nextInt(maxAttributeValue) + increaseAttributeValue;
    }

    @Override
    public void drawMe(Graphics2D graphics, int x, int y, boolean desc, int fontsize)
    {
        super.drawMe(graphics,x,y,desc,fontsize);
        graphics.setColor(Color.WHITE);
        Font font = new Font("NoScary", Font.BOLD, (fontsize-1));
        graphics.setFont(font);
        graphics.drawString("Damage: " + mindamage + "-" + maxdamage, x, y + (desc ? 6*(fontsize-1) : 4*(fontsize-1)));
        graphics.drawString("CritChance: " + critchance+ "%", x, y + (desc ? 7*(fontsize-1) : 5*(fontsize-1)));
    }

    @Override
    public void update() {

    }
}
