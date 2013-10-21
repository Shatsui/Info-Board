
package me.xxsniperzzxxsd.infoboard.Variables;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;


public class WorldGuardVariables {

	public static String replaceVariables(String string, Player player) {
		String newString = "Hi";
		newString = string;
		ArrayList<String> playersRegions = new ArrayList<String>();
		ArrayList<ProtectedRegion> inRegions = new ArrayList<ProtectedRegion>();
		WorldGuardPlugin wg = (WorldGuardPlugin) Bukkit.getPluginManager().getPlugin("WorldGuard");
		LocalPlayer lplayer = wg.wrapPlayer(player);
		RegionManager regions = wg.getRegionManager(player.getWorld());
		
		for(Entry<String, ProtectedRegion> r : regions.getRegions().entrySet()){
			if(r.getValue().isOwner(lplayer)){
				playersRegions.add(r.getKey());
			}
		}
		Iterator<ProtectedRegion> iter = regions.getApplicableRegions(player.getLocation()).iterator();
		while (iter.hasNext())
			inRegions.add(iter.next());
		
        ////////////////////////////////////////////////////
		
		if (newString.contains("<worldguardinid>"))
			if(inRegions.get(0) != null)
				newString = newString.replaceAll("<worldguardinid>", String.valueOf(inRegions.get(0).getId()));
			else
				newString = newString.replaceAll("<worldguardinid>", "Unkown");

		if (newString.contains("<worldguardinowner>"))
			if(inRegions.get(0) != null)
				newString = newString.replaceAll("<worldguardinowner>", String.valueOf(inRegions.get(0).getOwners().getPlayers().iterator().next()));
			else
				newString = newString.replaceAll("<worldguardinowner>", "Unkown");

		if (newString.contains("<worldguardinvolume>"))
			if(inRegions.get(0) != null)
				newString = newString.replaceAll("<worldguardinvolume>", String.valueOf(inRegions.get(0).volume()));
			else
				newString = newString.replaceAll("<worldguardinvolume>", "0");

		if (newString.contains("<worldguardinmembers>"))
			if(inRegions.get(0) != null)
				newString = newString.replaceAll("<worldguardinmembers>", String.valueOf(inRegions.get(0).getMembers().size()));
			else
				newString = newString.replaceAll("<worldguardinmembers>", "0");
					
		return newString;
	}
}
