package fr.lezard;

import fr.lezard.plugins.*;
import net.minecraft.client.Minecraft;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;

public class PluginsManager {
    protected Minecraft minecraft;
    private static File settingsFileLezard;
    public static File hudPosFileLezard;

    private static final PluginsManager instance = new PluginsManager();
    public static final List<Plugin> plugins = new ArrayList<>();
    private final Map<String, Plugin> pluginsByName = new HashMap<>();

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

        instance.registerPlugin(new ItemPhysicsPlugin("Item Physics"));
        instance.registerPlugin(new InGameTimeHudPlugin("In Game Time HUD"));
        instance.registerPlugin(new RealTimeHudPlugin("Real Time HUD"));
        instance.registerPlugin(new ArmorHudPlugin("Armor HUD"));
        instance.registerPlugin(new FpsHudPlugin("FPS HUD"));
        instance.registerPlugin(new CompassHudPlugin("Compass Hud"));
    }

    private void registerPlugin(Plugin plugin) {
        plugins.add(plugin);
        pluginsByName.put(plugin.getName(),plugin);
        plugin.init();
        System.out.println(plugin.getName() + " | Loaded. Activated : " + plugin.enabled);
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
