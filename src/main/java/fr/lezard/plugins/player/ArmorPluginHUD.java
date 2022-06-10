package fr.lezard.plugins.player;

import com.mojang.blaze3d.vertex.PoseStack;

import fr.lezard.Lezard;
import fr.lezard.events.Event;
import fr.lezard.events.listeners.EventInGame;
import fr.lezard.events.listeners.EventStart;
import fr.lezard.gui.screen.DragScreen;
import fr.lezard.plugins.PluginHUD;
import fr.lezard.utils.FileWriterJson;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;

public class ArmorPluginHUD extends PluginHUD{
	public static int posX=0;
	public static int posY=0;
	public static int width=0, height=0;
	
	public ArmorPluginHUD() {
		super("Armor HUD", FileWriterJson.getBoolean("armor", "enabled"), Category.PLAYER, "armor", Minecraft.getInstance().options.keyArmor);
	}
	
	public void onEvent(Event<?> e) {
		if(e instanceof EventInGame) {
			PoseStack poseStack = new PoseStack();
			Minecraft mc = Minecraft.getInstance();
			
			if(isDragged() && DragScreen.plugin == this) {
				posX = DragScreen.posX;
				posY = DragScreen.posY;
			}else {
				posX = getPosX();
				posY = getPosY();
			}
			if(mc.options.renderDebug){
	            return;
	        }
			
			setWidth(120);
			setHeight(64);
			width=getWidth();
			height=getHeight();
		}
	}
	
	public static void renderFill(PoseStack poseStack) {
		if(Minecraft.getInstance().options.renderDebug){
            return;
        }
		if(isFilled()) {
            GuiComponent.fill(poseStack, posX - Lezard.GAP, posY - Lezard.GAP, width + posX + Lezard.GAP, height + posY + Lezard.GAP, Lezard.color.getRGB());
        }
	}
	
	public static void renderAmor(int pos, ItemStack item, PoseStack poseStack) {
		Font font = Minecraft.getInstance().font;
        int posYadd = (-16 * pos) + 48;
        
        if(item == null || item == ItemStack.EMPTY || item.equals(new ItemStack(Item.byBlock(Blocks.AIR)))){
            return;
        }

        Minecraft.getInstance().getItemRenderer().renderGuiItem(item, posX, posY + posYadd);
        if(item.isDamageableItem()){
            float damagePercent = (100 * (item.getMaxDamage() - item.getDamageValue())) / item.getMaxDamage();
            String damageLeft = "(" + (item.getMaxDamage() - item.getDamageValue()) + "/" + item.getMaxDamage() + ")";
            String string = String.format("%.2f%%", damagePercent) + " | " + damageLeft;
            GuiComponent.drawString(poseStack, font, string, posX + 19, posY + posYadd + 4, isRainbow() ? Lezard.rainbowText() : getColors().getRgb());
        }
	}
}
