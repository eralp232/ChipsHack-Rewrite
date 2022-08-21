package me.cumhax.chipshack.module.render;

import me.cumhax.chipshack.module.Category;
import me.cumhax.chipshack.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Fullbright extends Module {
	
	public Fullbright() {
        super("Fullbright", "", Category.RENDER);
    }


    @Override
    public void onEnable() {
        Minecraft.getMinecraft().gameSettings.gammaSetting = 10000f;

    }

    @Override
    public void onDisable() {
        Minecraft.getMinecraft().gameSettings.gammaSetting = 1.0f;

    }

    public void onTick(TickEvent.ClientTickEvent var1) {

    }
}
