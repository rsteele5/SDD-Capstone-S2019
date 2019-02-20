package gameobjects;


public abstract class GameObject {

    protected int x;
    protected int y;

    public GameObject() {
        x = 0;
        y = 0;
    }

    public GameObject(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public abstract void update();

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
