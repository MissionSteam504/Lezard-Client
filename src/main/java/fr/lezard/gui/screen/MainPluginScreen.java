package fr.lezard.gui.screen;

import com.mojang.blaze3d.vertex.PoseStack;

import fr.lezard.Lezard;
import fr.lezard.plugins.Plugin;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.TranslatableComponent;

public class MainPluginScreen extends Screen {
	public static int scrolling;

	public MainPluginScreen() {
		super(new TranslatableComponent(Lezard.NAMESPACE + ".gui.plugin"));
	}
	
	protected void init() {
		this.addRenderableWidget(new Button(this.width / 2 - 100, this.height / 6 + 168, 200, 20, CommonComponents.GUI_DONE, (p_96257_) -> {
	         this.minecraft.setScreen(new DragScreen());
	      }));
		int count = 0;
		for(Plugin p : Lezard.plugins) {
			if(scrolling > 0 ) {
				scrolling =0;
			}else if(scrolling < -44) {
				scrolling = -44;
			}
			int x = this.width / 2 - 48;
			int y = this.height / 6 + count*24 + scrolling*24;
			count++;
			if(y<40 || y + 20>200) {
				continue;
			}
			this.addRenderableWidget(new Button(x, y, 96, 20, new TranslatableComponent(Lezard.NAMESPACE + ".plugin." + p.getNamespace()), (p_96257_) -> {
		         this.minecraft.setScreen(p.getScreen());
		      }));
		}
	}
	
	public void tick() {
		super.tick();
	}
	
	public void render(PoseStack poseStack, int mouseX, int mouseY, float p_96565_) {
		renderBackground(poseStack);
		drawCenteredString(poseStack, this.font, this.title, this.width / 2, 15, 16777215);
		float temp=3.11f;
		int divider=44;
		fill(poseStack, this.width/2+50, this.height / 6, this.width/2+50+3, this.height/6 + 140, 0x70000000);
		fill(poseStack, this.width/2+50, this.height / 6 - scrolling*temp, this.width/2+50+3, this.height/6+140/divider - scrolling*temp, 0x75ffffff);
		
		Minecraft.getInstance().setScreen(this);
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
	
	public static void scroll(double x, double y, double direction) {
		if(direction<0)
			scrolling--;
		if(direction>0){
			scrolling++;
		}
	}

}
