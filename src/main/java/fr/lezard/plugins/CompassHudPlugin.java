package fr.lezard.plugins;

import com.mojang.blaze3d.vertex.PoseStack;
import fr.lezard.LezardCore;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.Mth;

import java.awt.*;

import static fr.lezard.plugins.Plugin.isEnabled;
import static net.minecraft.client.gui.GuiComponent.drawCenteredString;
import static net.minecraft.client.gui.GuiComponent.drawString;

public class CompassHudPlugin extends Plugin{
    public static boolean enabled;
    private static Minecraft minecraft;
    public static String name;

    public CompassHudPlugin(String name) {
        super(name);
        this.name = name;
    }

    public void init() {
        minecraft = Minecraft.getInstance();
        this.enabled = isEnabled();
        System.out.println("Compass Enabled");
    }

    public static void renderHud(PoseStack p_93031_){
        int posX = HudPlugin.loadPosX(plugin);
        int posY = HudPlugin.loadPosY(plugin);

        int screenWidth = minecraft.getWindow().getGuiScaledWidth();

        int angle = (int) Mth.wrapDegrees(minecraft.player.getYRot());
        int width = screenWidth / 2;

        drawString(p_93031_, minecraft.font, "V", posX, posY, Color.WHITE.getRGB());

        for(int i =0; i<=1; i++){
            for(double d=0.0D; d<=3.5D; d+=0.5D){
                if(width + angle > width - 60 && width + angle < width + 60){
                    // String s = "\u00A76" + new TranslatableComponent("lezard.south");
                    String s = "\u00A76South";
                    if(d==0.5D){
                        // s = String.valueOf(new TranslatableComponent("lezard.southWest"));
                        s = "South-East";
                    }
                    if(d==1.0D){
                        // s = "\u00A76" + new TranslatableComponent("lezard.west");
                        s = "\u00A76West";
                    }
                    if(d==1.5D){
                        // s = String.valueOf(new TranslatableComponent("lezard.northWest"));
                        s = "North-West";
                    }
                    if(d==2.0D){
                        //s = "\u00A76" + new TranslatableComponent("lezard.north");
                        s = "\u00A76North";
                    }
                    if(d==2.5D){
                        s = "North-East";
                    }
                    if(d==3.0D){
                        // s = "\u00A76" + new TranslatableComponent("lezard.east");
                        s = "\u00A76East";
                    }
                    if(d==3.5D){
                        // s = String.valueOf(new TranslatableComponent("lezard.southEast"));
                        s = "South-East";
                    }
                    Font font = minecraft.font;
                    drawString(p_93031_, font, s, posX + angle, posY + 8, Color.WHITE.getRGB());
                }
                angle+=45;
            }
        }
    }
}
