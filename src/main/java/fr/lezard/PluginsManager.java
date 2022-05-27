package fr.lezard;

import fr.lezard.plugins.*;
import fr.lezard.plugins.utils.HudPlugin;
import fr.lezard.plugins.utils.Plugin;
import fr.lezard.plugins.utils.PluginPos;
import net.minecraft.client.Minecraft;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

public class PluginsManager {
    protected Minecraft minecraft;

    public static File pluginFile;

    private static final PluginsManager instance = new PluginsManager();
    public static final List<Plugin> plugins = new ArrayList<>();
    public static final List<HudPlugin> hudPlugins = new ArrayList<>();
    public static final List<String> pluginsName = new ArrayList<>();

    public static int colorAlpha = 95;

    public static Color color = new Color(0, 0, 0, colorAlpha);

    public void launch(){
        minecraft = Minecraft.getInstance();

        pluginFile = new File(minecraft.gameDirectory, "lezard-settings.json");

        if(!pluginFile.exists()){
            try {
                pluginFile.createNewFile();
                PrintWriter printWriter = new PrintWriter(pluginFile);
                printWriter.write("{}");
                printWriter.close();
            }catch (IOException e){
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }

        PluginPos.init();

        instance.registerPlugin(new ItemPhysicsPlugin("Item Physics"));
        instance.registerPlugin(new InGameTimeHudPlugin("In Game Time HUD", PluginFileManager.getInt("In Game Time HUD", "posX"), PluginFileManager.getInt("In Game Time HUD", "posY")));
        instance.registerPlugin(new RealTimeHudPlugin("Real Time HUD", PluginFileManager.getInt("Real Time HUD", "posX"), PluginFileManager.getInt("Real Time HUD", "posY")));
        instance.registerPlugin(new ArmorHudPlugin("Armor HUD", PluginFileManager.getInt("Armor HUD", "posX"), PluginFileManager.getInt("Armor HUD", "posY")));
        instance.registerPlugin(new FpsHudPlugin("FPS HUD", PluginFileManager.getInt("FPS HUD", "posX"), PluginFileManager.getInt("FPS HUD", "posY")));
        instance.registerPlugin(new CompassHudPlugin("Compass HUD", PluginFileManager.getInt("Compass HUD", "posX"), PluginFileManager.getInt("Compass HUD", "posY")));
        instance.registerPlugin(new KeyStrokePlugin("KeyStroke HUD", PluginFileManager.getInt("KeyStroke HUD", "posX"), PluginFileManager.getInt("KeyStroke HUD", "posY")));
    }

    private void registerPlugin(Plugin plugin) {
        pluginsName.add(plugin.getName());
        plugins.add(plugin);
        if(plugin instanceof HudPlugin hudPlugin){
            hudPlugins.add(hudPlugin);
        }
        plugin.init();
    }

    public static PluginsManager getInstance(){
        return instance;
    }

    public static int rainbowText(){
        return Color.HSBtoRGB((float) (System.currentTimeMillis() % 5000L) / 5000F, 0.8f, 0.8f);
    }
}
