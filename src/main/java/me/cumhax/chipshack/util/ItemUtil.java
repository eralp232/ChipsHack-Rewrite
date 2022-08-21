package me.cumhax.chipshack.util;

import java.util.Iterator;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.network.play.client.CPacketHeldItemChange;

public class ItemUtil{

	public static int getItemFromHotbar(Item item) {
        int slot = -1;
        for (int i = 0; i < 9; ++i) {
            ItemStack stack = Minecraft.getMinecraft().player.inventory.getStackInSlot(i);
            if (stack.getItem() != item) continue;
            slot = i;
        }
        return slot;
    }

    public static int getItemSlot(Class clss) {
        int itemSlot = -1;
        for (int i = 45; i > 0; --i) {
            if (Minecraft.getMinecraft().player.inventory.getStackInSlot(i).getItem().getClass() != clss) continue;
            itemSlot = i;
            break;
        }
        return itemSlot;
    }

    public static int getItemSlot(Item item) {
        int itemSlot = -1;
        for (int i = 45; i > 0; --i) {
            if (!Minecraft.getMinecraft().player.inventory.getStackInSlot(i).getItem().equals(item)) continue;
            itemSlot = i;
            break;
        }
        return itemSlot;
    }

    public static int getItemCount(Item item) {
        int count = 0;
        int size = Minecraft.getMinecraft().player.inventory.mainInventory.size();
        for (int i = 0; i < size; ++i) {
            ItemStack itemStack = (ItemStack)Minecraft.getMinecraft().player.inventory.mainInventory.get(i);
            if (itemStack.getItem() != item) continue;
            count += itemStack.getCount();
        }
        ItemStack offhandStack = Minecraft.getMinecraft().player.getHeldItemOffhand();
        if (offhandStack.getItem() == item) {
            count += offhandStack.getCount();
        }
        return count;
    }

    public static boolean isArmorLow(EntityPlayer player, int durability) {
        Iterator iterator = player.inventory.armorInventory.iterator();
        while (iterator.hasNext()) {
            ItemStack piece = (ItemStack)iterator.next();
            if (piece != null && !(getDamageInPercent(piece) < (float)durability)) continue;
            return true;
        }
        return false;
    }

    public static int getItemDamage(ItemStack stack) {
        return stack.getMaxDamage() - stack.getItemDamage();
    }

    public static float getDamageInPercent(ItemStack stack) {
        float green = ((float)stack.getMaxDamage() - (float)stack.getItemDamage()) / (float)stack.getMaxDamage();
        float red = 1.0f - green;
        return 100 - (int)(red * 100.0f);
    }

    public static int getRoundedDamage(ItemStack stack) {
        return (int)getDamageInPercent(stack);
    }

    public static boolean hasDurability(ItemStack stack) {
        Item item = stack.getItem();
        return item instanceof ItemArmor || item instanceof ItemSword || item instanceof ItemTool || item instanceof ItemShield;
    }
    
    public static int getHotbarItemSlot(Item item) {
        for (int i = 0; i < 9; i++) {
            if (Minecraft.getMinecraft().player.inventory.getStackInSlot(i).getItem() == item)
                return i;
        }
        return -1;
    }
    
    public static void switchToSlotGhost(int slot) {
    	Minecraft mc = Minecraft.getMinecraft();
        if (slot != -1 && mc.player.inventory.currentItem != slot)
            mc.player.connection.sendPacket(new CPacketHeldItemChange(slot));
    }
    
    public static void switchToSlotGhost(Item item) {
    	Minecraft mc = Minecraft.getMinecraft();
        if (getHotbarItemSlot(item) != -1 && mc.player.inventory.currentItem != getHotbarItemSlot(item))
            switchToSlotGhost(getHotbarItemSlot(item));
    }
    
    public static void switchToSlot(int slot) {
    	Minecraft mc = Minecraft.getMinecraft();
        if (slot != -1 && mc.player.inventory.currentItem != slot)
            mc.player.inventory.currentItem = slot;
    }
    
    public static void switchToSlot(Item item) {
    	Minecraft mc = Minecraft.getMinecraft();
        if (getHotbarItemSlot(item) != -1 && mc.player.inventory.currentItem != getHotbarItemSlot(item))
            mc.player.inventory.currentItem = getHotbarItemSlot(item);
    }
}
 
