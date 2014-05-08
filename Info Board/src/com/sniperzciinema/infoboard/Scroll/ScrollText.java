
package com.sniperzciinema.infoboard.Scroll;


import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import com.sniperzciinema.infoboard.InfoBoard;
import com.sniperzciinema.infoboard.Scoreboard.Create;
import com.sniperzciinema.infoboard.Util.Files;


public class ScrollText {
	
	public boolean slideScore(Player player) {
		if (!Files.getConfig().getStringList("Disabled Worlds").contains(player.getWorld().getName()) && !InfoBoard.hidefrom.contains(player.getName()) && ((player.getScoreboard().getObjective(DisplaySlot.SIDEBAR) == null) || player.getScoreboard().getObjective(DisplaySlot.SIDEBAR).getName().equalsIgnoreCase("InfoBoard")))
		{
			if (ScrollManager.getScrollers(player) != null)
				for (Scroller sc : ScrollManager.getScrollers(player))
				{
					sc.scroll();
					
					String lastString = sc.getLastMessage();
					Scoreboard infoBoard = player.getScoreboard();
					Objective infoObjective = player.getScoreboard().getObjective(DisplaySlot.SIDEBAR);
					int score = infoObjective.getScore(Bukkit.getOfflinePlayer(lastString)).getScore();
					
					try
					{
						lastString = sc.getLastMessage();
						infoBoard = player.getScoreboard();
						infoObjective = player.getScoreboard().getObjective(DisplaySlot.SIDEBAR);
						score = infoObjective.getScore(Bukkit.getOfflinePlayer(lastString)).getScore();
					}
					catch (Exception e)
					{
						Create.createScoreBoard(player);
					}
					infoBoard.resetScores(Bukkit.getOfflinePlayer(lastString));
					
					Score tempScore = infoObjective.getScore(Bukkit.getOfflinePlayer("&1&2&3 "));
					tempScore.setScore(1);
					tempScore.setScore(score);
					
					String newMessage = sc.getScrolled();
					Score newScore = infoObjective.getScore(Bukkit.getOfflinePlayer(newMessage));
					newScore.setScore(1);
					newScore.setScore(score);
					infoBoard.resetScores(tempScore.getPlayer());
					
				}
			try
			{
				if (ScrollManager.getTitleScroller(player) != null)
				{
					Scroller sc = ScrollManager.getTitleScroller(player);
					sc.scroll();
					Objective infoObjective = player.getScoreboard().getObjective(DisplaySlot.SIDEBAR);
					
					infoObjective.setDisplayName(sc.getScrolled());
				}
			}
			catch (NullPointerException e)
			{
			}
		}
		else
			ScrollManager.reset(player);
		
		return true;
	}
	
}
