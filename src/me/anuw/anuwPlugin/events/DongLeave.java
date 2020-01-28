package me.anuw.anuwPlugin.events;
import me.anuw.anuwPlugin.Main;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import me.anuw.anuwPlugin.Dong;

public class DongLeave implements Listener{
	
	public Dong dong;
	public Main plugin;
	
	public DongLeave(Dong d, Main m) {
		plugin = m;
		dong = d;
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		if(dong.owner == event.getPlayer()) {
			plugin.deleteDong(dong);
		}
	}
	
}
