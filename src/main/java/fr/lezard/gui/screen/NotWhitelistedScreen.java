package fr.lezard.gui.screen;

import com.mojang.blaze3d.vertex.PoseStack;

import fr.lezard.Lezard;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TranslatableComponent;

public class NotWhitelistedScreen extends Screen {

	public NotWhitelistedScreen() {
		super(new TranslatableComponent(Lezard.NAMESPACE + ".gui.screen.notWhitelited"));
	}
	
	protected void init() {
		
	}
	
	public void render(PoseStack poseStack, int mouseX, int mouseY, float p_96565_) {
		String[] strings = {"Thanks for playing the Lezard Client!", "Actually the client is in beta testing", "If you want to be a beta-tester, join our discord", "If you dont, please try again later"};
		Font font = Minecraft.getInstance().font;
		int line=0;
		this.renderBackground(poseStack);
		for(String s : strings) {
			drawCenteredString(poseStack, font, s, width/2, this.height/2-((font.lineHeight*strings.length)/2)+line*12, -1);
			line++;
		}
		super.render(poseStack, mouseX, mouseY, p_96565_);
	}
}
