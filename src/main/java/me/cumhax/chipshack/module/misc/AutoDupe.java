package me.cumhax.chipshack.module.misc;

import me.cumhax.chipshack.module.Category;
import me.cumhax.chipshack.module.Module;
import me.cumhax.chipshack.event.PacketEvent;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.*;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class AutoDupe extends Module
{
    private Entity e = null;

    public AutoDupe(String name, String description, Category category)
    {
        super(name, description, category);
    }

    @Override
    public void onEnable()
    {
        if (mc.player == null)
        {
            e = null;
            toggle();
            return;
        }

        if (!mc.player.isRiding())
        {
            e = null;
            toggle();
            return;
        }

        e = mc.player.getRidingEntity();

        mc.player.dismountRidingEntity();
        mc.world.removeEntity(e);
    }

    @Override
    public void onDisable()
    {
        if (e != null)
        {
            e.isDead = false;
            if (!mc.player.isRiding())
            {
                mc.world.spawnEntity(e);
                mc.player.startRiding(e, true);
            }
            e = null;
        }
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event)
    {
        if (e == null || mc.player.isRiding() || mc.player == null || mc.world == null) return;

        mc.player.onGround = true;

        e.setPosition(mc.player.posX, mc.player.posY, mc.player.posZ);

        mc.player.connection.sendPacket(new CPacketVehicleMove(e));
    }

    @SubscribeEvent
    public void onPacket(PacketEvent event)
    {
        if ((event.getPacket() instanceof CPacketInput) || (event.getPacket() instanceof CPacketPlayer.Position) || (event.getPacket() instanceof CPacketPlayer.PositionRotation) || (event.getPacket() instanceof CPacketPlayer.Rotation) || (event.getPacket() instanceof CPacketPlayerAbilities) || (event.getPacket() instanceof CPacketPlayerDigging) || (event.getPacket() instanceof CPacketPlayerTryUseItem || (event.getPacket() instanceof CPacketPlayerTryUseItemOnBlock) || (event.getPacket() instanceof CPacketUseEntity) || (event.getPacket() instanceof CPacketVehicleMove)))
        {
            event.setCanceled(true);
        }
    }
}