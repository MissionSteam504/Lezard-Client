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

public class FpsHudPlugin extends HudPlugin {
    public static boolean enabled;
    public static boolean filled;
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
        System.out.println(LezardCore.PREFIX + "InGameTime Enabled");
    }

    public void updatePos(){
        posX = PluginPos.loadPosX(name);
        posY = PluginPos.loadPosY(name);
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
            GuiComponent.fill(p_93031_, posX - 4, posY - 4, PluginPos.getWidth(name) + posX + 4, PluginPos.getHeight(name) + posY + 4, 0x2929292F);
        }

        Font font = Minecraft.getInstance().font;
        String[] data = Minecraft.getInstance().fpsString.split(" ");

        string = "FPS: " + data[0];

        drawString(p_93031_, font, string, posX, posY, Color.WHITE.getRGB());
    }
}
