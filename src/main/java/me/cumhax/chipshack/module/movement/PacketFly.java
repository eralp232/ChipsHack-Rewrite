package me.cumhax.chipshack.module.movement;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.network.play.client.CPacketConfirmTeleport;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.common.MinecraftForge;
import me.cumhax.chipshack.module.Category;
import me.cumhax.chipshack.module.Module;

public class PacketFly extends Module
{
    private float counter;
    int j;
    
	public PacketFly()
	{
		super("PacketFly", "", Category.MOVEMENT);
        this.counter = 0.0f;
    }
    
    @Override
    public void onDisable() {
        MinecraftForge.EVENT_BUS.unregister((Object)this);
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent var1) {
        if (this.mc.player != null) {
            if (var1.phase == TickEvent.Phase.END) {
                if (!this.mc.player.isElytraFlying()) {
                    if (this.counter < 1.0f) {
                        this.counter += 0;
                        this.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(this.mc.player.posX, this.mc.player.posY, this.mc.player.posZ, this.mc.player.onGround));
                        this.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(this.mc.player.posX, this.mc.player.posY - 0.03, this.mc.player.posZ, this.mc.player.onGround));
                        this.mc.player.connection.sendPacket((Packet)new CPacketConfirmTeleport(++this.j));
                    }
                    else {
                        --this.counter;
                    }
                }
            }
            else if (this.mc.gameSettings.keyBindJump.isPressed()) {
                this.mc.player.motionY = 0.05f;
            }
            else {
                this.mc.player.motionY = -0.005f;
            }
        }
    }
    
    @Override
    public void onEnable() {
        MinecraftForge.EVENT_BUS.register((Object)this);
        this.j = 0;
    }
}
