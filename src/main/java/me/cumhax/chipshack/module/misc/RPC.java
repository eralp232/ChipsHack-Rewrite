package me.cumhax.chipshack.module.misc;

import me.cumhax.chipshack.module.Category;
import me.cumhax.chipshack.module.Module;
import me.cumhax.chipshack.Discord;

public class RPC extends Module {
	
	public RPC() {
        super("RPC", "", Category.MISC);
    }

    public void enable() {
        Discord.start();
    }

    public void disable() {
        Discord.stop();
    }
}
