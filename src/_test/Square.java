package _test;

import gameengine.physics.Kinematic;
import gameengine.physics.PhysicsVector;
import gameobjects.renderables.RenderableObject;
import gamescreens.DrawLayer;
import gamescreens.GameScreen;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Square extends RenderableObject implements Kinematic {
    private PhysicsVector accel = new PhysicsVector(0,1);
    public Square(int x, int y, String path, DrawLayer drawLayer){
        super(x,y,path,drawLayer);
    }
    PhysicsVector movement = new PhysicsVector(0,0);
    @Override
    public void update() {

    }

    @Override
    public PhysicsVector getVelocity() {
        return new PhysicsVector(0,1).mult(accel);
    }

    @Override
    public void setVelocity(PhysicsVector pv) {
        movement = pv;
    }

    @Override
    public PhysicsVector getAcceleration() {
        return accel;
    }

    @Override
    public void setAcceleration(PhysicsVector pv) {
        accel = pv;
    }

    @Override
    public Rectangle getHitbox() {
        return new Rectangle(x, y, image.getWidth(), image.getHeight());
    }

    public String description(){
        return "\nX:\t\t" + x + "\nY:\t\t" + y + "\nWidth:\t" + width + "\nHeight:\t" + height;
    }

    @Override
    public boolean setActive(GameScreen screen){
        if(super.setActive(screen)){
            screen.kinematics.add(this);
            return true;
        }
        return false;
    }

    @Override
    public boolean setInactive(GameScreen screen){
        if(super.setInactive(screen)){
            screen.kinematics.remove(this);
            return true;
        }
        return false;
    }

    @Override
    public void addToScreen(GameScreen screen, boolean isActive){
        super.addToScreen(screen, isActive);

        if(isActive) {
           screen.kinematics.add(this);
        }
    }
}
