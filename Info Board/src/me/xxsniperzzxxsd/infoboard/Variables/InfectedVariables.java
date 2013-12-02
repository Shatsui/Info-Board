
package me.xxsniperzzxxsd.infoboard.Variables;

import me.sniperzciinema.infected.Handlers.Lobby;
import me.sniperzciinema.infected.Handlers.Player.InfPlayer;
import me.sniperzciinema.infected.Handlers.Player.InfPlayerManager;
import me.sniperzciinema.infected.Tools.Settings;

import org.bukkit.entity.Player;


public class InfectedVariables {

	public static String replaceVariables(String string, Player player) {
		String newString = string;
		InfPlayer ip = InfPlayerManager.getInfPlayer(player);

		if (newString.contains("<infectedpoints>"))
			newString = newString.replaceAll("<infectedpoints>", String.valueOf(ip.getPoints(Settings.VaultEnabled())));
		if (newString.contains("<infectedscore>"))
			newString = newString.replaceAll("<infectedscore>", String.valueOf(ip.getScore()));
		if (newString.contains("<infecteddeaths>"))
			newString = newString.replaceAll("<infecteddeaths>", String.valueOf(ip.getDeaths()));
		if (newString.contains("<infectedkills>"))
			newString = newString.replaceAll("<infectedkills>", String.valueOf(ip.getKills()));
		if (newString.contains("<infectedhighestkills>"))
			newString = newString.replaceAll("<infectedhighestkills>", String.valueOf(ip.getHighestKillStreak()));
		if (newString.contains("<infectedgamestate>"))
			newString = newString.replaceAll("<infectedgamestate>", String.valueOf(Lobby.getGameState().toString()));
		if (newString.contains("<infectedactivearena>"))
			newString = newString.replaceAll("<infectedactivearena>", String.valueOf(Lobby.getActiveArena().getName()));
		if (newString.contains("<infectedplayers>"))
			newString = newString.replaceAll("<infectedplayers>", String.valueOf(Lobby.getInGame().size()));

		return newString;
	}
}
