package model.gameobjects;

import java.awt.*;

public class Armor extends Item {

    protected int defense;

    public Armor(){
        super();
    }

    @Override
    public void drawMe(Graphics2D graphics, int x, int y, boolean desc)
    {
        super.drawMe(graphics,x,y,desc);
        graphics.setColor(Color.WHITE);
        Font font = new Font("NoScary", Font.BOLD, 36);
        graphics.setFont(font);
        graphics.drawString("Defense: " + defense, x, y + ((desc) ? 6*35 : 4*35));
    }

    @Override
    public void update() { }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }
}
