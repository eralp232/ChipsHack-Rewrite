package me.cumhax.chipshack.gui.clickgui.button.settings;

import me.cumhax.chipshack.Client;
import me.cumhax.chipshack.gui.clickgui.button.SettingButton;
import me.cumhax.chipshack.module.Module;
import me.cumhax.chipshack.setting.Setting;
import me.cumhax.chipshack.util.font.FontUtil;

import java.awt.*;

public class BoolButton extends SettingButton
{
	private final Setting setting;

	public BoolButton(Module module, Setting setting, int X, int Y, int W, int H)
	{
		super(module, X, Y, W, H);
		this.setting = setting;
	}

	@Override
	public void render(int mX, int mY)
	{
		if (setting.getBooleanValue())
		{
			drawButton(mX, mY);
			FontUtil.drawStringWithShadow(setting.getName(), (float) (getX() + 6), (float) (getY() + 4), new Color(255, 255, 255, 232).getRGB());
		}
		else
		{
			if (isHover(getX(), getY(), getW(), getH() - 1, mX, mY))
			{
				Client.clickGUI.drawGradient(getX(), getY(), getX() + getW(), getY() + getH(), new Color(220, 220, 220, 232).getRGB(), new Color(218, 218, 218, 232).getRGB());
			}
			else
			{
				Client.clickGUI.drawGradient(getX(), getY(), getX() + getW(), getY() + getH(), new Color(240, 240, 240, 232).getRGB(), new Color(238, 238, 238, 232).getRGB());
			}

			FontUtil.drawString(setting.getName(), (float) (getX() + 6), (float) (getY() + 4), new Color(29, 29, 29, 232).getRGB());
		}
	}

	@Override
	public void mouseDown(int mX, int mY, int mB)
	{
		if (isHover(getX(), getY(), getW(), getH() - 1, mX, mY) && (mB == 0 || mB == 1))
		{
			setting.setBooleanValue(!setting.getBooleanValue());
		}
	}
}
