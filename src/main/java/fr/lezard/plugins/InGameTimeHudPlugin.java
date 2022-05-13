package fr.lezard.plugins;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;

import java.awt.*;

import static net.minecraft.client.gui.GuiComponent.drawString;

public class InGameTimeHudPlugin extends Plugin{
    public static boolean enabled;
    private static Minecraft minecraft;
    public static String name;

    public static int posX;
    public static int posY;

    public InGameTimeHudPlugin(String name) {
        super(name);
        this.name = name;
    }

    public void init() {
        minecraft = Minecraft.getInstance();
        this.enabled = isEnabled();
        System.out.println("InGameTime Enabled");
        posX = HudPlugin.loadPosX(plugin);
        posY = HudPlugin.loadPosY(plugin);
    }

    public static void renderIgTime(PoseStack p_93031_){
        Font font = minecraft.font;
        drawString(p_93031_, font, "Time: " + getInGameWorldHours() + ":" + getInGameWorldMinutes(), posX, posY, Color.DARK_GRAY.getRGB());
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
