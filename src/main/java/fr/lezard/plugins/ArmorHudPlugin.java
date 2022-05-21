package fr.lezard.plugins;

import com.mojang.blaze3d.vertex.PoseStack;
import fr.lezard.screens.PluginsLocationScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;

import java.awt.*;

import static net.minecraft.client.gui.GuiComponent.drawString;

public class ArmorHudPlugin extends HudPlugin{
    public static boolean enabled;
    public static boolean blit;
    public static String name;

    public static String string;
    public static int posX;
    public static int posY;


    public ArmorHudPlugin(String name, int posX, int posY) {
        super(name, posX, posY);
        this.name = name;
        this.posX = posX;
        this.posY = posY;
    }

    public void init() {
        this.enabled = isEnabled();
        this.blit = isBlit();
        System.out.println("ArmorHUD Enabled");
    }

    public static void renderHud(int pos, ItemStack item, PoseStack poseStack){
        posX = PluginPos.loadPosX(name);
        posY = PluginPos.loadPosY(name);

        if(blit) {
            GuiComponent.fill(poseStack, posX - 4, posY - 4, PluginPos.getWidth(name) + posX + 4, PluginPos.getHeight(name) + posY + 4, 0x2929292F);
        }

        if(item == null || item == ItemStack.EMPTY || item.equals(new ItemStack(Item.byBlock(Blocks.AIR)))){
            return;
        }


        Font font = Minecraft.getInstance().font;
        int posYadd = (-16 * pos) + 48;

        Minecraft.getInstance().getItemRenderer().renderGuiItem(item, posX, posY + posYadd);
        if(item.isDamageableItem()){
            float damagePercent = (100 * (item.getMaxDamage() - item.getDamageValue())) / item.getMaxDamage();
            String damageLeft = "(" + (item.getMaxDamage() - item.getDamageValue()) + "/" + item.getMaxDamage() + ")";
            string = String.format("%.2f%%", damagePercent) + " | " + damageLeft;
            drawString(poseStack, font, string, posX + 19, posY + posYadd + 4, Color.WHITE.getRGB());
        }

    }
}
