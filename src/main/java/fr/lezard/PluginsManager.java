package fr.lezard;

import com.google.gson.JsonObject;
import fr.lezard.plugins.*;
import net.minecraft.client.Minecraft;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.List;

public class PluginsManager {
    protected Minecraft minecraft;

    public static File pluginFile;

    private static final PluginsManager instance = new PluginsManager();
    public static final List<Plugin> plugins = new ArrayList<>();
    public static final List<HudPlugin> hudPlugins = new ArrayList<>();
    public static final List<String> pluginsName = new ArrayList<>();

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
        instance.registerPlugin(new InGameTimeHudPlugin("In Game Time HUD", PluginPos.loadPosX("In Game Time HUD"), PluginPos.loadPosY("In Game Time HUD")));
        instance.registerPlugin(new RealTimeHudPlugin("Real Time HUD", PluginPos.loadPosX("Real Time HUD"), PluginPos.loadPosY("Real Time HUD")));
        instance.registerPlugin(new ArmorHudPlugin("Armor HUD", PluginPos.loadPosX("Armor HUD"), PluginPos.loadPosY("Armor HUD")));
        instance.registerPlugin(new FpsHudPlugin("FPS HUD", PluginPos.loadPosX("FPS HUD"), PluginPos.loadPosY("FPS HUD")));
        instance.registerPlugin(new CompassHudPlugin("Compass HUD", PluginPos.loadPosX("Compass HUD"), PluginPos.loadPosY("Compass HUD")));

        for(HudPlugin plugin : PluginsManager.hudPlugins){
            plugin.updatePos();
        }
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
}
