package me.cumhax.chipshack.module.chat;

import me.cumhax.chipshack.module.Category;
import me.cumhax.chipshack.module.Module;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import me.cumhax.chipshack.util.LoggerUtil;
import me.cumhax.chipshack.setting.Setting;
import me.cumhax.chipshack.setting.SettingType;

import java.util.ArrayList;
import java.util.Arrays;

// Made by Lnadav

public class Shrug extends Module
{
	private final Setting Mode = new Setting("Mode", this, Arrays.asList(
		// "Append",
		"Replace"
		));
		
	public Shrug(String name, String description, Category category)
	{
		super(name, description, category);
	}

	@SubscribeEvent
	public void onChat(ClientChatEvent event)
	{
		// if ( Mode.getEnumValue().equalsIgnoreCase("Append") ){
		// 	if ( event.getMessage().contains("shrug")) {
		// 		event.setMessage(event.getMessage().replace("shrug", "") + " \u00AF\\_(\u30C4)_/\u00AF");
		// 	}
        // }
        
		if ( Mode.getEnumValue().equalsIgnoreCase("Replace") ){
			if ( event.getMessage().contains("shrug") ) {
				event.setMessage(event.getMessage().replace("shrug", "\u00AF\\_(\u30C4)_/\u00AF"));
			}
		}
	}
}