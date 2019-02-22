package gameobjects.renderables.items;

public class Sword extends Item {

    /* Temporary set up for Sprint 1 */
    public Sword(){
        imagePath = "/assets/Items/swordSmall.png";
        itemName = "Wooden Sword";
        type = "Weapon";
        damage = 4;
        immunity = 0;
        critChance = 6;
        value = 10;
        // No text wrap yet. Max of 28 characters per line
        description1 = "Just your standard wooden";
        description2 = "sword";
    }

    @Override
    public void update() {

    }
}
