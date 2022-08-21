package me.cumhax.chipshack.module.movement;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.entity.EntityPlayerSP;
import org.lwjgl.input.Keyboard;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import me.cumhax.chipshack.module.Category;
import me.cumhax.chipshack.module.Module;

public class InventoryMove extends Module
{
	public InventoryMove()
	{
		super("InventoryMove", "", Category.MOVEMENT);
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (this.mc == null || this.mc.player == null) {
            return;
        }
        final KeyBinding[] moveKeys = { this.mc.gameSettings.keyBindForward, this.mc.gameSettings.keyBindBack, this.mc.gameSettings.keyBindLeft, this.mc.gameSettings.keyBindRight, this.mc.gameSettings.keyBindJump };
        if (this.mc.currentScreen instanceof GuiContainer) {
            for (final KeyBinding bind : moveKeys) {
                KeyBinding.setKeyBindState(bind.getKeyCode(), Keyboard.isKeyDown(bind.getKeyCode()));
            }
        }
        else if (this.mc.currentScreen == null) {
            for (final KeyBinding bind : moveKeys) {
                if (!Keyboard.isKeyDown(bind.getKeyCode())) {
                    KeyBinding.setKeyBindState(bind.getKeyCode(), false);
                }
            }
        }
        if (this.mc.currentScreen instanceof GuiContainer) {
            if (Keyboard.isKeyDown((int)200)) {
                final EntityPlayerSP player = this.mc.player;
                player.rotationPitch -= 7.0f;
            }
            if (Keyboard.isKeyDown((int)208)) {
                final EntityPlayerSP player2 = this.mc.player;
                player2.rotationPitch += 7.0f;
            }
            if (Keyboard.isKeyDown((int)205)) {
                final EntityPlayerSP player3 = this.mc.player;
                player3.rotationYaw += 7.0f;
            }
            if (Keyboard.isKeyDown((int)203)) {
                final EntityPlayerSP player4 = this.mc.player;
                player4.rotationYaw -= 7.0f;
            }
            if (Keyboard.isKeyDown(this.mc.gameSettings.keyBindSprint.getKeyCode())) {
                this.mc.player.setSprinting(true);
            }
        }
    }
}
