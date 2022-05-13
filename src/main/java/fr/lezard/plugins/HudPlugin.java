package fr.lezard.plugins;

import fr.lezard.PluginsManager;
import net.minecraft.client.Minecraft;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class HudPlugin{
    protected static int posX = Minecraft.getInstance().getWindow().getGuiScaledWidth() / 2;
    protected static int posY = Minecraft.getInstance().getWindow().getGuiScaledHeight() / 2;
    private static File posFile = PluginsManager.hudPosFileLezard;

    public void init() {

    }

    public static void writePos(int x, int y, Plugin plugin) {
        try {
            String pluginName = plugin.getName();
            List<String> fileContent = new ArrayList<>(Files.readAllLines(posFile.toPath(), StandardCharsets.UTF_8));
            boolean goodX = false;
            boolean goodY = false;
            for (int i = 0; i < fileContent.size(); i++) {
                if(fileContent.get(i).contains("=")){
                    String[] output = fileContent.get(i).split("=");
                    System.out.println(output[0] + ", " + output[1]);
                    System.out.println("Correct Name : x: " + (output[0].equalsIgnoreCase(pluginName + "-x")) + " y: " + (output[0].equalsIgnoreCase(pluginName + "-y")));
                    if(fileContent.get(i).equalsIgnoreCase(pluginName + "-x=" + output[1])){
                        fileContent.set(i, pluginName + "-x=" + loadPosX(plugin));
                        goodX = true;
                        continue;
                    }
                    if(fileContent.get(i).equalsIgnoreCase(pluginName + "-y=" + output[1])) {
                        fileContent.set(i, pluginName + "-y=" + loadPosY(plugin));
                        goodY = true;
                        continue;
                    }
                }
                if(goodX && goodY)
                break;
            }
            if(!fileContent.contains(pluginName + "-x=" + x)) fileContent.add(pluginName + "-x=" + x);
            if(!fileContent.contains(pluginName + "-y=" + y)) fileContent.add(pluginName + "-y=" + y);
            Files.write(posFile.toPath(), fileContent, StandardCharsets.UTF_8);
        } catch (IOException e){
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static int loadPosX(Plugin plugin){
        try {
            String pluginName = plugin.getName();
            Scanner myReader = new Scanner(posFile);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] output = data.split("=");
                if(output[0].equalsIgnoreCase(pluginName+"-x")){
                    myReader.close();
                    return Integer.parseInt(output[1]);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return Minecraft.getInstance().getWindow().getGuiScaledWidth() / 2;
    }
    public static int loadPosY(Plugin plugin){
        try {
            String pluginName = plugin.getName();
            Scanner myReader = new Scanner(posFile);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] output = data.split("=");
                if(output[0].equalsIgnoreCase(pluginName+"-y")){
                    myReader.close();
                    return Integer.parseInt(output[1]);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return Minecraft.getInstance().getWindow().getGuiScaledHeight() / 2;
    }
}
