
package me.xxsniperzzxxsd.infoboard.Variables;

import nl.lolmewn.stats.StatType;
import nl.lolmewn.stats.api.StatsAPI;
import nl.lolmewn.stats.player.StatsPlayer;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;


@SuppressWarnings("deprecation")
public class StatsVariables {

	
	/*
	 * I have almost no idea what i'm doing... I'll work on it more for the next version, i just really needed to upload a version with the bug fixes
	 * 
	 */
	
	public static String replaceVariables(String string, Player player) {
		String newString = string;
		   RegisteredServiceProvider<StatsAPI> stats = Bukkit.getServer().getServicesManager().getRegistration(nl.lolmewn.stats.api.StatsAPI.class);
	       StatsAPI sa = stats.getProvider();
	       StatsPlayer sp = sa.getStatsPlayer(player.getName());	       
	       //if(sa.isUsingBetaFunctions())
	    	//   sp.gets.getStatData(sp., Bukkit.getWorld("0"), true);
	       
			if (newString.contains("<statskills>"))
				newString = newString.replaceAll("<statskills>", String.valueOf(sp.getStat(StatType.KILL, true).getValueUnsafe()));
			if (newString.contains("<statsdeaths>"))
				newString = newString.replaceAll("<statsdeaths>", String.valueOf(sp.getStat(StatType.DEATH, true).getValueUnsafe()));
			if (newString.contains("<statsplaytime>"))
				newString = newString.replaceAll("<statsplaytime>", String.valueOf(sp.getStat(StatType.PLAYTIME, true).getValueUnsafe()));
			if (newString.contains("<statsblocksplaced>"))
				newString = newString.replaceAll("<statsblocksplaced>", String.valueOf(sp.getStat(StatType.BLOCK_PLACE, true).getValueUnsafe()));
			if (newString.contains("<statsblocksbroken>"))
				newString = newString.replaceAll("<statsblocksbroken>", String.valueOf(sp.getStat(StatType.BLOCK_BREAK, true).getValueUnsafe()));
			if (newString.contains("<statsarrows>"))
				newString = newString.replaceAll("<statsarrows>", String.valueOf(sp.getStat(StatType.ARROWS, true).getValueUnsafe()));
			if (newString.contains("<statsbedenter>"))
				newString = newString.replaceAll("<statsbedenter>", String.valueOf(sp.getStat(StatType.BEDENTER, true).getValueUnsafe()));
			if (newString.contains("<statscommands>"))
				newString = newString.replaceAll("<statscommands>", String.valueOf(sp.getStat(StatType.COMMANDS_DONE, true).getValueUnsafe()));
			if (newString.contains("<statswords>"))
				newString = newString.replaceAll("<statswords>", String.valueOf(sp.getStat(StatType.WORDS_SAID, true).getValueUnsafe()));
			if (newString.contains("<statsvotes>"))
				newString = newString.replaceAll("<statsvotes>", String.valueOf(sp.getStat(StatType.VOTES, true).getValueUnsafe()));
			return newString;
	}
}
