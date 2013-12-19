
package me.xxsniperzzxxsd.infoboard.Variables;

import org.bukkit.entity.Player;


public class StatsVariables {

	/*
	 * I'm kinda still kinda lost... i'll continue on this for the next update
	 */

	public static String replaceVariables(String string, Player player) {
		String newString = string;
	    /*StatsHandler sh = new StatsHandler();
		String name = player.getName();

		if (newString.contains("<statskill"))
		{
			String s = newString.split("<statskill")[1].split(">")[0];
			if (sh.getEntityType(s) != null)
				newString = newString.replaceAll("<statskill" + s + ">", String.valueOf(sh.getTotalMobsKilled(name, s)));
			else
				newString = newString.replaceAll("<statskill" + s + ">", "0");
		}
		if (newString.contains("<statsdeath"))
		{
			String s = newString.split("<statsdeath")[1].split(">")[0];
			if (sh.getEntityType(s) != null)
				newString = newString.replaceAll("<statsdeath" + s + ">", String.valueOf(sh.getTotalDeaths(name, s)));
			else
				newString = newString.replaceAll("<statsdeath" + s + ">", "0");
		}*/
		return newString;
	}
}
