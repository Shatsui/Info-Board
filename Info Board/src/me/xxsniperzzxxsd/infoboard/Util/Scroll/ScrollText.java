
package me.xxsniperzzxxsd.infoboard.Util.Scroll;

import me.xxsniperzzxxsd.infoboard.Main;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;


public class ScrollText {

	public Main plugin;

	public ScrollText(Main instance)
	{
		plugin = instance;
	}

	public boolean slideScore(Player player) {

		if (plugin.ScrollManager.getScrollers(player) != null)
			for (Scroller sc : plugin.ScrollManager.getScrollers(player))
			{
				sc.scroll();

				String lastString = sc.getLastMessage();

				Scoreboard infoBoard = player.getScoreboard();
				Objective infoObjective = player.getScoreboard().getObjective(DisplaySlot.SIDEBAR);
				int score = infoObjective.getScore(Bukkit.getOfflinePlayer(lastString)).getScore();

				infoBoard.resetScores(Bukkit.getOfflinePlayer(lastString));

				Score tempScore = infoObjective.getScore(Bukkit.getOfflinePlayer(" "));
				tempScore.setScore(1);
				tempScore.setScore(score);

				String newMessage = sc.getScrolled();
				Score newScore = infoObjective.getScore(Bukkit.getOfflinePlayer(newMessage));
				newScore.setScore(1);
				newScore.setScore(score);
				infoBoard.resetScores(tempScore.getPlayer());

			}
		if (plugin.ScrollManager.getTitleScroller(player) != null)
		{
			Scroller sc = plugin.ScrollManager.getTitleScroller(player);
			sc.scroll();
			Objective infoObjective = player.getScoreboard().getObjective(DisplaySlot.SIDEBAR);

			infoObjective.setDisplayName(sc.getScrolled());
		}
		return true;
	}

}
