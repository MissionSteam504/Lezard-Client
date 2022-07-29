package fr.lezard.gui.screen.plugins.hud;

import java.util.Arrays;

import com.mojang.blaze3d.vertex.PoseStack;

import fr.lezard.Lezard;
import fr.lezard.plugins.Plugin;
import fr.lezard.plugins.hud.BlockInfoTooltipPlugin;
import fr.lezard.utils.Colors;
import fr.lezard.utils.CommonLezardVariables;
import fr.lezard.utils.Utils;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

public class BlockInfoTooltipPluginScreen extends Screen {

	public BlockInfoTooltipPluginScreen() {
		super(new TranslatableComponent(Lezard.NAMESPACE + ".plugin.blocktooltip"));
	}
	
	public boolean isPauseScreen()
    {
        return false;
    }
	
	protected void init() {
		Utils.normalPluginScreenInit(13, this, width, height);
		Plugin plugin = Lezard.plugins.get(13);
		this.addRenderableWidget(CycleButton.builder(Colors::getName).withValues(Arrays.asList(Colors.values())).withInitialValue(BlockInfoTooltipPlugin.firstColor).create(this.width / 2 - 48, this.height / 6+ 22, 96, 20, CommonLezardVariables.COLOR, (p_167441_, p_167442_) -> {
			BlockInfoTooltipPlugin.firstColor = p_167442_;
			Utils.getPlugin(plugin.getNamespace()).setColor(BlockInfoTooltipPlugin.firstColor.getLiteralName());
        }));
		this.addRenderableWidget(CycleButton.onOffBuilder(BlockInfoTooltipPlugin.rainbow).create(this.width / 2 - 48, this.height / 6 + 44, 96, 20, CommonLezardVariables.RAINBOW, (p_170168_, p_170169_) -> {
			BlockInfoTooltipPlugin.rainbow = !BlockInfoTooltipPlugin.rainbow;
			Utils.getPlugin(plugin.getNamespace()).setRainbow(BlockInfoTooltipPlugin.rainbow);
		}));
		this.addRenderableWidget(CycleButton.onOffBuilder(BlockInfoTooltipPlugin.filled).create(this.width / 2 - 48, this.height / 6 + 66, 96, 20, CommonLezardVariables.FILLED, (p_170168_, p_170169_) -> {
			BlockInfoTooltipPlugin.filled = !BlockInfoTooltipPlugin.filled;
			Utils.getPlugin(plugin.getNamespace()).setFilled(BlockInfoTooltipPlugin.filled);
		}));
		
		String sliderTitle = "Size: " + String.format("%.1f%%", BlockInfoTooltipPlugin.size).replace("%", "").replace(",0", "");
		
		this.addRenderableWidget(new AbstractSliderButton(width / 2 - 48, height / 6 + 88, 96, 20, new TextComponent(sliderTitle), BlockInfoTooltipPlugin.size/2) {

			@Override
			protected void updateMessage() {
				this.setMessage(new TextComponent("Size: " + String.format("%.1f%%", BlockInfoTooltipPlugin.size).replace("%", "").replace(",0", "")));
			}

			@Override
			protected void applyValue() {
				BlockInfoTooltipPlugin.size = (float) (this.value*2);
				Utils.getPlugin(plugin.getNamespace()).setSize(BlockInfoTooltipPlugin.size);
			}
		});
		this.addRenderableWidget(CycleButton.builder(Colors::getName).withValues(Arrays.asList(Colors.values())).withInitialValue(BlockInfoTooltipPlugin.secondColor).create(this.width / 2 - 48, this.height / 6+ 110, 96, 20, CommonLezardVariables.SECOND_COLOR, (p_167441_, p_167442_) -> {
			BlockInfoTooltipPlugin.secondColor = p_167442_;
			Utils.getPlugin(plugin.getNamespace()).setSecondColor(BlockInfoTooltipPlugin.secondColor.getLiteralName());
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

}
