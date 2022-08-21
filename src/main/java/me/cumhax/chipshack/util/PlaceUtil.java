package me.cumhax.chipshack.util;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer.Rotation;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class PlaceUtil
{
	private static final Minecraft mc = Minecraft.getMinecraft();
	public static List<Block> emptyBlocks;
	public static List<Block> rightClickableBlocks;

	static
	{
		emptyBlocks = Arrays.asList(Blocks.AIR, Blocks.FLOWING_LAVA, Blocks.LAVA, Blocks.FLOWING_WATER, Blocks.WATER, Blocks.VINE, Blocks.SNOW_LAYER, Blocks.TALLGRASS, Blocks.FIRE);
		rightClickableBlocks = Arrays.asList(Blocks.CHEST, Blocks.TRAPPED_CHEST, Blocks.ENDER_CHEST, Blocks.WHITE_SHULKER_BOX, Blocks.ORANGE_SHULKER_BOX, Blocks.MAGENTA_SHULKER_BOX, Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.YELLOW_SHULKER_BOX, Blocks.LIME_SHULKER_BOX, Blocks.PINK_SHULKER_BOX, Blocks.GRAY_SHULKER_BOX, Blocks.SILVER_SHULKER_BOX, Blocks.CYAN_SHULKER_BOX, Blocks.PURPLE_SHULKER_BOX, Blocks.BLUE_SHULKER_BOX, Blocks.BROWN_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX, Blocks.RED_SHULKER_BOX, Blocks.BLACK_SHULKER_BOX, Blocks.ANVIL, Blocks.WOODEN_BUTTON, Blocks.STONE_BUTTON, Blocks.UNPOWERED_COMPARATOR, Blocks.UNPOWERED_REPEATER, Blocks.POWERED_REPEATER, Blocks.POWERED_COMPARATOR, Blocks.OAK_FENCE_GATE, Blocks.SPRUCE_FENCE_GATE, Blocks.BIRCH_FENCE_GATE, Blocks.JUNGLE_FENCE_GATE, Blocks.DARK_OAK_FENCE_GATE, Blocks.ACACIA_FENCE_GATE, Blocks.BREWING_STAND, Blocks.DISPENSER, Blocks.DROPPER, Blocks.LEVER, Blocks.NOTEBLOCK, Blocks.JUKEBOX, Blocks.BEACON, Blocks.BED, Blocks.FURNACE, Blocks.OAK_DOOR, Blocks.SPRUCE_DOOR, Blocks.BIRCH_DOOR, Blocks.JUNGLE_DOOR, Blocks.ACACIA_DOOR, Blocks.DARK_OAK_DOOR, Blocks.CAKE, Blocks.ENCHANTING_TABLE, Blocks.DRAGON_EGG, Blocks.HOPPER, Blocks.REPEATING_COMMAND_BLOCK, Blocks.COMMAND_BLOCK, Blocks.CHAIN_COMMAND_BLOCK, Blocks.CRAFTING_TABLE);
	}

	public static boolean placeBlock(BlockPos pos, int slot, boolean rotate, boolean rotateBack)
	{
		if (isBlockNotEmpty(pos) || mc.player.inventory.getStackInSlot(slot).getItem() == Item.getItemFromBlock(Blocks.WEB))
		{

			for (Entity entity : mc.world.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(pos)))
			{
				if (!(entity instanceof EntityItem) && !(entity instanceof EntityXPOrb))
				{
					return false;
				}
			}

			if (slot != mc.player.inventory.currentItem)
			{
				mc.player.inventory.currentItem = slot;
			}

			EnumFacing[] enumFacings = EnumFacing.values();

			for (EnumFacing enumFacing : enumFacings)
			{

				Block neighborBlock = mc.world.getBlockState(pos.offset(enumFacing)).getBlock();
				Vec3d vec = new Vec3d(pos.getX() + 0.5D + (double) enumFacing.getXOffset() * 0.5D, pos.getY() + 0.5D + (double) enumFacing.getYOffset() * 0.5D, pos.getZ() + 0.5D + (double) enumFacing.getZOffset() * 0.5D);

				if (!emptyBlocks.contains(neighborBlock) && mc.player.getPositionEyes(mc.getRenderPartialTicks()).distanceTo(vec) <= 4.25D)
				{

					float[] rot = new float[]
							{
									mc.player.rotationYaw,
									mc.player.rotationPitch
							};

					if (rotate)
					{
						rotatePacket(vec.x, vec.y, vec.z);
					}

					if (rightClickableBlocks.contains(neighborBlock))
					{
						mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_SNEAKING));
					}

					mc.playerController.processRightClickBlock(mc.player, mc.world, pos.offset(enumFacing), enumFacing.getOpposite(), new Vec3d(pos), EnumHand.MAIN_HAND);

					if (rightClickableBlocks.contains(neighborBlock))
					{
						mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
					}

					if (rotateBack)
					{
						mc.player.connection.sendPacket(new Rotation(rot[0], rot[1], mc.player.onGround));
					}

					return true;
				}

			}

		}

		return false;

	}

	public static boolean isBlockNotEmpty(BlockPos pos)
	{
		if (emptyBlocks.contains(mc.world.getBlockState(pos).getBlock()))
		{
			AxisAlignedBB axisAlignedBB = new AxisAlignedBB(pos);
			Iterator<Entity> iterator = mc.world.loadedEntityList.iterator();
			Entity entity;
			do
			{
				if (!iterator.hasNext())
				{
					return true;
				}
				entity = iterator.next();
			}
			while (!(entity instanceof EntityLivingBase) || !axisAlignedBB.intersects(entity.getEntityBoundingBox()));
		}
		return false;
	}

	public static boolean canPlaceBlock(BlockPos pos)
	{

		if (isBlockNotEmpty(pos))
		{

			EnumFacing[] enumFacings = EnumFacing.values();

			for (EnumFacing enumFacing : enumFacings)
			{

				if (!emptyBlocks.contains(mc.world.getBlockState(pos.offset(enumFacing)).getBlock()) && mc.player.getPositionEyes(mc.getRenderPartialTicks()).distanceTo(new Vec3d(pos.getX() + 0.5D + (double) enumFacing.getXOffset() * 0.5D, pos.getY() + 0.5D + (double) enumFacing.getYOffset() * 0.5D, pos.getZ() + 0.5D + (double) enumFacing.getZOffset() * 0.5D)) <= 4.25D)
				{
					return true;
				}

			}

		}

		return false;

	}

	public static EnumFacing getClosestFacing(BlockPos pos)
	{
		return EnumFacing.DOWN;
	}

	public static void rotateClient(double x, double y, double z)
	{

		double diffX = x - mc.player.posX;
		double diffY = y - (mc.player.posY + (double) mc.player.getEyeHeight());
		double diffZ = z - mc.player.posZ;
		double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);

		float yaw = (float) Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0F;
		float pitch = (float) (-Math.toDegrees(Math.atan2(diffY, diffXZ)));

		mc.player.rotationYaw += MathHelper.wrapDegrees(yaw - mc.player.rotationYaw);
		mc.player.rotationPitch += MathHelper.wrapDegrees(pitch - mc.player.rotationPitch);

	}

	public static void rotatePacket(double x, double y, double z)
	{

		double diffX = x - mc.player.posX;
		double diffY = y - (mc.player.posY + (double) mc.player.getEyeHeight());
		double diffZ = z - mc.player.posZ;
		double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);

		float yaw = (float) Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0F;
		float pitch = (float) (-Math.toDegrees(Math.atan2(diffY, diffXZ)));

		mc.player.connection.sendPacket(new Rotation(yaw, pitch, mc.player.onGround));

	}
}
