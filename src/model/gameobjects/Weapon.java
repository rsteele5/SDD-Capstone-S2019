package model.gameobjects;

import java.awt.*;

public class Weapon extends Item {

    protected int mindamage;
    protected int maxdamage;
    protected int critchance;

    public Weapon(){
        super();
    }

    @Override
    public void drawMe(Graphics2D graphics, int x, int y, boolean desc)
    {
        super.drawMe(graphics,x,y,desc);
        graphics.setColor(Color.WHITE);
        Font font = new Font("NoScary", Font.BOLD, 36);
        graphics.setFont(font);
        graphics.drawString("Damage: " + mindamage + "-" + maxdamage, x, y + ((desc) ? 6*35 : 4*35));
        graphics.drawString("CritChance: " + critchance+ "%", x, y + ((desc) ? 7*35 : 5*35));
    }

    @Override
    public void update() {

    }

    public int getMindamage() {
        return mindamage;
    }

    public void setMindamage(int mindamage) {
        this.mindamage = mindamage;
    }

    public int getMaxdamage() {
        return maxdamage;
    }

    public void setMaxdamage(int maxdamage) {
        this.maxdamage = maxdamage;
    }

    public int getCritchance() {
        return critchance;
    }

    public void setCritchance(int critchance) {
        this.critchance = critchance;
    }
}
