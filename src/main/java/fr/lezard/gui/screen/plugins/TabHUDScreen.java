package fr.lezard.gui.screen.plugins;

import java.util.Arrays;

import com.mojang.blaze3d.vertex.PoseStack;

import fr.lezard.Lezard;
import fr.lezard.gui.screen.MainPluginScreen;
import fr.lezard.plugins.Plugin;
import fr.lezard.plugins.PluginHUD;
import fr.lezard.utils.Colors;
import fr.lezard.utils.CommonLezardVariables;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.TranslatableComponent;

public class TabHUDScreen extends Screen {

	public TabHUDScreen() {
		super(new TranslatableComponent(Lezard.NAMESPACE + ".plugin.screen"));
	}
	
	protected void init() {
		this.addRenderableWidget(new Button(this.width / 2 - 100, this.height / 6 + 168, 200, 20, CommonComponents.GUI_DONE, (p_96257_) -> {
	         this.minecraft.setScreen(new MainPluginScreen());
	      }));
		Plugin plugin = Lezard.plugins.get(12);
		PluginHUD p = (PluginHUD) plugin;
		this.addRenderableWidget(CycleButton.onOffBuilder(plugin.isEnabled()).create(this.width / 2 - 48, this.height / 6, 96, 20, CommonLezardVariables.ENABLED, (p_170168_, p_170169_) -> {
			plugin.toggle();
		}));
		this.addRenderableWidget(CycleButton.builder(Colors::getName).withValues(Arrays.asList(Colors.values())).withInitialValue(p.getColors()).create(this.width / 2 - 48, this.height / 6+ 22, 96, 20, CommonLezardVariables.COLOR, (p_167441_, p_167442_) -> {
            p.setColors(p_167442_);
        }));
		this.addRenderableWidget(CycleButton.onOffBuilder(p.isRainbow()).create(this.width / 2 - 48, this.height / 6 + 44, 96, 20, CommonLezardVariables.RAINBOW, (p_170168_, p_170169_) -> {
			p.setRainbow(!p.isRainbow());
		}));
		this.addRenderableWidget(CycleButton.onOffBuilder(p.isFilled()).create(this.width / 2 - 48, this.height / 6 + 66, 96, 20, CommonLezardVariables.FILLED, (p_170168_, p_170169_) -> {
			p.setFilled(!p.isFilled());
		}));
	}
	
	public void tick() {
		super.tick();
	}
	
	public void render(PoseStack poseStack, int mouseX, int mouseY, float p_96565_) {
		renderBackground(poseStack);
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

}
