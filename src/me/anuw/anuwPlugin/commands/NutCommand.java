package me.anuw.anuwPlugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.anuw.anuwPlugin.Main;

//This class is used for both /nut and /piss as they both share the majority of their code with one another
public class NutCommand implements CommandExecutor {
	
	private Main plugin;
	private int maxArgs=4;

	public NutCommand(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("nut").setExecutor(this);
		plugin.getCommand("piss").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			System.out.println("Robots can't "+cmd.getName());
			return true;
		}
		if(plugin.toggled) {
			if(args.length>maxArgs) {
				sender.sendMessage(ChatColor.RED+"Too many arguments! See '/"+cmd.getName()+" ?' for help.");
				return false;
			}
			float strength=0.1f;
			int duration=20;
			int load=1;
			Player p = (Player) sender;
			if(args.length>0) {
				//display help
				if(args[0].equalsIgnoreCase("?")) {
					sender.sendMessage(ChatColor.DARK_GREEN+"This command accepts "+maxArgs+" optional arguments.\n/"+cmd.getName()+" <strength> <duration> <load> <player>");
					return true;
				}
				
				int s;
				try {
					s = Integer.parseInt(args[0]);
				} catch(Exception e) {
					sender.sendMessage(ChatColor.RED+"Argument must be an integer!");
					return false;
				}
				int ms = plugin.getConfig().getInt("maxStrength");
				if(s>ms) {
					s=ms;
					sender.sendMessage(ChatColor.RED+"(Maximum strength of "+ms+")");
				}
				strength = s/5.0f;
				if(args.length>1) {
					try {
						duration = Integer.parseInt(args[1]);
					} catch(Exception e) {
						sender.sendMessage(ChatColor.RED+"Argument must be integer!");
						return false;
					}
					int md = plugin.getConfig().getInt("maxDuration");
					if(duration>md) {
						duration=md;
						sender.sendMessage(ChatColor.RED+"(Maximum duration of "+md+")");
					}
					if(args.length>2) {
						try {
							load = Integer.parseInt(args[2]);
						} catch(Exception e) {
							sender.sendMessage(ChatColor.RED+"Argument must be integer!");
							return false;
						}
						int ml = plugin.getConfig().getInt("maxLoad");
						if(load>ml) {
							load=ml;
							sender.sendMessage(ChatColor.RED+"(Maximum load of "+ml+")");
						}
						if(args.length>3) {
							p = Bukkit.getPlayer(args[3]);
							if(p==null) {
								sender.sendMessage(ChatColor.RED+"No such player currently online!");
								return false;
							}
						}
					}
				}
			}
			if(cmd.getName().equalsIgnoreCase("nut")) {
				nut(p, new int[]{duration}, strength, load, Particle.SPIT);
				if(plugin.getConfig().getBoolean("messageUserNut")) {
					sender.sendMessage(plugin.getConfig().getString("nutMessage"));
				}
			}
			if(cmd.getName().equalsIgnoreCase("piss")) {
				nut(p, new int[]{duration}, strength, load, Particle.WATER_WAKE);
				if(plugin.getConfig().getBoolean("messageUserPiss")) {
					sender.sendMessage(plugin.getConfig().getString("pissMessage"));
				}
			}
			return true;
		}
		sender.sendMessage(ChatColor.RED+"This plugin is disabled. To toggle the plugin, use /oop");
		return true;
	}
	
	//d is an array to circumvent java's rule about changing values inside lambdas
	public void nut(Player p, int[] d, float s, int ld, Particle pt) {
		new BukkitRunnable() {
			public void run() {
				Location l = p.getLocation();
				Location loc = new Location(l.getWorld(),0,0,0,0,0);
				//Position nut 0.5 blocks in front of player
				loc.setX(l.getX()-Math.sin(Math.toRadians(l.getYaw()))*0.5);
				loc.setY(l.getY()+0.75);
				loc.setZ(l.getZ()+Math.cos(Math.toRadians(l.getYaw()))*0.5);
				//Unfortunately, to use the directional parameters for spawnParticle, the count must be
				//set to 0. Therefore a loop must be used to create the desired amount of particles
				for(int i=0; i<ld; i++) {
					p.getWorld().spawnParticle(pt, loc, 0, -Math.sin(Math.toRadians(l.getYaw())), -l.getPitch()/45, Math.cos(Math.toRadians(l.getYaw())), s);
				}
				d[0]--;
				if(d[0]<1) {
					this.cancel();
				}
			}
		}.runTaskTimer(plugin, 0, 0);
	}

}
