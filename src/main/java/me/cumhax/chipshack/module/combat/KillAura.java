package me.cumhax.chipshack.module.combat;

import net.minecraft.util.EnumHand;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.network.play.client.CPacketPlayer.Rotation;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemSword;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import me.cumhax.chipshack.module.Category;
import me.cumhax.chipshack.module.Module;
import me.cumhax.chipshack.setting.Setting;
import me.cumhax.chipshack.util.aura.MathUtil;
import me.cumhax.chipshack.util.aura.PacketSendEvent;

import java.util.Arrays;
import java.util.Comparator;
import net.minecraft.entity.player.EntityPlayer;
import java.util.List;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class KillAura extends Module {
	
	boolean isAttacking = false;
	public static EntityPlayer target;
	boolean rotating;
	
	private final Setting range = new Setting("Range", this, 4, 0, 10);
	private final Setting rotation = new Setting("Rotation", this, true);
	private final Setting swordOnly = new Setting("Sword Only", this, false);
	private final Setting mode = new Setting("Mode", this, Arrays.asList("Default", "Leg" ));

	public KillAura() 
	{
		super("Aura", "", Category.COMBAT);
	}
	
	@SubscribeEvent
	public void onTick(final TickEvent.ClientTickEvent event) {
		if (mc.player != null || mc.world != null) {
            for (EntityPlayer player : mc.world.playerEntities) {
                if (player != mc.player) {
                    if (mc.player.getDistance(player) < range.getIntegerValue()) {
                        if (player.isDead || player.getHealth() > 0) {
                            if (rotating && rotation.getBooleanValue()) {
                                float[] angle = MathUtil.calcAngle(mc.player.getPositionEyes(mc.getRenderPartialTicks()), player.getPositionVector());
                                mc.player.rotationYaw = angle[0];
                                if (mode.getEnumValue().equals("Leg")) {
                                    mc.player.rotationPitch = angle[1];
                                }
                            }
                            attackPlayer(player);
                        }
                        target = player;
                    }
                    else {
                        rotating = false;
                    }

                }
            }
        }
	}
	
	 public void attackPlayer(EntityPlayer player) {
	        if (player != null) {
	            if (player != mc.player) {
	                if (mc.player.getCooledAttackStrength(0.0f) >= 1) {
	                    rotating = true;
	                    mc.playerController.attackEntity(mc.player, player);
	                    mc.player.swingArm(EnumHand.MAIN_HAND);
	                }
	            }
	        }
	        else {
	            rotating = false;
	    }
    }
}	
