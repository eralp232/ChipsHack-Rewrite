package me.cumhax.chipshack.module.chat;

import me.cumhax.chipshack.module.Category;
import me.cumhax.chipshack.module.Module;
import org.lwjgl.input.Keyboard;

public class AutoSuicide extends Module {

	public AutoSuicide() {
		super("AutoSuicide", "", Category.CHAT);
	}
	
    @Override
    public void onEnable() {
        if (mc.player != null) {
            mc.player.sendChatMessage("/kill");
            toggle();
        }
    }
}