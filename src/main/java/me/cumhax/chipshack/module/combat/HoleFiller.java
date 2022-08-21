package me.cumhax.chipshack.module.combat;

import me.cumhax.chipshack.module.Category;
import me.cumhax.chipshack.module.Module;
import me.cumhax.chipshack.setting.Setting;
import me.cumhax.chipshack.util.PlaceUtil;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class HoleFiller extends Module {
    private final Setting range = new Setting("Range", this, 1, 5, 10);
    private final Setting disable = new Setting("Disable", this, true);
    private final Setting blockMode = new Setting("Block", this, Arrays.asList("Obsidian", "Web"));
    private final Setting self = new Setting("Self", this, false);

    private int oldHand;

    public HoleFiller() 
{
        super("HoleFiller", "", Category.COMBAT);
    }

    @Override
    public void onEnable() {
        oldHand = mc.player.inventory.currentItem;
    }

    @Override
    public void onDisable() {
        mc.player.inventory.currentItem = oldHand;
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (mc.player == null || mc.world == null) return;

        int slot = getSlot();
        List<BlockPos> blocks = getHoles();
        List<BlockPos> blocksToRemove = new ArrayList<>();

        if (slot == -1) {
            disable();
            return;
        }

        if (!self.getBoolValue()) {
            for (BlockPos block : blocks) {
                if (inPlayer(block)) blocksToRemove.add(block);
            }
            for (BlockPos blockPos : blocksToRemove) {
                blocks.remove(blockPos);
            }
            blocksToRemove.clear();
        }

        if (self.getBoolValue() && blockMode.getEnumValue().equals("Web")) {
            PlaceUtil.placeBlock(mc.player.getPosition(), slot, true, true);
        }

        if (blocks.size() == 0) {
            if (disable.getBoolValue()) disable();
            return;
        }

        PlaceUtil.placeBlock(blocks.get(0), slot, true, true);
    }

    private int getSlot() {
        Block block;
        if (blockMode.getEnumValue().equals("Web")) {
            block = Blocks.WEB;
        } else {
            block = Blocks.OBSIDIAN;
        }

        int slot = mc.player.getHeldItemMainhand().getItem() == Item.getItemFromBlock(block) ? mc.player.inventory.currentItem : -1;
        if (slot == -1) {
            for (int i = 0; i < 9; i++) {
                if (mc.player.inventory.getStackInSlot(i).getItem() == Item.getItemFromBlock(block)) {
                    slot = i;
                    break;
                }
            }
        }
        return slot;
    }

    private List<BlockPos> getHoles() {
        NonNullList<BlockPos> positions = NonNullList.create();
        positions.addAll(getSphere(new BlockPos(Math.floor(mc.player.posX), Math.floor(mc.player.posY), Math.floor(mc.player.posZ)), range.getIntValue(), range.getIntValue(), false, true, 0).stream().filter(this::isHole).collect(Collectors.toList()));
        return positions;
    }

    public List<BlockPos> getSphere(BlockPos loc, float r, int h, boolean hollow, boolean sphere, int plus_y) {
        List<BlockPos> blocks = new ArrayList<>();
        int cx = loc.getX();
        int cy = loc.getY();
        int cz = loc.getZ();
        for (int x = cx - (int) r; x <= cx + r; x++) {
            for (int z = cz - (int) r; z <= cz + r; z++) {
                for (int y = (sphere ? cy - (int) r : cy); y < (sphere ? cy + r : cy + h); y++) {
                    double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? (cy - y) * (cy - y) : 0);
                    if (dist < r * r && !(hollow && dist < (r - 1) * (r - 1))) {
                        BlockPos l = new BlockPos(x, y + plus_y, z);
                        blocks.add(l);
                    }
                }
            }
        }
        return blocks;
    }

    private boolean isHole(BlockPos blockPos) {
        BlockPos b1 = blockPos.add(0, 1, 0);
        BlockPos b2 = blockPos.add(0, 0, 0);
        BlockPos b3 = blockPos.add(0, 0, -1);
        BlockPos b4 = blockPos.add(1, 0, 0);
        BlockPos b5 = blockPos.add(-1, 0, 0);
        BlockPos b6 = blockPos.add(0, 0, 1);
        BlockPos b7 = blockPos.add(0, 2, 0);
        BlockPos b8 = blockPos.add(0.5, 0.5, 0.5);
        BlockPos b9 = blockPos.add(0, -1, 0);
        return mc.world.getBlockState(b1).getBlock() == Blocks.AIR && (mc.world.getBlockState(b2).getBlock() == Blocks.AIR) && (mc.world.getBlockState(b7).getBlock() == Blocks.AIR) && ((mc.world.getBlockState(b3).getBlock() == Blocks.OBSIDIAN) || (mc.world.getBlockState(b3).getBlock() == Blocks.BEDROCK)) && ((mc.world.getBlockState(b4).getBlock() == Blocks.OBSIDIAN) || (mc.world.getBlockState(b4).getBlock() == Blocks.BEDROCK)) && ((mc.world.getBlockState(b5).getBlock() == Blocks.OBSIDIAN) || (mc.world.getBlockState(b5).getBlock() == Blocks.BEDROCK)) && ((mc.world.getBlockState(b6).getBlock() == Blocks.OBSIDIAN) || (mc.world.getBlockState(b6).getBlock() == Blocks.BEDROCK)) && (mc.world.getBlockState(b8).getBlock() == Blocks.AIR) && ((mc.world.getBlockState(b9).getBlock() == Blocks.OBSIDIAN) || (mc.world.getBlockState(b9).getBlock() == Blocks.BEDROCK));
    }

    private boolean inPlayer(BlockPos pos) {
        AxisAlignedBB axisAlignedBB = new AxisAlignedBB(pos);
        return axisAlignedBB.intersects(mc.player.getEntityBoundingBox());
    }
}
