
package me.xxsniperzzxxsd.infoboard.Util;

import java.util.ArrayList;
import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;


//The Origional Message(With the new color) | scrollWords(How many scrolls so far) | lastScroll(What the last scroll was

//Message - How many scrolls so far
//		  - Last Scroll text
//        - 

public class ScrollManager {

	private ArrayList<String> scrollLines = new ArrayList<String>();
	private HashMap<String, Integer> scrollCount = new HashMap<String, Integer>();
	private HashMap<String, Integer> scrollScore = new HashMap<String, Integer>();
	private HashMap<String, String> scrollLast = new HashMap<String, String>();


	public void resetAllScroll(Player player) {
		for (String message : getAllMessages())
		{
			String lastString = scrollLast.get(message);

			Scoreboard infoBoard = player.getScoreboard();
			
			infoBoard.resetScores(Bukkit.getOfflinePlayer(lastString));
		}
		scrollCount.clear();
		scrollLast.clear();
		scrollLines.clear();
	}

	
	public boolean isScroll(String message) {
		return scrollLines.contains(message);
	}

	public int getScrollCount(String origionalMessage) {
		return scrollCount.get(origionalMessage);
	}

	public int getScrollScore(String origionalMessage) {
		return scrollScore.get(origionalMessage);
	}

	public String getLastScrollText(String origionalMessage) {
		return scrollLast.get(origionalMessage);
	}

	public boolean isScrollTitle(String origionalMessage) {
		return false;
		// TODO: Add support for title scrolls
	}

	public String getScrollTextColor(String origionalMessage) {
		if (ChatColor.getLastColors(getLastScrollText(origionalMessage)) != null)
			return ChatColor.getLastColors(getLastScrollText(origionalMessage));
		else
			return "§f";
	}

	public void setScroll(String message, int scrolls, String lastScroll, int score, boolean isTitle) {
		if (!scrollLines.contains(message))
			scrollLines.add(message);
		scrollCount.put(message, scrolls);
		scrollLast.put(message, lastScroll);
		scrollScore.put(message, score);
		System.out.println(message + "-" + scrolls + "-" + lastScroll + "-" + score);
		// TODO: Add support for title scrolls
	}

	public ArrayList<String> getAllMessages() {

		return scrollLines;
	}

}
