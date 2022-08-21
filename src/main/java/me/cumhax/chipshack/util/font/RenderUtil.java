package me.cumhax.chipshack.util.font;

import java.awt.Color;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

public class RenderUtil {
    public static void prepare() {
        GlStateManager.pushMatrix();
        GlStateManager.disableLighting();
        GlStateManager.enableBlend();
        GlStateManager.disableDepth();
        GlStateManager.enableAlpha();
        GlStateManager.disableCull();
        GlStateManager.tryBlendFuncSeparate((int)770, (int)771, (int)0, (int)1);
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask((boolean)false);
        GL11.glEnable((int)2848);
        GL11.glEnable((int)34383);
        GL11.glHint((int)3154, (int)4354);
    }

    public static void release() {
        GL11.glDisable((int)2848);
        GL11.glDisable((int)34383);
        GlStateManager.depthMask((boolean)true);
        GlStateManager.enableCull();
        GlStateManager.enableDepth();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.enableLighting();
        GlStateManager.popMatrix();
    }

    public static Color genRainbow(int delay) {
        double rainbowState = Math.ceil((double)(System.currentTimeMillis() + (long)delay) / 20.0);
        return Color.getHSBColor((float)(rainbowState % 360.0 / 360.0), 1.0f, 1.0f);
    }
}
