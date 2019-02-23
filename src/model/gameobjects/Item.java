package model.gameobjects;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.lang.Math;

import static java.lang.Math.round;

import model.gameobjects.buttons.Button;

public abstract class Item extends RenderableObject {
    protected String name;
    protected String type;
    protected int value;
    protected String descPart1;
    protected String descPart2;

    /*public void setButton(Button button) {
        this.button = button;
    }

    public Button getButton() {
        return button;
    }

    protected Button button;*/

    public Item(){
        super();
        name = "";
        type = "";
        value = 0;
        // No text wrap yet. Max of 28 characters per line
        descPart1 = "";
        descPart2 = "";
    }

    //desc is yes no on whether to draw the description
    public void drawMe(Graphics2D graphics, int x, int y, boolean desc)
    {
        graphics.setColor(Color.WHITE);
        Font font = new Font("NoScary", Font.BOLD, 36);
        graphics.setFont(font);
        graphics.drawString(name, x, y+ 35);
        graphics.drawString("Type:     " + type , x, y + 2*35);
        graphics.drawString("Value:     $" + value, x, y + 3*35);
        if(desc){
            graphics.drawString(descPart1, x, y + 4*35);
            graphics.drawString(descPart2, x, y + 5*35);
        }
    }

    /* Getters and setters */

    public String getName() {
        return name;
    }

    protected void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    protected void setType(String type) {
        this.type = type;
    }

    public int getValue() {
        return value;
    }

    protected void setValue(int value) {
        this.value = value;
    }

    public String getDescPart1() {
        return descPart1;
    }

    protected void setDescPart1(String descPart1) {
        this.descPart1 = descPart1;
    }

    public String getDescPart2() {
        return descPart2;
    }

    protected void setDescPart2(String descPart2) {
        this.descPart2 = descPart2;
    }

    @Override
    public void update() {

    }
}
