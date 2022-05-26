package fr.lezard.plugins.utils;

import fr.lezard.plugins.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;

public class PluginPos {
    private static Font font;

    public static void init(){
        font = Minecraft.getInstance().font;
    }

    public static int getWidth(String pluginName){
        if(pluginName.equalsIgnoreCase(ArmorHudPlugin.name))
            return 120;
        else if(pluginName.equalsIgnoreCase(CompassHudPlugin.name))
            return 192;
        else if(pluginName.equalsIgnoreCase(FpsHudPlugin.name))
            return font.width(FpsHudPlugin.string);
        else if (pluginName.equalsIgnoreCase(InGameTimeHudPlugin.name))
            return font.width(InGameTimeHudPlugin.string);
        else if (pluginName.equalsIgnoreCase(RealTimeHudPlugin.name))
            return font.width(RealTimeHudPlugin.string);
        else if (pluginName.equalsIgnoreCase(KeyStrokePlugin.name))
            return KeyStrokePlugin.keyMode.getWidth();

        return 0;
    }
    public static int getHeight(String pluginName){
        if(pluginName.equalsIgnoreCase(ArmorHudPlugin.name))
            return 64;
        else if(pluginName.equalsIgnoreCase(CompassHudPlugin.name))
            return 16;
        else if(pluginName.equalsIgnoreCase(FpsHudPlugin.name))
            return 8;
        else if(pluginName.equalsIgnoreCase(InGameTimeHudPlugin.name))
            return 8;
        else if(pluginName.equalsIgnoreCase(RealTimeHudPlugin.name))
            return 8;
        else if(pluginName.equalsIgnoreCase(KeyStrokePlugin.name))
            return KeyStrokePlugin.keyMode.getHeight();

        return 0;
    }
}
