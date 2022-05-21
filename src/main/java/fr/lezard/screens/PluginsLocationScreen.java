package fr.lezard.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import fr.lezard.PluginsManager;
import fr.lezard.plugins.*;
import fr.lezard.screens.plugins.MainPluginsScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.List;

public class PluginsLocationScreen extends Screen {
    public static boolean dragMenu;
    public static boolean fakeDrag = false;
    private static final List<Integer> relativeX = new ArrayList<>();
    private static final List<Integer> relativeY = new ArrayList<>();
    public static int tempX;
    public static int tempY;
    public static HudPlugin tempPlugin = null;

    private static int gap = 4;

    public PluginsLocationScreen() {
        super(new TranslatableComponent("lezard.gui.plugins.title"));
    }

    protected void init() {
        this.createPluginsMenu();
    }

    private void createPluginsMenu() {
        this.addRenderableWidget(new Button(this.width / 2 - 49, this.height / 4 + 24 -16, 98, 20, new TranslatableComponent("lezard.plugins.edit"), (p_96335_) -> {
            Minecraft.getInstance().setScreen(new MainPluginsScreen());
            for(HudPlugin plugin : PluginsManager.hudPlugins){
                plugin.updatePos();
            }
        }));
        this.addRenderableWidget(new Button(this.width / 2 - 49, this.height / 4 + 48 -16, 98, 20, new TranslatableComponent("lezard.goBack"), (p_96335_) -> {
            Minecraft.getInstance().setScreen(new LezardScreen());
            for(HudPlugin plugin : PluginsManager.hudPlugins){
                plugin.updatePos();
            }
        }));
    }

    public void tick() {
        super.tick();
    }
    public void render(PoseStack poseStack, int mouseX, int mouseY, float p_96565_) {
        for(HudPlugin plugin : PluginsManager.hudPlugins){
            String pluginName = PluginsManager.pluginsName.get(PluginsManager.plugins.indexOf(plugin));
            int x = PluginPos.loadPosX(pluginName);
            int y = PluginPos.loadPosY(pluginName);
            if(fakeDrag && tempPlugin != null){
                if(pluginName.equalsIgnoreCase(PluginsManager.pluginsName.get(PluginsManager.plugins.indexOf(tempPlugin)))){
                    x = tempX;
                    y = tempY;
                    GuiComponent.fill(poseStack, x - gap, y - gap,PluginPos.getWidth(pluginName) + x + gap, PluginPos.getHeight(pluginName) + y + gap, 0x2929292F);
                    continue;
                }
            }
            GuiComponent.fill(poseStack, x - gap, y - gap,PluginPos.getWidth(pluginName) + x + gap, PluginPos.getHeight(pluginName) + y + gap, 0x2929292F);
        }
        super.render(poseStack, mouseX, mouseY, p_96565_);
    }


    public boolean mouseClicked(double mouseX, double mouseY, int p_94697_) {
        relativeX.clear();
        relativeY.clear();
        for(HudPlugin plugin : PluginsManager.hudPlugins){
            String pluginName = PluginsManager.pluginsName.get(PluginsManager.plugins.indexOf(plugin));
            relativeX.add((int) (mouseX - PluginPos.loadPosX(pluginName)));
            relativeY.add((int) (mouseY - PluginPos.loadPosY(pluginName)));
        }
        return super.mouseClicked(mouseX, mouseY, p_94697_);
    }

    public boolean mouseDragged(double mouseX, double mouseY, int p_94701_, double p_94702_, double p_94703_) {
        for(HudPlugin currentPlugin : PluginsManager.hudPlugins){
            String pluginName = PluginsManager.pluginsName.get(PluginsManager.plugins.indexOf(currentPlugin));

            if(relativeX.size() != 0 || relativeY.size() != 0){
                int relX = relativeX.get(PluginsManager.hudPlugins.indexOf(currentPlugin));
                int relY = relativeY.get(PluginsManager.hudPlugins.indexOf(currentPlugin));

                if(relX <= PluginPos.getWidth(pluginName) + gap && relX >= -gap && relY <= PluginPos.getHeight(pluginName) +gap && relY >= -gap){
                    // PluginPos.writePos((int) (mouseX - relX), (int) (mouseY - relY), PluginsManager.plugins.indexOf(currentPlugin));
                    tempX=(int) (mouseX - relX);
                    tempY=(int) (mouseY - relY);
                    fakeDrag=true;
                    tempPlugin=currentPlugin;
                }
            }
        }

        return super.mouseDragged(mouseX, mouseY, p_94701_, p_94702_, p_94703_);
    }

    @Override
    public boolean mouseReleased(double p_94722_, double p_94723_, int p_94724_) {
        if(tempPlugin!=null){
            PluginPos.writePos(tempX, tempY, PluginsManager.plugins.indexOf(tempPlugin));
            fakeDrag = false;
        }
        for(HudPlugin plugin : PluginsManager.hudPlugins){
            plugin.updatePos();
        }
        return super.mouseReleased(p_94722_, p_94723_, p_94724_);
    }
}
