package me.cumhax.chipshack.module;

public enum Category
{
	COMBAT("Combat"),
	RENDER("Visuals"),
	MOVEMENT("Movement"),
	CHAT("Chat"),
	MISC("Miscellaneous"),
	HUD("Hud"),
	HIDDEN("Hidden");

	private String name;

	Category(String name)
	{
		setName(name);
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
}
