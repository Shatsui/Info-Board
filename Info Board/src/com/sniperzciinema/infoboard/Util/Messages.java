
package com.sniperzciinema.infoboard.Util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import com.sniperzciinema.infoboard.GetVariables;
import com.sniperzciinema.infoboard.InfoBoard;
import com.sniperzciinema.infoboard.Scroll.ScrollManager;


public class Messages {
	
	public static OfflinePlayer getLine(String line, Player user) {
		
		String prefix = "", name = "", suffix = "";
		
		// Replace all the variables
		if (line.contains("<") && line.contains(">"))
			line = GetVariables.replaceVariables(line, user);
		// Replace color codes
		line = ChatColor.translateAlternateColorCodes('&', line);
		line = line.replaceAll("&x", RandomChatColor.getColor().toString());
		line = line.replaceAll("&y", RandomChatColor.getFormat().toString());
		
		if (line.length() > 48)
			line = line.substring(0, 47);
		
		if (line.length() <= 16)
		{
			name = line;
			System.out.println("Name: '" + name + "'");
		}
		
		else if (line.length() <= 32)
		{
			name = line.substring(0, 16);
			suffix = line.substring(16, line.length());
			System.out.println("Name: '" + name + "'");
			System.out.println("Suffix: '" + suffix + "'");
		}
		else
		{
			prefix = line.substring(0, 16);
			name = line.substring(16, 32);
			suffix = line.substring(32, line.length());
			System.out.println("Prefix: '" + prefix + "'");
			System.out.println("Name: '" + name + "'");
			System.out.println("Suffix: '" + suffix + "'");
		}
		
		OfflinePlayer op = Bukkit.getOfflinePlayer(name);
		
		if (!prefix.equals("") || !suffix.equals(""))
		{
			System.out.println("Creating team: " + name);
			Team team = user.getScoreboard().getPlayerTeam(op);
			
			if (team == null)
				team = user.getScoreboard().registerNewTeam(name);
			team.addPlayer(op);
			if (!prefix.equals(""))
			{
				team.setPrefix(prefix);
				System.out.println("Setting Prefix: " + prefix);
			}
			if (!suffix.equals(""))
			{
				team.setSuffix(suffix);
				System.out.println("Setting Suffix: " + suffix);
			}
		}
		
		return op;
	}
	
	public static String getTitle(Player player, Scoreboard board, Objective infoObjective, String worldName, String rankName) {
		
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
			title = getLine(title, player).getName();
		
		// And now we set the title
		return title;
		
	}
}
