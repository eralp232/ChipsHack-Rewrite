package me.cumhax.chipshack.module.misc;

import com.mojang.authlib.GameProfile;
import me.cumhax.chipshack.module.Category;
import me.cumhax.chipshack.module.Module;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import java.util.UUID;

public class FakePlayer extends Module {
	
   public FakePlayer() {
      super("FakePlayer", "FakePlayer", Category.MISC);
   }

   public void onEnable() {
      if (mc.world == null) {
         return;
      }

      EntityOtherPlayerMP fakePlayer = new EntityOtherPlayerMP((World)mc.world, new GameProfile(UUID.fromString("0f75a81d-70e5-43c5-b892-f33c524284f2"), "CumHax"));
      fakePlayer.copyLocationAndAnglesFrom((Entity)mc.player);
      fakePlayer.rotationYawHead = mc.player.rotationYawHead;
      mc.world.addEntityToWorld(-100, (Entity)fakePlayer);
   }

   public void onDisable() {
      mc.world.removeEntityFromWorld(-100);
   }
}
