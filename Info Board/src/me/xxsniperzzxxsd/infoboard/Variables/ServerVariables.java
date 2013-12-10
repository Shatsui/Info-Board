
package me.xxsniperzzxxsd.infoboard.Variables;

import me.xxsniperzzxxsd.infoboard.Util.VaraibleUtils.Lag;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;


public class ServerVariables {

	public static String replaceVariables(String string) {
		String newString = string;
		double tps = Lag.getTPS();

		// Server Variables
		if (newString.contains("<motd>"))
			newString = newString.replaceAll("<motd>", String.valueOf(Bukkit.getMotd()));
		if (newString.contains("<maxplayers>"))
			newString = newString.replaceAll("<maxplayers>", String.valueOf(Bukkit.getMaxPlayers()));
		if (newString.contains("<servername>"))
			newString = newString.replaceAll("<servername>", String.valueOf(Bukkit.getServerName()));
		if (newString.contains("<tps>"))
			newString = newString.replaceAll("<tps>", String.valueOf(Math.round(tps *100.0D)/100.0D));
		if (newString.contains("<peoplewith"))
		{
			String perm = newString.split("<peoplewith")[1].split(">")[0];

			int i = 0;
			for(Player ppl : Bukkit.getOnlinePlayers()){
				if(ppl.hasPermission(perm))
					i++;
			}
			newString = newString.replaceAll("<peoplewith" + (perm) + ">", String.valueOf(i));
		}
		return newString;
	}
}
