package fr.lezard.plugins;

import fr.lezard.PluginsManager;

public abstract class Plugin {
    protected static String name;
    public static boolean enabled;

    protected Plugin(String name){
        this.name = name;
        this.enabled = PluginsManager.getInstance().getEnabled(name);
    }
    public static String getName(){
        return name;
    }
    protected static boolean isEnabled(){
        return enabled;
    }

    public abstract void init();

}
