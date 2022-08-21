package me.cumhax.chipshack.module.misc;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.util.EnumHand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.RayTraceResult;
import org.lwjgl.input.Mouse;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import me.cumhax.chipshack.module.Category;
import me.cumhax.chipshack.module.Module;

public class MultiTask extends Module
{
	public MultiTask()
	{
		super("MultiTask", "", Category.MISC);
    }
    
    @SubscribeEvent
    public void onMouseInput(final InputEvent.MouseInputEvent event) {
        if (Mouse.getEventButtonState() && this.mc.player != null && this.mc.objectMouseOver.typeOfHit.equals((Object)RayTraceResult.Type.ENTITY) && this.mc.player.isHandActive() && (this.mc.gameSettings.keyBindAttack.isPressed() || Mouse.getEventButton() == this.mc.gameSettings.keyBindAttack.getKeyCode())) {
            this.mc.playerController.attackEntity((EntityPlayer)this.mc.player, this.mc.objectMouseOver.entityHit);
            this.mc.player.swingArm(EnumHand.MAIN_HAND);
        }
    }
}
