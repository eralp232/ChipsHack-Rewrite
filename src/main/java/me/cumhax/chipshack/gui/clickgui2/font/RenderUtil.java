package me.cumhax.chipshack.gui.clickgui2.font;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

import me.cumhax.chipshack.mixin.mixins.accessor.IRenderManager;

import java.awt.*;
import java.util.HashMap;
import java.util.Objects;

import static org.lwjgl.opengl.GL11.*;

public class RenderUtil extends net.minecraft.client.renderer.Tessellator {

	public static final HashMap<EnumFacing, Integer> FACEMAP = new HashMap<>();
	public static final float DEFAULT_COLOR_SATURATION = 0.95F;
	public static final float DEFAULT_COLOR_BRIGHTNESS = 0.95F;
	private final static Frustum frustrum = new Frustum();
	public static RenderUtil INSTANCE = new RenderUtil();
	static Minecraft mc = Minecraft.getMinecraft();

	static {
		FACEMAP.put(EnumFacing.DOWN, Quad.DOWN);
		FACEMAP.put(EnumFacing.WEST, Quad.WEST);
		FACEMAP.put(EnumFacing.NORTH, Quad.NORTH);
		FACEMAP.put(EnumFacing.SOUTH, Quad.SOUTH);
		FACEMAP.put(EnumFacing.EAST, Quad.EAST);
		FACEMAP.put(EnumFacing.UP, Quad.UP);
	}

	public RenderUtil() {
		super(0x200000);
	}

	public static void prepare(int mode) {
		prepareGL();
		begin(mode);
	}

	public static boolean isInViewFrustrum(AxisAlignedBB bb) {
		Entity current = Minecraft.getMinecraft().getRenderViewEntity();
		frustrum.setPosition(Objects.requireNonNull(current).posX, current.posY, current.posZ);
		return frustrum.isBoundingBoxInFrustum(bb);
	}

	public static void drawBox(AxisAlignedBB boundingBox) {
		if (boundingBox == null) {
			return;
		}

		GlStateManager.glBegin(GL11.GL_QUADS);
		GlStateManager.glVertex3f((float) boundingBox.minX, (float) boundingBox.minY, (float) boundingBox.maxZ);
		GlStateManager.glVertex3f((float) boundingBox.maxX, (float) boundingBox.minY, (float) boundingBox.maxZ);
		GlStateManager.glVertex3f((float) boundingBox.maxX, (float) boundingBox.maxY, (float) boundingBox.maxZ);
		GlStateManager.glVertex3f((float) boundingBox.minX, (float) boundingBox.maxY, (float) boundingBox.maxZ);
		GlStateManager.glEnd();

		GlStateManager.glBegin(GL11.GL_QUADS);
		GlStateManager.glVertex3f((float) boundingBox.maxX, (float) boundingBox.minY, (float) boundingBox.maxZ);
		GlStateManager.glVertex3f((float) boundingBox.minX, (float) boundingBox.minY, (float) boundingBox.maxZ);
		GlStateManager.glVertex3f((float) boundingBox.minX, (float) boundingBox.maxY, (float) boundingBox.maxZ);
		GlStateManager.glVertex3f((float) boundingBox.maxX, (float) boundingBox.maxY, (float) boundingBox.maxZ);
		GlStateManager.glEnd();

		GlStateManager.glBegin(GL11.GL_QUADS);
		GlStateManager.glVertex3f((float) boundingBox.minX, (float) boundingBox.minY, (float) boundingBox.minZ);
		GlStateManager.glVertex3f((float) boundingBox.minX, (float) boundingBox.minY, (float) boundingBox.maxZ);
		GlStateManager.glVertex3f((float) boundingBox.minX, (float) boundingBox.maxY, (float) boundingBox.maxZ);
		GlStateManager.glVertex3f((float) boundingBox.minX, (float) boundingBox.maxY, (float) boundingBox.minZ);
		GlStateManager.glEnd();

		GlStateManager.glBegin(GL11.GL_QUADS);
		GlStateManager.glVertex3f((float) boundingBox.minX, (float) boundingBox.minY, (float) boundingBox.maxZ);
		GlStateManager.glVertex3f((float) boundingBox.minX, (float) boundingBox.minY, (float) boundingBox.minZ);
		GlStateManager.glVertex3f((float) boundingBox.minX, (float) boundingBox.maxY, (float) boundingBox.minZ);
		GlStateManager.glVertex3f((float) boundingBox.minX, (float) boundingBox.maxY, (float) boundingBox.maxZ);
		GlStateManager.glEnd();

		GlStateManager.glBegin(GL11.GL_QUADS);
		GlStateManager.glVertex3f((float) boundingBox.maxX, (float) boundingBox.minY, (float) boundingBox.maxZ);
		GlStateManager.glVertex3f((float) boundingBox.maxX, (float) boundingBox.minY, (float) boundingBox.minZ);
		GlStateManager.glVertex3f((float) boundingBox.maxX, (float) boundingBox.maxY, (float) boundingBox.minZ);
		GlStateManager.glVertex3f((float) boundingBox.maxX, (float) boundingBox.maxY, (float) boundingBox.maxZ);
		GlStateManager.glEnd();

		GlStateManager.glBegin(GL11.GL_QUADS);
		GlStateManager.glVertex3f((float) boundingBox.maxX, (float) boundingBox.minY, (float) boundingBox.minZ);
		GlStateManager.glVertex3f((float) boundingBox.maxX, (float) boundingBox.minY, (float) boundingBox.maxZ);
		GlStateManager.glVertex3f((float) boundingBox.maxX, (float) boundingBox.maxY, (float) boundingBox.maxZ);
		GlStateManager.glVertex3f((float) boundingBox.maxX, (float) boundingBox.maxY, (float) boundingBox.minZ);
		GlStateManager.glEnd();

		GlStateManager.glBegin(GL11.GL_QUADS);
		GlStateManager.glVertex3f((float) boundingBox.minX, (float) boundingBox.minY, (float) boundingBox.minZ);
		GlStateManager.glVertex3f((float) boundingBox.maxX, (float) boundingBox.minY, (float) boundingBox.minZ);
		GlStateManager.glVertex3f((float) boundingBox.maxX, (float) boundingBox.maxY, (float) boundingBox.minZ);
		GlStateManager.glVertex3f((float) boundingBox.minX, (float) boundingBox.maxY, (float) boundingBox.minZ);
		GlStateManager.glEnd();

		GlStateManager.glBegin(GL11.GL_QUADS);
		GlStateManager.glVertex3f((float) boundingBox.maxX, (float) boundingBox.minY, (float) boundingBox.minZ);
		GlStateManager.glVertex3f((float) boundingBox.minX, (float) boundingBox.minY, (float) boundingBox.minZ);
		GlStateManager.glVertex3f((float) boundingBox.minX, (float) boundingBox.maxY, (float) boundingBox.minZ);
		GlStateManager.glVertex3f((float) boundingBox.maxX, (float) boundingBox.maxY, (float) boundingBox.minZ);
		GlStateManager.glEnd();

		GlStateManager.glBegin(GL11.GL_QUADS);
		GlStateManager.glVertex3f((float) boundingBox.minX, (float) boundingBox.maxY, (float) boundingBox.minZ);
		GlStateManager.glVertex3f((float) boundingBox.maxX, (float) boundingBox.maxY, (float) boundingBox.minZ);
		GlStateManager.glVertex3f((float) boundingBox.maxX, (float) boundingBox.maxY, (float) boundingBox.maxZ);
		GlStateManager.glVertex3f((float) boundingBox.minX, (float) boundingBox.maxY, (float) boundingBox.maxZ);
		GlStateManager.glEnd();

		GlStateManager.glBegin(GL11.GL_QUADS);
		GlStateManager.glVertex3f((float) boundingBox.maxX, (float) boundingBox.maxY, (float) boundingBox.minZ);
		GlStateManager.glVertex3f((float) boundingBox.minX, (float) boundingBox.maxY, (float) boundingBox.minZ);
		GlStateManager.glVertex3f((float) boundingBox.minX, (float) boundingBox.maxY, (float) boundingBox.maxZ);
		GlStateManager.glVertex3f((float) boundingBox.maxX, (float) boundingBox.maxY, (float) boundingBox.maxZ);
		GlStateManager.glEnd();

		GlStateManager.glBegin(GL11.GL_QUADS);
		GlStateManager.glVertex3f((float) boundingBox.minX, (float) boundingBox.minY, (float) boundingBox.minZ);
		GlStateManager.glVertex3f((float) boundingBox.maxX, (float) boundingBox.minY, (float) boundingBox.minZ);
		GlStateManager.glVertex3f((float) boundingBox.maxX, (float) boundingBox.minY, (float) boundingBox.maxZ);
		GlStateManager.glVertex3f((float) boundingBox.minX, (float) boundingBox.minY, (float) boundingBox.maxZ);
		GlStateManager.glEnd();

		GlStateManager.glBegin(GL11.GL_QUADS);
		GlStateManager.glVertex3f((float) boundingBox.maxX, (float) boundingBox.minY, (float) boundingBox.minZ);
		GlStateManager.glVertex3f((float) boundingBox.minX, (float) boundingBox.minY, (float) boundingBox.minZ);
		GlStateManager.glVertex3f((float) boundingBox.minX, (float) boundingBox.minY, (float) boundingBox.maxZ);
		GlStateManager.glVertex3f((float) boundingBox.maxX, (float) boundingBox.minY, (float) boundingBox.maxZ);
		GlStateManager.glEnd();
	}

	public static void drawOutlinedBox(AxisAlignedBB bb) {
		GL11.glBegin(GL11.GL_LINES);
		{
			GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
			GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);

			GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
			GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);

			GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
			GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);

			GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
			GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);

			GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
			GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);

			GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
			GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);

			GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
			GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);

			GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
			GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);

			GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
			GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);

			GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
			GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);

			GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
			GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);

			GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
			GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
		}
		GL11.glEnd();
	}

	public static void drawESPOutline(AxisAlignedBB bb, float red, float green, float blue, float alpha, float width) {
		GL11.glPushMatrix();
		GL11.glEnable(3042);
		GL11.glBlendFunc(770, 771);
		GL11.glDisable(3553);
		GL11.glEnable(2848);
		GL11.glDisable(2929);
		GL11.glDepthMask(false);
		GL11.glLineWidth(width);
		GL11.glColor4f(red / 255f, green / 255f, blue / 255f, alpha / 255f);
		drawOutlinedBox(bb);
		GL11.glDisable(2848);
		GL11.glEnable(3553);
		GL11.glEnable(2929);
		GL11.glDepthMask(true);
		GL11.glDisable(3042);
		GL11.glPopMatrix();
		GL11.glColor4f(1f, 1f, 1f, 1f);
	}

	public static void drawESP(AxisAlignedBB bb, float red, float green, float blue, float alpha) {
		GL11.glPushMatrix();
		GL11.glEnable(3042);
		GL11.glBlendFunc(770, 771);
		GL11.glDisable(3553);
		GL11.glEnable(2848);
		GL11.glDisable(2929);
		GL11.glDepthMask(false);
		GL11.glColor4f(red / 255f, green / 255f, blue / 255f, alpha / 255f);
		drawBox(bb);
		GL11.glDisable(2848);
		GL11.glEnable(3553);
		GL11.glEnable(2929);
		GL11.glDepthMask(true);
		GL11.glDisable(3042);
		GL11.glPopMatrix();
		GL11.glColor4f(1f, 1f, 1f, 1f);
	}

	public static void renderTag(String name, double pX, double pY, double pZ, int color) {
		float scale = (float) (mc.player.getDistance(pX + ((IRenderManager) mc.getRenderManager()).getRenderPosX(),
				pY + ((IRenderManager) mc.getRenderManager()).getRenderPosY(),
				pZ + ((IRenderManager) mc.getRenderManager()).getRenderPosZ()) / 8.0D);
		if (scale < 1.6F) {
			scale = 1.6F;
		}
		scale /= 50;
		GL11.glPushMatrix();
		GL11.glTranslatef((float) pX, (float) pY + 1.4F, (float) pZ);
		GL11.glNormal3f(0.0F, 1.0F, 0.0F);
		GL11.glRotatef(-mc.getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(mc.getRenderManager().playerViewX, 1.0F, 0.0F, 0.0F);
		GL11.glScalef(-scale, -scale, scale);
		GL11.glDisable(2896);
		GL11.glDisable(2929);
		GL11.glEnable(3042);
		GL11.glBlendFunc(770, 771);

		int width = mc.fontRenderer.getStringWidth(name) / 2;
		GL11.glPushMatrix();
		GL11.glPopMatrix();
		GL11.glColor4f(1, 1, 1, 1);
		GL11.glScalef(0.5f, 0.5f, 0.5f);
		mc.fontRenderer.drawStringWithShadow(name, -(width), -(mc.fontRenderer.FONT_HEIGHT + 7), color);
		GL11.glScalef(1f, 1f, 1f);
		GlStateManager.enableTexture2D();
		GL11.glDisable(3042);
		GL11.glEnable(2896);
		GL11.glEnable(2929);
		GL11.glPopMatrix();
		GL11.glColor4f(1f, 1f, 1f, 1f);
	}

	public static void drawGradientSideways(final double left, final double top, final double right,
			final double bottom, final int col1, final int col2) {
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

	public static void drawGradient(final double x, final double y, final double x2, final double y2, final int col1,
			final int col2) {
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
		GL11.glVertex2d(x2, y);
		GL11.glVertex2d(x, y);
		GL11.glColor4f(f6, f7, f8, f5);
		GL11.glVertex2d(x, y2);
		GL11.glVertex2d(x2, y2);
		GL11.glEnd();
		GL11.glPopMatrix();
		GL11.glEnable(3553);
		GL11.glDisable(3042);
		GL11.glDisable(2848);
		GL11.glShadeModel(7424);
		GL11.glColor4d(1.0, 1.0, 1.0, 1.0);
	}

	public static void drawBorderedRect(double x, double y, double x1, double y1, double width, int internalColor,
			int borderColor) {
		enableGL2D();
		fakeGuiRect(x + width, y + width, x1 - width, y1 - width, internalColor);
		fakeGuiRect(x + width, y, x1 - width, y + width, borderColor);
		fakeGuiRect(x, y, x + width, y1, borderColor);
		fakeGuiRect(x1 - width, y, x1, y1, borderColor);
		fakeGuiRect(x + width, y1 - width, x1 - width, y1, borderColor);
		disableGL2D();
	}

	public static void rectangleBordered(final double x, final double y, final double x1, final double y1,
			final double width, final int internalColor, final int borderColor) {
		fakeGuiRect(x + width, y + width, x1 - width, y1 - width, internalColor);
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		fakeGuiRect(x + width, y, x1 - width, y + width, borderColor);
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		fakeGuiRect(x, y, x + width, y1, borderColor);
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		fakeGuiRect(x1 - width, y, x1, y1, borderColor);
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		fakeGuiRect(x + width, y1 - width, x1 - width, y1, borderColor);
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
	}

	public static void drawRect(final float x, final float y, final float x1, final float y1) {
		GL11.glBegin(7);
		GL11.glVertex2f(x, y1);
		GL11.glVertex2f(x1, y1);
		GL11.glVertex2f(x1, y);
		GL11.glVertex2f(x, y);
		GL11.glEnd();
	}

	public static void color(int color) {
		GL11.glColor4f((color >> 16 & 0xFF) / 255f, (color >> 8 & 0xFF) / 255f, (color & 0xFF) / 255f,
				(color >> 24 & 0xFF) / 255f);
	}

	public static int generateRainbowFadingColor(int offset, boolean drastic) {
		long offset_ = (drastic ? 200000000L : 20000000L) * offset;
		float hue = (System.nanoTime() + offset_) / 4.0E9f % 1.0F;
		return (int) Long.parseLong(
				Integer.toHexString(Color.HSBtoRGB(hue, DEFAULT_COLOR_SATURATION, DEFAULT_COLOR_BRIGHTNESS)), 16);
	}

	public static void fakeGuiRect(double left, double top, double right, double bottom, int color) {
		if (left < right) {
			double i = left;
			left = right;
			right = i;
		}

		if (top < bottom) {
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

	private static void enableGL2D() {
		GL11.glDisable(2929);
		GL11.glEnable(3042);
		GL11.glDisable(3553);
		GL11.glBlendFunc(770, 771);
		GL11.glDepthMask(true);
		GL11.glEnable(2848);
		GL11.glHint(3154, 4354);
		GL11.glHint(3155, 4354);
	}

	private static void disableGL2D() {
		GL11.glEnable(3553);
		GL11.glDisable(3042);
		GL11.glDisable(2848);
		GL11.glHint(3154, 4352);
		GL11.glHint(3155, 4352);
	}

	public static void drawBoxBottom(BufferBuilder buffer, float x, float y, float z, float w, float d, int r, int g,
			int b, int a) {
		buffer.pos((x + w), y, z).color(r, g, b, a).endVertex();
		buffer.pos((x + w), y, (z + d)).color(r, g, b, a).endVertex();
		buffer.pos(x, y, (z + d)).color(r, g, b, a).endVertex();
		buffer.pos(x, y, z).color(r, g, b, a).endVertex();
	}

	public static void drawBoxBottom(BlockPos blockPos, int r, int g, int b, int a) {
		drawBoxBottom(INSTANCE.getBuffer(), blockPos.getX(), blockPos.getY(), blockPos.getZ(), 1.0f, 1.0f, r, g, b, a);
	}

	public static void prepareGL() {
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
				GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
				GlStateManager.DestFactor.ZERO);
		GlStateManager.glLineWidth(1.5F);
		GlStateManager.disableTexture2D();
		GlStateManager.depthMask(false);
		GlStateManager.enableBlend();
		GlStateManager.disableDepth();
		GlStateManager.disableLighting();
		GlStateManager.disableCull();
		GlStateManager.enableAlpha();
		GlStateManager.color(1, 1, 1);
	}

	public static void begin(int mode) {
		INSTANCE.getBuffer().begin(mode, DefaultVertexFormats.POSITION_COLOR);
	}

	public static void render() {
		INSTANCE.draw();
	}

	public static void release() {
		render();
		releaseGL();
	}

	public static void releaseGL() {
		GlStateManager.enableCull();
		GlStateManager.depthMask(true);
		GlStateManager.enableTexture2D();
		GlStateManager.enableBlend();
		GlStateManager.enableDepth();
		GlStateManager.color(1, 1, 1);
		GL11.glColor4f(1, 1, 1, 1);
	}

	public static void drawBox(BlockPos blockPos, int argb, int sides) {
		final int a = (argb >>> 24) & 0xFF;
		final int r = (argb >>> 16) & 0xFF;
		final int g = (argb >>> 8) & 0xFF;
		final int b = argb & 0xFF;
		drawBox(blockPos, r, g, b, a, sides);
	}

	public static void drawBox(BlockPos blockPos, int r, int g, int b, int a, int sides) {
		drawBox(INSTANCE.getBuffer(), blockPos.getX(), blockPos.getY(), blockPos.getZ(), 1, 1, 1, r, g, b, a, sides);
	}

	public static void drawBox(BufferBuilder buffer, float x, float y, float z, float w, float h, float d, int r, int g,
			int b, int a, int sides) {
		if ((sides & Quad.DOWN) != 0) {
			buffer.pos(x + w, y, z).color(r, g, b, a).endVertex();
			buffer.pos(x + w, y, z + d).color(r, g, b, a).endVertex();
			buffer.pos(x, y, z + d).color(r, g, b, a).endVertex();
			buffer.pos(x, y, z).color(r, g, b, a).endVertex();
		}

		if ((sides & Quad.UP) != 0) {
			buffer.pos(x + w, y + h, z).color(r, g, b, a).endVertex();
			buffer.pos(x, y + h, z).color(r, g, b, a).endVertex();
			buffer.pos(x, y + h, z + d).color(r, g, b, a).endVertex();
			buffer.pos(x + w, y + h, z + d).color(r, g, b, a).endVertex();
		}

		if ((sides & Quad.NORTH) != 0) {
			buffer.pos(x + w, y, z).color(r, g, b, a).endVertex();
			buffer.pos(x, y, z).color(r, g, b, a).endVertex();
			buffer.pos(x, y + h, z).color(r, g, b, a).endVertex();
			buffer.pos(x + w, y + h, z).color(r, g, b, a).endVertex();
		}

		if ((sides & Quad.SOUTH) != 0) {
			buffer.pos(x, y, z + d).color(r, g, b, a).endVertex();
			buffer.pos(x + w, y, z + d).color(r, g, b, a).endVertex();
			buffer.pos(x + w, y + h, z + d).color(r, g, b, a).endVertex();
			buffer.pos(x, y + h, z + d).color(r, g, b, a).endVertex();
		}

		if ((sides & Quad.WEST) != 0) {
			buffer.pos(x, y, z).color(r, g, b, a).endVertex();
			buffer.pos(x, y, z + d).color(r, g, b, a).endVertex();
			buffer.pos(x, y + h, z + d).color(r, g, b, a).endVertex();
			buffer.pos(x, y + h, z).color(r, g, b, a).endVertex();
		}

		if ((sides & Quad.EAST) != 0) {
			buffer.pos(x + w, y, z + d).color(r, g, b, a).endVertex();
			buffer.pos(x + w, y, z).color(r, g, b, a).endVertex();
			buffer.pos(x + w, y + h, z).color(r, g, b, a).endVertex();
			buffer.pos(x + w, y + h, z + d).color(r, g, b, a).endVertex();
		}
	}

	public static void drawBox(AxisAlignedBB bb, int argb, int sides) {
		final int a = (argb >>> 24) & 0xFF;
		final int r = (argb >>> 16) & 0xFF;
		final int g = (argb >>> 8) & 0xFF;
		final int b = argb & 0xFF;
		drawBox(INSTANCE.getBuffer(), bb, r, g, b, a, sides);
	}

	public static void drawBox(BufferBuilder buffer, AxisAlignedBB bb, int r, int g, int b, int a, int sides) {
		if ((sides & Quad.DOWN) != 0) {
			buffer.pos(bb.maxX, bb.minY, bb.minZ).color(r, g, b, a).endVertex();
			buffer.pos(bb.maxX, bb.minY, bb.maxZ).color(r, g, b, a).endVertex();
			buffer.pos(bb.minX, bb.minY, bb.maxZ).color(r, g, b, a).endVertex();
			buffer.pos(bb.minX, bb.minY, bb.minZ).color(r, g, b, a).endVertex();
		}
		if ((sides & Quad.UP) != 0) {
			buffer.pos(bb.maxX, bb.maxY, bb.minZ).color(r, g, b, a).endVertex();
			buffer.pos(bb.minX, bb.maxY, bb.minZ).color(r, g, b, a).endVertex();
			buffer.pos(bb.minX, bb.maxY, bb.maxZ).color(r, g, b, a).endVertex();
			buffer.pos(bb.maxX, bb.maxY, bb.maxZ).color(r, g, b, a).endVertex();
		}
		if ((sides & Quad.NORTH) != 0) {
			buffer.pos(bb.maxX, bb.minY, bb.minZ).color(r, g, b, a).endVertex();
			buffer.pos(bb.minX, bb.minY, bb.minZ).color(r, g, b, a).endVertex();
			buffer.pos(bb.minX, bb.maxY, bb.minZ).color(r, g, b, a).endVertex();
			buffer.pos(bb.maxX, bb.maxY, bb.minZ).color(r, g, b, a).endVertex();
		}
		if ((sides & Quad.SOUTH) != 0) {
			buffer.pos(bb.minX, bb.minY, bb.maxZ).color(r, g, b, a).endVertex();
			buffer.pos(bb.maxX, bb.minY, bb.maxZ).color(r, g, b, a).endVertex();
			buffer.pos(bb.maxX, bb.maxY, bb.maxZ).color(r, g, b, a).endVertex();
			buffer.pos(bb.minX, bb.maxY, bb.maxZ).color(r, g, b, a).endVertex();
		}
		if ((sides & Quad.WEST) != 0) {
			buffer.pos(bb.minX, bb.minY, bb.minZ).color(r, g, b, a).endVertex();
			buffer.pos(bb.minX, bb.minY, bb.maxZ).color(r, g, b, a).endVertex();
			buffer.pos(bb.minX, bb.maxY, bb.maxZ).color(r, g, b, a).endVertex();
			buffer.pos(bb.minX, bb.maxY, bb.minZ).color(r, g, b, a).endVertex();
		}
		if ((sides & Quad.EAST) != 0) {
			buffer.pos(bb.maxX, bb.minY, bb.maxZ).color(r, g, b, a).endVertex();
			buffer.pos(bb.maxX, bb.minY, bb.minZ).color(r, g, b, a).endVertex();
			buffer.pos(bb.maxX, bb.maxY, bb.minZ).color(r, g, b, a).endVertex();
			buffer.pos(bb.maxX, bb.maxY, bb.maxZ).color(r, g, b, a).endVertex();
		}
	}

	public static void drawBoundingBox(final AxisAlignedBB bb, final float width, final int red, final int green,
			final int blue, final int alpha) {
		GlStateManager.pushMatrix();
		glEnable(GL_LINE_SMOOTH);
		GlStateManager.enableBlend();
		GlStateManager.disableDepth();
		GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
		GlStateManager.disableTexture2D();
		GlStateManager.depthMask(false);
		GlStateManager.glLineWidth(width);
		final BufferBuilder bufferbuilder = INSTANCE.getBuffer();
		bufferbuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
		bufferbuilder.pos(bb.minX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
		bufferbuilder.pos(bb.minX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
		bufferbuilder.pos(bb.maxX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
		bufferbuilder.pos(bb.maxX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
		bufferbuilder.pos(bb.minX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
		bufferbuilder.pos(bb.minX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
		bufferbuilder.pos(bb.minX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
		bufferbuilder.pos(bb.minX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
		bufferbuilder.pos(bb.maxX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
		bufferbuilder.pos(bb.maxX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
		bufferbuilder.pos(bb.minX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
		bufferbuilder.pos(bb.maxX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
		bufferbuilder.pos(bb.maxX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
		bufferbuilder.pos(bb.maxX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
		bufferbuilder.pos(bb.maxX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
		bufferbuilder.pos(bb.minX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
		render();
		GlStateManager.depthMask(true);
		GlStateManager.enableDepth();
		GlStateManager.enableTexture2D();
		GlStateManager.disableBlend();
		glDisable(GL_LINE_SMOOTH);
		GlStateManager.popMatrix();
	}

	public static void glBillboard(float x, float y, float z) {
		float scale = 0.016666668f * 1.6f;
		GlStateManager.translate(x - ((IRenderManager) Minecraft.getMinecraft().getRenderManager()).getRenderPosX(),
				y - ((IRenderManager) Minecraft.getMinecraft().getRenderManager()).getRenderPosY(),
				z - ((IRenderManager) Minecraft.getMinecraft().getRenderManager()).getRenderPosZ());
		GlStateManager.glNormal3f(0.0f, 1.0f, 0.0f);
		GlStateManager.rotate(-Minecraft.getMinecraft().player.rotationYaw, 0.0f, 1.0f, 0.0f);
		GlStateManager.rotate(Minecraft.getMinecraft().player.rotationPitch,
				Minecraft.getMinecraft().gameSettings.thirdPersonView == 2 ? -1.0f : 1.0f, 0.0f, 0.0f);
		GlStateManager.scale(-scale, -scale, scale);
	}

	public static void glBillboardDistanceScaled(float x, float y, float z, EntityPlayer player, float scale) {
		glBillboard(x, y, z);
		int distance = (int) player.getDistance(x, y, z);
		float scaleDistance = (distance / 2.0f) / (2.0f + (2.0f - scale));
		if (scaleDistance < 1f)
			scaleDistance = 1;
		GlStateManager.scale(scaleDistance, scaleDistance, scaleDistance);
	}

	public static Vec3d getInterpolatedPos(Entity entity, float ticks) {
		return new Vec3d(entity.lastTickPosX, entity.lastTickPosY, entity.lastTickPosZ)
				.add(getInterpolatedAmount(entity, ticks));
	}

	public static Vec3d getInterpolatedAmount(Entity entity, double ticks) {
		return getInterpolatedAmount(entity, ticks, ticks, ticks);
	}

	public static Vec3d getInterpolatedAmount(Entity entity, double x, double y, double z) {
		return new Vec3d((entity.posX - entity.lastTickPosX) * x, (entity.posY - entity.lastTickPosY) * y,
				(entity.posZ - entity.lastTickPosZ) * z);
	}

	public static void drawBoundingBoxBottomBlockPos(BlockPos bp, float width, int r, int g, int b, int alpha) {
		GlStateManager.pushMatrix();
		GlStateManager.enableBlend();
		GlStateManager.disableDepth();
		GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
		GlStateManager.disableTexture2D();
		GlStateManager.depthMask(false);
		GL11.glEnable(2848);
		GL11.glHint(3154, 4354);
		GL11.glLineWidth(width);
		Minecraft mc = Minecraft.getMinecraft();
		double x = (double) bp.getX() - mc.getRenderManager().viewerPosX;
		double y = (double) bp.getY() - mc.getRenderManager().viewerPosY;
		double z = (double) bp.getZ() - mc.getRenderManager().viewerPosZ;
		AxisAlignedBB bb = new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0);
		net.minecraft.client.renderer.Tessellator tessellator = net.minecraft.client.renderer.Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		bufferbuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
		bufferbuilder.pos(bb.minX, bb.minY, bb.minZ).color(r, g, b, alpha).endVertex();
		bufferbuilder.pos(bb.maxX, bb.minY, bb.minZ).color(r, g, b, alpha).endVertex();
		bufferbuilder.pos(bb.maxX, bb.minY, bb.maxZ).color(r, g, b, alpha).endVertex();
		bufferbuilder.pos(bb.minX, bb.minY, bb.maxZ).color(r, g, b, alpha).endVertex();
		bufferbuilder.pos(bb.minX, bb.minY, bb.minZ).color(r, g, b, alpha).endVertex();
		tessellator.draw();
		GL11.glDisable(2848);
		GlStateManager.depthMask(true);
		GlStateManager.enableDepth();
		GlStateManager.enableTexture2D();
		GlStateManager.disableBlend();
		GlStateManager.popMatrix();
	}

	public static void drawBoundingBoxBlockPos(BlockPos bp, float width, int r, int g, int b, int alpha) {
		GlStateManager.pushMatrix();
		GlStateManager.enableBlend();
		GlStateManager.disableDepth();
		GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
		GlStateManager.disableTexture2D();
		GlStateManager.depthMask(false);
		GL11.glEnable(2848);
		glEnable(GL_LINE_SMOOTH);
		GL11.glHint(3154, 4354);
		GL11.glLineWidth(width);
		Minecraft mc = Minecraft.getMinecraft();
		double x = (double) bp.getX() - mc.getRenderManager().viewerPosX;
		double y = (double) bp.getY() - mc.getRenderManager().viewerPosY;
		double z = (double) bp.getZ() - mc.getRenderManager().viewerPosZ;
		AxisAlignedBB bb = new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0);
		net.minecraft.client.renderer.Tessellator tessellator = net.minecraft.client.renderer.Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		bufferbuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
		bufferbuilder.pos(bb.minX, bb.minY, bb.minZ).color(r, g, b, alpha).endVertex();
		bufferbuilder.pos(bb.maxX, bb.minY, bb.minZ).color(r, g, b, alpha).endVertex();
		bufferbuilder.pos(bb.maxX, bb.minY, bb.maxZ).color(r, g, b, alpha).endVertex();
		bufferbuilder.pos(bb.minX, bb.minY, bb.maxZ).color(r, g, b, alpha).endVertex();
		bufferbuilder.pos(bb.minX, bb.minY, bb.minZ).color(r, g, b, alpha).endVertex();
		tessellator.draw();
		bufferbuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
		bufferbuilder.pos(bb.minX, bb.maxY, bb.minZ).color(r, g, b, alpha).endVertex();
		bufferbuilder.pos(bb.maxX, bb.maxY, bb.minZ).color(r, g, b, alpha).endVertex();
		bufferbuilder.pos(bb.maxX, bb.maxY, bb.maxZ).color(r, g, b, alpha).endVertex();
		bufferbuilder.pos(bb.minX, bb.maxY, bb.maxZ).color(r, g, b, alpha).endVertex();
		bufferbuilder.pos(bb.minX, bb.maxY, bb.minZ).color(r, g, b, alpha).endVertex();
		tessellator.draw();
		bufferbuilder.begin(1, DefaultVertexFormats.POSITION_COLOR);
		bufferbuilder.pos(bb.minX, bb.minY, bb.minZ).color(r, g, b, alpha).endVertex();
		bufferbuilder.pos(bb.minX, bb.maxY, bb.minZ).color(r, g, b, alpha).endVertex();
		bufferbuilder.pos(bb.maxX, bb.minY, bb.minZ).color(r, g, b, alpha).endVertex();
		bufferbuilder.pos(bb.maxX, bb.maxY, bb.minZ).color(r, g, b, alpha).endVertex();
		bufferbuilder.pos(bb.maxX, bb.minY, bb.maxZ).color(r, g, b, alpha).endVertex();
		bufferbuilder.pos(bb.maxX, bb.maxY, bb.maxZ).color(r, g, b, alpha).endVertex();
		bufferbuilder.pos(bb.minX, bb.minY, bb.maxZ).color(r, g, b, alpha).endVertex();
		bufferbuilder.pos(bb.minX, bb.maxY, bb.maxZ).color(r, g, b, alpha).endVertex();
		tessellator.draw();
		glDisable(GL_LINE_SMOOTH);
		GL11.glDisable(2848);
		GlStateManager.depthMask(true);
		GlStateManager.enableDepth();
		GlStateManager.enableTexture2D();
		GlStateManager.disableBlend();
		GlStateManager.popMatrix();
	}

	public static void drawBoundingBox(final AxisAlignedBB bb, final float width, final int argb) {
		final int a = argb >>> 24 & 0xFF;
		final int r = argb >>> 16 & 0xFF;
		final int g = argb >>> 8 & 0xFF;
		final int b = argb & 0xFF;
		drawBoundingBox(bb, width, r, g, b, a);
	}

	public static final class Quad {
		public static final int DOWN = 0x01;
		public static final int UP = 0x02;
		public static final int NORTH = 0x04;
		public static final int SOUTH = 0x08;
		public static final int WEST = 0x10;
		public static final int EAST = 0x20;
		public static final int ALL = DOWN | UP | NORTH | SOUTH | WEST | EAST;
	}

	public static void drawBoxFromBlockpos(BlockPos blockPos, float r, float g, float b, float a) {
		AxisAlignedBB axisAlignedBB = new AxisAlignedBB(
				blockPos.getX() - Minecraft.getMinecraft().getRenderManager().viewerPosX,
				blockPos.getY() - Minecraft.getMinecraft().getRenderManager().viewerPosY,
				blockPos.getZ() - Minecraft.getMinecraft().getRenderManager().viewerPosZ,
				blockPos.getX() + 1 - Minecraft.getMinecraft().getRenderManager().viewerPosX,
				blockPos.getY() + 1 - Minecraft.getMinecraft().getRenderManager().viewerPosY,
				blockPos.getZ() + 1 - Minecraft.getMinecraft().getRenderManager().viewerPosZ);
		drawBox(axisAlignedBB);
	}
}
