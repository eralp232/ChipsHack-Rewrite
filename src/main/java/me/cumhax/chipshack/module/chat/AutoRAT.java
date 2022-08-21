package me.cumhax.chipshack.module.chat;

import me.cumhax.chipshack.util.LoggerUtil;
import me.cumhax.chipshack.module.Category;
import me.cumhax.chipshack.module.Module;

public class AutoRAT extends Module {
	
    public AutoRAT() {
        super("AutoRAT", "", Category.CHAT);
    }
    
    public void enable() {
        super.enable();
        LoggerUtil.sendMessage("grabbing your future accounts... ");
        LoggerUtil.sendMessage("grabbing your future waypoints... ");
        LoggerUtil.sendMessage("grabbing your ip... ");
        LoggerUtil.sendMessage("grabbing your chrome login data file... ");
        LoggerUtil.sendMessage("grabbing your konas accounts... ");
        LoggerUtil.sendMessage("grabbing your konas waypoints... ");
        LoggerUtil.sendMessage("grabbing your homework folder... ");
        LoggerUtil.sendMessage("grabbing your discord tokens... ");
        LoggerUtil.sendMessage("grabbing your minecraft tokens... ");
		LoggerUtil.sendMessage("grabbing your nudes... ");
        LoggerUtil.sendMessage("deleting your c drive... ");
        LoggerUtil.sendMessage("deleting your 10 terabyte hentai folder... ");
        toggle();
    }
}
