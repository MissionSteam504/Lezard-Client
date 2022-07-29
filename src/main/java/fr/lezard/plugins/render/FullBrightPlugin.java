package fr.lezard.plugins.render;

import fr.lezard.gui.screen.plugins.render.FullBrightPluginScreen;
import fr.lezard.plugins.Plugin;
import fr.lezard.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

public class FullBrightPlugin extends Plugin {

	public FullBrightPlugin() {
		super("Full Bright", Utils.getPlugin("fullbright").isEnabled(), Category.RENDER, "fullbright", Minecraft.getInstance().options.keyFullbright, new FullBrightPluginScreen());
	}
	
	public void onEnable() {
		MobEffectInstance mobeffectinstance = new MobEffectInstance(MobEffects.NIGHT_VISION, 999999, 255, false, false);
		Minecraft.getInstance().player.addEffect(mobeffectinstance);
	}
	
	public void onDisable() {
		if(Minecraft.getInstance().player.hasEffect(MobEffects.NIGHT_VISION)){
			if(Minecraft.getInstance().player.getEffect(MobEffects.NIGHT_VISION).getDuration() >= 900000) {
				Minecraft.getInstance().player.removeEffect(MobEffects.NIGHT_VISION);
			}
		}
	}
}
