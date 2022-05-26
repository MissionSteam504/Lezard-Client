package fr.lezard.plugins;

import com.mojang.blaze3d.vertex.PoseStack;
import fr.lezard.LezardCore;
import fr.lezard.PluginsManager;
import fr.lezard.screens.PluginsLocationScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;

import java.awt.*;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static net.minecraft.client.gui.GuiComponent.drawString;

public class RealTimeHudPlugin extends HudPlugin{
    public static boolean enabled;
    public static boolean filled;
    public static String name;

    public static String string;
    public static int posX;
    public static int posY;

    public RealTimeHudPlugin(String name, int posX, int posY) {
        super(name, posX, posY);
        RealTimeHudPlugin.name = name;
        RealTimeHudPlugin.posX = posX;
        RealTimeHudPlugin.posY = posY;
    }

    public void init() {
        enabled = isEnabled();
        filled = isFilled();
        System.out.println(LezardCore.PREFIX + "RealTimeHUD Enabled");
    }

    public void updatePos(){
        posX = PluginPos.loadPosX(name);
        posY = PluginPos.loadPosY(name);
    }

    public static void renderIrlTime(PoseStack poseStack){
        if(Minecraft.getInstance().options.renderDebug){
            return;
        }

        if(PluginsLocationScreen.fakeDrag && name.equalsIgnoreCase(PluginsManager.pluginsName.get(PluginsManager.plugins.indexOf(PluginsLocationScreen.tempPlugin)))){
            posX = PluginsLocationScreen.tempX;
            posY = PluginsLocationScreen.tempY;
        }


        if(filled) {
            GuiComponent.fill(poseStack, posX - 4, posY - 4, PluginPos.getWidth(name) + posX + 4, PluginPos.getHeight(name) + posY + 4, 0x2929292F);
        }

        Calendar calendar = new GregorianCalendar();

        int sec = calendar.get(Calendar.SECOND);
        int minutes = calendar.get(Calendar.MINUTE);
        int hours = calendar.get(Calendar.HOUR_OF_DAY);

        string = "IRL Time: " + hours + ":" + minutes + ":" + sec;
        Font font = Minecraft.getInstance().font;
        drawString(poseStack, font, string, posX, posY, Color.DARK_GRAY.getRGB());
    }
}
