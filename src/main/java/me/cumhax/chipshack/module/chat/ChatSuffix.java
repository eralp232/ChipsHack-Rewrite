package me.cumhax.chipshack.module.chat;


import me.cumhax.chipshack.Client;
import me.cumhax.chipshack.module.Category;
import me.cumhax.chipshack.module.Module;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.Arrays;

public class ChatSuffix extends Module
{
	private final ArrayList<String> prefixes = new ArrayList<>(Arrays.asList("/", ".", "-", ",", ":", ";", "'", "\"", "+", "\\"));

	public ChatSuffix(String name, String description, Category category)
	{
		super(name, description, category);

	}

	@SubscribeEvent
	public void onMessage(ClientChatEvent event)
	{
		if (mc.player == null || mc.world == null || event.getMessage().startsWith(Client.commandManager.getPrefix()))
		{
			return;
		}

		for (String prefix : prefixes)
		{
			if (event.getMessage().startsWith(prefix)) return;
		}

		String msg;
		{
			msg = String.format("%s \uFF5C ᴄʜɪᴘꜱʜᴀᴄᴋ", event.getMessage());
		}

		event.setMessage(msg);

	}
}