package fr.lezard.plugins.player;

import java.util.Locale;

import com.mojang.blaze3d.vertex.PoseStack;

import fr.lezard.Lezard;
import fr.lezard.events.Event;
import fr.lezard.events.listeners.EventInGame;
import fr.lezard.events.listeners.EventStart;
import fr.lezard.gui.screen.DragScreen;
import fr.lezard.gui.screen.plugins.player.ArmorPluginHUDScreen;
import fr.lezard.plugins.PluginHUD;
import fr.lezard.utils.FileWriterJson;
import fr.lezard.utils.LezardOptions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

public class ArmorPluginHUD extends PluginHUD{
	public static int posX=0;
	public static int posY=0;
	public static float width=0, height=0;
	
	public static Modes currentMode = Modes.BASIC_ITEM;
	
	public ArmorPluginHUD() {
		super("Armor HUD", FileWriterJson.getBoolean("armor", "enabled"), Category.PLAYER, "armor", Minecraft.getInstance().options.keyArmor, new ArmorPluginHUDScreen());
	}
	
	public void onEvent(Event<?> e) {
		if(e instanceof EventInGame) {
			float size = getSize();
			if(isDragged() && DragScreen.plugin == this) {
				posX = (int) DragScreen.posX;
				posY = (int) DragScreen.posY;
			}else {
				posX = getPosX();
				posY = getPosY();
			}
			
			if(currentMode.isBasic())
				setWidth(16*size);
			
			if(currentMode.haveItem()) {
				setHeight(80*size);
			}else {
				setHeight(64*size);
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
		boolean haveArmor = false;
		boolean haveItem = false;
		for(ItemStack i : Minecraft.getInstance().player.getInventory().armor) {
			if(i.getItem() != null && i.getItem() != Items.AIR) {
				if(!haveArmor) haveArmor = true;
			}
		}
		if(!haveItem) {
			if(Minecraft.getInstance().player.getMainHandItem().getItem() != null && Minecraft.getInstance().player.getMainHandItem().getItem() != Items.AIR) {
				haveItem = true;
			}
		}
		
		PluginHUD p =(PluginHUD) Lezard.plugins.get(0);
		if(p.isFilled() && (haveArmor || haveItem)) {
			if(!haveArmor && Minecraft.getInstance().player.getMainHandItem().getItem() != Items.AIR){
				String txt = "";
				if(Minecraft.getInstance().player.getMainHandItem().isDamageableItem()) {
					ItemStack item = Minecraft.getInstance().player.getMainHandItem();
					float damagePercent = (100 * (item.getMaxDamage() - item.getDamageValue())) / item.getMaxDamage();
	                String damageLeft = "(" + (item.getMaxDamage() - item.getDamageValue()) + "/" + item.getMaxDamage() + ")";
	                txt = String.format("%.0f%%", damagePercent) + " | " + damageLeft;
				}else {
					txt = Minecraft.getInstance().player.getMainHandItem().getHoverName().getString();
				}
				width=(16+Minecraft.getInstance().font.width(txt) + LezardOptions.gap+2)*p.getSize();
			}
			else if(!haveArmor)
				width=16*p.getSize();
			
            GuiComponent.fill(poseStack, posX - LezardOptions.gap, posY - LezardOptions.gap, width + posX + LezardOptions.gap, height + posY + LezardOptions.gap, Lezard.color.getRGB());
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
        
        boolean rightSide = false;
        
        if(posX+width/2 > Minecraft.getInstance().getWindow().getGuiScaledWidth()/2) {
        	rightSide=true;
        }else {
        	rightSide=false;
        }
        
        int damageWidth = 0;
        
        if(!currentMode.isBasic()) {
        	String name = "";
        	if(item.isDamageableItem()){
                float damagePercent = (100 * (item.getMaxDamage() - item.getDamageValue())) / item.getMaxDamage();
                String damageLeft = "(" + (item.getMaxDamage() - item.getDamageValue()) + "/" + item.getMaxDamage() + ")";
                name = String.format("%.0f%%", damagePercent) + " | " + damageLeft;
                damageWidth = font.width(name);
                poseStack.pushPose();
                poseStack.translate(p.getSize(), p.getSize(), 1);
                GuiComponent.drawString(poseStack, font, name, rightSide ? posX : posX + 20, posY + posYadd + 4, p.isRainbow() ? Lezard.rainbowText() : p.getColors().getRgb());
                poseStack.popPose();
                p.setWidth((font.width(name) + 20)*p.getSize());
        	}else {
        		if(item.getItem() != Items.AIR) {
        			name = item.getHoverName().getString();
        			int textSize = font.width(name);
        			poseStack.pushPose();
                    poseStack.translate(p.getSize(), p.getSize(), 1);
        			GuiComponent.drawString(poseStack, font, name, rightSide ? posX + width- textSize-20: posX + 20, posY + posYadd + 4, p.isRainbow() ? Lezard.rainbowText() : p.getColors().getRgb());
        			poseStack.popPose();
        			if(p.getWidth() < font.width(name) + 20) p.setWidth((font.width(name) + 20)*p.getSize());
        		}
        	}
        }
        
        boolean damageable = item.isDamageableItem();
        
        float itemX = posX;
        
        if(!damageable && rightSide && !currentMode.isBasic()) {
        	itemX = posX+width-20;
        }
        poseStack.pushPose();
        poseStack.scale(p.getSize(), p.getSize(), 1);
        Minecraft.getInstance().getItemRenderer().renderGuiItem(item, rightSide && !currentMode.isBasic() ? itemX + damageWidth + 2 : itemX, posY+posYadd);
        Minecraft.getInstance().getItemRenderer().renderGuiItemDecorations(font, (item == Minecraft.getInstance().player.getMainHandItem() ? temp : item), rightSide && !currentMode.isBasic() ? posX + damageWidth + 2 : posX, posY + posYadd);
        poseStack.popPose();
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
