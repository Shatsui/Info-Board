package me.xxsniperzzxxsd.infoboard.Util;

import me.xxsniperzzxxsd.infoboard.GetVariables;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;


public class Scroller {

	@SuppressWarnings("unused")
	private Player player;
	private String origional;
	private int position = 0;
	private String lastMessage;
	private String color = "§f";
	private int holded= 0;
	
	public Scroller(Player player,String message){
		this.player = player;
		String string = message;
		lastMessage = null;
		string = string.replaceAll("&x", RandomChatColor.getColor().toString());
		string = string.replaceAll("&y", RandomChatColor.getFormat().toString());
		string = ChatColor.translateAlternateColorCodes('&', string);
		if(ChatColor.getLastColors(string) != null)
			color = ChatColor.getLastColors(string);
		
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
		if(holded == 2)
			position++;
		else
			holded++;
	}
	public String getScrolled(){
		String newLine = null;
		try
		{
		newLine = color + origional.substring(position + color.length(), Math.min(origional.length(), 16 + position));
		
		
			@SuppressWarnings("unused")
			char t = newLine.charAt(0);
		} catch (StringIndexOutOfBoundsException npe)
		{
			// RESETS TO THE WHOLE WORD
			position = 0;
			newLine = color + origional.substring(position + color.length(), Math.min(origional.length(), 16 + position));
			
			holded = 0;
		}
		lastMessage = newLine;
				
		return newLine;
	}
}