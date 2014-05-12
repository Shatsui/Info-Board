
package com.sniperzciinema.infoboard.Scoreboard;

import org.bukkit.entity.Player;

import com.sniperzciinema.infoboard.Util.Messages;


public class ShouldSet {
	
	private String	line;
	private Player	player;
	
	public ShouldSet(Player player, String line)
	{
		this.line = line;
		this.player = player;
	}
	
	/**
	 * Should the line be set
	 * 
	 * @return true/false
	 */
	public boolean getBoolean() {
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
			String l1 = Messages.getLine("<" + l + ">", player);
			if (l1.equalsIgnoreCase("Unknown") || l1.equalsIgnoreCase("false") || l1.equalsIgnoreCase("None") || l1.equalsIgnoreCase("") || l1.equalsIgnoreCase("0") || l1.equalsIgnoreCase("-1"))
				return true;
			else
				return false;
		}
		else
			return true;
		
	}
	
	/**
	 * Gets the line after removing the boolean variables
	 * 
	 * @return the new line
	 */
	public String getLine() {
		if (line.contains("~!<"))
		{
			String l = (line.split("~!<")[1]).split(">")[0];
			
			line = line.replaceAll("~!<" + l + ">", "");
		}
		
		else if (line.contains("~@<"))
		{
			String l = (line.split("~@<")[1]).split(">")[0];
			
			line = line.replaceAll("~@<" + l + ">", "");
		}
		return line;
	}
}
