package me.cumhax.chipshack.util;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.cumhax.chipshack.mixin.loader.IMinecraft;

public class InteractHelper {

	public static List<Block> blackList;

	public static List<Block> shulkerList;
	private static Minecraft mc = Minecraft.getMinecraft();

	static {
		blackList = Arrays.asList(Blocks.ENDER_CHEST, Blocks.CHEST, Blocks.TRAPPED_CHEST, Blocks.CRAFTING_TABLE,
				Blocks.ANVIL, Blocks.BREWING_STAND, Blocks.HOPPER, Blocks.DROPPER, Blocks.DISPENSER);
		shulkerList = Arrays.asList(Blocks.WHITE_SHULKER_BOX, Blocks.ORANGE_SHULKER_BOX, Blocks.MAGENTA_SHULKER_BOX,
				Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.YELLOW_SHULKER_BOX, Blocks.LIME_SHULKER_BOX,
				Blocks.PINK_SHULKER_BOX, Blocks.GRAY_SHULKER_BOX, Blocks.SILVER_SHULKER_BOX, Blocks.CYAN_SHULKER_BOX,
				Blocks.PURPLE_SHULKER_BOX, Blocks.BLUE_SHULKER_BOX, Blocks.BROWN_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX,
				Blocks.RED_SHULKER_BOX, Blocks.BLACK_SHULKER_BOX);
		mc = Minecraft.getMinecraft();
	}

	public static ValidResult valid(BlockPos pos) {
		// There are no entities to block placement,
		if (!mc.world.checkNoEntityCollision(new AxisAlignedBB(pos)))
			return ValidResult.NoEntityCollision;

		if (mc.world.getBlockState(pos.down()).getBlock() == Blocks.WATER)
			return ValidResult.Ok;

		if (!checkForNeighbours(pos))
			return ValidResult.NoNeighbors;

		IBlockState l_State = mc.world.getBlockState(pos);

		if (l_State.getBlock() == Blocks.AIR) {
			final BlockPos[] l_Blocks = { pos.north(), pos.south(), pos.east(), pos.west(), pos.up(), pos.down() };

			for (BlockPos l_Pos : l_Blocks) {
				IBlockState l_State2 = mc.world.getBlockState(l_Pos);

				if (l_State2.getBlock() == Blocks.AIR)
					continue;

				for (final EnumFacing side : EnumFacing.values()) {
					final BlockPos neighbor = pos.offset(side);

					if (mc.world.getBlockState(neighbor).getBlock().canCollideCheck(mc.world.getBlockState(neighbor),
							false)) {
						return ValidResult.Ok;
					}
				}
			}

			return ValidResult.NoNeighbors;
		}

		return ValidResult.AlreadyBlockThere;
	}

	public static BlockPos GetLocalPlayerPosFloored() {
		return new BlockPos(Math.floor(mc.player.posX), Math.floor(mc.player.posY), Math.floor(mc.player.posZ));
	}

	public static void placeBlockScaffold(BlockPos pos) {
		Vec3d eyesPos = new Vec3d(mc.player.posX, mc.player.posY + mc.player.getEyeHeight(), mc.player.posZ);

		for (EnumFacing side : EnumFacing.values()) {
			BlockPos neighbor = pos.offset(side);
			EnumFacing side2 = side.getOpposite();

			// check if neighbor can be right clicked
			if (canBeClicked(neighbor)) {
				continue;
			}

			Vec3d hitVec = new Vec3d(neighbor).add(0.5, 0.5, 0.5).add(new Vec3d(side2.getDirectionVec()).scale(0.5));

			// check if hitVec is within range (4.25 blocks)
			if (eyesPos.squareDistanceTo(hitVec) > 18.0625) {
				continue;
			}

			// place block
			faceVectorPacketInstant(hitVec);
			processRightClickBlock(neighbor, side2, hitVec);
			mc.player.swingArm(EnumHand.MAIN_HAND);
			((IMinecraft) mc).setRightClickDelayTimer(4);
			return;
		}

	}

	private static float[] getLegitRotations(Vec3d vec) {
		Vec3d eyesPos = getEyesPos();

		double diffX = vec.x - eyesPos.x;
		double diffY = vec.y - eyesPos.y;
		double diffZ = vec.z - eyesPos.z;

		double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);

		float yaw = (float) Math.toDegrees(Math.atan2(diffZ, diffX)) - 90F;
		float pitch = (float) -Math.toDegrees(Math.atan2(diffY, diffXZ));

		return new float[] { mc.player.rotationYaw + MathHelper.wrapDegrees(yaw - mc.player.rotationYaw),
				mc.player.rotationPitch + MathHelper.wrapDegrees(pitch - mc.player.rotationPitch) };
	}

	private static Vec3d getEyesPos() {
		return new Vec3d(mc.player.posX, mc.player.posY + mc.player.getEyeHeight(), mc.player.posZ);
	}

	public static void faceVectorPacketInstant(Vec3d vec) {
		float[] rotations = getLegitRotations(vec);

		mc.player.connection.sendPacket(new CPacketPlayer.Rotation(rotations[0], rotations[1], mc.player.onGround));
	}

	private static void processRightClickBlock(BlockPos pos, EnumFacing side, Vec3d hitVec) {
		getPlayerController().processRightClickBlock(mc.player, mc.world, pos, side, hitVec, EnumHand.MAIN_HAND);
	}

	public static boolean canBeClicked(BlockPos pos) {
		return !getBlock(pos).canCollideCheck(getState(pos), false);
	}

	private static Block getBlock(BlockPos pos) {
		return getState(pos).getBlock();
	}

	private static PlayerControllerMP getPlayerController() {
		return Minecraft.getMinecraft().playerController;
	}

	private static IBlockState getState(BlockPos pos) {
		return mc.world.getBlockState(pos);
	}

	public static boolean checkForNeighbours(BlockPos blockPos) {
		// check if we don't have a block adjacent to blockpos
		if (!hasNeighbour(blockPos)) {
			// find air adjacent to blockpos that does have a block adjacent to it, let's
			// fill this first as to form a bridge between the player and the original
			// blockpos. necessary if the player is going diagonal.
			for (EnumFacing side : EnumFacing.values()) {
				BlockPos neighbour = blockPos.offset(side);
				if (hasNeighbour(neighbour)) {
					return true;
				}
			}
			return false;
		}
		return true;
	}

	private static boolean hasNeighbour(BlockPos blockPos) {
		for (EnumFacing side : EnumFacing.values()) {
			BlockPos neighbour = blockPos.offset(side);
			if (!mc.world.getBlockState(neighbour).getMaterial().isReplaceable()) {
				return true;
			}
		}
		return false;
	}

	public static List<BlockPos> getCircle(final BlockPos loc, final int y, final float r, final boolean hollow) {
		final List<BlockPos> circleblocks = new ArrayList<BlockPos>();
		final int cx = loc.getX();
		final int cz = loc.getZ();
		for (int x = cx - (int) r; x <= cx + r; ++x) {
			for (int z = cz - (int) r; z <= cz + r; ++z) {
				final double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z);
				if (dist < r * r && (!hollow || dist >= (r - 1.0f) * (r - 1.0f))) {
					final BlockPos l = new BlockPos(x, y, z);
					circleblocks.add(l);
				}
			}
		}
		return circleblocks;
	}

	public static List<BlockPos> getSphere(BlockPos loc, float r, int h, boolean hollow, boolean sphere, int plus_y) {
		List<BlockPos> circleblocks = new ArrayList<>();
		int cx = loc.getX();
		int cy = loc.getY();
		int cz = loc.getZ();
		for (int x = cx - (int) r; x <= cx + r; x++) {
			for (int z = cz - (int) r; z <= cz + r; z++) {
				for (int y = (sphere ? cy - (int) r : cy); y < (sphere ? cy + r : cy + h); y++) {
					double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? (cy - y) * (cy - y) : 0);
					if (dist < r * r && !(hollow && dist < (r - 1) * (r - 1))) {
						BlockPos l = new BlockPos(x, y + plus_y, z);
						circleblocks.add(l);
					}
				}
			}
		}
		return circleblocks;
	}

	public static EnumFacing getPlaceableSide(BlockPos pos) {

		for (EnumFacing side : EnumFacing.values()) {

			BlockPos neighbour = pos.offset(side);

			if (!mc.world.getBlockState(neighbour).getBlock().canCollideCheck(mc.world.getBlockState(neighbour),
					false)) {
				continue;
			}

			IBlockState blockState = mc.world.getBlockState(neighbour);
			if (!blockState.getMaterial().isReplaceable()) {
				return side;
			}

		}

		return null;

	}

	public enum ValidResult {
		NoEntityCollision, AlreadyBlockThere, NoNeighbors, Ok,
	}

}
