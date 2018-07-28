package us.infinitydev.ss;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SpeedCommand implements CommandExecutor{
	
	public void removeSpeed(Player p) {
		p.removePotionEffect(PotionEffectType.SPEED);
		ScuffedSpeedCore.instance.speedEnabled.remove(p.getUniqueId());
		p.sendMessage(ScuffedSpeedCore.prefix + ChatColor.RED + "Your Speed effects have been removed from you!");
	}
	
	public void addSpeed(Player p) {
		p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 9999999, 1));
		ScuffedSpeedCore.instance.speedEnabled.add(p.getUniqueId());
		p.sendMessage(ScuffedSpeedCore.prefix + ChatColor.GREEN + "A permanent Speed 2 effect has been added to you!");
	}
	
	public void commandUsage(CommandSender p) {
		p.sendMessage("/speed - Toggles your permanent Speed 2.");
		p.sendMessage("/speed on - Turns your permanent Speed 2 on.");
		p.sendMessage("/speed off - Turns your speed off.");
		p.sendMessage("/speed verify <target> - Tells you if the target has permanent Speed 2 on or off.");
		p.sendMessage("/speed on <target> - Turns the target's permanent Speed 2 on.");
		p.sendMessage("/speed off <target> - Turns the target's speed off.");
	}
	
	public void commandUsage(Player p) {
		p.sendMessage("/speed - Toggles your permanent Speed 2.");
		p.sendMessage("/speed on - Turns your permanent Speed 2 on.");
		p.sendMessage("/speed off - Turns your speed off.");
		p.sendMessage("/speed verify <target> - Tells you if the target has permanent Speed 2 on or off.");
		p.sendMessage("/speed on <target> - Turns the target's permanent Speed 2 on.");
		p.sendMessage("/speed off <target> - Turns the target's speed off.");
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		
		if(cmd.getName().equalsIgnoreCase("speed")) {
			if(args.length == 0) {
				if(sender.hasPermission("speed.use") && sender instanceof Player) {
					Player p = (Player) sender;
				    if(ScuffedSpeedCore.instance.speedEnabled.contains(p.getUniqueId())) {
						removeSpeed(p);
						return true;
					}else {
						addSpeed(p);
						return true;
					}
				}else {
					sender.sendMessage(ScuffedSpeedCore.prefix + ChatColor.RED + "You are not allowed to use this command!");
					return true;
				}
			}else if(args.length == 1) {
				if(args[0].equalsIgnoreCase("on")) {
					if(sender.hasPermission("speed.use") && sender instanceof Player) {
						Player p = (Player) sender;
						if(ScuffedSpeedCore.instance.speedEnabled.contains(p.getUniqueId())) {
							p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999, 1));
							p.sendMessage(ScuffedSpeedCore.prefix + ChatColor.GREEN + "Your permanent Speed 2 is already enabled (and has been reapplied)!");
							return true;
						}else {
							addSpeed(p);
							return true;
						}
					}else {
						sender.sendMessage(ScuffedSpeedCore.prefix + ChatColor.RED + "You are not allowed to use this command!");
						return true;
					}
				}else if(args[0].equalsIgnoreCase("off")) {
					if(sender.hasPermission("speed.use") && sender instanceof Player) {
						Player p = (Player) sender;
						if(ScuffedSpeedCore.instance.speedEnabled.contains(p.getUniqueId())) {
							removeSpeed(p);
							return true;
						}else {
							p.sendMessage(ScuffedSpeedCore.prefix + ChatColor.RED + "Your permanent Speed 2 is already removed!");
							return true;
						}
					}else {
						sender.sendMessage(ScuffedSpeedCore.prefix + ChatColor.RED + "You are not allowed to use this command!");
						return true;
					}
				}else if(args[0].equalsIgnoreCase("help")) {
					sender.sendMessage(ScuffedSpeedCore.prefix + ChatColor.GREEN + "Here are all the usages of the \"/speed\" command:");
					commandUsage(sender);
					return true;
				}else {
					sender.sendMessage(ScuffedSpeedCore.prefix + ChatColor.RED + "Invalid usage. Valid usages:");
					commandUsage(sender);
					return true;
				}
				//else
			}else if(args.length == 2) {
				if(args[0].equalsIgnoreCase("on")) {
					if(sender.hasPermission("speed.use.others")) {
						if(Bukkit.getPlayer(args[1]) != null) {
							Player ptarget = Bukkit.getPlayer(args[1]);
							if(ScuffedSpeedCore.instance.speedEnabled.contains(ptarget.getUniqueId())) {
								ptarget.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999, 1));
								ptarget.sendMessage(ScuffedSpeedCore.prefix + ChatColor.GREEN + "Your permanent Speed 2 is already enabled (and has been reapplied)!");
								sender.sendMessage(ScuffedSpeedCore.prefix + ChatColor.GREEN + "Command success!");
								return true;
							}else {
								addSpeed(ptarget);
								sender.sendMessage(ScuffedSpeedCore.prefix + ChatColor.GREEN + "Command success!");
								return true;
							}
						}else {
							sender.sendMessage(ScuffedSpeedCore.prefix + ChatColor.AQUA + args[1] + ChatColor.RED + " is not online!");
							return true;
						}
					}else {
						sender.sendMessage(ScuffedSpeedCore.prefix + ChatColor.RED + "You are not allowed to use this command!");
						return true;
					}
				}else if(args[0].equalsIgnoreCase("off")) {
					if(sender.hasPermission("speed.use.others")) {
						if(Bukkit.getPlayer(args[1]) != null) {
							Player ptarget = Bukkit.getPlayer(args[1]);
							if(ScuffedSpeedCore.instance.speedEnabled.contains(ptarget.getUniqueId())) {
								removeSpeed(ptarget);
								sender.sendMessage(ScuffedSpeedCore.prefix + ChatColor.GREEN + "Command success!");
								return true;
							}else {
								ptarget.sendMessage(ScuffedSpeedCore.prefix + ChatColor.RED + "Your permanent Speed 2 is already removed!");
								sender.sendMessage(ScuffedSpeedCore.prefix + ChatColor.GREEN + "Command success!");
								return true;
							}
						}else {
							sender.sendMessage(ScuffedSpeedCore.prefix + ChatColor.AQUA + args[1] + ChatColor.RED + " is not online!");
							return true;
						}
					}else {
						sender.sendMessage(ScuffedSpeedCore.prefix + ChatColor.RED + "You are not allowed to use this command!");
						return true;
					}
				}else if(args[0].equalsIgnoreCase("verify")) {
					if(sender.hasPermission("speed.verify")) {
						if(Bukkit.getPlayer(args[1]) != null) {
							Player ptarget = Bukkit.getPlayer(args[1]);
							if(ScuffedSpeedCore.instance.speedEnabled.contains(ptarget.getUniqueId())) {
								sender.sendMessage(ScuffedSpeedCore.prefix + ChatColor.AQUA + args[1] + ChatColor.RESET + " has speed enabled!");
								return true;
							}else {
								sender.sendMessage(ScuffedSpeedCore.prefix + ChatColor.AQUA + args[1] + ChatColor.RESET + " has speed disabled!");
								return true;
							}
						}else {
							sender.sendMessage(ScuffedSpeedCore.prefix + ChatColor.AQUA + args[1] + ChatColor.RED + " is not online!");
							return true;
						}
					}else {
						sender.sendMessage(ScuffedSpeedCore.prefix + ChatColor.RED + "You are not allowed to use this command!");
						return true;
					}
				}else {
					sender.sendMessage(ScuffedSpeedCore.prefix + ChatColor.RED + "Invalid usage. Valid usages:");
					commandUsage(sender);
					return true;
				}
			}
		}
		
		return false;
	}

}
