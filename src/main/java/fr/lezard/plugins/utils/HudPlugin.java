package fr.lezard.plugins.utils;

import fr.lezard.PluginFileManager;
import fr.lezard.plugins.utils.Plugin;

public abstract class HudPlugin extends Plugin {
    public static int posX;
    public static int posY;
    public static boolean filled;
    public static boolean rainbow;

    protected HudPlugin(String name, int posX, int posY) {
        super(name);
        this.posX = posX;
        this.posY = posY;
        filled = PluginFileManager.getBoolean(name, "filled");
        rainbow = PluginFileManager.getBoolean(name, "rainbow");
    }

    public void init() { }
    public abstract void updatePos();

    public static boolean isFilled() {
        return filled;
    }
    public static boolean isRainbow(){
        return rainbow;
    }
}
