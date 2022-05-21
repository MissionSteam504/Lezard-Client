package fr.lezard.plugins;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.util.Mth;

import java.awt.*;

import static net.minecraft.client.gui.GuiComponent.drawCenteredString;
import static net.minecraft.client.gui.GuiComponent.drawString;

public class CompassHudPlugin extends HudPlugin{
    public static boolean enabled;
    public static boolean blit;
    private static Minecraft minecraft;
    public static String name;
    public static int posX;
    public static int posY;
    private static int width;

    public CompassHudPlugin(String name, int posX, int posY) {
        super(name, posX, posY);
        this.name = name;
        this.posX = posX;
        this.posY = posY;
    }

    public void init() {
        minecraft = Minecraft.getInstance();
        this.enabled = isEnabled();
        this.blit = isBlit();
        width = PluginPos.getWidth(name);
        System.out.println("Compass Enabled");
    }

    public static void renderHud(PoseStack p_93031_){
        posX = PluginPos.loadPosX(name);
        posY = PluginPos.loadPosY(name);

        if(blit) {
            GuiComponent.fill(p_93031_, posX - 4, posY - 4, PluginPos.getWidth(name) + posX + 4, PluginPos.getHeight(name) + posY + 4, 0x2929292F);
        }

        int middle = PluginPos.getWidth(name) / 2;

        int angle = (int) Mth.wrapDegrees(minecraft.player.getYRot());
        Font font = minecraft.font;

        drawString(p_93031_, font, "V", posX + middle, posY, Color.WHITE.getRGB());

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
                    drawString(p_93031_, font, s, posX + middle + angle, posY + 8, Color.WHITE.getRGB());
                }
                angle+=45;
            }
        }
    }
}