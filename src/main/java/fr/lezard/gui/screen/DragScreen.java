package fr.lezard.gui.screen;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;

import fr.lezard.Lezard;
import fr.lezard.plugins.PluginHUD;
import fr.lezard.utils.FileWriterJson;
import fr.lezard.utils.LezardOptions;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;

public class DragScreen extends Screen {
	private static final ResourceLocation SETTINGS_TEXTURE = new ResourceLocation("lezard/gui/settings.png");
	private static final ResourceLocation PLUGINS_TEXTURE = new ResourceLocation("lezard/gui/plugins.png");
	
    private static final List<Integer> relativeX = new ArrayList<>();
    private static final List<Integer> relativeY = new ArrayList<>();
    public static float posX, posY;
	
	public static PluginHUD plugin = null;

	public DragScreen() {
		super(new TranslatableComponent(Lezard.NAMESPACE + ".gui.drag"));
	}
	
	public boolean isPauseScreen()
    {
        return false;
    }
	
	protected void init() {
		this.addRenderableWidget(new ImageButton(this.width / 2 -22, this.height-45, 20, 20, 0, 0, 20, SETTINGS_TEXTURE, 32, 64, (p_96784_) -> {
	         this.minecraft.setScreen(new LezardSettingsScreen(this));
	      }));
		this.addRenderableWidget(new ImageButton(this.width / 2 +2, this.height-45, 20, 20, 0, 0, 20, PLUGINS_TEXTURE, 32, 64, (p_96784_) -> {
	         this.minecraft.setScreen(new MainPluginScreen());
	      }));
	}
	
	public void tick() {
		super.tick();
	}
	
	public void render(PoseStack poseStack, int mouseX, int mouseY, float p_96565_) {
		drawCenteredString(poseStack, this.font, this.title, this.width / 2, 15, 16777215);
		if(LezardOptions.showAnchor) {
			fill(poseStack, this.width/2-0.5f, 0, this.width/2+0.5f, this.height, 0x60ff0000);
			fill(poseStack, this.width/4-0.5f, 0, this.width/4+0.5f, this.height, 0x60ff0000);
			fill(poseStack, (this.width/4)*3-0.5f, 0, (this.width/4)*3+0.5f, this.height, 0x60ff0000);
			fill(poseStack, 0, this.height/2-0.5f, this.width, this.height/2+0.5f, 0x60ff0000);
			fill(poseStack, 0, this.height/4-0.5f, this.width, this.height/4+0.5f, 0x60ff0000);
			fill(poseStack, 0, (this.height/4)*3-0.5f, this.width, (this.height/4)*3+0.5f, 0x60ff0000);
			for(PluginHUD p : Lezard.pluginsHUD) {
				if(p.isEnabled()) {
					if(plugin != null) {
						if(plugin != p) {
							fill(poseStack, p.getPosX() - LezardOptions.gap+0.5f, 0, p.getPosX() - LezardOptions.gap-0.5f, this.height, 0x600000ff);
							fill(poseStack, p.getPosX() + p.getWidth() + LezardOptions.gap+0.5f, 0, p.getPosX() + p.getWidth() + LezardOptions.gap-0.5f, this.height, 0x600000ff);
							fill(poseStack, 0, p.getPosY() - LezardOptions.gap-0.5f, this.width, p.getPosY() - LezardOptions.gap+0.5f, 0x600000ff);
							fill(poseStack, 0, p.getPosY() + p.getHeight() + LezardOptions.gap-0.5f, this.width, p.getPosY() + p.getHeight() + LezardOptions.gap+0.5f, 0x600000ff);
							
							fill(poseStack, p.getPosX() + p.getWidth()/2 + 0.5f, 0, p.getPosX() + p.getWidth()/2 - 0.5f, this.height, 0x6000ff00);
							fill(poseStack, 0, p.getPosY() + p.getHeight()/2 + 0.5f, this.width, p.getPosY() + p.getHeight()/2 - 0.5f, 0x6000ff00);
						}
					}
				}
			}
		}
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
	
	public boolean mouseDragged(double mouseX, double mouseY, int button, double moveX, double moveY) {
		for(PluginHUD currentPlugin : Lezard.pluginsHUD){
			if(currentPlugin.isEnabled()) {
				if(relativeX.size() != 0 || relativeY.size() != 0){
					int relX = relativeX.get(Lezard.pluginsHUD.indexOf(currentPlugin));
					int relY = relativeY.get(Lezard.pluginsHUD.indexOf(currentPlugin));
					int anchor = 3;
		
					if(relX >= -LezardOptions.gap && relX <= currentPlugin.getWidth() + LezardOptions.gap && relY >= -LezardOptions.gap && relY <= currentPlugin.getHeight() + LezardOptions.gap && currentPlugin.isEnabled()){
						posX=(int) (mouseX - relX);
			            posY=(int) (mouseY - relY);
			            currentPlugin.setDragged(true);
			         
			            if(posX+currentPlugin.getWidth()/2 >= this.width/2 -anchor && posX+currentPlugin.getWidth()/2 <= this.width/2 +anchor) {
				            posX=this.width/2-currentPlugin.getWidth()/2;
				        }
				        if(posY+currentPlugin.getHeight()/2 >= this.height/2 -anchor && posY+currentPlugin.getHeight()/2 <= this.height/2 + anchor) {
				            posY=this.height/2-currentPlugin.getHeight()/2;
				        }
				            
				        if(posX+currentPlugin.getWidth()/2 >= this.width/4 -anchor && posX+currentPlugin.getWidth()/2 <= this.width/4 +anchor) {
				        	posX=this.width/4-currentPlugin.getWidth()/2;
				        }
				        if(posX+currentPlugin.getWidth()/2 >= (this.width/4)*3 -anchor && posX+currentPlugin.getWidth()/2 <= (this.width/4)*3 +anchor) {
				          	posX=(this.width/4)*3-currentPlugin.getWidth()/2;
				        }
				            
				        if(posY+currentPlugin.getHeight()/2 >= this.height/4 -anchor && posY+currentPlugin.getHeight()/2 <= this.height/4 +anchor) {
				          	posY=this.height/4-currentPlugin.getHeight()/2;
				        }
				        if(posY+currentPlugin.getHeight()/2 >= (this.height/4)*3 -anchor && posY+currentPlugin.getHeight()/2 <= (this.height/4)*3 +anchor) {
				           	posY=(this.height/4)*3-currentPlugin.getHeight()/2;
				        }
				        
				        for(PluginHUD p : Lezard.pluginsHUD) {
				        	if(p.isEnabled()) {
				        		if(p != currentPlugin) {
				        			// Check if posX are the same
				        			if(posX >= p.getPosX()-anchor && posX <= p.getPosX()+anchor) {
							        	posX=p.getPosX();
							        }
					        		if(posX >= p.getPosX()+p.getWidth()+LezardOptions.gap-anchor && posX <= p.getPosX()+p.getWidth()+LezardOptions.gap+anchor) {
							        	posX=p.getPosX()+p.getWidth()+LezardOptions.gap*2;
							        }
					        		if(posX+currentPlugin.getWidth()+LezardOptions.gap >= p.getPosX()+p.getWidth()+LezardOptions.gap-anchor && posX+currentPlugin.getWidth()+LezardOptions.gap <= p.getPosX()+p.getWidth()+LezardOptions.gap+anchor) {
					        			posX=p.getPosX()+p.getWidth()-currentPlugin.getWidth();
					        		}
					        		if(posX+currentPlugin.getWidth()+LezardOptions.gap >= p.getPosX()-LezardOptions.gap-anchor && posX+currentPlugin.getWidth()+LezardOptions.gap <= p.getPosX()-LezardOptions.gap+anchor) {
					        			posX=p.getPosX()-(LezardOptions.gap*2)-currentPlugin.getWidth();
					        		}
					        		
					        		// Check if posY are the same
					        		if(posY >= p.getPosY() -anchor && posY <= p.getPosY()+anchor) {
					        			posY=p.getPosY();
					        		}
					        		if(posY+currentPlugin.getHeight()+LezardOptions.gap >= p.getPosY()-LezardOptions.gap-anchor && posY+currentPlugin.getHeight()+LezardOptions.gap <= p.getPosY()-LezardOptions.gap+anchor) {
					        			posY=p.getPosY()-currentPlugin.getHeight()-LezardOptions.gap*2;
					        		}
					        		if(posY >= p.getPosY()+p.getHeight()+LezardOptions.gap-anchor && posY-LezardOptions.gap <= p.getPosY()+p.getHeight()+LezardOptions.gap+anchor) {
					        			posY=p.getPosY()+p.getHeight()+LezardOptions.gap*2;
					        		}
					        		if(posY+currentPlugin.getHeight()+LezardOptions.gap >=p.getPosY()+p.getHeight()+LezardOptions.gap-anchor && posY+currentPlugin.getHeight()+LezardOptions.gap <=p.getPosY()+p.getHeight()+LezardOptions.gap+anchor) {
					        			posY=p.getPosY()+p.getHeight()-currentPlugin.getHeight();
					        		}
					        		
					        		// Check center lines ( X and Y)
					        		// X
					        		if(posX+currentPlugin.getWidth()/2 >= p.getPosX()+p.getWidth()/2-anchor && posX+currentPlugin.getWidth()/2 <= p.getPosX()+p.getWidth()/2+anchor) {
					        			posX=p.getPosX()+p.getWidth()/2-currentPlugin.getWidth()/2;
					        		}
					        		//Y
					        		if(posY+currentPlugin.getHeight()/2 >= p.getPosY()+p.getHeight()/2-anchor && posY+currentPlugin.getHeight()/2 <= p.getPosY()+p.getHeight()/2+anchor) {
					        			posY=p.getPosY()+p.getHeight()/2-currentPlugin.getHeight()/2;
					        		}
				        		}
				        	}
				        }
				        
				        plugin=currentPlugin;
			        }
			     }
			}
		 }
		 return super.mouseDragged(mouseX, mouseY, button, moveX, moveY);
	}
	 
	public boolean mouseReleased(double p_94722_, double p_94723_, int p_94724_) {
		if(plugin!=null){
			if(plugin.isEnabled()) {
				plugin.setPosX((int)posX);
				plugin.setPosY((int)posY);
			}
		}
		for(PluginHUD plugin : Lezard.pluginsHUD){
			plugin.setDragged(false);
	    }
		for(PluginHUD p : Lezard.pluginsHUD) {
			if(!p.isEnabled()) {
				plugin=p;
				break;
			}
		}
		return super.mouseReleased(p_94722_, p_94723_, p_94724_);
	}
}
