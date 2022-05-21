package fr.lezard.plugins;

public class HudPlugin extends Plugin{
    public static int posX;
    public static int posY;
    public static boolean blit;

    protected HudPlugin(String name, int posX, int posY) {
        super(name);
        this.posX = posX;
        this.posY= posY;
    }

    public void init() { }

    public static boolean isBlit() {
        return false;
    }
}
