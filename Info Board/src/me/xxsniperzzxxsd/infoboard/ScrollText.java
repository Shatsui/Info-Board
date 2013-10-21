package me.xxsniperzzxxsd.infoboard;

import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;


public class ScrollText {
	

	// Row the scrolls in | The full message
	public HashMap<Integer, String> scroll = new HashMap<Integer, String>();
	public HashMap<String, Integer> scrollStays = new HashMap<String, Integer>();	
	//The full message | What location in the message we're at
	public HashMap<String, Integer> scrollWords = new HashMap<String, Integer>();
	//The full message | What the last scroll said
	public HashMap<String, String> lastScroll = new HashMap<String, String>();
	//The full message | The last color codes of the message
	public HashMap<String, String> scrollColors = new HashMap<String, String>();	
	
	
	
	public void slideScore(Player player){
		for(Entry<Integer, String> i : scroll.entrySet()){
			int row = i.getKey();
			String orgString = i.getValue();
			String lastScrollValue = scrollColors.get(orgString) + lastScroll.get(i.getValue());
			
			if(scrollStays.get(orgString) != 3){
				scrollStays.put(orgString, scrollStays.get(orgString) + 1);
			}else

				scrollStays.put(orgString, 0);
			
			Scoreboard infoBoard = player.getScoreboard();
			Objective infoObjective = player.getScoreboard().getObjective(DisplaySlot.SIDEBAR);
			int score = 0;
			if(row != 0){
				score = infoObjective.getScore(Bukkit.getOfflinePlayer(lastScrollValue)).getScore();
				infoBoard.resetScores(Bukkit.getOfflinePlayer(lastScrollValue));
			}
			String cutString = orgString.substring(scrollWords.get(orgString), Math.min(orgString.length(), 16 - scrollColors.get(orgString).length() + scrollWords.get(orgString)));

			try
			{
				@SuppressWarnings("unused")
				char t = cutString.charAt(0);
			} catch (StringIndexOutOfBoundsException npe)
			{
				//RESETS TO THE WHOLE WORD
				cutString = orgString.substring(0, Math.min(orgString.length(), 16 - scrollColors.get(orgString).length()));
				scrollWords.put(orgString, -1);
			}
			
			lastScroll.put(orgString, cutString);

			cutString = scrollColors.get(orgString) + cutString;
		
			scrollWords.put(orgString, scrollWords.get(orgString) + 1);
			if(row != 0){
				Score newScore = infoObjective.getScore(Bukkit.getOfflinePlayer(cutString));
				newScore.setScore(score);
			}else
				infoObjective.setDisplayName(cutString);
		}
	}
}
