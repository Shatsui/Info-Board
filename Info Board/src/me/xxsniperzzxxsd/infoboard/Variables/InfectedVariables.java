
package me.xxsniperzzxxsd.infoboard.Variables;

import me.xxsniperzzxx_sd.infected.Infected;

import org.bukkit.entity.Player;


public class InfectedVariables {

	public static String replaceVariables(String string, Player player) {
		String newString = "Hi";
		newString = string;

		if (newString.contains("<infectedpoints>"))
			newString = newString.replaceAll("<infectedpoints>", String.valueOf(Infected.playerGetPoints(player.getName())));
		if (newString.contains("<infectedscore>"))
			newString = newString.replaceAll("<infectedscore>", String.valueOf(Infected.playerGetScore(player.getName())));
		if (newString.contains("<infecteddeaths>"))
			newString = newString.replaceAll("<infecteddeaths>", String.valueOf(Infected.playerGetDeaths(player.getName())));
		if (newString.contains("<infectedkills>"))
			newString = newString.replaceAll("<infectedkills>", String.valueOf(Infected.playerGetKills(player.getName())));
		if (newString.contains("<infectedgamestate>"))
			newString = newString.replaceAll("<infectedgamestate>", String.valueOf(Infected.getGameState().getStatus()));

		return newString;
	}
}
