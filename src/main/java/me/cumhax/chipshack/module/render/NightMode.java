package me.cumhax.chipshack.module.render;

import me.cumhax.chipshack.event.PacketReceiveEvent;
import me.cumhax.chipshack.module.Category;
import me.cumhax.chipshack.module.Module;
import me.cumhax.chipshack.setting.Setting;
import net.minecraft.network.play.server.SPacketTimeUpdate;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class NightMode extends Module 
{
	private final Setting time = new Setting("Time", this, 18000, 0, 23000);
	
	public NightMode() 
	{
		super("NightMode", "", Category.RENDER);
	}
	
	@SubscribeEvent
    public void onUpdate(final TickEvent.ClientTickEvent event) 
	{
		if(nullCheck()) return;
        mc.world.setWorldTime(time.getIntegerValue());
	}
	
	@SubscribeEvent
    public void onPacket(final PacketReceiveEvent event) 
	{
		if(event.getPacket() instanceof SPacketTimeUpdate) 
		{
			event.setCanceled(true);
		}
	}
}