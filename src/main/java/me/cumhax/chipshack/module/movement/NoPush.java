package me.cumhax.chipshack.module.movement;

import me.cumhax.chipshack.event.UpdateEvent;
import me.cumhax.chipshack.module.Category;
import me.cumhax.chipshack.module.Module;
import me.cumhax.chipshack.event.Subscribe;


public class NoPush extends Module {
	
	public NoPush() {
        super("NoPush", "", Category.MOVEMENT);
    }

    private float savedReduction;
        @Subscribe
    public void onUpdate(UpdateEvent event) {
        mc.player.entityCollisionReduction = 1.0F;
    }

    @Override
    public void onEnable() {
        savedReduction = mc.player != null ? mc.player.entityCollisionReduction : 0.0f;
    }

    @Override
    public void onDisable() {
        mc.player.entityCollisionReduction = savedReduction;
    }
}
