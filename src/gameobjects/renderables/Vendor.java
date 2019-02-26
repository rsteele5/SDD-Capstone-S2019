package gameobjects.renderables;

import gameobjects.renderables.items.Item;
import gameobjects.renderables.items.Weapon;
import gameobjects.renderables.items.WeaponBuilder;
import gameobjects.renderables.items.WeaponType;
import gamescreens.DrawLayer;

import java.util.concurrent.CopyOnWriteArrayList;

public class Vendor extends RenderableObject {
    private CopyOnWriteArrayList<Item> items = new CopyOnWriteArrayList<>();


    // Default constructor
    public Vendor(int x, int y){
        super(x, y);
        this.drawLayer = DrawLayer.Entity;

    }

    private void initializeItems() {
        items.add(new WeaponBuilder()
                .imagePath("/assets/Items/club1.png")
                .name("My Fwirst Club")
                .type(WeaponType.Club)
                .value(10)
                .minDamage(1)
                .maxDamage(4)
                .critChance(6)
                .buildWeapon());

        items.add(new WeaponBuilder()
                .imagePath("/assets/Items/spear1.png")
                .name("My Fwirst Spear")
                .type(WeaponType.Spear)
                .value(9)
                .minDamage(2)
                .maxDamage(6)
                .critChance(4)
                .buildWeapon());

        items.add(new WeaponBuilder()
                .imagePath("/assets/Items/sword1.png")
                .name("My Fwirst Sword")
                .type(WeaponType.Sword)
                .value(11)
                .minDamage(4)
                .maxDamage(12)
                .critChance(3)
                .buildWeapon());


    }

    @Override
    public void update() {

    }

    public CopyOnWriteArrayList<Item> getItems() {
        return items;
    }
}
