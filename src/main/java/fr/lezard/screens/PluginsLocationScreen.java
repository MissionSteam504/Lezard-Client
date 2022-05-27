package fr.lezard.screens;

import com.mojang.blaze3d.vertex.PoseStack;
import fr.lezard.PluginFileManager;
import fr.lezard.PluginsManager;
import fr.lezard.plugins.utils.HudPlugin;
import fr.lezard.plugins.utils.PluginPos;
import fr.lezard.screens.plugins.MainPluginsScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TranslatableComponent;

import java.util.ArrayList;
import java.util.List;

public class PluginsLocationScreen extends Screen {
    public static boolean fakeDrag = false;
    private static final List<Integer> relativeX = new ArrayList<>();
    private static final List<Integer> relativeY = new ArrayList<>();
    public static int tempX;
    public static int tempY;
    public static HudPlugin tempPlugin = null;

    public static final int GAP = 4;

    public PluginsLocationScreen() {
        super(new TranslatableComponent("lezard.gui.plugins.title"));
    }

    protected void init() {
        this.createPluginsMenu();
    }

    private void createPluginsMenu() {
        this.addRenderableWidget(new Button(this.width / 2 - 49, this.height / 4 + 24 -16, 98, 20, new TranslatableComponent("lezard.plugins.edit"), (p_96335_) -> {
            Minecraft.getInstance().setScreen(new MainPluginsScreen());
        }));
        this.addRenderableWidget(new Button(this.width / 2 - 49, this.height / 4 + 48 -16, 98, 20, new TranslatableComponent("lezard.goBack"), (p_96335_) -> {
            Minecraft.getInstance().setScreen(new LezardScreen());
        }));
    }

    public void tick() {
        super.tick();
    }

    public void render(PoseStack poseStack, int mouseX, int mouseY, float p_96565_) {
        super.render(poseStack, mouseX, mouseY, p_96565_);
    }


    public boolean mouseClicked(double mouseX, double mouseY, int p_94697_) {
        relativeX.clear();
        relativeY.clear();
        for(HudPlugin plugin : PluginsManager.hudPlugins){
            String pluginName = PluginsManager.pluginsName.get(PluginsManager.plugins.indexOf(plugin));
            relativeX.add((int) (mouseX - PluginFileManager.getInt(pluginName, "posX")));
            relativeY.add((int) (mouseY - PluginFileManager.getInt(pluginName, "posY")));
        }
        return super.mouseClicked(mouseX, mouseY, p_94697_);
    }

    public boolean mouseDragged(double mouseX, double mouseY, int p_94701_, double p_94702_, double p_94703_) {
        for(HudPlugin currentPlugin : PluginsManager.hudPlugins){
            String pluginName = PluginsManager.pluginsName.get(PluginsManager.plugins.indexOf(currentPlugin));

            if(relativeX.size() != 0 || relativeY.size() != 0){
                int relX = relativeX.get(PluginsManager.hudPlugins.indexOf(currentPlugin));
                int relY = relativeY.get(PluginsManager.hudPlugins.indexOf(currentPlugin));

                if(relX <= PluginPos.getWidth(pluginName) + GAP && relX >= -GAP && relY <= PluginPos.getHeight(pluginName) +GAP && relY >= -GAP){
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
            PluginFileManager.writeJson(PluginsManager.pluginsName.get(PluginsManager.plugins.indexOf(tempPlugin)), "posX", tempX);
            PluginFileManager.writeJson(PluginsManager.pluginsName.get(PluginsManager.plugins.indexOf(tempPlugin)), "posY", tempY);
            fakeDrag = false;
        }
        for(HudPlugin plugin : PluginsManager.hudPlugins){
            plugin.updatePos();
        }
        return super.mouseReleased(p_94722_, p_94723_, p_94724_);
    }
}
