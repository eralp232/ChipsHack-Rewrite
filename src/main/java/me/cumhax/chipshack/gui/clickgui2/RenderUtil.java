package me.cumhax.chipshack.gui.clickgui2;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Color;

public class RenderUtil
{
	public static void drawGradientSideways(final double left, final double top, final double right, final double bottom, final int col1, final int col2) 
	{
        final float f = (col1 >> 24 & 0xFF) / 255.0f;
        final float f2 = (col1 >> 16 & 0xFF) / 255.0f;
        final float f3 = (col1 >> 8 & 0xFF) / 255.0f;
        final float f4 = (col1 & 0xFF) / 255.0f;
        final float f5 = (col2 >> 24 & 0xFF) / 255.0f;
        final float f6 = (col2 >> 16 & 0xFF) / 255.0f;
        final float f7 = (col2 >> 8 & 0xFF) / 255.0f;
        final float f8 = (col2 & 0xFF) / 255.0f;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glShadeModel(7425);
        GL11.glPushMatrix();
        GL11.glBegin(7);
        GL11.glColor4f(f2, f3, f4, f);
        GL11.glVertex2d(left, top);
        GL11.glVertex2d(left, bottom);
        GL11.glColor4f(f6, f7, f8, f5);
        GL11.glVertex2d(right, bottom);
        GL11.glVertex2d(right, top);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glShadeModel(7424);
    }

	public static void drawBorderedRect(double x, double y, double x1, double y1, double width, int internalColor, int borderColor) 
	{
		enableGL2D();
		fakeGuiRect(x + width, y + width, x1 - width, y1 - width, internalColor);
		fakeGuiRect(x + width, y, x1 - width, y + width, borderColor);
		fakeGuiRect(x, y, x + width, y1, borderColor);
		fakeGuiRect(x1 - width, y, x1, y1, borderColor);
		fakeGuiRect(x + width, y1 - width, x1 - width, y1, borderColor);
		disableGL2D();
	}
	
	private static void enableGL2D() 
	{
		GL11.glDisable(2929);
		GL11.glEnable(3042);
		GL11.glDisable(3553);
		GL11.glBlendFunc(770, 771);
		GL11.glDepthMask(true);
		GL11.glEnable(2848);
		GL11.glHint(3154, 4354);
		GL11.glHint(3155, 4354);
	}

	private static void disableGL2D() 
	{
		GL11.glEnable(3553);
		GL11.glDisable(3042);
		GL11.glDisable(2848);
		GL11.glHint(3154, 4352);
		GL11.glHint(3155, 4352);
	}
	
	public static void fakeGuiRect(double left, double top, double right, double bottom, int color) 
	{
		if (left < right) 
		{
			double i = left;
			left = right;
			right = i;
		}

		if (top < bottom) 
		{
			double j = top;
			top = bottom;
			bottom = j;
		}

		float f3 = (float) (color >> 24 & 255) / 255.0F;
		float f = (float) (color >> 16 & 255) / 255.0F;
		float f1 = (float) (color >> 8 & 255) / 255.0F;
		float f2 = (float) (color & 0xFF) / 255.0F;

		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		GlStateManager.enableBlend();
		GlStateManager.disableTexture2D();
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
				GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
				GlStateManager.DestFactor.ZERO);
		GlStateManager.color(f, f1, f2, f3);
		bufferbuilder.begin(7, DefaultVertexFormats.POSITION);
		bufferbuilder.pos(left, bottom, 0.0D).endVertex();
		bufferbuilder.pos(right, bottom, 0.0D).endVertex();
		bufferbuilder.pos(right, top, 0.0D).endVertex();
		bufferbuilder.pos(left, top, 0.0D).endVertex();
		tessellator.draw();
		GlStateManager.enableTexture2D();
		GlStateManager.disableBlend();
	}
	
	public static AxisAlignedBB generateBB(long x, long y, long z) {
        BlockPos blockPos = new BlockPos(x, y, z);
        final AxisAlignedBB bb = new AxisAlignedBB
                (
                        blockPos.getX() - Minecraft.getMinecraft().getRenderManager().viewerPosX,
                        blockPos.getY() - Minecraft.getMinecraft().getRenderManager().viewerPosY,
                        blockPos.getZ() - Minecraft.getMinecraft().getRenderManager().viewerPosZ,
                        blockPos.getX() + 1 - Minecraft.getMinecraft().getRenderManager().viewerPosX,
                        blockPos.getY() + (1) - Minecraft.getMinecraft().getRenderManager().viewerPosY,
                        blockPos.getZ() + 1 - Minecraft.getMinecraft().getRenderManager().viewerPosZ
                );
        return bb;
    }
	
	public static AxisAlignedBB generateBBTop(long x, long y, long z) {
        BlockPos blockPos = new BlockPos(x, y, z);
        final AxisAlignedBB bb = new AxisAlignedBB
                (
                        blockPos.getX() - Minecraft.getMinecraft().getRenderManager().viewerPosX,
                        blockPos.getY() + 1 - Minecraft.getMinecraft().getRenderManager().viewerPosY,
                        blockPos.getZ() - Minecraft.getMinecraft().getRenderManager().viewerPosZ,
                        blockPos.getX() + 1 - Minecraft.getMinecraft().getRenderManager().viewerPosX,
                        blockPos.getY() + 1.1 - Minecraft.getMinecraft().getRenderManager().viewerPosY,
                        blockPos.getZ() + 1 - Minecraft.getMinecraft().getRenderManager().viewerPosZ
                );
        return bb;
    }
	
	public static AxisAlignedBB generateBBPlayer(long x, long y, long z) {
        BlockPos blockPos = new BlockPos(x, y, z);
        final AxisAlignedBB bb = new AxisAlignedBB
                (
                        blockPos.getX() - Minecraft.getMinecraft().getRenderManager().viewerPosX,
                        blockPos.getY() - Minecraft.getMinecraft().getRenderManager().viewerPosY,
                        blockPos.getZ() - Minecraft.getMinecraft().getRenderManager().viewerPosZ,
                        blockPos.getX() + 1 - Minecraft.getMinecraft().getRenderManager().viewerPosX,
                        blockPos.getY() + 2 - Minecraft.getMinecraft().getRenderManager().viewerPosY,
                        blockPos.getZ() + 1 - Minecraft.getMinecraft().getRenderManager().viewerPosZ
                );
        return bb;
    }

	public static void prepareGL() {
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableDepth();
        GlStateManager.disableCull();
        GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask(false);
        glLineWidth(2F);
        glEnable(GL_LINE_SMOOTH);
        glHint(GL_LINE_SMOOTH_HINT, GL_NICEST);
    }
	
	public static void releaseGL() {
        glDisable(GL_LINE_SMOOTH);
        GlStateManager.depthMask(true);
        GlStateManager.enableCull();
        GlStateManager.enableDepth();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }
	
	public static void drawBoxOutline(AxisAlignedBB bb, float r, float g, float b, float a, float n) {
        prepareGL();
        GlStateManager.color(r , g, b, a);
        RenderGlobal.renderFilledBox(bb, r, g, b, n);
        RenderGlobal.drawSelectionBoundingBox(bb, r, g, b, a * 1.5F);
        releaseGL();
    }
	
	public static int getRGB(float seconds, float brightness, float saturation)
	{
        float hue = (System.currentTimeMillis() % (int) (seconds * 1000)) / (seconds * 1000);
        return Color.HSBtoRGB(hue, saturation, brightness);
    }

	public static void drawBoundingBox(AxisAlignedBB bb, Color color, float lineWidth) {
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableDepth();
        GlStateManager.tryBlendFuncSeparate((int)770, (int)771, (int)0, (int)1);
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask((boolean)false);
        GL11.glEnable((int)2848);
        GL11.glHint((int)3154, (int)4354);
        GL11.glLineWidth((float)lineWidth);
        RenderGlobal.drawBoundingBox((double)bb.minX, (double)bb.minY, (double)bb.minZ, (double)bb.maxX, (double)bb.maxY, (double)bb.maxZ, (float)((float)color.getRed() / 255.0f), (float)((float)color.getGreen() / 255.0f), (float)((float)color.getBlue() / 255.0f), (float)((float)color.getAlpha() / 255.0f));
        RenderGlobal.renderFilledBox(bb, color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, 0.105F);
        GL11.glDisable((int)2848);
        GlStateManager.depthMask((boolean)true);
        GlStateManager.enableDepth();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }
}
