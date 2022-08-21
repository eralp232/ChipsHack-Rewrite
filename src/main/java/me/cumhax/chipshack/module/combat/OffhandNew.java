package me.cumhax.chipshack.module.combat;

import net.minecraft.item.Item;
import net.minecraft.init.Items;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Arrays;
import me.cumhax.chipshack.module.Module;
import me.cumhax.chipshack.setting.Setting;
import me.cumhax.chipshack.module.Category;

public class OffhandNew extends Module
{
    private final Setting stopInGUI = new Setting("Stop In Gui", this, true);
    private final Setting swordGap = new Setting("Sword Gap", this, true);
    private final Setting soft = new Setting("Soft", this, true);
    private final Setting minHealth = new Setting("MinHealth", this, 16, 1, 36);
    private final Setting delay = new Setting("Delay", this, 1,  0, 10);
    private final Setting mode = new Setting("Mode", this, Arrays.asList("Crystal", "Gapple", "Bed", "Totem"));
    private final Setting packetFix = new Setting("PacketFix", this, true);
    private int previtemSlot;
    
    public OffhandNew() 
{
        super("OffhandNew", "kekw", Category.COMBAT);
        this.previtemSlot = -6;
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (this.mc.player == null || this.mc.world == null) {
            return;
        }
        if (this.stopInGUI.getBooleanValue() && this.mc.currentScreen != null) {
            return;
        }
        final int itemSlot = this.getItemSlot();
        if (itemSlot == -1) {
            return;
        }
        if (itemSlot == this.previtemSlot) {
            return;
        }
        this.previtemSlot = itemSlot;
        this.mc.playerController.windowClick(this.mc.player.inventoryContainer.windowId, itemSlot, 0, ClickType.PICKUP, (EntityPlayer)this.mc.player);
        this.mc.playerController.windowClick(this.mc.player.inventoryContainer.windowId, 45, 0, ClickType.PICKUP, (EntityPlayer)this.mc.player);
        this.mc.playerController.windowClick(this.mc.player.inventoryContainer.windowId, itemSlot, 0, ClickType.PICKUP, (EntityPlayer)this.mc.player);
    }
    
    private int getItemSlot() {
        Item itemToSearch = Items.TOTEM_OF_UNDYING;
        if (this.mc.player.getHealth() + this.mc.player.getAbsorptionAmount() > this.minHealth.getIntegerValue()) {
            if (this.swordGap.getBooleanValue() && this.mc.player.getHeldItemMainhand().getItem() == Items.DIAMOND_SWORD) {
                itemToSearch = Items.GOLDEN_APPLE;
            }
            else {
                final String enumValue = this.mode.getEnumValue();
                switch (enumValue) {
                    case "Crystal": {
                        itemToSearch = Items.END_CRYSTAL;
                        break;
                    }
                    case "Gapple": {
                        itemToSearch = Items.GOLDEN_APPLE;
                        break;
                    }
                    case "Bed": {
                        itemToSearch = Items.BED;
                        break;
                    }
                }
            }
        }
        if (this.mc.player.inventory.getStackInSlot(45).getItem() == itemToSearch) {
            return -1;
        }
        for (int i = 9; i < 36; ++i) {
            if (this.mc.player.inventory.getStackInSlot(i).getItem() == itemToSearch) {
                return (i < 9) ? (i + 36) : i;
            }
        }
        return -1;
    }
}
