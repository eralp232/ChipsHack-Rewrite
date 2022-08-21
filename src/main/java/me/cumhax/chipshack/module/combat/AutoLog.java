package me.cumhax.chipshack.module.combat;

import me.cumhax.chipshack.module.Category;
import me.cumhax.chipshack.module.Module;

import me.cumhax.chipshack.setting.Setting;
import me.cumhax.chipshack.setting.SettingType;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class AutoLog extends Module
{
    private final Setting health = new Setting("Health", this, 10, 1, 30);

    public AutoLog(String name, String description, Category category)
    {
        super(name, description, category);
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event)
    {
        if (nullCheck()) return;

        if (mc.player.getHealth() <= health.getIntegerValue())
        {
            disable();
            mc.world.sendQuittingDisconnectingPacket();
            mc.loadWorld(null);
            mc.displayGuiScreen(new GuiMainMenu());
		}
   }
}