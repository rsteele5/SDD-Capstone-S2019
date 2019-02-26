package gameobjects.renderables.items;

import gamescreens.DrawLayer;

public class ConsumableBuilder {

    // Renderable requirements
    private int _x = 0;
    private int _y = 0;
    private String _imagePath = "";
    private DrawLayer _layer = DrawLayer.Entity;

    // Item requirements
    private String _name = "[Insert Weapon Name]";
    private int _value = 1;

    // Consumable requirements
    private ConsumableType _type = ConsumableType.misc;
    private AffectType _affect = AffectType.random;
    private int _maxAffect = 0;
    private int _minAffect = 0;

    public ConsumableBuilder() { }

    public Consumable buildConsumable() {
        return new Consumable(_x, _y, _imagePath, _layer, _name, _value, _type, _affect, _maxAffect, _minAffect);
    }

    public ConsumableBuilder position(int x, int y){
        _x = x;
        _y = y;
        return this;
    }

    public ConsumableBuilder imagePath(String _imagePath) {
        this._imagePath = _imagePath;
        return this;
    }

    public ConsumableBuilder layer(DrawLayer _layer) {
        this._layer = _layer;
        return this;
    }

    public ConsumableBuilder name(String _name) {
        this._name = _name;
        return this;
    }

    public ConsumableBuilder value(int _value) {
        this._value = _value;
        return this;
    }

    public ConsumableBuilder type(ConsumableType _type) {
        this._type = _type;
        return this;
    }

    public ConsumableBuilder affect(AffectType _affect) {
        this._affect = _affect;
        return this;
    }

    public ConsumableBuilder maxAffect(int _maxAffect) {
        this._maxAffect = _maxAffect;
        return this;
    }

    public ConsumableBuilder minAffect(int _minAffect) {
        this._minAffect = _minAffect;
        return this;
    }

}
