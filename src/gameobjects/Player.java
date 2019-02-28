package gameobjects;

import gameengine.physics.Kinematic;
import gameengine.physics.PhysicsVector;
import gameobjects.renderables.RenderableObject;
import gamescreens.DrawLayer;
import gamescreens.GameScreen;
import main.utilities.Debug;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Player extends RenderableObject implements Kinematic {
    private PhysicsVector accel = new PhysicsVector(1,1);
    PhysicsVector movement = new PhysicsVector(0,0);
    int mov = 0;
    public boolean grounded;
    public enum PlayerState {
        sideScroll,
        asleep,
        overWorld
    }
    public PlayerState playerState;
    @Override
    public void update() { }
    public void movement(KeyEvent e){
       // Debug.log(true,e.toString());
       // Debug.log(true, String.valueOf(e.getKeyCode()));
    }
    String name = "teddy";
    public Player(int x, int y, String path, DrawLayer drawLayer){
        super(x,y,path,drawLayer);
        playerState = PlayerState.asleep;
    }
    @Override
    public PhysicsVector getVelocity() {
        //Debug.log(true,"\nVelocity:\nX: " + movement.add(new PhysicsVector(0,1)).mult(accel).x  + "\ny: " +  movement.add(new PhysicsVector(0,1)).mult(accel).y);
        PhysicsVector pV = movement.add(new PhysicsVector(0,1)).mult(accel);
        double y = pV.y;
        y = y < 1 && y > .5 ? 1 : y;
        y = y < -.5 && y > -1 ? -1 : y;

        return new PhysicsVector(pV.x,y);
        //return movement.add(new PhysicsVector(0,1)).mult(accel);
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
        playerState = PlayerState.sideScroll;
        if(isActive) screen.kinematics.add(this);
    }

    public void reset(){
        x = 50;
        y = 50;
        accel=new PhysicsVector(1,1);
    }

    public int mov() {
        return mov;
    }

    public void setMov(int flags){
        mov = flags;
    }

    public PlayerState getState(){ return playerState;}

    public boolean setState(PlayerState ps){
        //TODO: Implement error checking
       switch (ps){
           case overWorld: return true;
           case asleep: return true;
           case sideScroll: return true;
       }
       return false;
    }
}
