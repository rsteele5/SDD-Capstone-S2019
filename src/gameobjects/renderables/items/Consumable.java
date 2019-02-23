package gameobjects.renderables.items;

import javax.swing.*;
import java.awt.*;
import java.util.Random;


// Want to think more about this one... Different types of potions - healing, increase effectiveness of armor
// or weapon, or damaging (used as a weapon)
public class Consumable extends Item {

    int protection;
    int health;
    int damage;
    int critchance;

    public Consumable(){
        super();
        protection = 0;
        health = 0;
        damage = 0;
        critchance = 0;
    }

    public Consumable(String imgPath, String name, String type, String description){
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
        health = rand.nextInt(maxAttributeValue) + increaseAttributeValue;
        damage = rand.nextInt(maxAttributeValue) + increaseAttributeValue;
        critchance = rand.nextInt(maxAttributeValue) + increaseAttributeValue;
    }

    @Override
    public void drawMe(Graphics2D graphics, int x, int y, boolean desc, int fontsize)
    {
        super.drawMe(graphics,x,y,desc,fontsize);
        graphics.setColor(Color.WHITE);
        Font font = new Font("NoScary", Font.BOLD, (fontsize-1));
        graphics.setFont(font);
        graphics.drawString("Health: " + health,  x, y + (desc ? 6*(fontsize-1) : 4*(fontsize-1)));
        graphics.drawString("Protection: " + protection+ "%", x, y + (desc ? 7*(fontsize-1) : 5*(fontsize-1)));
        graphics.drawString("CritChance: " + critchance+ "%", x, y + (desc ? 8*(fontsize-1) : 6*(fontsize-1)));
    }

    @Override
    public void update() {

    }
}
