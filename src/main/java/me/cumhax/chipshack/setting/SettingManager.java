package me.cumhax.chipshack.setting;

import me.cumhax.chipshack.module.Module;

import java.util.ArrayList;

public class SettingManager
{
	private final ArrayList<Setting> settings = new ArrayList<>();

	public void addSetting(Setting setting)
	{
		settings.add(setting);
	}

	public ArrayList<Setting> getSettings(Module module)
	{
		ArrayList<Setting> sets = new ArrayList<>();

		for (Setting setting : settings)
		{
			if (setting.getModule().equals(module))
			{
				sets.add(setting);
			}
		}

		return sets;
	}

	public Setting getSetting(String moduleName, String name)
	{
		for (Setting setting : settings)
		{
			if (setting.getModule().getName().equalsIgnoreCase(moduleName) && setting.getName().equalsIgnoreCase(name))
			{
				return setting;
			}
		}

		return null;
	}

	public ArrayList<Setting> getSettings()
	{
		return settings;
	}
}
