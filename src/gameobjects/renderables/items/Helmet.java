package model.gameobjects;

public class Helmet extends Item {

    /* Temporary set up for Sprint 1 */
    public Helmet(){
        super();
        imagePath = "/assets/Items/helmetSmall.png";
        itemName = "Bronze Helmet";
        type = "Armor";
        damage = 0;
        immunity = 4;
        critChance = 0;
        value = 10;
        // No text wrap yet. Max of 28 characters per line
        description1 = "Protect your noggin";
    }


    @Override
    public void update() {

    }
}
