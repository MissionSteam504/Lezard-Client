package fr.lezard;

// import club.minnced.discord.rpc.*;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.slf4j.Logger;

import java.util.Random;

public class LezardCore {

    private final Minecraft mc = Minecraft.getInstance();
    static final Logger LOGGER = LogUtils.getLogger();
    private static final LezardCore instance = new LezardCore();
    public static final boolean IDE_CLIENT = true;
    public static final String NAMESPACE = "lezard";
    public static final String PREFIX = "[LezardClient] ";
    public static final String CLIENT_USERNAME = "LezardUser";
    private final String CLIENT_NAME = "Lezard Client";
    private final String CLIENT_VERSION = "0.3.0";

    Minecraft minecraft;

    public static LezardCore getInstance(){
        return instance;
    }

    public void launch(){
        System.out.println("[LezardClient] Starting");
        PluginsManager.getInstance().launch();
        // this.discordRPC();
    }

    public String getWindowTitle(){

        return CLIENT_NAME + " - " + CLIENT_VERSION + " | " + mc.getUser().getName();
    }

    /* private void discordRPC(){
        DiscordRPC discord = DiscordRPC.INSTANCE;
        String applicationId = "971435977199464528";
        String steamId = "";

        DiscordEventHandlers handlers = new DiscordEventHandlers();
        discord.Discord_Initialize(applicationId, handlers, true, steamId);

        DiscordRichPresence presence = new DiscordRichPresence();
        presence.startTimestamp = System.currentTimeMillis() / 1000;
        presence.largeImageKey = "image";
        presence.largeImageText = "Making a MC Client";
        presence.details = CLIENT_NAME;
        presence.state = "Version : 1.18.2-" + CLIENT_VERSION;
        discord.Discord_UpdatePresence(presence);
    } */

    public static int getRandomNumberInRange(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
}
