package fr.lezard.plugins;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.ItemEntityRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class ItemPhysicsPlugin extends Plugin{
    public static boolean enabled;
    public static String name;
    public static double speed = 1;

    public ItemPhysicsPlugin(String name) {
        super(name);
        this.name = name;
    }

    public void init() {
        this.enabled = isEnabled();
        System.out.println("ItemPhysics Enabled");
    }

    public static void itemPhysics(BakedModel bakedModel, ItemEntity p_115036_, float p_115038_, PoseStack p_115039_, ItemStack itemStack){
        if(enabled){
            ItemEntityRenderer.shadowRadius = 0;
            Item item = itemStack.getItem();
            Block block = Block.byItem(item);

            float angle = (float) ((System.currentTimeMillis() % (360 * 20 ) / 2F) * speed);

            if(block!=null && block != Blocks.AIR){
                // Block
                if(!p_115036_.isOnGround())
                    p_115039_.mulPose(Vector3f.YP.rotationDegrees(angle));
                p_115039_.translate(0.0f, -0.09, 0.0f);
                p_115039_.scale(1.5f, 1.5f, 1.5f);
                p_115039_.mulPose(Vector3f.YN.rotationDegrees(50f));
            }else{
                // Item
                if(!p_115036_.isOnGround())
                    p_115039_.mulPose(Vector3f.YP.rotationDegrees(angle));
                p_115039_.translate(0.02f, 0.02f, 0.16f);
                p_115039_.scale(1.5f, 1.5f, 1.5f);
                p_115039_.mulPose(Vector3f.XN.rotationDegrees(90f));
                p_115039_.mulPose(Vector3f.ZN.rotationDegrees(128f));
            }
        } else{
            float f1 = Mth.sin(((float)p_115036_.getAge() + p_115038_) / 10.0F + p_115036_.bobOffs) * 0.1F + 0.1F;
            float f2 = bakedModel.getTransforms().getTransform(ItemTransforms.TransformType.GROUND).scale.y();
            p_115039_.translate(0.0D, f1 + 0.25F * f2, 0.0D);
            float f3 = p_115036_.getSpin(p_115038_);
            p_115039_.mulPose(Vector3f.YP.rotation(f3));
        }
    }
}
