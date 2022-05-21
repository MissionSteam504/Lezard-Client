package fr.lezard.plugins;

import com.mojang.blaze3d.vertex.PoseStack;
import fr.lezard.LezardCore;
import fr.lezard.PluginsManager;
import fr.lezard.screens.PluginsLocationScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;

import java.awt.*;

import static net.minecraft.client.gui.GuiComponent.drawString;

public class InGameTimeHudPlugin extends HudPlugin{
    public static boolean enabled;
    public static boolean filled;
    private static Minecraft minecraft;
    public static String name;

    public static String string;
    public static int posX;
    public static int posY;

    public InGameTimeHudPlugin(String name, int posX, int posY) {
        super(name, posX, posY);
        this.name = name;
        this.posX = posX;
        this.posY = posY;
    }

    public void init() {
        minecraft = Minecraft.getInstance();
        this.enabled = isEnabled();
        this.filled = isFilled();
        System.out.println(LezardCore.PREFIX + "InGameTime Enabled");
    }

    public void updatePos(){
        posX = PluginPos.loadPosX(name);
        posY = PluginPos.loadPosY(name);
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
            GuiComponent.fill(p_93031_, posX - 4, posY - 4, PluginPos.getWidth(name) + posX + 4, PluginPos.getHeight(name) + posY + 4, 0x2929292F);
        }

        Font font = minecraft.font;

        string = "Time: " + getInGameWorldHours() + ":" + getInGameWorldMinutes();
        drawString(p_93031_, font, string, posX, posY, Color.DARK_GRAY.getRGB());
    }

    private static String getInGameWorldMinutes(){
        int time = (int) minecraft.player.getCommandSenderWorld().getDayTime();
        int finalTime =(time % 1000) * 6 / 100;
        return finalTime < 10 ? "0" + finalTime : String.valueOf(finalTime);
    }

    private static String getInGameWorldHours(){
        int time = (int) ((minecraft.player.getCommandSenderWorld().getDayTime() + 6000) % 24000);
        int finalTime = (int) (time / 1000F);
        return finalTime < 10 ? "0" + finalTime : String.valueOf(finalTime);
    }
}
