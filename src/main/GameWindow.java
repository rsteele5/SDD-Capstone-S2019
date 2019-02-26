package main;

import gameengine.GameEngine;
import gameengine.physics.PhysicsVector;
import gameobjects.Player;
import gamescreens.GameScreen;
import main.utilities.Debug;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameWindow extends JFrame implements KeyListener {
    public GameWindow() {
        addKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(GameEngine.players.get(0).playerState == Player.State.awake) {
            switch (e.getKeyCode()) {
                case 32 /*space*/:
                    Debug.log(true, "Player Jumped");
                    GameEngine.players.get(0).setAcceleration(new PhysicsVector(0,-5));
                    break;
                case 68 /*d*/:
                    Debug.log(true, "Player Moved Right");
                    //GameEngine.players.get(0).setVelocity(new PhysicsVector(1,0));
                    break;
                case 65 /*a*/:
                    Debug.log(true, "Player Moved Left");
                    //GameEngine.players.get(0).setVelocity(new PhysicsVector(-1,0));
                    break;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
