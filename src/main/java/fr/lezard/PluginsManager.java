package fr.lezard;

import com.google.gson.JsonObject;
import fr.lezard.plugins.*;
import net.minecraft.client.Minecraft;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.List;

public class PluginsManager {
    protected Minecraft minecraft;
    private static File settingsFileLezard;
    public static File hudPosFileLezard;

    public static File pluginFile;

    private static final PluginsManager instance = new PluginsManager();
    public static final List<Plugin> plugins = new ArrayList<>();
    public static final List<HudPlugin> hudPlugins = new ArrayList<>();
    public static final List<String> pluginsName = new ArrayList<>();

    public void launch(){
        minecraft = Minecraft.getInstance();
        settingsFileLezard = new File(minecraft.gameDirectory, "plugins-lezard.txt");
        hudPosFileLezard = new File(minecraft.gameDirectory, "plugins-pos.txt");
        pluginFile = new File(minecraft.gameDirectory, "test.json");
        if(!settingsFileLezard.exists()){
            try {
                settingsFileLezard.createNewFile();
            }catch (IOException e){
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }
        if(!pluginFile.exists()){
            try {
                pluginFile.createNewFile();
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

        /* PluginFileManager.writeJson("In Game Time HUD", "test", "hello");
        PluginFileManager.writeJson("In Game Time HUD", "int", 1235);
        PluginFileManager.writeJson("In Game Time HUD", "bool", true);
        System.out.println(PluginFileManager.getString("In Game Time HUD", "test"));
        System.out.println(PluginFileManager.getInt("In Game Time HUD", "int"));
        System.out.println(PluginFileManager.getBoolean("In Game Time HUD", "bool")); */

        // All code up here is on break for now

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
