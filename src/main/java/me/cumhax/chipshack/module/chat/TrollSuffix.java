package me.cumhax.chipshack.module.chat;

import me.cumhax.chipshack.Client;
import me.cumhax.chipshack.module.Category;
import me.cumhax.chipshack.module.Module;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.Arrays;

public class TrollSuffix extends Module
{
	private final ArrayList<String> prefixes = new ArrayList<>(Arrays.asList("/", ".", "-", ",", ":", ";", "'", "\"", "+", "\\"));

	public TrollSuffix() {
		super("TrollSuffix", "", Category.CHAT);
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
			msg = String.format("%s \uFF5C  » ɴᴇʙᴜʟᴀ ᵟᵃᴸᴴᵃᶜᴷ » ɪ�?ᴀᴘᴄᴛ₊ » ʌгᴇѕ+ « ᴋᴀ�?ɪ ʙʟᴜᴇ �?ɴ ᴛ�?ᴘ » ˢ�?�ᵒʷ�?? �?εᎮнᗩεѕƭυѕ » ʙᴀᴄᴋᴅ�?�?ʀᴇᴅ | �?ᴇ�?ᴡ » ᴜɴɪᴄ�?ʀɴɢ�?ᴅ.ɢɢ ~~ ꜱᴇᴘᴘᴜᴋᴜ | ʜᴜᴢᴜɴɪɢʀᴇᴇɴ.ɢɢ™ » ʙᴀᴄᴋᴄʟɪᴇɴᴛ™ » ɴ�?ᴜ ʟᴇᴀᴋ ☯ �?? ғ�?ʀɢᴇʀᴀᴛ ♡ | ӨBΛMΛ ᄃᄂIΣПƬ - ᴇʟᴇ�?ᴇɴᴛᴀʀꜱ.ᴄ�?�? 》�?ꜱɪʀɪꜱ | WÔÔK�?Ê ÇLîëÑT™ {ʀᴀɪ�?ɴᴋᴇᴋ} ッ Ｒ�?�?Ｔ ｜ ʀᴜʜᴀ�?ᴀ | ᴅ�?ᴛғᴀɢ.ɪɴ™ >> ᴀʀɪѕᴛ�?ɪѕ ʳᵘˢʰᵉʳʰᵃᶜ�?", event.getMessage());
		}

		event.setMessage(msg);

	}
}