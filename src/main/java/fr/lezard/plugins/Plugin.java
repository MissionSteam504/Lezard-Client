package fr.lezard.plugins;

import fr.lezard.PluginFileManager;
import fr.lezard.PluginsManager;

public abstract class Plugin {
    protected static String name;
    public static boolean enabled;

    protected Plugin(String name){
        this.name = name;
        this.enabled = PluginFileManager.getBoolean(name, "enabled");
    }
    public static String getName(){
        return name;
    }
    protected static boolean isEnabled(){
        return enabled;
    }

    public abstract void init();

}
