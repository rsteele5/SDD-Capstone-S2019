package gameobjects.renderables;

import gameobjects.renderables.items.Item;

import java.util.Comparator;

// Method to sort items list
public class SortByType implements Comparator<Item> {

    @Override
    public int compare(Item o1, Item o2) {
        return o1.getCategory().compareTo(o2.getCategory());
    }
}
