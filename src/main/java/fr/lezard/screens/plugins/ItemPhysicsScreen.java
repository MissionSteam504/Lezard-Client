package fr.lezard.screens.plugins;

import com.mojang.blaze3d.vertex.PoseStack;
import fr.lezard.PluginsManager;
import fr.lezard.plugins.ItemPhysicsPlugin;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.JigsawBlockEditScreen;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.Mth;

public class ItemPhysicsScreen extends Screen {
    public ItemPhysicsScreen() {
        super(new TranslatableComponent("lezard.plugin.itemPhysics"));
    }

    protected void init() {
        this.createPluginsMenu();
    }

    private void createPluginsMenu() {
        this.addRenderableWidget(new Button(this.width / 2 - 49, this.height / 4 + 12 -16, 98, 20, ItemPhysicsPlugin.enabled ? new TranslatableComponent("lezard.plugin.enabled") : new TranslatableComponent("lezard.plugin.disabled"), (p_96335_) -> {
            ItemPhysicsPlugin.enabled = !ItemPhysicsPlugin.enabled;
            PluginsManager.writeEnabled(ItemPhysicsPlugin.name, ItemPhysicsPlugin.enabled);
        }));
        this.addRenderableWidget(new Button(this.width / 2 - 49, this.height / 4 + 156 -16, 98, 20, new TranslatableComponent("lezard.goBack"), (p_96335_) -> {
            Minecraft.getInstance().setScreen(new MainPluginsScreen());
        }));
    }

    public void tick() {
        super.tick();
    }
    public void render(PoseStack p_96562_, int p_96563_, int p_96564_, float p_96565_) {
        this.renderBackground(p_96562_);
        super.render(p_96562_, p_96563_, p_96564_, p_96565_);
        drawCenteredString(p_96562_, this.font, this.title, this.width / 2, 15, 16777215);
    }
}
