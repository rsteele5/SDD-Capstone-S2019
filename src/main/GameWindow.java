package main;

import gameengine.GameEngine;
import gameengine.physics.PhysicsVector;
import gameobjects.Player;
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
        //TODO: Implement multiple players.
        GameEngine.players.get(0).move(e);
    }

    @Override
    public void keyReleased(KeyEvent e){
        //TODO: Implement multiple players.
        GameEngine.players.get(0).moveRelease(e);
    }
}


