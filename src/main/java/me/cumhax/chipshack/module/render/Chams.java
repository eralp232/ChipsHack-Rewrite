package me.cumhax.chipshack.module.render;

import me.cumhax.chipshack.event.EventModelPlayerRender;
import me.cumhax.chipshack.module.Category;
import me.cumhax.chipshack.module.Module;
import me.cumhax.chipshack.setting.Setting;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class Chams extends Module {

    private final Setting Vr = new Setting("Red", this, 255, 0, 255);
    private final Setting Vg = new Setting("Green", this, 255, 0, 255);
    private final Setting Vb = new Setting("Blue", this, 255, 0, 255);
    private final Setting alpha = new Setting("Alpha", this, 255, 0, 255);
    Setting lines = new Setting("Lines", this, true);
    private final Setting width = new Setting("Width", this, 40, 0, 100);
    Setting crystals = new Setting("Crystals", this, true);

    public Chams() {
        super("Chams", "doesnt work rn", Category.RENDER);
    }

    @SubscribeEvent
    public void onPlayerModel(final EventModelPlayerRender event) {
        final Color c = new Color(this.Vr.getIntegerValue(), this.Vg.getIntegerValue(), this.Vb.getIntegerValue());
        if (event.type == 0) {
            GL11.glPushMatrix();
            GL11.glEnable(32823);
            GL11.glPolygonOffset(1.0f, -1.0E7f);
            GL11.glPushAttrib(1048575);
            if (!this.lines.getBooleanValue()) {
                GL11.glPolygonMode(1028, 6914);
            } else {
                GL11.glPolygonMode(1028, 6913);
            }
            GL11.glDisable(3553);
            GL11.glDisable(2896);
            GL11.glDisable(2929);
            GL11.glEnable(2848);
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glColor4f(c.getRed() / 255.0f, c.getGreen() / 255.0f, c.getBlue() / 255.0f, this.alpha.getIntegerValue() / 255.0f / 2.0f);
            if (this.lines.getBooleanValue()) {
                GL11.glLineWidth(this.width.getIntegerValue() / 10.0f);
            }
        } else if (event.type == 1) {
            GL11.glPopAttrib();
            GL11.glPolygonOffset(1.0f, 1.0E7f);
            GL11.glDisable(32823);
            GL11.glPopMatrix();
        }
    }

}
 
