
package com.sniperzciinema.infoboard.Scroll;

import org.bukkit.ChatColor;


public class Scroll {
	
	private String		message;
	private String		origionalMessage;
	
	private ChatColor	color			= ChatColor.RESET;
	private char			COLORCHAR	= '§';
	
	private int				width;
	private int				position	= 0;
	private int				pause			= 0;
	private int				row;
	
	/**
	 * Create a new scroller
	 * 
	 * @param message
	 * @param row
	 * @param width
	 */
	public Scroll(String message, int row, int width)
	{
		this.row = row;
		this.width = width;
		this.origionalMessage = message;
		StringBuilder builder = new StringBuilder(message);
		while (builder.length() <= (width * 2))
			builder.append("          " + message);
		
		String string = builder.toString();
		
		this.message = string;
	}
	
	/**
	 * Get the scrolled message
	 * 
	 * @return message
	 */
	public String getMessage() {
		
		String message = this.message.substring(this.position, Math.min(this.message.length(), (this.width - 2) + this.position));
		
		if (message.charAt(0) != this.COLORCHAR)
			message = this.color + message;
		return message;
	}
	
	/**
	 * @return the row
	 */
	public int getRow() {
		return this.row;
	}
	
	/**
	 * Move position up one unless it's being paused for 3 counts first
	 */
	public void next() {
		
		if ((this.position == 0) && (this.pause != 3))
			this.pause++;
		else
		{
			this.position++;
			this.pause = 0;
			
			if (this.position == (this.origionalMessage.length() + 10))
				this.position = 0;
			
		}
		
	}
	
}
