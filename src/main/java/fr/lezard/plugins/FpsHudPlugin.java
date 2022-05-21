package fr.lezard.plugins;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;

import java.awt.*;

import static net.minecraft.client.gui.GuiComponent.drawString;

public class FpsHudPlugin extends HudPlugin {
    public static boolean enabled;
    public static boolean blit;
    public static String name;

    public static String string;
    public static int posX;
    public static int posY;

    public FpsHudPlugin(String name, int posX, int posY) {
        super(name, posX, posY);
        this.name = name;
        this.posX = posX;
        this.posY = posY;
    }

    public void init() {
        this.enabled = isEnabled();
        this.blit = isBlit();
        System.out.println("InGameTime Enabled");
    }

    public static void renderFps(PoseStack p_93031_){
        posX = PluginPos.loadPosX(name);
        posY = PluginPos.loadPosY(name);

        if(blit) {
            GuiComponent.fill(p_93031_, posX - 4, posY - 4, PluginPos.getWidth(name) + posX + 4, PluginPos.getHeight(name) + posY + 4, 0x2929292F);
        }

        Font font = Minecraft.getInstance().font;
        String[] data = Minecraft.getInstance().fpsString.split(" ");

        string = "FPS: " + data[0];

        drawString(p_93031_, font, string, posX, posY, Color.WHITE.getRGB());
    }
}
