package fr.lezard.plugins.player;

import java.util.Locale;

import com.mojang.blaze3d.vertex.PoseStack;

import fr.lezard.Lezard;
import fr.lezard.events.Event;
import fr.lezard.events.listeners.EventInGame;
import fr.lezard.events.listeners.EventStart;
import fr.lezard.gui.screen.DragScreen;
import fr.lezard.gui.screen.plugins.ArmorPluginHUDScreen;
import fr.lezard.plugins.PluginHUD;
import fr.lezard.utils.FileWriterJson;
import fr.lezard.utils.LezardOption;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;

public class ArmorPluginHUD extends PluginHUD{
	public static int posX=0;
	public static int posY=0;
	public static int width=0, height=0;
	
	public static Modes currentMode = Modes.BASIC_ITEM;
	
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
			
			if(currentMode.isBasic())
				setWidth(16);
			
			if(currentMode.haveItem()) {
				setHeight(80);
			}else {
				setHeight(64);
			}
			
			width=getWidth();
			height=getHeight();
			
			if(e.isPost()) {
				PoseStack poseStack = new PoseStack();
				ArmorPluginHUD.renderFill(poseStack);
				for(int i =0; i<Minecraft.getInstance().player.getInventory().armor.size() + (currentMode.haveItem() ? 1 : 0); i++) {
					ItemStack is;
					if(i==4) {
						is = Minecraft.getInstance().player.getMainHandItem();
					}else {
						is = Minecraft.getInstance().player.getInventory().armor.get(i);
					}
					ArmorPluginHUD.renderAmor(i, is, poseStack);
				}
			}
		}
		if(e instanceof EventStart) {
			if(!FileWriterJson.getString(getNamespace(), "mode").equalsIgnoreCase("")){
	            currentMode = Modes.valueOf(FileWriterJson.getString(getNamespace(), "mode"));
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
		LocalPlayer player = Minecraft.getInstance().player;
		if(Minecraft.getInstance().options.renderDebug){
            return;
        }
		
		ItemStack temp = new ItemStack(item.getItem(), item.getCount());
		int count = 0;
        int posYadd = (-16 * pos) + 48;
        
        if(item == player.getMainHandItem()) {
        	posYadd = 64;
        	
        	
        	for(int i=0; i<player.getInventory().getContainerSize(); i++) {
        		ItemStack is = player.getSlot(i).get();
        		if(is.getItem() == temp.getItem()) {
        			count+= is.getCount();
        		}
        	}
        }
        temp.setDamageValue(item.getDamageValue());
        temp.setCount(count);
        
        if(item == null || item == ItemStack.EMPTY || item.equals(new ItemStack(Item.byBlock(Blocks.AIR)))){
            return;
        }
        PluginHUD p =(PluginHUD) Lezard.plugins.get(0);

        Minecraft.getInstance().getItemRenderer().renderGuiItem(item, posX, posY+posYadd);
        Minecraft.getInstance().getItemRenderer().renderGuiItemDecorations(font, (item == Minecraft.getInstance().player.getMainHandItem() ? temp : item), posX, posY + posYadd);
        
        if(!currentMode.isBasic()) {
        	if(item.isDamageableItem()){
                float damagePercent = (100 * (item.getMaxDamage() - item.getDamageValue())) / item.getMaxDamage();
                String damageLeft = "(" + (item.getMaxDamage() - item.getDamageValue()) + "/" + item.getMaxDamage() + ")";
                String string = String.format("%.2f%%", damagePercent) + " | " + damageLeft;
                GuiComponent.drawString(poseStack, font, string, posX + 19, posY + posYadd + 4, p.isRainbow() ? Lezard.rainbowText() : p.getColors().getRgb());
                p.setWidth(font.width(string) + 20);
        	}
        }
	}
	
	public enum Modes{
		BASIC("BASIC", true, false),
		ADVANCED("ADVANCED", false, false),
		BASIC_ITEM("BASIC_ITEM", true, true),
		ADVANCED_ITEM("ADVANCED_ITEM", false, true),
		;
		
		private String name;
		private boolean basic;
		private boolean haveItem;
		Modes(String name, boolean basic, boolean haveItem){
			this.name = name;
			this.basic = basic;
			this.haveItem = haveItem;
		}
		
		public String getLiteralName() {
			return name;
		}
		
		public Component getName() {
			return new TranslatableComponent(Lezard.NAMESPACE + ".armor." + name.toLowerCase(Locale.ROOT));
		}
		
		public boolean isBasic() {
			return basic;
		}
		
		public boolean haveItem() {
			return haveItem;
		}
	}
}
