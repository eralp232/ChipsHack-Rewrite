package me.cumhax.chipshack.mixin.mixins.accessor;

import net.minecraft.network.play.client.CPacketUseEntity.Action;

public interface ICPacketUseEntity {
    void setEntityId(int var1);

    void setAction(Action var1);
}