package model.gameobjects;

public class Potion extends Item {

    /* Temporary set up for Sprint 1 */
    public Potion(){
        super();
        imagePath = "/assets/Items/redPotionSmall.png";
        itemName = "Fire Potion";
        type = "Potion";
        damage = 0;
        immunity = 3;
        critChance = 6;
        value = 5;
        // No text wrap yet. Max of 28 characters per line
        description1 = "This will boost your fire";
        description2 = "resistance!";
    }

    @Override
    public void update() {

    }
}
