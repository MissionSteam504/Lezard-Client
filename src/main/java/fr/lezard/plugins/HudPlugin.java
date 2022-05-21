package fr.lezard.plugins;

public abstract class HudPlugin extends Plugin{
    public static int posX;
    public static int posY;
    public static boolean filled;

    protected HudPlugin(String name, int posX, int posY) {
        super(name);
        this.posX = posX;
        this.posY= posY;
    }

    public void init() { }
    public abstract void updatePos();

    public static boolean isFilled() {
        return false;
    }
}
