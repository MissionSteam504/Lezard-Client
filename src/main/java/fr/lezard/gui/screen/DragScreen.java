package fr.lezard.gui.screen;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;

import fr.lezard.Lezard;
import fr.lezard.plugins.PluginHUD;
import fr.lezard.utils.FileWriterJson;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TranslatableComponent;

public class DragScreen extends Screen {
    private static final List<Integer> relativeX = new ArrayList<>();
    private static final List<Integer> relativeY = new ArrayList<>();
    public static int posX;
    public static int posY;
	
	public static PluginHUD plugin = null;

	public DragScreen() {
		super(new TranslatableComponent(Lezard.NAMESPACE + ".gui.drag"));
	}
	
	protected void init() {
		
	}
	
	public void tick() {
		super.tick();
	}
	
	public void render(PoseStack poseStack, int mouseX, int mouseY, float p_96565_) {
		drawCenteredString(poseStack, this.font, this.title, this.width / 2, 15, 16777215);
        super.render(poseStack, mouseX, mouseY, p_96565_);
    }
	
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		relativeX.clear();
        relativeY.clear();
        for(PluginHUD plugin : Lezard.pluginsHUD){
            relativeX.add((int) (mouseX - FileWriterJson.getInt(plugin.getNamespace(), "posX")));
            relativeY.add((int) (mouseY - FileWriterJson.getInt(plugin.getNamespace(), "posY")));
        }
		return super.mouseClicked(mouseX, mouseY, button);
	}
	
	public boolean mouseDragged(double mouseX, double mouseY, int p_94701_, double p_94702_, double p_94703_) {
		for(PluginHUD currentPlugin : Lezard.pluginsHUD){
			if(relativeX.size() != 0 || relativeY.size() != 0){
				int relX = relativeX.get(Lezard.pluginsHUD.indexOf(currentPlugin));
				int relY = relativeY.get(Lezard.pluginsHUD.indexOf(currentPlugin));
	
				if(relX >= -Lezard.GAP && relX <= currentPlugin.getWidth() + Lezard.GAP && relY >= -Lezard.GAP && relY <= currentPlugin.getHeight() + Lezard.GAP && currentPlugin.isEnabled()){
					posX=(int) (mouseX - relX);
		            posY=(int) (mouseY - relY);
		            currentPlugin.setDragged(true);
		            plugin=currentPlugin;
		        }
		     }
		 }
		 return super.mouseDragged(mouseX, mouseY, p_94701_, p_94702_, p_94703_);
	}
	 
	public boolean mouseReleased(double p_94722_, double p_94723_, int p_94724_) {
		if(plugin!=null){
			if(plugin.isEnabled()) {
				FileWriterJson.writeJson(plugin.getNamespace(), "posX", posX);
				FileWriterJson.writeJson(plugin.getNamespace(), "posY", posY);
				plugin.setPosX(posX);
				plugin.setPosY(posY);
			}
		}
		for(PluginHUD plugin : Lezard.pluginsHUD){
			plugin.setDragged(false);
	    }
		return super.mouseReleased(p_94722_, p_94723_, p_94724_);
	}
}
