package gameobjects;

import gameengine.GameEngine;
import gameengine.physics.Kinematic;
import gameengine.physics.PhysicsVector;
import gameobjects.renderables.RenderableObject;
import gamescreens.DrawLayer;
import gamescreens.GameScreen;
import main.utilities.Debug;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.Console;

public class Player extends RenderableObject implements Kinematic {
    private PhysicsVector accel = new PhysicsVector(0,1);
    PhysicsVector movement = new PhysicsVector(0,0);
    public enum State{
        awake,
        asleep
    }
    public State playerState;
    @Override
    public void update() { }
    public void movement(KeyEvent e){
       // Debug.log(true,e.toString());
       // Debug.log(true, String.valueOf(e.getKeyCode()));
    }
    String name = "teddy";
    public Player(int x, int y, String path, DrawLayer drawLayer){
        super(x,y,path,drawLayer);
        playerState = State.asleep;
    }
    @Override
    public PhysicsVector getVelocity() {
        return movement.add(new PhysicsVector(0,1).mult(accel));
    }

    @Override
    public void setVelocity(PhysicsVector pv) {
        movement=pv;
    }

    @Override
    public PhysicsVector getAcceleration() {
      //  Debug.log(true, String.valueOf(accel.y));
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

    @Override
    public void addToScreen(GameScreen screen, boolean isActive){
        super.addToScreen(screen, isActive);
        playerState = State.awake;
        if(isActive) {
            screen.kinematics.add(this);
        }
    }

    public void reset(){
        x = 500;
        y = 50;
        accel=new PhysicsVector(0,1);
    }
}
