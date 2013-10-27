package me.xxsniperzzxxsd.infoboard.Util;

import me.xxsniperzzxxsd.infoboard.GetVariables;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;


public class Scroller {

	@SuppressWarnings("unused")
	private Player player;
	private String origional;
	private int position;
	private String lastMessage;
	private String color = "§f";
	
	public Scroller(Player player,String message){
		this.player = player;
		String string = message;
		lastMessage = null;
		if(ChatColor.getLastColors(ChatColor.translateAlternateColorCodes('&', string)) != null)
		color = ChatColor.getLastColors(ChatColor.translateAlternateColorCodes('&', string));
		
		// Replace all the variables
				string = GetVariables.replaceVariables(string, player);
		origional = color + ChatColor.stripColor(string);
	}
	public String getColors(){
		return color;
	}
	public String getLastMessage(){
		return lastMessage;
	}
	public String getOrigional(){
		return origional;
	}
	public int getPosition(){
		return position;
	}
	public void scroll(){
		position++;
	}
	public String getScrolled(){
		String newLine = null;
		try
		{
		newLine = color + origional.substring(position + color.length() + color.length(), Math.min(origional.length(), 16 + position));
		
		
			@SuppressWarnings("unused")
			char t = newLine.charAt(0);
		} catch (StringIndexOutOfBoundsException npe)
		{
			// RESETS TO THE WHOLE WORD
			newLine = color + origional.substring(color.length()+color.length(), Math.min(origional.length(), 16));
		}
		lastMessage = newLine;
				
		return newLine;
	}
}