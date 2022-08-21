package me.cumhax.chipshack.gui.clickgui.button.settings;

import me.cumhax.chipshack.Client;
import me.cumhax.chipshack.gui.clickgui.button.SettingButton;
import me.cumhax.chipshack.module.Module;
import me.cumhax.chipshack.setting.Setting;
import me.cumhax.chipshack.util.font.FontUtil;

import java.awt.*;
import java.text.DecimalFormat;

public class SliderButton extends SettingButton
{
	private final Setting setting;
	protected boolean dragging;
	protected int sliderWidth;

	SliderButton(Module module, Setting setting, int X, int Y, int W, int H)
	{
		super(module, X, Y, W, H);
		this.dragging = false;
		this.sliderWidth = 0;
		this.setting = setting;
	}

	protected void updateSlider(int mouseX)
	{
	}

	@Override
	public void render(int mX, int mY)
	{
		updateSlider(mX);

		if (isHover(getX(), getY(), getW(), getH() - 1, mX, mY))
		{
			Client.clickGUI.drawGradient(getX() + (sliderWidth) + 6, getY(), getX() + getW(), getY() + getH(), new Color(220, 220, 220, 232).getRGB(), new Color(218, 218, 218, 232).getRGB());
			switch (Client.settingManager.getSetting("ClickGUI", "Color").getEnumValue())
			{
			case "Red":
			                Client.clickGUI.drawGradient(getX(), getY(), getX() + (sliderWidth) + 6, getY() + getH(), new Color(220, 30, 220, 232).getRGB(), new Color(216, 30, 30, 232).getRGB());
					break;
		        case "Green":
			               Client.clickGUI.drawGradient(getX(), getY(), getX() + (sliderWidth) + 6, getY() + getH(), new Color(30, 30, 220, 232).getRGB(), new Color(30, 30, 216, 232).getRGB());
					break;
			case "Blue":
			               Client.clickGUI.drawGradient(getX(), getY(), getX() + (sliderWidth) + 6, getY() + getH(), new Color(30, 30, 220, 232).getRGB(), new Color(30, 30, 216, 232).getRGB());
					break;
			case "Purple":
					Client.clickGUI.drawGradient(getX(), getY(), getX() + (sliderWidth) + 6, getY() + getH(), new Color(140, 0, 180, 232).getRGB(), new Color(136, 0, 180, 232).getRGB());
					break;
				default:
					break;
			}
		}
		else
		{
			Client.clickGUI.drawGradient(getX() + (sliderWidth) + 6, getY(), getX() + getW(), getY() + getH(), new Color(240, 240, 240, 232).getRGB(), new Color(238, 238, 238, 232).getRGB());
			switch (Client.settingManager.getSetting("ClickGUI", "Color").getEnumValue())
			{
			case "Red":
			                Client.clickGUI.drawGradient(getX(), getY(), getX() + (sliderWidth) + 6, getY() + getH(), new Color(220, 30, 220, 232).getRGB(), new Color(216, 30, 30, 232).getRGB());
					break;
		        case "Green":
			               Client.clickGUI.drawGradient(getX(), getY(), getX() + (sliderWidth) + 6, getY() + getH(), new Color(30, 30, 220, 232).getRGB(), new Color(30, 30, 216, 232).getRGB());
					break;
			case "Blue":
			               Client.clickGUI.drawGradient(getX(), getY(), getX() + (sliderWidth) + 6, getY() + getH(), new Color(30, 30, 220, 232).getRGB(), new Color(30, 30, 216, 232).getRGB());
					break;
			case "Purple":
					Client.clickGUI.drawGradient(getX(), getY(), getX() + (sliderWidth) + 6, getY() + getH(), new Color(140, 0, 180, 232).getRGB(), new Color(136, 0, 180, 232).getRGB());
					break;
				default:
					break;
			}
		}


		FontUtil.drawStringWithShadow(setting.getName(), (float) (getX() + 6), (float) (getY() + 4), new Color(255, 255, 255, 255).getRGB());
		FontUtil.drawStringWithShadow(String.valueOf(setting.getIntegerValue()), (float) ((getX() + getW() - 6) - FontUtil.getStringWidth(String.valueOf(setting.getIntegerValue()))), (float) (getY() + 4), new Color(255, 255, 255, 255).getRGB());
	}

	public void mouseDown(int mX, int mY, int mB)
	{
		if (isHover(getX(), getY(), getW(), getH() - 1, mX, mY))
		{
			dragging = true;
		}
	}

	public void mouseUp(int mouseX, int mouseY)
	{
		dragging = false;
	}

	public void close()
	{
		dragging = false;
	}

	public static class IntSlider extends SliderButton
	{
		private final Setting intSetting;

		public IntSlider(Module module, Setting setting, int X, int Y, int W, int H)
		{
			super(module, setting, X, Y, W, H);
			intSetting = setting;
		}

		@Override
		protected void updateSlider(final int mouseX)
		{
			final double diff = Math.min(getW(), Math.max(0, mouseX - getX()));
			final double min = intSetting.getMinIntegerValue();
			final double max = intSetting.getMaxIntegerValue();
			sliderWidth = (int) ((getW() - 6) * (intSetting.getIntegerValue() - min) / (max - min));
			if (dragging)
			{
				if (diff == 0.0)
				{
					intSetting.setIntegerValue(intSetting.getIntegerValue());
				}
				else
				{
					final DecimalFormat format = new DecimalFormat("##");
					final String newValue = format.format(diff / getW() * (max - min) + min);
					intSetting.setIntegerValue(Integer.parseInt(newValue));
				}
			}
		}
	}
}
