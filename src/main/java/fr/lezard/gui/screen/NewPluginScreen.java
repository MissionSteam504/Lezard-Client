package fr.lezard.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import fr.lezard.Lezard;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;

public class NewPluginScreen extends Screen {
	
	ResourceLocation HUD = new ResourceLocation("lezard/gui/plugins_background.png");

	public NewPluginScreen() {
		super(new TranslatableComponent(Lezard.NAMESPACE + ".gui.plugin"));
	}
	
	protected void init() {
		
	}
	
	public void tick() {
		super.tick();
	}
	
	public void render(PoseStack poseStack, int mouseX, int mouseY, float p_96565_) {
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.enableBlend();
		RenderSystem.setShaderTexture(0, this.HUD);
		// blit(poseStack, 0, 0, 0, 0, 256, 256, 256, 256);
		blit(poseStack, 0, 0, 0, 0, width, height, width, height);
		
		drawCenteredString(poseStack, this.font, this.title, this.width / 2, 15, 16777215);
        super.render(poseStack, mouseX, mouseY, p_96565_);
    }
	
	/*public boolean mouseClicked(double mouseX, double mouseY, int button) {
		return super.mouseClicked(mouseX, mouseY, button);
	}
	
	public boolean mouseDragged(double mouseX, double mouseY, int button, double d1, double d2) {
		return super.mouseDragged(mouseX, mouseY, button, d1, d2);
	}
	
	public boolean mouseReleased(double mouseX, double mouseY, int button) {
		return super.mouseReleased(mouseX, mouseY, button);
	}*/
	
	public boolean isPauseScreen()
    {
        return false;
    }

}
