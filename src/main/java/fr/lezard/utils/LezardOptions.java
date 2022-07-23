package fr.lezard.utils;

import fr.lezard.Lezard;
import net.minecraft.client.CycleOption;
import net.minecraft.client.ProgressOption;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class LezardOptions {
	public static int alpha = 95;
	public static int gap = 4;
	public static int rainbowSpeed = 5;
	public static boolean showAnchor = false;
	
	public static final ProgressOption ALPHA_SLIDER = new ProgressOption(Lezard.NAMESPACE + ".option.alpha", 0.0D, 255.0D, 1.0F, (p_168133_) -> {
	      return (double)alpha;
	   }, (p_168277_, p_168278_) -> {
	      alpha = (int)p_168278_.doubleValue();
	   }, (p_168274_, p_168275_) -> {
	      double d0 = p_168275_.get(p_168274_);
	      return (Component)(d0 == 0.0D ? CommonComponents.optionStatus(p_168275_.getCaption(), false) : p_168275_.genericValueLabel((int)d0));
	   });
	public static final ProgressOption GAP_SLIDER = new ProgressOption(Lezard.NAMESPACE + ".option.gap", 0.0D, 10.0D, 1.0F, (p_168133_) -> {
	      return (double)gap;
	   }, (p_168277_, p_168278_) -> {
	      gap = (int)p_168278_.doubleValue();
	   }, (p_168274_, p_168275_) -> {
	      double d0 = p_168275_.get(p_168274_);
	      return (Component)(d0 == 0.0D ? CommonComponents.optionStatus(p_168275_.getCaption(), false) : p_168275_.genericValueLabel((int)d0));
	   });
	public static final ProgressOption RAINBOW_SPEED = new ProgressOption(Lezard.NAMESPACE + ".option.rainbowSpeed", 1.0D, 10.0D, 1.0F, (p_168133_) -> {
	      return (double)rainbowSpeed;
	   }, (p_168277_, p_168278_) -> {
		   rainbowSpeed = (int)p_168278_.doubleValue();
	   }, (p_168274_, p_168275_) -> {
	      double d0 = p_168275_.get(p_168274_);
	      return (Component)(d0 == 0.0D ? CommonComponents.optionStatus(p_168275_.getCaption(), false) : p_168275_.genericValueLabel((int)d0));
	   });
	
	public static final CycleOption<Boolean> SHOW_ANCHOR = CycleOption.createOnOff(Lezard.NAMESPACE + ".option.showAnchor", (option) -> {
    	return showAnchor;
    }, (option, p_168395_1_, value) -> {
    	showAnchor = value;
    });
}
