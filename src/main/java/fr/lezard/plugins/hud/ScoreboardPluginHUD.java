package fr.lezard.plugins.hud;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Pair;

import fr.lezard.events.Event;
import fr.lezard.gui.screen.plugins.ScoreboardPluginHUDScreen;
import fr.lezard.plugins.PluginHUD;
import fr.lezard.utils.FileWriterJson;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Score;
import net.minecraft.world.scores.Scoreboard;

public class ScoreboardPluginHUD extends PluginHUD {

	public ScoreboardPluginHUD() {
		super("Scoreboard HUD", FileWriterJson.getBoolean("score", "enabled"), Category.HUD, "score", Minecraft.getInstance().options.keyScoreboard, new ScoreboardPluginHUDScreen());
	}
	
	public void onEvent(Event<?> e) {
		
	}
	
	public static void renderSidebar(PoseStack pPoseStack, Objective pObjective) {
		Minecraft minecraft = Minecraft.getInstance();
		Font font = minecraft.font;
		
		Scoreboard scoreboard = pObjective.getScoreboard();
        Collection<Score> collection = scoreboard.getPlayerScores(pObjective);
        
        List<Score> list = collection.stream().filter((p_93026_0_) ->
        {
            return p_93026_0_.getOwner() != null && !p_93026_0_.getOwner().startsWith("#");
        }).collect(Collectors.toList());

        if (list.size() > 15)
        {
            collection = Lists.newArrayList(Iterables.skip(list, collection.size() - 15));
        }
        else
        {
            collection = list;
        }

        List<Pair<Score, Component>> list1 = Lists.newArrayListWithCapacity(collection.size());
        Component component = pObjective.getDisplayName();
        int i = font.width(component);
        int j = i;
        int k = font.width(": ");

        for (Score score : collection)
        {
            PlayerTeam playerteam = scoreboard.getPlayersTeam(score.getOwner());
            Component component1 = PlayerTeam.formatNameForTeam(playerteam, new TextComponent(score.getOwner()));
            list1.add(Pair.of(score, component1));
            j = Math.max(j, font.width(component1) + k + font.width(Integer.toString(score.getScore())));
        }

        int j2 = minecraft.getWindow().getScreenHeight() / 2 + (collection.size() * 9) / 3; // Y
        int l2 = minecraft.getWindow().getScreenWidth() - j - 3; // Start X
        int l = 0;
        int i1 = minecraft.options.getBackgroundColor(0.3F); // Background Score
        int j1 = minecraft.options.getBackgroundColor(0.4F); // Background Title

        for (Pair<Score, Component> pair : list1)
        {
            ++l; // Gap ++
            Score score1 = pair.getFirst(); // Getting Score
            Component component2 = pair.getSecond(); // Getting Player usernames
            String s = "" + ChatFormatting.RED + score1.getScore(); // Making score showing red
            int k1 = j2 - l * 9; // PosY of the scoreboard
            int l1 = minecraft.getWindow().getScreenWidth() - 3 + 2; // Width of the scoreboard
            GuiComponent.fill(pPoseStack, l2 - 2, k1, l1, k1 + 9, i1); // Rendering background of the score
            font.draw(pPoseStack, component2, (float)l2, (float)k1, -1); // Player Username
            font.draw(pPoseStack, s, (float)(l1 - font.width(s)), (float)k1, -1); // Score

            if (l == collection.size())
            {
                GuiComponent.fill(pPoseStack, l2 - 2, k1 - 9 - 1, l1, k1 - 1, j1); // Head Background
                GuiComponent.fill(pPoseStack, l2 - 2, k1 - 1, l1, k1, i1); // Line between the score and head
                font.draw(pPoseStack, component, (float)(l2 + j / 2 - i / 2), (float)(k1 - 9), -1); // Title
            }
        }
	}

}
