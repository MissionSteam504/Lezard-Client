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

public class InGameTimeHudPlugin extends HudPlugin {
    public static boolean enabled;
    public static boolean filled;
    public static boolean rainbow;
    public static Colors colors;
    private static Minecraft minecraft;
    public static String name;

    public static String string;
    public static int posX;
    public static int posY;

    public InGameTimeHudPlugin(String name, int posX, int posY) {
        super(name, posX, posY);
        InGameTimeHudPlugin.name = name;
        InGameTimeHudPlugin.posX = posX;
        InGameTimeHudPlugin.posY = posY;
    }

    public void init() {
        minecraft = Minecraft.getInstance();
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

    public static void renderIgTime(PoseStack p_93031_){
        if(Minecraft.getInstance().options.renderDebug){
            return;
        }

        if(PluginsLocationScreen.fakeDrag && name.equalsIgnoreCase(PluginsManager.pluginsName.get(PluginsManager.plugins.indexOf(PluginsLocationScreen.tempPlugin)))){
            posX = PluginsLocationScreen.tempX;
            posY = PluginsLocationScreen.tempY;
        }

        if(filled) {
            GuiComponent.fill(p_93031_, posX - PluginsLocationScreen.GAP, posY - PluginsLocationScreen.GAP, PluginPos.getWidth(name) + posX + PluginsLocationScreen.GAP, PluginPos.getHeight(name) + posY + PluginsLocationScreen.GAP, 0x2929292F);
        }

        Font font = minecraft.font;

        string = "Time: " + getInGameWorldHours() + ":" + getInGameWorldMinutes();
        drawString(p_93031_, font, string, posX, posY, rainbow ? PluginsManager.rainbowText() : Color.WHITE.getRGB());
    }

    private static String getInGameWorldMinutes(){
        assert minecraft.player != null;
        int time = (int) minecraft.player.getCommandSenderWorld().getDayTime();
        int finalTime =(time % 1000) * 6 / 100;
        return finalTime < 10 ? "0" + finalTime : String.valueOf(finalTime);
    }

    private static String getInGameWorldHours(){
        assert minecraft.player != null;
        int time = (int) ((minecraft.player.getCommandSenderWorld().getDayTime() + 6000) % 24000);
        int finalTime = (int) (time / 1000F);
        return finalTime < 10 ? "0" + finalTime : String.valueOf(finalTime);
    }
}
