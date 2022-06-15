package fr.lezard.plugins.player;

import com.mojang.blaze3d.vertex.PoseStack;

import fr.lezard.Lezard;
import fr.lezard.events.Event;
import fr.lezard.events.listeners.EventInGame;
import fr.lezard.gui.screen.DragScreen;
import fr.lezard.gui.screen.plugins.ArmorPluginHUDScreen;
import fr.lezard.plugins.PluginHUD;
import fr.lezard.utils.FileWriterJson;
import fr.lezard.utils.LezardOption;
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
		super("Armor HUD", FileWriterJson.getBoolean("armor", "enabled"), Category.PLAYER, "armor", Minecraft.getInstance().options.keyArmor, new ArmorPluginHUDScreen());
	}
	
	public void onEvent(Event<?> e) {
		if(e instanceof EventInGame) {		
			if(isDragged() && DragScreen.plugin == this) {
				posX = DragScreen.posX;
				posY = DragScreen.posY;
			}else {
				posX = getPosX();
				posY = getPosY();
			}
			
			setWidth(120);
			setHeight(64);
			width=getWidth();
			height=getHeight();
		}
		if(e.isPost()) {
			PoseStack poseStack = new PoseStack();
			ArmorPluginHUD.renderFill(poseStack);
			for(int i =0; i<Minecraft.getInstance().player.getInventory().armor.size(); i++) {
				ItemStack is = Minecraft.getInstance().player.getInventory().armor.get(i);
				ArmorPluginHUD.renderAmor(i, is, poseStack);
			}
		}
	}
	
	public static void renderFill(PoseStack poseStack) {
		if(Minecraft.getInstance().options.renderDebug){
            return;
        }
		PluginHUD p =(PluginHUD) Lezard.plugins.get(0);
		if(p.isFilled()) {
            GuiComponent.fill(poseStack, posX - LezardOption.gap, posY - LezardOption.gap, width + posX + LezardOption.gap, height + posY + LezardOption.gap, Lezard.color.getRGB());
        }
	}
	
	@SuppressWarnings("deprecation")
	public static void renderAmor(int pos, ItemStack item, PoseStack poseStack) {
		Font font = Minecraft.getInstance().font;
		if(Minecraft.getInstance().options.renderDebug){
            return;
        }
        int posYadd = (-16 * pos) + 48;
        
        if(item == null || item == ItemStack.EMPTY || item.equals(new ItemStack(Item.byBlock(Blocks.AIR)))){
            return;
        }
        PluginHUD p =(PluginHUD) Lezard.plugins.get(0);

        Minecraft.getInstance().getItemRenderer().renderGuiItem(item, posX, posY + posYadd);
        if(item.isDamageableItem()){
            float damagePercent = (100 * (item.getMaxDamage() - item.getDamageValue())) / item.getMaxDamage();
            String damageLeft = "(" + (item.getMaxDamage() - item.getDamageValue()) + "/" + item.getMaxDamage() + ")";
            String string = String.format("%.2f%%", damagePercent) + " | " + damageLeft;
            GuiComponent.drawString(poseStack, font, string, posX + 19, posY + posYadd + 4, p.isRainbow() ? Lezard.rainbowText() : p.getColors().getRgb());
        }
	}
}
