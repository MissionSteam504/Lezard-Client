package fr.lezard.gui.screen;

import com.mojang.blaze3d.vertex.PoseStack;

import fr.lezard.Lezard;
import fr.lezard.utils.FileWriterJson;
import fr.lezard.utils.LezardOptions;
import net.minecraft.client.Option;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.OptionsList;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.TranslatableComponent;

public class LezardSettingsScreen extends Screen {
	private OptionsList list;
	private Screen lastScreen;

	public LezardSettingsScreen(Screen lastScreen) {
		super(new TranslatableComponent(Lezard.NAMESPACE + ".gui.main"));
		this.lastScreen = lastScreen;
	}
	
	public boolean isPauseScreen()
    {
        return false;
    }
	
	protected void init() {
		Option[] OPTIONS = new Option[]{LezardOptions.ALPHA_SLIDER, LezardOptions.GAP_SLIDER, LezardOptions.SHOW_ANCHOR, LezardOptions.RAINBOW_SPEED};
		this.list = new OptionsList(this.minecraft, this.width, this.height, 32, this.height - 32, 25);
		// this.list.addBig(LezardOption.ALPHA_SLIDER);
		this.list.a(OPTIONS);
		this.addWidget(this.list);
		
		this.addRenderableWidget(new Button(this.width / 2 - 100, this.height - 27, 200, 20, CommonComponents.GUI_DONE, (p_96827_) -> {
			FileWriterJson.writeJson(Lezard.NAMESPACE, "alpha", LezardOptions.alpha); 
			FileWriterJson.writeJson(Lezard.NAMESPACE, "gap", LezardOptions.gap); 
			FileWriterJson.writeJson(Lezard.NAMESPACE, "showAnchor", LezardOptions.showAnchor); 
			FileWriterJson.writeJson(Lezard.NAMESPACE, "rainbowSpeed", LezardOptions.rainbowSpeed); 
			this.minecraft.setScreen(this.lastScreen);
	      }));
	}
	
	public void tick() {
		super.tick();
	}
	
	public void render(PoseStack poseStack, int mouseX, int mouseY, float p_96565_) {
		this.list.render(poseStack, mouseX, mouseY, p_96565_);
		drawCenteredString(poseStack, this.font, this.title, this.width / 2, 15, 16777215);
        super.render(poseStack, mouseX, mouseY, p_96565_);
    }
	
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		return super.mouseClicked(mouseX, mouseY, button);
	}
	
	public boolean mouseDragged(double mouseX, double mouseY, int button, double d1, double d2) {
		return super.mouseDragged(mouseX, mouseY, button, d1, d2);
	}
	
	public boolean mouseReleased(double mouseX, double mouseY, int button) {
		return super.mouseReleased(mouseX, mouseY, button);
	}
	
	public void onClose() {
		FileWriterJson.writeJson(Lezard.NAMESPACE, "alpha", LezardOptions.alpha); 
		FileWriterJson.writeJson(Lezard.NAMESPACE, "gap", LezardOptions.gap); 
		FileWriterJson.writeJson(Lezard.NAMESPACE, "showAnchor", LezardOptions.showAnchor); 
		FileWriterJson.writeJson(Lezard.NAMESPACE, "rainbowSpeed", LezardOptions.rainbowSpeed); 
	}

}
