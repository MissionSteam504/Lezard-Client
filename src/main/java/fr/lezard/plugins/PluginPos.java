package fr.lezard.plugins;

import fr.lezard.PluginFileManager;
import fr.lezard.PluginsManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PluginPos {
    private static Font font;

    public static void init(){
        font = Minecraft.getInstance().font;
    }

    public static int loadPosX(String name){
        return PluginFileManager.getInt(name, "posX");
    }
    public static int loadPosY(String name){
        return PluginFileManager.getInt(name, "posY");
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

        return 0;
    }
}
