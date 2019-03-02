package gamescreens.screens.gameplay.level;

import _test.Square;
import gameengine.GameEngine;
import gameobjects.Player;
import gameobjects.renderables.ImageContainer;
import gameobjects.renderables.levelTiles.FloorTile;
import gameobjects.renderables.items.Weapon;
import gameobjects.renderables.items.WeaponBuilder;
import gameobjects.renderables.items.WeaponType;
import gamescreens.DrawLayer;
import gamescreens.GameScreen;

public class BedroomLevel implements Level {


    @Override
    public void buildBackground(GameScreen gameScreen) {
        ImageContainer background = new ImageContainer(0, 0, "/assets/backgrounds/BG-Level.png", DrawLayer.Background);
        background.addToScreen(gameScreen, true);
    }

    public void buildTerrain(GameScreen gameScreen) {
        //This is where the instruction for how to procedurally generate a level would go
        FloorTile floorTile = new FloorTile(10, 576, "/assets/levelObjects/WoodTile1.png");
        FloorTile floorTile2 = new FloorTile(10, 720, "/assets/levelObjects/WoodTile1.png");
        floorTile.setWidth(1260);
        floorTile.setHeight(50);
        floorTile2.setWidth(50);
        floorTile2.setHeight(96);
        gameScreen.kinematics.add(floorTile);
        gameScreen.kinematics.add(floorTile2);
        floorTile.addToScreen(gameScreen, true);

        Square square;
        for(int x1 = 0; x1 < 5; x1++){
            for(int y1 = 0; y1 < x1; y1++){
                square = new Square(x1 * 75 + 100,y1 * 75,"/assets/testAssets/square.png",DrawLayer.Entity);
                square.addToScreen(gameScreen, true);
            }
        }

        Weapon myWeap = new WeaponBuilder()
                .position(800, 476)
                .imagePath("/assets/Items/sword1.png")
                .name("My Fwirst Sword!")
                .type(WeaponType.Sword)
                .value(15)
                .buildWeapon();
        myWeap.setWidth(50);
        myWeap.setHeight(50);
        gameScreen.kinematics.add(myWeap);
        myWeap.addToScreen(gameScreen, true);
    }

    @Override
    public void buildPlayer(GameScreen gameScreen) {
        Player player = GameEngine.players.get(0);
        player.reset();
        player.setX(10);
        player.setY(476);
        player.setState(Player.PlayerState.sideScroll);
        player.addToScreen(gameScreen, true);

    }


    @Override
    public void buildEnemies(GameScreen gameScreen) {
    }

}
