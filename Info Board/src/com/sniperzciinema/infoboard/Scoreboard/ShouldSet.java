
package com.sniperzciinema.infoboard.Scoreboard;


import org.bukkit.entity.Player;

import com.sniperzciinema.infoboard.Util.Messages;


public class ShouldSet {
	
	public static String getLine(String line, Player player) {
		if (line.contains("~!<"))
		{
			String l = (line.split("~!<")[1]).split(">")[0];
			
			line = line.replaceAll("~!<" + l + ">", "");
		}
		// ~@ in front of a variable means it'll only show the
		// line
		// if the variable is "0" or "Unknown"
		else if (line.contains("~@<"))
		{
			String l = (line.split("~@<")[1]).split(">")[0];
			
			line = line.replaceAll("~@<" + l + ">", "");
		}
		return line;
	}
	
	public static boolean test(String line, Player player) {
		// If the variable isn't 0
		if (line.contains("~!<"))
		{
			String l = (line.split("~!<")[1]).split(">")[0];
			String l1 = Messages.getLine("<" + l + ">", player);
			if (l1.equalsIgnoreCase("Unknown") || l1.equalsIgnoreCase("false") || l1.equalsIgnoreCase("None") || l1.equalsIgnoreCase("") || l1.equalsIgnoreCase("0") || l1.equalsIgnoreCase("-1"))
				return false;
			else
				return true;
		}
		// If the variable is 0
		else if (line.contains("~@<"))
		{
			String l = (line.split("~@<")[1]).split(">")[0];
			System.out.println(l);
			String l1 = Messages.getLine("<" + l + ">", player);
			if (l1.equalsIgnoreCase("Unknown") || l1.equalsIgnoreCase("false") || l1.equalsIgnoreCase("None") || l1.equalsIgnoreCase("") || l1.equalsIgnoreCase("0") || l1.equalsIgnoreCase("-1"))
				return true;
			else
				return false;
		}
		else
			return true;
	}
}
