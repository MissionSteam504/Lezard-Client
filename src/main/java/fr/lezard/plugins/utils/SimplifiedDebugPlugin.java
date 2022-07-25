package fr.lezard.plugins.utils;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import javax.annotation.Nullable;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.platform.GlUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.DataFixUtils;

import fr.lezard.events.Event;
import fr.lezard.events.listeners.EventStart;
import fr.lezard.gui.screen.plugins.utils.SimplifiedDebugPluginScreen;
import fr.lezard.plugins.Plugin;
import fr.lezard.utils.FileWriterJson;
import net.minecraft.ChatFormatting;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.SectionPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class SimplifiedDebugPlugin extends Plugin {
	
	public static Minecraft minecraft;
    private static Font font;
    private static HitResult block;
    private static HitResult liquid;
    @Nullable
    private static ChunkPos lastPos;
    @Nullable
    private static LevelChunk clientChunk;
    @Nullable
    private static CompletableFuture<LevelChunk> serverChunk;

	public SimplifiedDebugPlugin() {
		super("Simplified Debug", FileWriterJson.getBoolean("debug", "enabled"), Category.UTILS, "debug", Minecraft.getInstance().options.keySimplfiedDebug, new SimplifiedDebugPluginScreen());
	}
	
	
	public void onEvent(Event<?> e) {
		if(e instanceof EventStart) {
			minecraft = Minecraft.getInstance();
			font = minecraft.font;
		}
	}
	
	public static void clearChunkCache() {
        serverChunk = null;
        clientChunk = null;
    }

    public static void renderGameInfo(PoseStack poseStack, HitResult block, HitResult liquid){
    	SimplifiedDebugPlugin.block = block;
        SimplifiedDebugPlugin.liquid = liquid;
        List<String> list = getGameInformation();
        list.add("");
        list.add("For help: press F3 + Q");

        for(int i = 0; i < list.size(); ++i) {
            String s = list.get(i);
            if (!Strings.isNullOrEmpty(s)) {
                int j = 9;
                int k = font.width(s);
                int i1 = 2 + j * i;
                GuiComponent.fill(poseStack, 1, i1 - 1, 2 + k + 1, i1 + j - 1, -1873784752);
                font.draw(poseStack, s, 2.0F, (float)i1, 14737632);
            }
        }
    }

    public static void renderSystemInfo(PoseStack poseStack, HitResult block, HitResult liquid){
        SimplifiedDebugPlugin.block = block;
        SimplifiedDebugPlugin.liquid = liquid;
        
        List<String> list = getSystemInformation();

        for(int i = 0; i < list.size(); ++i) {
            String s = list.get(i);
            if (!Strings.isNullOrEmpty(s)) {
                int j = 9;
                int k = font.width(s);
                int l = minecraft.getWindow().getGuiScaledWidth() - 2 - k;
                int i1 = 2 + j * i;
                GuiComponent.fill(poseStack, l - 1, i1 - 1, l + k + 1, i1 + j - 1, -1873784752);
                font.draw(poseStack, s, (float)l, (float)i1, 14737632);
            }
        }
    }

    static List<String> getGameInformation() {
        IntegratedServer integratedserver = minecraft.getSingleplayerServer();
        String s;

        String[] data = minecraft.fpsString.split(" ");

        String tempFpsString = "FPS: " + data[0];

        if (integratedserver != null) {
            s = String.format("Integrated server @ %.0f ms ticks", integratedserver.getAverageTickTime());
        } else {
            s = String.format("\"%s\" server", minecraft.player.getServerBrand());
        }

        BlockPos blockpos = minecraft.getCameraEntity().blockPosition();
        Entity entity = minecraft.getCameraEntity();
        Direction direction = entity.getDirection();
        String s1 = switch (direction) {
            case NORTH -> "Towards negative Z";
            case SOUTH -> "Towards positive Z";
            case WEST -> "Towards negative X";
            case EAST -> "Towards positive X";
            default -> "Invalid";
        };

        ChunkPos chunkpos = new ChunkPos(blockpos);
        if (!Objects.equals(lastPos, chunkpos)) {
            lastPos = chunkpos;
            clearChunkCache();
        }

        Level level = getLevel();
        List<String> list = Lists.newArrayList("Minecraft " + SharedConstants.getCurrentVersion().getName() + " (" + minecraft.getLaunchedVersion() + "/" + ClientBrandRetriever.getClientModName() + ("release".equalsIgnoreCase(minecraft.getVersionType()) ? "" : "/" + minecraft.getVersionType()) + ")", tempFpsString, s, getEntityStatistics());

        list.add("Dimension: " + minecraft.level.dimension().location());
        list.add("");
        list.add(String.format(Locale.ROOT, "XYZ: %.2f / %.2f / %.2f", minecraft.getCameraEntity().getX(), minecraft.getCameraEntity().getY(), minecraft.getCameraEntity().getZ()));
        list.add(String.format("Block: %d %d %d [%d %d %d]", blockpos.getX(), blockpos.getY(), blockpos.getZ(), blockpos.getX() & 15, blockpos.getY() & 15, blockpos.getZ() & 15));
        list.add(String.format("Chunk: %d %d %d [%d %d in r.%d.%d.mca]", chunkpos.x, SectionPos.blockToSectionCoord(blockpos.getY()), chunkpos.z, chunkpos.getRegionLocalX(), chunkpos.getRegionLocalZ(), chunkpos.getRegionX(), chunkpos.getRegionZ()));
        list.add("");
        list.add(String.format(Locale.ROOT, "Roll: %.1f, Pitch: %.1f", Mth.wrapDegrees(entity.getYRot()), Mth.wrapDegrees(entity.getXRot())));
        list.add(String.format(Locale.ROOT, "Facing: %s (%s)", direction, s1));
        LevelChunk levelchunk = getClientChunk();
        if (levelchunk.isEmpty()) {
            list.add("Waiting for chunk...");
        } else {
            int i = minecraft.level.getChunkSource().getLightEngine().getRawBrightness(blockpos, 0);
            int j = minecraft.level.getBrightness(LightLayer.SKY, blockpos);
            int k = minecraft.level.getBrightness(LightLayer.BLOCK, blockpos);
            list.add("Client Light: " + i + " (" + j + " sky, " + k + " block)");
            LevelChunk levelChunk1 = getServerChunk();

            if (blockpos.getY() >= minecraft.level.getMinBuildHeight() && blockpos.getY() < minecraft.level.getMaxBuildHeight()) {
                list.add("Biome: " + printBiome(minecraft.level.getBiome(blockpos)));
                long l = 0L;
                float f2 = 0.0F;
                if (levelChunk1 != null) {
                    f2 = level.getMoonBrightness();
                    l = levelChunk1.getInhabitedTime();
                }

                DifficultyInstance difficultyinstance = new DifficultyInstance(level.getDifficulty(), level.getDayTime(), l, f2);
                list.add(String.format(Locale.ROOT, "Local Difficulty: %.2f // %.2f", difficultyinstance.getEffectiveDifficulty(), difficultyinstance.getSpecialMultiplier()));
                list.add(String.format(Locale.ROOT, "Day: %d", minecraft.level.getDayTime() / 24000L));
            }
        }

        // list.add(minecraft.getSoundManager().getDebugString() + String.format(" (Mood %d%%)", Math.round(minecraft.player.getCurrentMood() * 100.0F)));
        return list;
    }

    private static String printBiome(Holder<Biome> p_205375_) {
        return p_205375_.unwrap().map((p_205377_) -> {
            return p_205377_.location().toString();
        }, (p_205367_) -> {
            return "[unregistered " + p_205367_ + "]";
        });
    }

    @Nullable
    private static ServerLevel getServerLevel() {
        IntegratedServer integratedserver = minecraft.getSingleplayerServer();
        return integratedserver != null ? integratedserver.getLevel(minecraft.level.dimension()) : null;
    }

    private static Level getLevel() {
        return DataFixUtils.orElse(Optional.ofNullable(minecraft.getSingleplayerServer()).flatMap((p_205373_) -> {
            return Optional.ofNullable(p_205373_.getLevel(minecraft.level.dimension()));
        }), minecraft.level);
    }

    @Nullable
    private static LevelChunk getServerChunk() {
        if (serverChunk == null) {
            ServerLevel serverlevel = getServerLevel();
            if (serverlevel != null) {
                serverChunk = serverlevel.getChunkSource().getChunkFuture(lastPos.x, lastPos.z, ChunkStatus.FULL, false).thenApply((p_205369_) -> {
                    return p_205369_.map((p_205371_) -> {
                        return (LevelChunk)p_205371_;
                    }, (p_205363_) -> {
                        return null;
                    });
                });
            }

            if (serverChunk == null) {
                serverChunk = CompletableFuture.completedFuture(getClientChunk());
            }
        }

        return serverChunk.getNow((LevelChunk)null);
    }

    private static LevelChunk getClientChunk() {
        if (clientChunk == null) {
            clientChunk = minecraft.level.getChunk(lastPos.x, lastPos.z);
        }

        return clientChunk;
    }

    static List<String> getSystemInformation() {
        long i = Runtime.getRuntime().maxMemory();
        long j = Runtime.getRuntime().totalMemory();
        long k = Runtime.getRuntime().freeMemory();
        long l = j - k;
        List<String> list = Lists.newArrayList(String.format("Java: %s %dbit", System.getProperty("java.version"), minecraft.is64Bit() ? 64 : 32), String.format("Mem: % 2d%% %03d/%03dMB", l * 100L / i, bytesToMegabytes(l), bytesToMegabytes(i)), String.format("Allocated: % 2d%% %03dMB", j * 100L / i, bytesToMegabytes(j)), "", String.format("CPU: %s", GlUtil.getCpuInfo()), "", String.format("Display: %dx%d (%s)", Minecraft.getInstance().getWindow().getWidth(), Minecraft.getInstance().getWindow().getHeight(), GlUtil.getVendor()), GlUtil.getRenderer(), GlUtil.getOpenGLVersion());
        if (minecraft.showOnlyReducedInfo()) {
            return list;
        } else {
            if (block.getType() == HitResult.Type.BLOCK) {
                BlockPos blockpos = ((BlockHitResult)block).getBlockPos();
                BlockState blockstate = minecraft.level.getBlockState(blockpos);
                list.add("");
                list.add(ChatFormatting.UNDERLINE + "Targeted Block: " + blockpos.getX() + ", " + blockpos.getY() + ", " + blockpos.getZ());
                list.add(String.valueOf((Object) Registry.BLOCK.getKey(blockstate.getBlock())));

                for(Map.Entry<Property<?>, Comparable<?>> entry : blockstate.getValues().entrySet()) {
                    list.add(getPropertyValueString(entry));
                }
            }

            if (liquid.getType() == HitResult.Type.BLOCK) {
                BlockPos blockpos1 = ((BlockHitResult)liquid).getBlockPos();
                FluidState fluidstate = minecraft.level.getFluidState(blockpos1);
                list.add("");
                list.add(ChatFormatting.UNDERLINE + "Targeted Fluid: " + blockpos1.getX() + ", " + blockpos1.getY() + ", " + blockpos1.getZ());
                list.add(String.valueOf((Object)Registry.FLUID.getKey(fluidstate.getType())));

                for(Map.Entry<Property<?>, Comparable<?>> entry1 : fluidstate.getValues().entrySet()) {
                    list.add(getPropertyValueString(entry1));
                }
            }

            Entity entity = minecraft.crosshairPickEntity;
            if (entity != null) {
                list.add("");
                list.add(ChatFormatting.UNDERLINE + "Targeted Entity");
                list.add(String.valueOf((Object)Registry.ENTITY_TYPE.getKey(entity.getType())));
            }

            return list;
        }
    }

    private static String getPropertyValueString(Map.Entry<Property<?>, Comparable<?>> p_94072_) {
        Property<?> property = p_94072_.getKey();
        Comparable<?> comparable = p_94072_.getValue();
        String s = Util.getPropertyName(property, comparable);
        if (Boolean.TRUE.equals(comparable)) {
            s = ChatFormatting.GREEN + s;
        } else if (Boolean.FALSE.equals(comparable)) {
            s = ChatFormatting.RED + s;
        }

        return property.getName() + ": " + s;
    }

    public static long bytesToMegabytes(long p_94051_) {
        return p_94051_ / 1024L / 1024L;
    }
    
    public static String getEntityStatistics() {
        return "Entities: " + minecraft.levelRenderer.renderedEntities + " Rendered/" + minecraft.levelRenderer.level.getEntityCount() + " Loaded";
     }
}
