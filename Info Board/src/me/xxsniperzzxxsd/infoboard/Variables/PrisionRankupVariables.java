
package me.xxsniperzzxxsd.infoboard.Variables;


import net.craftservers.prisonrankup.PrisonRankup;

import org.bukkit.entity.Player;


public class PrisionRankupVariables {

	public static String replaceVariables(String string, Player player) {
		String newString = string;
			String prefix = "prisonrankup";
			String name = player.getName();

		if (newString.contains("<" + prefix + "rank>"))
			newString = newString.replaceAll("<" + prefix + "rank>", PrisonRankup.getRank(name));
		if (newString.contains("<" + prefix + "nextrank>"))
			newString = newString.replaceAll("<" + prefix + "nextrank>", PrisonRankup.getNextRank(name));
		if (newString.contains("<" + prefix + "rankprice>"))
			newString = newString.replaceAll("<" + prefix + "rankprice>", PrisonRankup.getRankPrice(PrisonRankup.getNextRank(name)));
		
		return newString;
	}
}
