package gameobjects;

import gameengine.GameEngine;
import gameengine.physics.Kinematic;
import gameengine.physics.PhysicsMeta;
import gameengine.physics.PhysicsVector;
import gameobjects.renderables.RenderableObject;
import gameobjects.renderables.SortByType;
import gameobjects.renderables.items.*;
import gamescreens.DrawLayer;
import gamescreens.GameScreen;
import main.utilities.Debug;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.concurrent.CopyOnWriteArrayList;

public class Player extends RenderableObject implements Kinematic {
    private CopyOnWriteArrayList<Item> items = new CopyOnWriteArrayList<>();
    private CopyOnWriteArrayList<RenderableObject> rItems = new CopyOnWriteArrayList<>();
    private PhysicsVector accel = new PhysicsVector(1,1);
    private PhysicsVector movement = new PhysicsVector(0,0);
    private final int[] ssKeys= new int[] {68,65};
    private final int[] owKeys= new int[] {68,65,83,87};
    private int movFlag = 0;
    private int gold;

    /*
    0b1     =   right
    0b10    =   left
    0b100   =   down
    0b1000  =   up
     */
    public boolean grounded;
    private PlayerState playerState;

    public enum PlayerState {
        sideScroll,
        asleep,
        overWorld
    }

    public Player(int x, int y, String path, DrawLayer drawLayer){
        super(x,y,path,drawLayer);
        playerState = PlayerState.asleep;
        initializeItems();
        this.gold = 10;
    }

    private void initializeItems() {
        items.add(new WeaponBuilder()
                .imagePath("/assets/Items/sword1.png")
                .name("My Fwirst Sword")
                .type(WeaponType.Sword)
                .value(11)
                .minDamage(4)
                .maxDamage(12)
                .critChance(3)
                .buildWeapon());

        items.add(new ArmorBuilder()
                .imagePath("/assets/Items/helmet1.png")
                .name("My Fwirst Helmet")
                .type(ArmorType.Head)
                .value(11)
                .armorPoints(12)
                .buildArmor());

        if (items.size() > 0) {
            items.sort(new SortByType());
        }

        for (Item item : items){
            rItems.add((RenderableObject) item);
        }
    }
    @Override
    public void update() { }

    private void setVelocity(int flags) {
        int x1 = 0b1 & flags;
        x1 +=  (((0b10 & flags) / 0b10) * -1);
        int y1 = ((0b100 & flags) / 0b100);
        y1 += (((0b1000 & flags) / 0b1000) * -1);
        setVelocity(new PhysicsVector(x1,y1));
    }

    private void calculateMove(KeyEvent e,int[] keys){
        for(int i = 0; i < keys.length; i++) movFlag += e.getKeyCode() == keys[i] && ((movFlag & (int) Math.pow(2,i)) == 0) ? (int) Math.pow(2,i) : 0;
        setVelocity(movFlag);
    }

    private void calculateRelease(KeyEvent e,int[] keys){
        for(int i = 0; i < keys.length; i++) movFlag -= e.getKeyCode() == keys[i] && ((movFlag & (int) Math.pow(2,i)) == Math.pow(2,i)) ? (int) Math.pow(2,i) : 0;
        setVelocity(movFlag);
    }

    public void move(KeyEvent e){
        switch (getState()){
            case sideScroll:
                if(e.getKeyCode() == 32 && grounded ){
                    int sign = PhysicsMeta.AntiGravity ? -1 : 1;
                    setAcceleration(getAcceleration().add(new PhysicsVector(0,-7 * sign)));
                    grounded = false;
                }
                if(PhysicsMeta.Gravity == 0) calculateMove(e,owKeys);
                else calculateMove(e,ssKeys);
                break;

            case overWorld:
                calculateMove(e,owKeys);
                break;
        }
    }

    public void moveRelease(KeyEvent e){
        switch (getState()){
            case sideScroll:
                if(PhysicsMeta.Gravity == 0) calculateRelease(e,owKeys);
                else calculateRelease(e,ssKeys);
                break;

            case overWorld:
                calculateRelease(e,owKeys);
                break;
        }
    }

    @Override
    public PhysicsVector getVelocity() {
        int gravSign = PhysicsMeta.Gravity != 0 && playerState == PlayerState.sideScroll ? 1 : 0;
        PhysicsVector pV = movement.add(new PhysicsVector(0,gravSign)).mult(accel);
        double y = pV.y;
        y = y < 1 && y > .5 ? 1 : y;
        y = y < -.5 && y > -1 ? -1 : y;
        return new PhysicsVector(pV.x,y);
    }

    @Override
    public void setVelocity(PhysicsVector pv) { movement=pv; }

    @Override
    public PhysicsVector getAcceleration() {return accel;}

    @Override
    public void setAcceleration(PhysicsVector pv) {accel = pv;}

    @Override
    public Rectangle getHitbox() { return new Rectangle(x, y, image.getWidth(), image.getHeight()); }

    @Override
    public void addToScreen(GameScreen screen, boolean isActive){
        super.addToScreen(screen, isActive);
        if(isActive) screen.kinematics.add(this);
    }

    /**
     * Reset player coordinates and acceleration.
     */
    public void reset(){
        x = 50;
        y = 50;
        accel=new PhysicsVector(1,1);
    }

    public PlayerState getState(){ return playerState;}

    /**
     * Returns true or false depending on the acceptance of the state transition.
     */
    public boolean setState(PlayerState ps){
        //TODO: Implement error checking
       switch (ps){
           case overWorld:
               playerState = ps;
               return true;
           case asleep:
               playerState = ps;
               return true;
           case sideScroll:
               playerState = ps;
               return true;
       }
       return false;
    }

    public CopyOnWriteArrayList<Item> getItems() {
        return items;
    }

    public CopyOnWriteArrayList<RenderableObject> getRenderables() {
        return rItems;
    }

    public void addItem(Item item){
        items.add(item);
        rItems.add((RenderableObject) item);
    }

    public void removeItem(Item item){
        items.remove(item);
        rItems.remove(item);
    }

    // Needed for vendor screen
    public void replaceList(CopyOnWriteArrayList<Item> updatedItems){
        this.items = updatedItems;
        rItems.removeAll(rItems);
        for (Item item : items){
            rItems.add((RenderableObject) item);
        }
    }

    public int getGold(){ return gold;}

    public void changeGold(int amt) {
        gold += amt;
    }
}
