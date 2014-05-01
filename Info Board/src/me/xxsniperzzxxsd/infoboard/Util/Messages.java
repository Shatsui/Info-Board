
package me.xxsniperzzxxsd.infoboard.Util;

import me.xxsniperzzxxsd.infoboard.GetVariables;
import me.xxsniperzzxxsd.infoboard.InfoBoard;
import me.xxsniperzzxxsd.infoboard.Scroll.ScrollManager;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;


public class Messages {
	
	public static String getLine(String newString, Player user) {
		
		// Replace all the variable
		if (newString.contains("<") && newString.contains(">"))
			newString = GetVariables.replaceVariables(newString, user);
		
		// Replace color codes
		newString = ChatColor.translateAlternateColorCodes('&', newString);
		newString = newString.replaceAll("&x", RandomChatColor.getColor().toString());
		newString = newString.replaceAll("&y", RandomChatColor.getFormat().toString());
		
		// Make sure string isn't too long, if it is, just shorten it to the
		// first 16 characters
		if (newString.length() > 16)
			newString = newString.substring(0, Math.min(newString.length(), 16));
		
		return newString;
	}
	
	public static String getTitle(Player player, Objective infoObjective, String worldName, String rankName) {
		
		// Lets see if the title is supposed to scroll, first we'll get it from
		// the config
		String title = Files.getConfig().getString("Info Board." + String.valueOf(InfoBoard.rotation) + "." + worldName + "." + rankName + ".Title");
		// Then we'll see if it is a scrolling line
		if (title.startsWith("<scroll>") && Files.getConfig().getBoolean("Scrolling Text.Enable"))
		{
			// Replace <scroll> with ""
			title = title.replaceAll("<scroll>", "");
			// and create a Title scroller
			title = ScrollManager.createTitleScroller(player, title).getScrolled();
			
		}
		else
			// If it's not a scrolling line, we'll just get the line normally
			// with the variables
			title = getLine(title, player);
		
		// And now we set the title
		return title;
		
	}
}
