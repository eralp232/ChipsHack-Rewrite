package me.cumhax.chipshack.module;

import me.cumhax.chipshack.Client;
import me.cumhax.chipshack.setting.Setting;
import me.cumhax.chipshack.module.combat.*;
import me.cumhax.chipshack.module.misc.*;
import me.cumhax.chipshack.module.movement.*;
import me.cumhax.chipshack.module.render.*;
import me.cumhax.chipshack.module.chat.*;
import me.cumhax.chipshack.module.hud.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class ModuleManager
{
	private final ArrayList<Module> modules = new ArrayList<>();

	public ModuleManager()
	{
		// Render Category
                modules.add(new Animations());
		modules.add(new AntiWeather());
		modules.add(new BlockHighlight());
                modules.add(new Chams());
                modules.add(new ExtraTab());
		modules.add(new Fullbright());
		modules.add(new HoleESP());
		modules.add(new NameTags());
		modules.add(new NightMode());
                modules.add(new ViewModel());
		
		// Combat Category
		modules.add(new KillAura());
		modules.add(new Auto32K("Auto32K", "Automatically handles 32ks for you in combat", Category.COMBAT));
		modules.add(new AutoArmor());
		modules.add(new AutoCrystal());
                modules.add(new AutoCrystalNew());
		modules.add(new AutoLog("AutoLog", "Automatically logs out when your health is low", Category.COMBAT));
		modules.add(new AutoTrap("AutoTrap", "Traps players", Category.COMBAT));
		modules.add(new BedAura());
		modules.add(new BowSpam());
		modules.add(new AutoTotem());
		modules.add(new Criticals("Criticals", "Deal critical hits without jumping", Category.COMBAT));
                modules.add(new HoleFiller());
		modules.add(new KeyPearl());
		modules.add(new Offhand());
		modules.add(new OffhandNew());
		modules.add(new Surround("Surround", "Places blocks around you", Category.COMBAT));

		// Movement Category
		modules.add(new Anchor());
		modules.add(new AntiVoid ());
		modules.add(new BoatFly());
		modules.add(new FastWeb());
		modules.add(new InventoryMove());
		modules.add(new LongJump("LongJump", "Jumps far", Category.MOVEMENT));
		modules.add(new NoPush());
		modules.add(new NoSlow());
		modules.add(new PacketFly());
		modules.add(new ReverseStep());
		modules.add(new Speed("Speed", "Allows you to move faster", Category.MOVEMENT));
		modules.add(new Sprint("Sprint", "Automatically toggles sprint for you", Category.MOVEMENT));
		modules.add(new Step());
		modules.add(new Velocity());

		// Chat Category
		modules.add(new AutoRAT());
		modules.add(new AutoSuicide());
		modules.add(new BetterChat("BetterChat", "Modifies the look of your ingame chat", Category.CHAT));
		modules.add(new ChatSuffix("ChatSuffix", "Adds a suffix to your chat messages", Category.CHAT));
		modules.add(new Shrug("Shrug", "Adds the shrug emoji when used", Category.CHAT));
		modules.add(new TrollSuffix());

		// Misc Category
		modules.add(new AutoDupe("AutoDupe", "Automatically performs the SalC1 TreeMC dupe", Category.MISC));
                modules.add(new AutoGG());
		modules.add(new Blink("Blink", "Fake lag", Category.MISC));
		modules.add(new FakePlayer());
		modules.add(new FastUse());
		modules.add(new Godmode());
                modules.add(new ModuleAutoGhastFarmer());
		modules.add(new MultiTask());
		modules.add(new PacketCanceller());
		modules.add(new PacketMine("PacketMine", "Mine blocks with packets", Category.MISC));
		modules.add(new RPC());
		modules.add(new Timer("Timer", "Speeds up your game", Category.MISC));

        // Hud Category
		modules.add(new ArmorHud());
		modules.add(new ClickGUI());
		modules.add(new CustomFont());
		modules.add(new Hud());
		
		for (Module module : modules) {
			for (Field declaredField : module.getClass().getDeclaredFields()) {
				declaredField.setAccessible(true);
				if (declaredField.getType() == Setting.class) {
					try {
						Client.settingManager.addSetting((Setting) declaredField.get(module));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	public static boolean isModuleEnabled ( String zoom ) {
		return false;
	}

	public ArrayList<Module> getModules()
	{
		return modules;
	}

	public Module getModule(String name)
	{
		for (Module module : modules)
		{
			if (module.getName().equalsIgnoreCase(name)) return module;
		}

		return null;
	}

	public ArrayList<Module> getModules(Category category)
	{
		ArrayList<Module> mods = new ArrayList<>();

		for (Module module : modules)
		{
			if (module.getCategory().equals(category)) mods.add(module);
		}

		return mods;
	}

	public ArrayList<Module> getEnabledModules()
	{
		return modules.stream().filter(Module::isEnabled).collect(Collectors.toCollection(ArrayList::new));
	}

	public Module getModuleByName ( String moduleName ) {
		return null;
	}
}
