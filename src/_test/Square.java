package _test;

import gameengine.physics.Kinematic;
import gameengine.physics.PhysicsVector;
import gameobjects.renderables.RenderableObject;
import gamescreens.DrawLayer;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Square extends RenderableObject implements Kinematic {
    private double accel = 1;
    public Square(int x, int y, BufferedImage img, DrawLayer drawLayer){
        super(x,y,img,drawLayer);
    }
    @Override
    public void update() {

    }

    @Override
    public PhysicsVector getVelocity() {
        return new PhysicsVector(0,1).mult(accel);
    }

    @Override
    public double getAcceleration() {
        return accel;
    }

    @Override
    public void setAcceleration(double d) {
        accel = d;
    }

    @Override
    public Rectangle getHitbox() {
        return new Rectangle(x, y, image.getWidth(), image.getHeight());
    }

    public String description(){
        return "\nX:\t\t" + x + "\nY:\t\t" + y + "\nWidth:\t" + width + "\nHeight:\t" + height;
    }
}
