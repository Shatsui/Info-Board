
package me.xxsniperzzxxsd.infoboard.Variables;

import org.bukkit.entity.Player;

import praxis.slipcor.pvpstats.PVPData;


public class PVPStatsVariables {

	public static String replaceVariables(String string, Player player) {
		String newString = string;
		String name = player.getName();
		
		if(newString.contains("<pvpstatsdeaths>"))
			newString = newString.replaceAll("<pvpstatsdeaths>", String.valueOf(PVPData.getDeaths(name)));
		if(newString.contains("<pvpstatskills>"))
			newString = newString.replaceAll("<pvpstatskills>", String.valueOf(PVPData.getKills(name)));
		if(newString.contains("<pvpstatsmaxstreak>"))
			newString = newString.replaceAll("<pvpstatsmaxstreak>", String.valueOf(PVPData.getMaxStreak(name)));
		if(newString.contains("<pvpstatsstreak>"))
			newString = newString.replaceAll("<pvpstatsstreak>", String.valueOf(PVPData.getStreak(name)));
		
		
		return newString;
	}
}
