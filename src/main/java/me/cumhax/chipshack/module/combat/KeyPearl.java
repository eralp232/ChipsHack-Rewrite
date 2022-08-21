package me.cumhax.chipshack.module.combat;

import me.cumhax.chipshack.module.Module;
import me.cumhax.chipshack.util.InventoryUtil;
import me.cumhax.chipshack.module.Category;

import net.minecraft.init.Items;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.EnumHand;

public class KeyPearl extends Module 
{
    public KeyPearl() {
        super("KeyPearl", "", Category.COMBAT);
    }

    int startingHand;
    int pearlHand;

    public void onEnable() {
        startingHand = mc.player.inventory.currentItem;
        if (InventoryUtil.findItemInHotbar(Items.ENDER_PEARL) == -1) {
            this.disable();
        } else {
            pearlHand = InventoryUtil.findItemInHotbar(Items.ENDER_PEARL);
        }
        mc.player.connection.sendPacket(new CPacketHeldItemChange(pearlHand));
        mc.player.connection.sendPacket(new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
        mc.player.connection.sendPacket(new CPacketHeldItemChange(mc.player.inventory.currentItem));
        this.disable();
   }
}
