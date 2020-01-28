package me.anuw.anuwPlugin;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import me.anuw.anuwPlugin.commands.*;
import me.anuw.anuwPlugin.events.*;

public class Main extends JavaPlugin {
	
	public boolean toggled = true;
	public List<Dong> dongs = new ArrayList<Dong>();

	@Override
	public void onEnable() {
		System.out.println("[Dongs] Launched!");
		this.saveDefaultConfig();
		new OopCommand(this);
		new DongCommand(this);
		new NutCommand(this);
		toggled = this.getConfig().getBoolean("startEnabled");
	}
	
	@Override
	public void onDisable() {
		hideEverything();
		System.out.println("[Dongs] Closed!");
	}
	
	public void addDong(Player p, Material m) {
		//Because a Dong object cannot be added to a Player object, we must add a Player object to each Dong 
		Dong d = new Dong(p, m);
		Listener dongListen = new DongMoved(d);
		getServer().getPluginManager().registerEvents(dongListen, this);
		dongs.add(d);
		d.add(dongListen);
		getServer().getPluginManager().registerEvents(new DongLeave(d, this), this);
		
		/*
		 * The solution below is entirely client side (I think) which is BRILLIANT.
		 * Allows dongs to align based on body instead of head direction
		 * However, no tools exist as far as I'm aware to offset passengers (besides Y)
		 * 
		 * p.addPassenger(d.dong);
		 */
	}
	
	public void deleteDong(Dong d) {
		HandlerList.unregisterAll(d.listener);
		d.listener = null;
		d.castrate();
		dongs.remove(d);
	}
	
	public void hideEverything() {
		for(Dong d: dongs) {
			d.castrate();
		}
		dongs = new ArrayList<Dong>();
	}
	
}
