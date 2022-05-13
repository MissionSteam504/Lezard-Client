package fr.lezard.plugins;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

import static net.minecraft.client.gui.GuiComponent.drawString;

public class FpsHudPlugin extends Plugin {
    public static boolean enabled;
    private static Minecraft minecraft;
    public static String name;

    public static int posX;
    public static int posY;

    public FpsHudPlugin(String name) {
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

    public static void renderFps(PoseStack p_93031_){
        Font font = minecraft.font;
        String[] data = minecraft.fpsString.split(" ");

        drawString(p_93031_, font, "FPS: " + data[0], posX, posY, Color.WHITE.getRGB());
    }
}
