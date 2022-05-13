package fr.lezard.plugins;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;

import java.awt.*;

import static net.minecraft.client.gui.GuiComponent.drawString;

public class ArmorHudPlugin extends Plugin{
    public static boolean enabled;
    private static Minecraft minecraft;
    public static String name;
    public static int posX;
    public static int posY;

    public ArmorHudPlugin(String name) {
        super(name);
        this.name = name;
    }

    public void init() {
        minecraft = Minecraft.getInstance();
        this.enabled = isEnabled();
        System.out.println("ArmorHUD Enabled");
        posX = HudPlugin.loadPosX(plugin);
        posY = HudPlugin.loadPosY(plugin);
    }

    public static void renderHud(int pos, ItemStack item, PoseStack p_93031_){
        if(item == null || item == ItemStack.EMPTY || item.equals(new ItemStack(Item.byBlock(Blocks.AIR))))
            return;

        Font font = minecraft.font;
        int posYadd = (-16 * pos) + 48;

        minecraft.getItemRenderer().renderGuiItem(item, posX, posY + posYadd);
        if(item.isDamageableItem()){
            // double damagePercent = ((item.getMaxDamage() - item.getDamageValue()) / item.getMaxDamage() * 100);
            float damagePercent = (100 * (item.getMaxDamage() - item.getDamageValue())) / item.getMaxDamage();
            String damageLeft = "(" + (item.getMaxDamage() - item.getDamageValue()) + "/" + item.getMaxDamage() + ")";
            drawString(p_93031_, font, String.format("%.2f%%", damagePercent) + " | " + damageLeft, posX + 19, posY + posYadd + 4, Color.WHITE.getRGB());
        }

    }
}
