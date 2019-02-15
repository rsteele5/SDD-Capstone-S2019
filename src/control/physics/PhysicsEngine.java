package control.physics;

import control.CollisionManager;
import control.ScreenManager;
import model.levels.LevelData;
import utilities.Debug;
import utilities.DebugEnabler;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

public class PhysicsEngine {
    CollisionManager collisionManager;
    ScreenManager screenManager;
    LevelData levelData = null;

    private PhysicsVector position = new PhysicsVector(0,400);        //Position of object
    private PhysicsVector velocity = new PhysicsVector(0, 0);         //Velocity of object
    private PhysicsVector acceleration = new PhysicsVector(0,0);      //Acceleration of object
    private final PhysicsVector GRAVITY = new PhysicsVector(0, 0.1);  //Represents gravity acting on an object


    public PhysicsEngine(ScreenManager myScreenManager){
        screenManager = myScreenManager;
    }

    public void physicsUpdate() {
        //Get all objects to preform physics update on
        //GameScreen will add its level data when it gets there
        levelData = screenManager.getLevelData();
        if(levelData != null){
            for(Kinematic kinematic: levelData.getKinematicObjects()){
                Debug.success(DebugEnabler.PHYSICS_LOG, "FloatingEye -> My physics are being updated");
            }
        }
        //Do physics calculations and get the global motion vector
        //globalMotionVector = calculatePhysics(levelData);

        //clear our level data
        //levelData.clear();
    }

    public void update(){
        //TODO: Calculate physics with specified data from screenManager
        calculateMove();
        //Update
        screenManager.update();
        //TODO: Process collisions and adjust objects
    }

    //TODO: getRid from MyCanvas
    private void calculateMove(){
        //This is where the movement is being calculated
        acceleration.applyForce(GRAVITY);
        acceleration.add(acceleration.calculateFriction(velocity));
        velocity.add(acceleration);
        position.add(velocity);

        //Handles screen collision
        if (position.x > 400){
            position.x = 400;
        } else if (position.x < 0) {
            position.x = 0;
        }
        if (position.y > 400){
            position.y = 400;
        }else if (position.y < 0) {
            position.y = 0;
        }

        //Reset acceleration after at the end of each update
        acceleration.mult(0);

    }

    public void movement(Set<Character> pressed) {
        for(char key: pressed) {
            if (key == 'w') {
                acceleration.applyForce(new PhysicsVector(0,-5));
            }
            if (key == 'a') {
                velocity.applyForce(new PhysicsVector(-2,0));
            }
            if (key == 's') {
                velocity.applyForce(new PhysicsVector(0,2));
            }
            if (key == 'd') {
                velocity.applyForce(new PhysicsVector(2,0));
            }
            velocity.normalize();
        }
    }

}
