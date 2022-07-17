package fr.lezard.gui.screen;


import java.awt.Color;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.mojang.realmsclient.RealmsMainScreen;

import fr.lezard.Lezard;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.OptionsScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.client.gui.screens.worldselection.SelectWorldScreen;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class MainMenu extends Screen {
	
	private String splash;
	
	private ResourceLocation BACKGROUND = new ResourceLocation(Lezard.NAMESPACE + "/gui/background.png");
	PoseStack poseStack;

	public MainMenu() {
		super(new TranslatableComponent(Lezard.NAMESPACE + ".gui.mainmenu"));
	}
	
	protected void init() {
		if(splash==null) {	
			splash = this.minecraft.getSplashManager().getSplash();
		}
	}
	
	public void tick() {
		super.tick();
	}
	 	
	public boolean shouldCloseOnEsc()
    {
        return false;
    }
	
	public boolean isPauseScreen()
    {
        return false;
    }
	
	public void render(PoseStack poseStack, int mouseX, int mouseY, float p_96565_) {
		this.poseStack = poseStack;
		
		RenderSystem.setShaderTexture(0, this.BACKGROUND);
		blit(poseStack, 0, 0, 0, 0, width, height, width, height);
		
		this.fillGradient(poseStack, 0, height-100, width, height, 0x00000000, 0xff000000);
		
		String[] buttons = {"Singleplayer", "Multiplayer", "Realms", "Settings", "Quit"};
		int count = 0;
		
		for(String name : buttons) {
			int x = (width/buttons.length) * count + (width/buttons.length)/2 + 8 - font.width(name)/2;
			int y = height - 20;
			
			boolean hovered = mouseX >= x && mouseY >= y && mouseX < x + font.width(name) && mouseY < y + font.lineHeight;
			
			drawCenteredString(poseStack, font, name, x + font.width(name)/2, y, hovered && name.equals("Quit") ? Color.RED.getRGB() : hovered ? 0xff0090ff : -1);
			
			count++;
		}
		
		// System.out.println(Lezard.globalSettings);
		
		poseStack.pushPose();
		poseStack.translate(width/2f, height/2f, 0);
		poseStack.scale(3, 3, 1);
		poseStack.translate(-(width/2f), -(height/2), 0);
		drawCenteredString(poseStack, font, Lezard.NAME, width/2, height/2 - font.lineHeight/2, -1);
		poseStack.popPose();
		
		if(Lezard.globalSettings != null){
			if(!Lezard.globalSettings.getLatestVersion().equalsIgnoreCase(Lezard.VERSION)) {
				drawCenteredString(poseStack, font, "Outdated Client!", width/2, 20-font.lineHeight/2, 0xffff3030);
			}
		}
		
		poseStack.pushPose();
		poseStack.translate((double)(this.width / 2 + 90), this.height/2, 0.0D);
		poseStack.mulPose(Vector3f.ZP.rotationDegrees(-20.0F));
		float f2 = 1.2F - Mth.abs(Mth.sin((float)(Util.getMillis() % 1000L) / 1000.0F * ((float)Math.PI * 2F)) * 0.1F);
        f2 = f2 * 100.0F / (float)(this.font.width(this.splash) + 32);
        poseStack.scale(f2, f2, f2);
        drawCenteredString(poseStack, this.font, this.splash, 12, 16, 16776960);
        poseStack.popPose();
      
        if(Lezard.globalSettings != null) {
        	if(Lezard.ban) {
        		Minecraft.getInstance().setScreen(new BannedScreen());
        		return;
        	}
        	if(!Lezard.isWhitelisted && Lezard.globalSettings.isWhitelisted()) {
    			Minecraft.getInstance().setScreen(new NotWhitelistedScreen());
    			return;
    		}
        }
		
        super.render(poseStack, mouseX, mouseY, p_96565_);
    }
	
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		String[] buttons = {"Singleplayer", "Multiplayer", "Realms", "Settings", "Quit"};
		int count = 0;
		for(String s : buttons) {
			int x = (width/buttons.length) * count + (width/buttons.length)/2 + 8 - font.width(s)/2;
			int y = height - 20;
			
			if(mouseX >= x && mouseY >= y && mouseX < x + font.width(s) && mouseY < y + font.lineHeight) {
				switch(s) {
				case "Singleplayer":
					Minecraft.getInstance().setScreen(new SelectWorldScreen(this));
					break;
				case "Multiplayer":
					Minecraft.getInstance().setScreen(new JoinMultiplayerScreen(this));
					break;
				case "Realms":
					Minecraft.getInstance().setScreen(new RealmsMainScreen(this));
					break;
				case "Settings":
					Minecraft.getInstance().setScreen(new OptionsScreen(this, minecraft.options));
					break;
				case "Quit":
					minecraft.stop();
					break;
				}
			}
			
			count++;
		}
		return super.mouseClicked(mouseX, mouseY, button);
	}

}
