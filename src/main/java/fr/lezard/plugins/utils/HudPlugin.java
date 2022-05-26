package fr.lezard.plugins.utils;

import fr.lezard.PluginFileManager;
import fr.lezard.plugins.utils.Plugin;

public abstract class HudPlugin extends Plugin {
    private static int posX;
    private static int posY;
    private static boolean filled;
    private static boolean rainbow;
    private static Colors colors = null;

    protected HudPlugin(String name, int posX, int posY) {
        super(name);
        this.posX = posX;
        this.posY = posY;
        filled = PluginFileManager.getBoolean(name, "filled");
        rainbow = PluginFileManager.getBoolean(name, "rainbow");
        if(!PluginFileManager.getString(name, "color").equalsIgnoreCase("")){
            colors = Colors.valueOf(PluginFileManager.getString(name, "color"));
        }
    }

    public void init() { }
    public abstract void updatePos();

    public static boolean isFilled() {
        return filled;
    }
    public static boolean isRainbow(){
        return rainbow;
    }
    public static Colors getColors(){
        return colors==null ? Colors.WHITE : colors;
    }
}
