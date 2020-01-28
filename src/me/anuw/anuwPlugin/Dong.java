package me.anuw.anuwPlugin;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;

public class Dong {
	
	public Player owner;
	public ArmorStand dong;
	public Listener listener;
	
	//Create armorstand belonging to owner, holding m
	public Dong(Player owner, Material m) {
		this.owner = owner;
		dong = (ArmorStand) owner.getWorld().spawnEntity(new Location(owner.getWorld(), owner.getLocation().getX()+Math.cos(Math.toRadians(owner.getLocation().getYaw()))*0.375, owner.getLocation().getY()-0.15, owner.getLocation().getZ()+Math.sin(Math.toRadians(owner.getLocation().getYaw()))*0.375, owner.getLocation().getYaw(), owner.getLocation().getPitch()), EntityType.ARMOR_STAND);
		dong.setVisible(false);
		dong.setMarker(true);
		dong.setGravity(false);
        dong.getEquipment().setItemInMainHand(new ItemStack(m));
        //by default armorstands have a slight angle to their arms - override with 0,0,0
        dong.setRightArmPose(new EulerAngle(0,0,0));
	}
	
	//kills armorstand
	public void castrate() {
		dong.remove();
	}
	
	public void add(Listener l) {
		this.listener=l;
	}
	
	public void updateLoc(Location to) {
		dong.teleport(to);
	}
	
}
