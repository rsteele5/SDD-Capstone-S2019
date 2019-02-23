package model.gameobjects;
import utilities.Debug;

import java.util.ArrayList;
import java.io.*;
import control.RenderEngine;
import javax.imageio.ImageIO;

public class Inventory {

    private ArrayList<Weapon> weapons;
    private ArrayList<Armor> armors;
    private ArrayList<Consumable> consumables;

    public Inventory() {
        itemLoader();
    }

    private void itemLoader()
    {
        weapons = new ArrayList<>();
        armors = new ArrayList<>();
        consumables = new ArrayList<>();
        try {
            File file = new File("src/assets/Items/Weapons.txt");
            BufferedReader in = new BufferedReader(new FileReader(file));

            String s;
            //Read in Weapons
            while((s = in.readLine()) != null) {
                //Text file is formatted Name,type,value,desc1,desc2,min,max,critchance,imgpath
                Weapon weapon = new Weapon();
                String[] var = s.split(",");
                int x = 0;
                weapon.setName(var[x++]);
                weapon.setType(var[x++]);
                weapon.setValue(Integer.parseInt(var[x++]));
                weapon.setDescPart1(var[x++]);
                weapon.setDescPart2(var[x++]);
                weapon.setMindamage(Integer.parseInt(var[x++]));
                weapon.setMaxdamage(Integer.parseInt(var[x++]));
                weapon.setCritchance(Integer.parseInt(var[x++]));
                weapon.setCurrentImage(RenderEngine.convertToARGB(ImageIO.read(getClass().getResource(var[x++]))));

                weapons.add(weapon);
            }
            file = new File("src/assets/Items/Armors.txt");
            in = new BufferedReader(new FileReader(file));
            //Read in Armor
            while((s = in.readLine()) != null) {
                //Text file is formatted Name,type,value,desc1,desc2,defense,imgpath
                Armor armor = new Armor();
                String[] var = s.split(",");
                int x = 0;
                armor.setName(var[x++]);
                armor.setType(var[x++]);
                armor.setValue(Integer.parseInt(var[x++]));
                armor.setDescPart1(var[x++]);
                armor.setDescPart2(var[x++]);
                armor.setDefense(Integer.parseInt(var[x++]));
                armor.setCurrentImage(RenderEngine.convertToARGB(ImageIO.read(getClass().getResource(var[x++]))));

                armors.add(armor);
            }
            file = new File("src/assets/Items/Consumables.txt");
            in = new BufferedReader(new FileReader(file));
            //Read in Consumables
            while((s = in.readLine()) != null) {
                //Text file is formatted Name,type,value,desc1,desc2,effective,imgpath
                Consumable consumable = new Consumable();
                String[] var = s.split(",");
                int x = 0;
                consumable.setName(var[x++]);
                consumable.setType(var[x++]);
                consumable.setValue(Integer.parseInt(var[x++]));
                consumable.setDescPart1(var[x++]);
                consumable.setDescPart2(var[x++]);
                consumable.setEffectAmount(Integer.parseInt(var[x++]));
                consumable.setCurrentImage(RenderEngine.convertToARGB(ImageIO.read(getClass().getResource(var[x++]))));
                consumables.add(consumable);
            }

        }catch(Exception e)
        {
            Debug.success(true,"ITEM LOADING ERROR " + e.getMessage());
        }
    }

    public Weapon getWeapon(int index) {
        return weapons.get(index);
    }

    public ArrayList<Weapon> getWeapons() {
        return weapons;
    }

    public Armor getArmor(int index) {
        return armors.get(index);
    }

    public ArrayList<Armor> getArmors() {
        return armors;
    }
    public Consumable getConsumable(int index) {
        return consumables.get(index);
    }

    public ArrayList<Consumable> getConsumables() {
        return consumables;
    }
}