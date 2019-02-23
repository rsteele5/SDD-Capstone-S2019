package model.gameobjects;

import java.awt.*;

public class Consumable extends Item {

    protected int effectAmount;

    public Consumable(){
        super();
    }

    @Override
    public void drawMe(Graphics2D graphics, int x, int y, boolean desc)
    {
        super.drawMe(graphics,x,y,desc);
        graphics.setColor(Color.WHITE);
        Font font = new Font("NoScary", Font.BOLD, 36);
        graphics.setFont(font);
        graphics.drawString(effectAmount + " " + type, x, y + ((desc) ? 6*35 : 4*35));
    }

    @Override
    public void update() {

    }

    public int getEffectAmount() {
        return effectAmount;
    }

    public void setEffectAmount(int effectAmount) {
        this.effectAmount = effectAmount;
    }
}
