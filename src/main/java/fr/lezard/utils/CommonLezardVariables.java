package fr.lezard.utils;

import java.util.Arrays;
import java.util.List;

import fr.lezard.Lezard;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class CommonLezardVariables {
	public static final TranslatableComponent ENABLED = new TranslatableComponent(Lezard.NAMESPACE + ".gui.enabled");
	public static final TranslatableComponent COLOR = new TranslatableComponent(Lezard.NAMESPACE + ".gui.color");
	public static final TranslatableComponent RAINBOW = new TranslatableComponent(Lezard.NAMESPACE + ".gui.rainbow");
	public static final TranslatableComponent FILLED = new TranslatableComponent(Lezard.NAMESPACE + ".gui.filled");
	public static final TranslatableComponent MODE = new TranslatableComponent(Lezard.NAMESPACE + ".gui.mode");
	public static final TranslatableComponent SECOND_COLOR = new TranslatableComponent(Lezard.NAMESPACE + ".secondColor");
	
	public static final List<EntityType<?>> MOBS_WITH_SPAWNEGG = Arrays.asList(
			EntityType.AXOLOTL,
			EntityType.BAT,
			EntityType.BEE,
			EntityType.BLAZE,
			EntityType.CAT,
			EntityType.CAVE_SPIDER,
			EntityType.CHICKEN,
			EntityType.COD,
			EntityType.COW,
			EntityType.CREEPER,
			EntityType.DOLPHIN,
			EntityType.DONKEY,
			EntityType.DROWNED,
			EntityType.ELDER_GUARDIAN,
			EntityType.ENDERMAN,
			EntityType.ENDERMITE,
			EntityType.EVOKER,
			EntityType.FOX,
			EntityType.GHAST,
			EntityType.GLOW_SQUID,
			EntityType.GOAT,
			EntityType.GUARDIAN,
			EntityType.HOGLIN,
			EntityType.HORSE,
			EntityType.HUSK,
			EntityType.LLAMA,
			EntityType.MAGMA_CUBE,
			EntityType.MOOSHROOM,
			EntityType.MULE,
			EntityType.OCELOT,
			EntityType.PANDA,
			EntityType.PARROT,
			EntityType.PHANTOM,
			EntityType.PIG,
			EntityType.PIGLIN,
			EntityType.PIGLIN_BRUTE,
			EntityType.PILLAGER,
			EntityType.POLAR_BEAR,
			EntityType.PUFFERFISH,
			EntityType.RABBIT,
			EntityType.RAVAGER,
			EntityType.SALMON,
			EntityType.SHEEP,
			EntityType.SHULKER,
			EntityType.SILVERFISH,
			EntityType.SKELETON,
			EntityType.SKELETON_HORSE,
			EntityType.SLIME,
			EntityType.SPIDER,
			EntityType.SQUID,
			EntityType.STRAY,
			EntityType.STRIDER,
			EntityType.TRADER_LLAMA,
			EntityType.TROPICAL_FISH,
			EntityType.TURTLE,
			EntityType.VEX,
			EntityType.VILLAGER,
			EntityType.VINDICATOR,
			EntityType.WANDERING_TRADER,
			EntityType.WITCH,
			EntityType.WITHER_SKELETON,
			EntityType.WOLF,
			EntityType.ZOGLIN,
			EntityType.ZOMBIE,
			EntityType.ZOMBIE_HORSE,
			EntityType.ZOMBIE_VILLAGER,
			EntityType.ZOMBIFIED_PIGLIN
	);
	
	public static final List<Item> SPAWNEGGS = Arrays.asList(
			Items.AXOLOTL_BUCKET,
			Items.BAT_SPAWN_EGG,
			Items.BEE_SPAWN_EGG,
			Items.BLAZE_SPAWN_EGG,
			Items.CAT_SPAWN_EGG,
			Items.CAVE_SPIDER_SPAWN_EGG,
			Items.CHICKEN_SPAWN_EGG,
			Items.COD_BUCKET,
			Items.COW_SPAWN_EGG,
			Items.CREEPER_SPAWN_EGG,
			Items.DOLPHIN_SPAWN_EGG,
			Items.DONKEY_SPAWN_EGG,
			Items.DROWNED_SPAWN_EGG,
			Items.ELDER_GUARDIAN_SPAWN_EGG,
			Items.ENDERMAN_SPAWN_EGG,
			Items.ENDERMITE_SPAWN_EGG,
			Items.EVOKER_SPAWN_EGG,
			Items.FOX_SPAWN_EGG,
			Items.GHAST_SPAWN_EGG,
			Items.GLOW_SQUID_SPAWN_EGG,
			Items.GOAT_SPAWN_EGG,
			Items.GUARDIAN_SPAWN_EGG,
			Items.HOGLIN_SPAWN_EGG,
			Items.HORSE_SPAWN_EGG,
			Items.HUSK_SPAWN_EGG,
			Items.LLAMA_SPAWN_EGG,
			Items.MAGMA_CUBE_SPAWN_EGG,
			Items.MOOSHROOM_SPAWN_EGG,
			Items.MULE_SPAWN_EGG,
			Items.OCELOT_SPAWN_EGG,
			Items.PANDA_SPAWN_EGG,
			Items.PARROT_SPAWN_EGG,
			Items.PHANTOM_SPAWN_EGG,
			Items.PIG_SPAWN_EGG,
			Items.PIGLIN_SPAWN_EGG,
			Items.PIGLIN_BRUTE_SPAWN_EGG,
			Items.PILLAGER_SPAWN_EGG,
			Items.POLAR_BEAR_SPAWN_EGG,
			Items.PUFFERFISH_BUCKET,
			Items.RABBIT_SPAWN_EGG,
			Items.RAVAGER_SPAWN_EGG,
			Items.SALMON_BUCKET,
			Items.SHEEP_SPAWN_EGG,
			Items.SHULKER_SPAWN_EGG,
			Items.SILVERFISH_SPAWN_EGG,
			Items.SKELETON_SPAWN_EGG,
			Items.SKELETON_HORSE_SPAWN_EGG,
			Items.SLIME_SPAWN_EGG,
			Items.SPIDER_SPAWN_EGG,
			Items.SQUID_SPAWN_EGG,
			Items.STRAY_SPAWN_EGG,
			Items.STRIDER_SPAWN_EGG,
			Items.TRADER_LLAMA_SPAWN_EGG,
			Items.TROPICAL_FISH_BUCKET,
			Items.TURTLE_EGG,
			Items.VEX_SPAWN_EGG,
			Items.VILLAGER_SPAWN_EGG,
			Items.VINDICATOR_SPAWN_EGG,
			Items.WANDERING_TRADER_SPAWN_EGG,
			Items.WITCH_SPAWN_EGG,
			Items.WITHER_SKELETON_SPAWN_EGG,
			Items.WOLF_SPAWN_EGG,
			Items.ZOGLIN_SPAWN_EGG,
			Items.ZOMBIE_SPAWN_EGG,
			Items.ZOMBIE_HORSE_SPAWN_EGG,
			Items.ZOMBIE_VILLAGER_SPAWN_EGG,
			Items.ZOMBIFIED_PIGLIN_SPAWN_EGG
	);
}