package fr.lezard.plugins;

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
    private static final File posFile = PluginsManager.hudPosFileLezard;
    private static Font font;

    public static void init(){
        font = Minecraft.getInstance().font;
    }

    public static void writePos(int x, int y, int index) {
        try {
            String pluginName = PluginsManager.pluginsName.get(index);
            List<String> fileContent = new ArrayList<>(Files.readAllLines(posFile.toPath(), StandardCharsets.UTF_8));
            boolean goodX = false;
            boolean goodY = false;
            for (int i = 0; i < fileContent.size(); i++) {
                if(fileContent.get(i).contains("=")){
                    String[] output = fileContent.get(i).split("=");
                    if(fileContent.get(i).equalsIgnoreCase(pluginName + "-x=" + output[1])){
                        fileContent.set(i, pluginName + "-x=" + x);
                        goodX = true;
                        continue;
                    }
                    if(fileContent.get(i).equalsIgnoreCase(pluginName + "-y=" + output[1])) {
                        fileContent.set(i, pluginName + "-y=" + y);
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

    public static int loadPosX(String name){
        try {
            Scanner myReader = new Scanner(posFile);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] output = data.split("=");
                if(output[0].equalsIgnoreCase(name+"-x")){
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
    public static int loadPosY(String name){
        try {
            Scanner myReader = new Scanner(posFile);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] output = data.split("=");
                if(output[0].equalsIgnoreCase(name+"-y")){
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
