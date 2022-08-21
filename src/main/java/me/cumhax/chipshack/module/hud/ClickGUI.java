package me.cumhax.chipshack.module.hud;

import me.cumhax.chipshack.Client;
import me.cumhax.chipshack.module.Category;
import me.cumhax.chipshack.module.Module;
import me.cumhax.chipshack.setting.Setting;
import org.lwjgl.input.Keyboard;

import java.util.Arrays;

public class ClickGUI extends Module {

    private final Setting theme = new Setting("Theme", this, Arrays.asList("White", "Black"));
    private final Setting color = new Setting("Color", this, Arrays.asList("Purple", "Red", "Blue", "Green", "Rainbow"));
    private final Setting outline = new Setting("Outline", this, false);
    private final Setting blur = new Setting("Blur", this, false);

	public ClickGUI()
	{
		super("ClickGUI", "", Category.HUD);
		setBind(Keyboard.KEY_RSHIFT);
        } 
	
    
	@Override
	public void onEnable()
	{
		mc.displayGuiScreen(Client.clickGUI);
	}
}
