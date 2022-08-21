package me.cumhax.chipshack.module.render;

import me.cumhax.chipshack.module.Category;
import me.cumhax.chipshack.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class AntiWeather extends Module {
	
		public AntiWeather() {
        super("AntiWeather", "", Category.RENDER);
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    public void onTick(TickEvent.ClientTickEvent var1) {
        if (Minecraft.getMinecraft().world.isRaining()) {
            Minecraft.getMinecraft().world.setRainStrength(0);
        }
	}
}