package me.cumhax.chipshack.module.movement;

import me.cumhax.chipshack.event.MoveEvent;
import me.cumhax.chipshack.module.Category;
import me.cumhax.chipshack.module.Module;
import me.cumhax.chipshack.setting.Setting;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Step extends Module
{
	private final Setting stepHeight = new Setting("StepHeight", this, 5, 1, 10);
	
	public Step()
	{
		super("Step", "", Category.MOVEMENT);
	}
	
	@SubscribeEvent
    public void onMove(MoveEvent event)
	{
		if(nullCheck()) return;
		mc.player.stepHeight = stepHeight.getIntegerValue();
    }
	
	public void onDisable() {
        mc.player.stepHeight = 0.5f;
   }
}

