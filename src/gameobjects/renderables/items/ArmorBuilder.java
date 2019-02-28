package gameobjects.renderables.items;

import gamescreens.DrawLayer;
import static gamescreens.DrawLayer.Entity;

public class ArmorBuilder {

    //Renderable requirements
    private int _x = 0;
    private int _y = 0;
    private String _imagePath = "";
    private DrawLayer _layer = Entity;

    //Item requirements
    private String _name = "[Insert Armor Name]";
    private ArmorType _type;
    private int _value = -1;
    private int _armor = 0;

    //TODO check on mass with Austin
    //private int mass;

    public ArmorBuilder(){ }

    public Armor buildArmor(){
        return new Armor(_x, _y, _imagePath, _layer, _name, _value, _type, _armor);
    }

    public ArmorBuilder position(int x, int y){
        _x = x;
        _y = y;
        return this;
    }

    public ArmorBuilder imagePath(String _imagePath) {
        this._imagePath = _imagePath;
        return this;
    }

    public ArmorBuilder layer(DrawLayer _layer) {
        this._layer = _layer;
        return this;
    }

    public ArmorBuilder name(String _name) {
        this._name = _name;
        return this;
    }

    public ArmorBuilder value(int _value) {
        this._value = _value;
        return this;
    }

    public ArmorBuilder type(ArmorType _type) {
        this._type = _type;
        return this;
    }

    public ArmorBuilder armorPoints(int _armor) {
        this._armor = _armor;
        return this;
    }
}
