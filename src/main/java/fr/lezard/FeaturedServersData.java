package fr.lezard;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.ServerList;
import net.minecraft.resources.ResourceLocation;

import static net.minecraft.client.gui.GuiComponent.blit;

public class FeaturedServersData extends ServerData {

    public static final ResourceLocation STAR_ICON = new ResourceLocation("textures/gui/star.png");

    public FeaturedServersData(String serverName, String serverIp) {
        super(serverName, serverIp, false);
    }

    public static void loadServers(ServerList serverList){
        serverList.add(new FeaturedServersData("Server 1", "reduxsmp.mcserv.co"));
    }

    public static void drawImg(PoseStack poseStack, int x, int y, boolean lower) {
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.getTextureManager().bindForSetup(STAR_ICON);
    }
}
