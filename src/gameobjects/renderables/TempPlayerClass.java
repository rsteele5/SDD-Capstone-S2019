package gameobjects.renderables;

import gameobjects.renderables.items.*;
import gamescreens.DrawLayer;

import java.util.concurrent.CopyOnWriteArrayList;

public class TempPlayerClass extends RenderableObject{

    private CopyOnWriteArrayList<Item> items = new CopyOnWriteArrayList<>();
    private CopyOnWriteArrayList<RenderableObject> rItems = new CopyOnWriteArrayList<>();

    // Default constructor
    public TempPlayerClass(int x, int y){
        super(x, y);
        this.imagePath = "/assets/Teddy.png";
        this.drawLayer = DrawLayer.Entity;
        initializeItems();
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
    public void update() {

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

}
