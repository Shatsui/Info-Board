package me.xxsniperzzxxsd.infoboard.Util;

import net.minecraft.server.v1_6_R3.EntityPlayer;

import org.bukkit.craftbukkit.v1_6_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;


public class Ping {

	public int getPing(Player p){
		CraftPlayer cp = (CraftPlayer) p;
		EntityPlayer ep = cp.getHandle();
		return ep.ping;
	}
	
	
}
