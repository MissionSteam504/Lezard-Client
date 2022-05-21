package fr.lezard;

import fr.lezard.plugins.*;
import net.minecraft.client.Minecraft;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.List;

public class PluginsManager {
    protected Minecraft minecraft;
    private static File settingsFileLezard;
    public static File hudPosFileLezard;

    private static final PluginsManager instance = new PluginsManager();
    public static final List<Plugin> plugins = new ArrayList<>();
    public static final List<HudPlugin> hudPlugins = new ArrayList<>();
    public static final List<String> pluginsName = new ArrayList<>();

    public void launch(){
        minecraft = Minecraft.getInstance();
        settingsFileLezard = new File(minecraft.gameDirectory, "plugins-lezard.txt");
        hudPosFileLezard = new File(minecraft.gameDirectory, "plugins-pos.txt");
        if(!settingsFileLezard.exists()){
            try {
                settingsFileLezard.createNewFile();
            }catch (IOException e){
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }
        if(!hudPosFileLezard.exists()){
            try {
                hudPosFileLezard.createNewFile();
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

    public static void writeEnabled(String pluginName, boolean activated) {
        try {
            List<String> fileContent = new ArrayList<>(Files.readAllLines(settingsFileLezard.toPath(), StandardCharsets.UTF_8));
            for (int i = 0; i < fileContent.size(); i++) {
                if (fileContent.get(i).equals(pluginName + "=" + !activated)) {
                    fileContent.set(i, pluginName + "=" + activated);
                    break;
                }
            }
            if(!fileContent.contains(pluginName + "=" + activated))
                fileContent.add(pluginName + "=" + activated);
            Files.write(settingsFileLezard.toPath(), fileContent, StandardCharsets.UTF_8);
        } catch (IOException e){
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    public static boolean getEnabled(String pluginName){
        try {
            Scanner myReader = new Scanner(settingsFileLezard);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] output = data.split("=");
                if(output[0].equalsIgnoreCase(pluginName)){
                    myReader.close();
                    return Boolean.parseBoolean(output[1]);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return false;
    }
}
