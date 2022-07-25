package fr.lezard.plugins.hud;

import com.mojang.blaze3d.vertex.PoseStack;

import fr.lezard.Lezard;
import fr.lezard.events.Event;
import fr.lezard.events.listeners.EventInGame;
import fr.lezard.events.listeners.EventStart;
import fr.lezard.gui.screen.plugins.hud.BlockInfoTooltipPluginScreen;
import fr.lezard.plugins.Plugin;
import fr.lezard.utils.Colors;
import fr.lezard.utils.CommonLezardVariables;
import fr.lezard.utils.FileWriterJson;
import fr.lezard.utils.LezardOptions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class BlockInfoTooltipPlugin extends Plugin {
	private static int width, height;
	public static boolean filled = true, rainbow = false;
	public static float size = 1f;
	
	public static Colors secondColor = Colors.WHITE;
	public static Colors firstColor = Colors.WHITE;
	
	public BlockInfoTooltipPlugin() {
		super("Block Infos Tooltip", FileWriterJson.getBoolean("blocktooltip", "enabled"), Category.HUD, "blocktooltip", Minecraft.getInstance().options.keyCompass, new BlockInfoTooltipPluginScreen());
	}
	
	public void onEvent(Event<?> e) {
		if(e instanceof EventInGame) {
			int posX = 0;
			int posY = 3+LezardOptions.gap;
			
			posX = Minecraft.getInstance().getWindow().getGuiScaledWidth()/2-width/2+8;
			
			PoseStack poseStack = new PoseStack();
			Minecraft mc = Minecraft.getInstance();
			Font font = mc.font;
			
			if(mc.options.renderDebug){
	            return;
	        }
			
			Entity camera = mc.getCameraEntity();
			HitResult currentBlock = camera.pick(5.0D, 0.0F, false);
			
			Entity mob = mc.crosshairPickEntity;
			
			if(filled && ((mob != null && CommonLezardVariables.MOBS_WITH_SPAWNEGG.contains(mob.getType())) || currentBlock.getType() == HitResult.Type.BLOCK)) {
				GuiComponent.fill(poseStack, posX - LezardOptions.gap -18, posY - LezardOptions.gap, posX + width + LezardOptions.gap, posY + height + LezardOptions.gap, Lezard.color.getRGB());
			}
			
			// Current mob
			if(mob != null) {
				if(mob instanceof LivingEntity entity) {
					if(CommonLezardVariables.MOBS_WITH_SPAWNEGG.contains(mob.getType())) {
						String animalName = mob.getName().getString();
						String health = "Health: " + String.format("%.2f", entity.getHealth()/2) + "/" + String.format("%.0f", entity.getMaxHealth()/2);
						width = font.width(animalName.length() > health.length() ? animalName : health);
						GuiComponent.drawString(poseStack, font, animalName, posX, posY, rainbow ? Lezard.rainbowText() : firstColor.getRgb());
						GuiComponent.drawString(poseStack, font, health, posX, posY+font.lineHeight, rainbow ? Lezard.rainbowText() : secondColor.getRgb());
						ItemStack is = new ItemStack(getSpawnEggByType(mob.getType()));
						mc.getItemRenderer().renderGuiItem(is, posX-19, posY);
					}
				}
				return;
			}
			
			// Current block
			if (currentBlock.getType() == HitResult.Type.BLOCK) {
                BlockPos blockpos = ((BlockHitResult)currentBlock).getBlockPos();
                BlockState blockstate = mc.level.getBlockState(blockpos);
                
                Block block = blockstate.getBlock();
                String blockName = block.getName().getString();
                ItemStack is = new ItemStack(block.asItem());
                
    			width = font.width(blockName);
                height = font.lineHeight*2;
                
                GuiComponent.drawCenteredString(poseStack, font, blockName, posX+width/2, posY+font.lineHeight/2, rainbow ? Lezard.rainbowText() : firstColor.getRgb());
                mc.getItemRenderer().renderGuiItem(is, posX-19, posY);
			}
		}
		if(e instanceof EventStart) {
			if(!FileWriterJson.getString(getNamespace(), "second").equalsIgnoreCase("")){
	            secondColor = Colors.valueOf(FileWriterJson.getString(getNamespace(), "second"));
	        }
			if(!FileWriterJson.getString(getNamespace(), "color").equalsIgnoreCase("")){
	            firstColor = Colors.valueOf(FileWriterJson.getString(getNamespace(), "color"));
	        }
			filled = FileWriterJson.getBoolean(getNamespace(), "filled");
			rainbow = FileWriterJson.getBoolean(getNamespace(), "rainbow");
			size = FileWriterJson.getFloat(getNamespace(), "size");
			if(size == 0f) {
				size = 1f;
			}
		}
	}
	
	public Item getSpawnEggByType(EntityType<?> t) {
		for(EntityType<?> type : CommonLezardVariables.MOBS_WITH_SPAWNEGG) {
			if(type.toString().equals(t.toString())) {
				int index = CommonLezardVariables.MOBS_WITH_SPAWNEGG.indexOf(type);
				return CommonLezardVariables.SPAWNEGGS.get(index);
			}
		}
		return null;
	}
}
