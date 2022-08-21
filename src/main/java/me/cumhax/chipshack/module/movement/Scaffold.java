package me.cumhax.chipshack.module.movement;

import me.cumhax.chipshack.module.Category;
import me.cumhax.chipshack.module.Module;
import me.cumhax.chipshack.util.PlaceUtil;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Scaffold extends Module
{
    public Scaffold() {
        super("Scaffold", "", Category.MOVEMENT);
    }

	@SubscribeEvent
	public void onTick(TickEvent.ClientTickEvent event)
	{
		BlockPos pos = new BlockPos(mc.player.posX, mc.player.posY - 1, mc.player.posZ);

		if (PlaceUtil.canPlaceBlock(pos))
		{
			PlaceUtil.placeBlock(pos, getBlockSlot(), true, true);
		}
	}

	private int getBlockSlot()
	{
		int slot = 0;

		for (int i = 0; i < 9; i++)
		{
			ItemStack stack = mc.player.inventory.getStackInSlot(i);
			if (stack.getItem() instanceof ItemBlock)
			{
				return slot;
			}
		}
		return slot;
	}
}
