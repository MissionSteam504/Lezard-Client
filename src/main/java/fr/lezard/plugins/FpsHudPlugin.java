package fr.lezard.plugins;

import com.mojang.blaze3d.vertex.PoseStack;
import fr.lezard.LezardCore;
import fr.lezard.PluginFileManager;
import fr.lezard.PluginsManager;
import fr.lezard.plugins.utils.Colors;
import fr.lezard.plugins.utils.HudPlugin;
import fr.lezard.plugins.utils.PluginPos;
import fr.lezard.screens.PluginsLocationScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;

import java.awt.*;

import static net.minecraft.client.gui.GuiComponent.drawString;

public class FpsHudPlugin extends HudPlugin {
    public static boolean enabled;
    public static boolean filled;
    public static boolean rainbow;
    public static Colors colors;
    public static String name;

    public static String string;
    public static int posX;
    public static int posY;

    public FpsHudPlugin(String name, int posX, int posY) {
        super(name, posX, posY);
        FpsHudPlugin.name = name;
        FpsHudPlugin.posX = posX;
        FpsHudPlugin.posY = posY;
    }

    public void init() {
        enabled = isEnabled();
        filled = isFilled();
        rainbow = isRainbow();
        colors = getColors();
        System.out.println(LezardCore.PREFIX + "InGameTime Enabled");
    }

    public void updatePos(){
        posX = PluginFileManager.getInt(name, "posX");
        posY = PluginFileManager.getInt(name, "posY");
    }

    public static void renderFps(PoseStack p_93031_){
        if(Minecraft.getInstance().options.renderDebug){
            return;
        }

        if(PluginsLocationScreen.fakeDrag && name.equalsIgnoreCase(PluginsManager.pluginsName.get(PluginsManager.plugins.indexOf(PluginsLocationScreen.tempPlugin)))){
            posX = PluginsLocationScreen.tempX;
            posY = PluginsLocationScreen.tempY;
        }

        if(filled) {
            GuiComponent.fill(p_93031_, posX - PluginsLocationScreen.GAP, posY - PluginsLocationScreen.GAP, PluginPos.getWidth(name) + posX + PluginsLocationScreen.GAP, PluginPos.getHeight(name) + posY + PluginsLocationScreen.GAP, PluginsManager.color.getRGB());
        }

        Font font = Minecraft.getInstance().font;
        String[] data = Minecraft.getInstance().fpsString.split(" ");

        string = "FPS: " + data[0];

        drawString(p_93031_, font, string, posX, posY, rainbow ? PluginsManager.rainbowText() : colors.getRgb());
    }
}
