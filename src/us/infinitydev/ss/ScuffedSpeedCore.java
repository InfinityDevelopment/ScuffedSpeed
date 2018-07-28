package us.infinitydev.ss;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ScuffedSpeedCore extends JavaPlugin{
	
	public static ScuffedSpeedCore instance;
	List<UUID> speedEnabled = new ArrayList<UUID>();
	public static String prefix;
	
	public void registerListeners() {
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new PluginListener(), this);
	}
	
	public void registerCommands() {
		getCommand("speed").setExecutor(new SpeedCommand());
	}
	
	public void onEnable() {
		instance = this;
		registerListeners();
		registerCommands();
		getConfig().options().copyDefaults(true);
		saveDefaultConfig();
		
		if(instance.getConfig().getString("Prefix") != null) {
			prefix = instance.getConfig().getString("Prefix").replaceAll("(&([a-f0-9]))", "\u00A7$2") + " " + ChatColor.RESET;
		}else {
			prefix = ChatColor.BLUE + "ScuffedSpeed" + ChatColor.GRAY + " > " + ChatColor.RESET;
			getConfig().set("Prefix", prefix);
			System.out.println("[ScuffedSpeed] Prefix generated in config!");
		}
		
		Bukkit.getScheduler().scheduleSyncRepeatingTask(instance, new Runnable() {
			public void run() {
				for(UUID pu : speedEnabled) {
					if(Bukkit.getPlayer(pu) != null) {
						Bukkit.getPlayer(pu).addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 2, Integer.MAX_VALUE));
					}else {
						speedEnabled.remove(pu);
					}
				}
			}
		}, 0, 600 * 20);
	}
	
	public void onDisable() {
		saveConfig();
		instance = null;
	}

}
