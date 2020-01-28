package me.anuw.anuwPlugin.events;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import me.anuw.anuwPlugin.Dong;

public class DongMoved implements Listener{
	
	Player owner;
	Dong dong;
	
	public DongMoved(Dong d) {
		dong = d;
		owner = d.owner;
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent event) {
		if(event.getPlayer()==owner) {
			Location pl = event.getTo();
			Location loc = new Location(owner.getWorld(),0,0,0,pl.getYaw(),pl.getPitch());
			/* Initially it may appear that these could go inside IF statements
			 * to avoid expensive maths, if the player is just moving their mouse,
			 * HOWEVER: the XYZ MUST be updated upon turning, as the rotation is 
			 * based on the origin of the ARMOR_STAND which is offset from the origin
			 * of the player.
			 */
			loc.setX(pl.getX()+Math.cos(Math.toRadians(pl.getYaw()))*0.375);
			loc.setY(pl.getY()-0.15);
			loc.setZ(pl.getZ()+Math.sin(Math.toRadians(pl.getYaw()))*0.375);
			dong.updateLoc(loc);
		}
	}
	
}
