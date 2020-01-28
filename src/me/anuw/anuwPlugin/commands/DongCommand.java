package me.anuw.anuwPlugin.commands;

import me.anuw.anuwPlugin.Main;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DongCommand implements CommandExecutor {
	
	private Main plugin;
	public Material defaultDongMaterial;
	
	public DongCommand(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("dong").setExecutor(this);
		defaultDongMaterial = Material.getMaterial(plugin.getConfig().getString("defaultDong").toUpperCase());
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(plugin.toggled) {
			if(sender instanceof Player) {
				//Does sender already possess a dong?
				for(int i=0; i<plugin.dongs.size(); i++) {
					if(plugin.dongs.get(i).owner.equals(sender)) {
						//sender DOES possess dong
						if(args.length < 1) {
							plugin.deleteDong(plugin.dongs.get(i));
							System.out.println("dong REMOVED. Currently "+plugin.dongs.size()+" dongs exist");
							sender.sendMessage(plugin.getConfig().getString("dongRemoved"));
						} else {
							Material m = Material.getMaterial(args[0].toUpperCase());
							if(m == null) {
								sender.sendMessage(ChatColor.RED+"No dong exists of that name. Use official minecraft names!");
								return true;
							}
							plugin.deleteDong(plugin.dongs.get(i));
							plugin.addDong((Player) sender, m);
							sender.sendMessage(plugin.getConfig().getString("dongChanged"));
						}
						return true;
					}
				}
				//sender does NOT possess dong
				if(args.length < 1) {
					plugin.addDong((Player) sender, defaultDongMaterial);
				} else {
					Material m = Material.getMaterial(args[0].toUpperCase());
					if(m == null) {
						sender.sendMessage(ChatColor.RED+"No dong exists of that name. Use official minecraft names!");
						return true;
					}
					plugin.addDong((Player) sender, m);
				}
				System.out.println("dong ADDED. Currently "+plugin.dongs.size()+" dongs exist");
				sender.sendMessage(plugin.getConfig().getString("dongAdded"));
				return true;
			}
			System.out.println("You have access to console... you already have the biggest dong of all ;)");
			return false;
		}
		sender.sendMessage(ChatColor.RED+"This plugin is disabled. To toggle the plugin, use /oop");
		return false;
	}
	
}
