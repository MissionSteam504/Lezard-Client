package fr.lezard.plugins.utils;

import java.awt.Color;
import java.util.List;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.vertex.PoseStack;

import fr.lezard.Lezard;
import fr.lezard.events.Event;
import fr.lezard.events.listeners.EventInGame;
import fr.lezard.events.listeners.EventKey;
import fr.lezard.gui.screen.DragScreen;
import fr.lezard.gui.screen.plugins.utils.TabHUDScreen;
import fr.lezard.plugins.Plugin;
import fr.lezard.plugins.PluginHUD;
import fr.lezard.utils.FileWriterJson;
import fr.lezard.utils.LezardOptions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.network.chat.TranslatableComponent;

public class TabHUD extends PluginHUD{
	public int currentTab;
	public boolean expanded;

	public TabHUD() {
		super("Tab HUD", FileWriterJson.getBoolean("tab", "enabled"), Category.UTILS, "tab", Minecraft.getInstance().options.keyTabHud, new TabHUDScreen());
	}
	
	public void onEvent(Event<?> e) {
		if(Minecraft.getInstance().options.renderDebug){
            return;
        }
		if(e instanceof EventInGame) {
			PoseStack poseStack = new PoseStack();
			Minecraft mc = Minecraft.getInstance();
			Font font = mc.font;
			
			String cname ="";
			
			int posX = 0;
			int posY = 0;
			float size = getSize();
			
			if(isDragged() && DragScreen.plugin == this) {
				posX = (int) DragScreen.posX;
				posY = (int) DragScreen.posY;
			}else {
				posX = getPosX();
				posY = getPosY();
			}
			
			if(isFilled()) {
	            GuiComponent.fill(poseStack, posX - LezardOptions.gap, posY - LezardOptions.gap, getWidth() + posX + LezardOptions.gap, getHeight() + posY + LezardOptions.gap, Lezard.color.getRGB());
	        }
			
			for(Plugin.Category c : Plugin.Category.values()) {
				String temp=new TranslatableComponent(Lezard.NAMESPACE + ".category." + c.getNamespace()).getString();
				if(font.width(temp)>font.width(cname)) {
					cname=temp;
				}
			}
			
			int categorysize = font.width(cname)+11;
			
			poseStack.pushPose();
			poseStack.scale(size, size, 1);
			GuiComponent.fill(poseStack, posX*(1/size), posY*(1/size), (posX + categorysize*size)*(1/size), (posY + Plugin.Category.values().length*size * 16 +1)*(1/size), 0x5f000000);
			GuiComponent.fill(poseStack, (posX + 2)*(1/size), (posY+3 + currentTab*size * 16)*(1/size), (posX+2 + categorysize*size - 4)*(1/size), (posY+3 + currentTab*size * 16 + 12*size)*(1/size), expanded ? Color.TRANSLUCENT : (isRainbow() ? Lezard.rainbowText() : getColors().getRgb()));
			
			int count = 0;
			for(Category c : Plugin.Category.values()) {
				GuiComponent.drawString(poseStack, font, new TranslatableComponent(Lezard.NAMESPACE + ".category." + c.getNamespace()).getString(), (posX+6)*(1/size), (posY+5 + (count*16)*size)*(1/size), -1);
				count++;
			}
			poseStack.popPose();
			
			setWidth(categorysize*size);
			setHeight((Plugin.Category.values().length * 16 +1)*size);
			
			if(expanded) {
				String name = "";
				Category current = Plugin.Category.values()[currentTab];
				List<Plugin> plugins = Lezard.getPluginsByCategory(current);
				
				if(plugins.contains(this)) {
					plugins.remove(this);
				}
				
				if(plugins.size() == 0)
					return;
				
				count = 0;
				
				for(Plugin p : plugins) {
					String temp=new TranslatableComponent(Lezard.NAMESPACE + ".plugin." + p.getNamespace()).getString();
					if(font.width(temp)>font.width(name)) {
						name=temp;
					}
				}
				
				int pluginsize = font.width(name) + 10;
				
				poseStack.pushPose();
				poseStack.scale(size, size, 1);
				GuiComponent.fill(poseStack, (posX + categorysize*size +1)*(1/size), posY*(1/size), (posX + categorysize*size + pluginsize*size)*(1/size), (posY + plugins.size()*size * 16 +1)*(1/size), 0x5f000000);
				GuiComponent.fill(poseStack, (posX + categorysize*size + 3)*(1/size), (posY+3 + current.pluginIndex*size * 16)*(1/size), (posX+2 + categorysize*size + pluginsize*size - 4)*(1/size), (posY+3 + current.pluginIndex*size * 16 + 12*size)*(1/size), plugins.get(current.pluginIndex).isEnabled() ? 0xff0060ff : Color.RED.getRGB());
				
				count = 0;
				for(Plugin p : plugins) {
					GuiComponent.drawString(poseStack, font, new TranslatableComponent(Lezard.NAMESPACE + ".plugin." + p.getNamespace()).getString(), (posX+categorysize*size+6)*(1/size), (posY+5 + count*size*16)*(1/size), -1);
					count++;
				}
				
				poseStack.popPose();
				
				setWidth((categorysize + pluginsize)*size);
				setHeight(getHeight() < plugins.size()*size * 16 +1 ? plugins.size()*size * 16 +1 : getHeight());
			}
		}
		if(e instanceof EventKey e2) {
			int code = e2.getKey();
			Category current = Plugin.Category.values()[currentTab];
			List<Plugin> plugins = Lezard.getPluginsByCategory(current);
			
			if(plugins.contains(this)) {
				plugins.remove(this);
			}
			
			if(code == InputConstants.KEY_UP) {
				if(expanded) {
					if(current.pluginIndex <= 0) {
						current.pluginIndex = plugins.size();
					}
					current.pluginIndex--;
				}else {
					if(currentTab <= 0) {
						currentTab = Plugin.Category.values().length;
					}
					currentTab--;
				}
			}
			
			if(code == InputConstants.KEY_DOWN) {
				if(expanded) {
					if(current.pluginIndex >= plugins.size() - 1) {
						current.pluginIndex = -1;
					}
					current.pluginIndex++;
				}else {
					if(currentTab >= Plugin.Category.values().length - 1) {
						currentTab = -1;
					}
					currentTab++;
				}
			}
			
			if(code == InputConstants.KEY_RIGHT) {
				if(expanded) {
					plugins.get(current.pluginIndex).toggle();
				}else {
					if(plugins.size() != 0) {
						expanded = true;
					}
				}
			}
			if(code == InputConstants.KEY_LEFT) {
				expanded = false;
			}
		}
	}
}
