package fr.lezard.plugins;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;

import java.awt.*;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static net.minecraft.client.gui.GuiComponent.drawString;

public class RealTimeHudPlugin extends Plugin{
    public static boolean enabled;
    public static String name;
    public static Minecraft minecraft;
    public static int posX;
    public static int posY;

    public RealTimeHudPlugin(String name) {
        super(name);
        this.name = name;
    }

    public void init() {
        this.enabled = isEnabled();
        minecraft = Minecraft.getInstance();
        System.out.println("RealTimeHUD Enabled");
        posX = HudPlugin.loadPosX(plugin);
        posY = HudPlugin.loadPosY(plugin);
    }

    public static void renderIrlTime(PoseStack p_93031_){
        Calendar calendar = new GregorianCalendar();

        int sec = calendar.get(Calendar.SECOND);
        int minutes = calendar.get(Calendar.MINUTE);
        int hours = calendar.get(Calendar.HOUR_OF_DAY);

        Font font = minecraft.font;
        drawString(p_93031_, font, "IRL Time: " + hours + ":" + minutes + ":" + sec, posX, posY, Color.DARK_GRAY.getRGB());
    }
}
