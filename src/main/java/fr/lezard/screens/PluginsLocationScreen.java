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
    private static final List<Integer> relativeX = new ArrayList<>();
    private static final List<Integer> relativeY = new ArrayList<>();
    private static int tempX;
    private static int tempY;
    private static HudPlugin tempPlugin;

    private static int gap = 4;

    public PluginsLocationScreen() {
        super(new TranslatableComponent("lezard.gui.plugins.title"));
    }

    protected void init() {
        this.createPluginsMenu();
    }

    private void createPluginsMenu() {
        this.addRenderableWidget(new Button(this.width / 2 - 49, this.height / 4 + 24 -16, 98, 20, new TranslatableComponent("lezard.plugins.edit"), (p_96335_) -> {
            dragMenu = false;
            Minecraft.getInstance().setScreen(new MainPluginsScreen());
        }));
        this.addRenderableWidget(new Button(this.width / 2 - 49, this.height / 4 + 48 -16, 98, 20, new TranslatableComponent("lezard.goBack"), (p_96335_) -> {
            dragMenu = false;
            Minecraft.getInstance().setScreen(new LezardScreen());
        }));
    }

    public void tick() {
        super.tick();
    }
    public void render(PoseStack poseStack, int mouseX, int mouseY, float p_96565_) {
        // this.blit(poseStack, 10, 10, 0, 30, this.width / 2, this.height);
        for(HudPlugin plugin : PluginsManager.hudPlugins){
            String pluginName = PluginsManager.pluginsName.get(PluginsManager.plugins.indexOf(plugin));
            int x = PluginPos.loadPosX(pluginName);
            GuiComponent.fill(poseStack, x - gap, PluginPos.loadPosY(pluginName) - gap,PluginPos.getWidth(pluginName) + x + gap, PluginPos.getHeight(pluginName) + PluginPos.loadPosY(pluginName) + gap, 0x2929292F);
        }
        if(InGameTimeHudPlugin.enabled)
            InGameTimeHudPlugin.renderIgTime(poseStack);

        if(RealTimeHudPlugin.enabled)
            RealTimeHudPlugin.renderIrlTime(poseStack);

        if(ArmorHudPlugin.enabled) {
            for (int i = 0; i < 4; i++) {
                ItemStack is = null;
                if(i == 3)
                    is = new ItemStack(Items.NETHERITE_HELMET);
                if(i == 2)
                    is = new ItemStack(Items.NETHERITE_CHESTPLATE);
                if(i == 1)
                    is = new ItemStack(Items.NETHERITE_LEGGINGS);
                if(i == 0)
                    is = new ItemStack(Items.NETHERITE_BOOTS);

                ArmorHudPlugin.renderHud(i, is, poseStack);
            }
        }

        if(CompassHudPlugin.enabled)
            CompassHudPlugin.renderHud(poseStack);


        if(FpsHudPlugin.enabled)
            FpsHudPlugin.renderFps(poseStack);

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

            int relX = relativeX.get(PluginsManager.hudPlugins.indexOf(currentPlugin));
            int relY = relativeY.get(PluginsManager.hudPlugins.indexOf(currentPlugin));

            if(relX <= PluginPos.getWidth(pluginName) + gap && relX >= -gap && relY <= PluginPos.getHeight(pluginName) +gap && relY >= -gap){
                // PluginPos.writePos((int) (mouseX - relX), (int) (mouseY - relY), PluginsManager.plugins.indexOf(currentPlugin));
                tempX=(int) (mouseX - relX);
                tempY=(int) (mouseY - relY);
                tempPlugin=currentPlugin;
            }
        }

        return super.mouseDragged(mouseX, mouseY, p_94701_, p_94702_, p_94703_);
    }

    @Override
    public boolean mouseReleased(double p_94722_, double p_94723_, int p_94724_) {
        PluginPos.writePos(tempX, tempY, PluginsManager.plugins.indexOf(tempPlugin));
        return super.mouseReleased(p_94722_, p_94723_, p_94724_);
    }
}
