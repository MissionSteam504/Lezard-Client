package fr.lezard.plugins.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;

import fr.lezard.gui.screen.plugins.ItemPhysicsPluginScreen;
import fr.lezard.plugins.Plugin;
import fr.lezard.utils.FileWriterJson;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.ItemEntityRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class ItemPhysicsPlugin extends Plugin{
	public static double speed = 1;
	
	public ItemPhysicsPlugin() {
		super("Item Physics", FileWriterJson.getBoolean("itemphysics", "enabled"), Category.RENDER, "itemphysics", Minecraft.getInstance().options.keyItemPhysics, new ItemPhysicsPluginScreen());
	}
	
	public static void setPhysics(PoseStack poseStack, BakedModel bakedModel, ItemEntity itemEntity, float p1538, ItemStack itemStack) {
		ItemEntityRenderer.shadowRadius = 0.0F;
        Item item = itemStack.getItem();
        Block block = Block.byItem(item);

        float angle = (float) ((System.currentTimeMillis() % (360 * 20 ) / 2F) * speed);

        if(block!=null && block != Blocks.AIR){
            // Block
            if(!itemEntity.isOnGround())
            	if(!Minecraft.getInstance().isPaused())
            		poseStack.mulPose(Vector3f.YP.rotationDegrees(angle));
            poseStack.translate(0.0f, -0.09f, 0.0f);
            poseStack.scale(1.5f, 1.5f, 1.5f);
            poseStack.mulPose(Vector3f.YN.rotationDegrees(50f));
        }else{
            // Item
        	if(!itemEntity.isOnGround())
            	if(!Minecraft.getInstance().isPaused())
            		poseStack.mulPose(Vector3f.YP.rotationDegrees(angle));
            poseStack.translate(-0.15f, 0.02f, -0.1f);
            poseStack.scale(1.5f, 1.5f, 1.5f);
            poseStack.mulPose(Vector3f.XN.rotationDegrees(90f));
            poseStack.mulPose(Vector3f.ZN.rotationDegrees(128f));
        }
	}
}
