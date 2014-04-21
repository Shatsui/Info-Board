
package me.xxsniperzzxxsd.infoboard.Scroll;

import me.xxsniperzzxxsd.infoboard.GetVariables;
import me.xxsniperzzxxsd.infoboard.Util.RandomChatColor;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;


public class Scroller {

	@SuppressWarnings("unused")
	private Player	player;
	private String	origional;
	private int		position	= 0;
	private String	lastMessage;
	private String	color		= "§f";
	private int		holded		= 0;

	public Scroller(Player player, String message)
	{
		this.player = player;
		String string = message + "       " + message + "       " + message;
		this.lastMessage = null;
		string = string.replaceAll("&x", RandomChatColor.getColor().toString());
		string = string.replaceAll("&y", RandomChatColor.getFormat().toString());
		string = ChatColor.translateAlternateColorCodes('&', string);
		if (ChatColor.getLastColors(string) != null)
			this.color = ChatColor.getLastColors(string);

		// Replace all the variables
		string = GetVariables.replaceVariables(string, player);
		this.origional = this.color + ChatColor.stripColor(string);
	}

	public String getColors() {
		return this.color;
	}

	public String getLastMessage() {
		return this.lastMessage;
	}

	public String getOrigional() {
		return this.origional;
	}

	public int getPosition() {
		return this.position;
	}

	public String getScrolled() {
		String newLine = null;
		try
		{
			newLine = this.color + this.origional.substring(this.position + this.color.length(), Math.min(this.origional.length(), 16 + this.position));

			if ((this.position != 0) && newLine.equals(this.color + this.origional.substring(0 + this.color.length(), Math.min(this.origional.length(), 16))))
			{
				this.position = 0;
				this.holded = 0;
				newLine = this.color + this.origional.substring(this.position + this.color.length(), Math.min(this.origional.length(), 16 + this.position));
			}

		}
		catch (StringIndexOutOfBoundsException npe)
		{
			// RESETS TO THE WHOLE WORD
			this.position = 0;
			newLine = this.color + this.origional.substring(this.position + this.color.length(), Math.min(this.origional.length(), 16 + this.position));

			System.out.println("invalid");
			this.holded = 0;
		}
		this.lastMessage = newLine;

		return newLine;
	}

	public void scroll() {
		if (this.holded == 2)
			this.position++;
		else
			this.holded++;
	}
}
