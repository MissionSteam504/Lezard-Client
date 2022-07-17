package fr.lezard.utils;

import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.resources.ResourceLocation;

public class ServerDataFeatured extends ServerData {
	
	public static final ResourceLocation STAR = new ResourceLocation("lezard/gui/star.png");

	public ServerDataFeatured(String pName, String pIp) {
		super(pName, pIp, false);
	}

}
