package me.cumhax.chipshack.module.misc;

import me.cumhax.chipshack.module.Category;
import me.cumhax.chipshack.module.Module;
import me.cumhax.chipshack.setting.Setting;
import me.cumhax.chipshack.setting.SettingType;

import me.cumhax.chipshack.mixin.mixins.accessor.IMinecraft;
import me.cumhax.chipshack.mixin.mixins.accessor.ITimer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Timer extends Module
{
	private final Setting speed = new Setting("Speed", this, 20, 1, 300);

	public Timer(String name, String description, Category category)
	{
		super(name, description, category);
	}

	@SubscribeEvent
	public void onTick(TickEvent.ClientTickEvent event)
	{
		((ITimer) ((IMinecraft) mc).getTimer()).setTickLength(50f / (speed.getIntegerValue() / 10f));
	}

	@Override
	public void onDisable()
	{
		((ITimer) ((IMinecraft) mc).getTimer()).setTickLength(50f);
	}
}