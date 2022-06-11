package fr.lezard.utils;

import fr.lezard.Lezard;
import net.minecraft.client.CycleOption;
import net.minecraft.client.ProgressOption;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class LezardOption {
	public static boolean backgroundForChatOnly = true;
	public static int alpha = 95;
	public static int gap = 4;
	
	/* public static final CycleOption<Boolean> BOOL_CYCLE = CycleOption.createBinaryOption("options.accessibility.text_background", new TranslatableComponent("options.on"), new TranslatableComponent("options.off"), (p_168352_) -> {
	      return backgroundForChatOnly;
	   }, (p_168354_, p_168355_, p_168356_) -> {
	      backgroundForChatOnly = p_168356_;
	   }); */
	
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
}
