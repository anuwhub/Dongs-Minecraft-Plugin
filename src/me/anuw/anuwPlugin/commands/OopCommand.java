package me.anuw.anuwPlugin.commands;

import me.anuw.anuwPlugin.Main;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class OopCommand implements CommandExecutor {
	
	private Main plugin;
	
	public OopCommand(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("oop").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		plugin.toggled = !plugin.toggled;
		if(plugin.toggled) {
			Bukkit.broadcastMessage(ChatColor.YELLOW+"Plugin: "+ChatColor.GREEN+"on");
		} else {
			plugin.hideEverything();
			Bukkit.broadcastMessage(ChatColor.YELLOW+"Plugin: "+ChatColor.RED+"off");
		}
		return true;
	}
	
}
