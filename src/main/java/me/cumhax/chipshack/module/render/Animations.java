package me.cumhax.chipshack.module.render;

import me.cumhax.chipshack.module.Category;
import me.cumhax.chipshack.module.Module;
import me.cumhax.chipshack.setting.Setting;
import me.cumhax.chipshack.mixin.mixins.accessor.IItemRenderer;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.item.ItemSword;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author CumHax
 * @since 2/26/2022
 */
public class Animations extends Module
{

	private final Setting mode = new Setting("Mode", this, Arrays.asList("Normal", "Hard"));

	private IAttributeInstance speed;
	private AttributeModifier attribute;

        public Animations() {
	super("Animations", "", Category.RENDER); 
	} 

	@SubscribeEvent
	public void onTick(TickEvent.ClientTickEvent event)
	{
		if (mc.player == null || mc.world == null) return;

		if (mode.getEnumValue().equals("Hard"))
		{
			if (!(mc.player.inventory.getCurrentItem().getItem() instanceof ItemSword))
			{
				speed = mc.player.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED);
				attribute = new AttributeModifier("attack speed", 21767.0D, 0);
				speed.applyModifier(attribute);
			}
		}
		else
		{
			if (((IItemRenderer) mc.entityRenderer.itemRenderer).getPrevEquippedProgressMainHand() >= 0.9)
			{
				((IItemRenderer) mc.entityRenderer.itemRenderer).setEquippedProgressMainHand(1.0f);
				((IItemRenderer) mc.entityRenderer.itemRenderer).setItemStackMainHand(mc.player.getHeldItem(EnumHand.MAIN_HAND));
			}
			if (((IItemRenderer) mc.entityRenderer.itemRenderer).getPrevEquippedProgressOffHand() >= 0.9)
			{
				((IItemRenderer) mc.entityRenderer.itemRenderer).setEquippedProgressOffHand(1.0f);
				((IItemRenderer) mc.entityRenderer.itemRenderer).setItemStackOffHand(mc.player.getHeldItem(EnumHand.OFF_HAND));
			}
		}
	}

	@Override
	public void onDisable()
	{
		try
		{
			speed.removeModifier(attribute);
		}
		catch (Exception ignored)
		{
		}
	}

}
