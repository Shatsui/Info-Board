
package me.xxsniperzzxxsd.infoboard;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
		
		for(String message : plugin.ScrollManager.getAllMessages()){
			int scrollOver = plugin.ScrollManager.getScrollCount(message);
			String color = plugin.ScrollManager.getScrollTextColor(message);
			String lastString = plugin.ScrollManager.getLastScrollText(message);
			
			Scoreboard infoBoard = player.getScoreboard();
			Objective infoObjective = player.getScoreboard().getObjective(DisplaySlot.SIDEBAR);
			int score = plugin.ScrollManager.getScrollScore(message);
				infoBoard.resetScores(Bukkit.getOfflinePlayer(lastString));
			
			//TODO: Add support for Titles
			String newString = message.substring(scrollOver, Math.min(message.length(), 16 - plugin.ScrollManager.getScrollTextColor(message).length() + scrollOver));
			try
			{
				@SuppressWarnings("unused")
				char t = newString.charAt(0);
			} catch (StringIndexOutOfBoundsException npe)
			{
				// RESETS TO THE WHOLE WORD
				newString = message.substring(0, Math.min(message.length(), 16 - plugin.ScrollManager.getScrollTextColor(message).length()));
				plugin.ScrollManager.setScroll(message, -1, newString, score, false);
				
			}
				newString = color + newString;
				plugin.ScrollManager.setScroll(message, plugin.ScrollManager.getScrollCount(message)+1, newString, score, false);
				Score newScore = infoObjective.getScore(Bukkit.getOfflinePlayer(newString));
				newScore.setScore(1);
				newScore.setScore(score);
			
		}
		return true;
	}

}
