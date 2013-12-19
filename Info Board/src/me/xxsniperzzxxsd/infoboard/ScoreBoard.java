
package me.xxsniperzzxxsd.infoboard;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import me.xxsniperzzxxsd.infoboard.Util.RandomChatColor;
import me.xxsniperzzxxsd.infoboard.Util.Scroll.Scroller;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;


public class ScoreBoard {

	public Main plugin;

	public ScoreBoard(Main instance)
	{
		plugin = instance;
	}

	int rotation = 1;
	public ArrayList<String> hidefrom = new ArrayList<String>();
	public String rank = "default";
	public String world = "global";
	private static int numberScore = -1;

	public boolean updateScoreBoard(Player player) {
		// Lets make sure the player is supposed to see the scoreboard before we
		// do anything to them
		if (!plugin.getConfig().getStringList("Disabled Worlds").contains(player.getWorld().getName()) && !hidefrom.contains(player.getName()) && (player.getScoreboard().getObjective(DisplaySlot.SIDEBAR) == null || player.getScoreboard().getObjective(DisplaySlot.SIDEBAR).getName().equalsIgnoreCase("InfoBoard")))
		{
			if (!player.hasPermission("InfoBoard.View"))
			{
				player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
			} else
			{
				// If the player has been told to update the scoreboard when
				// they
				// don't even have one, we just tell it to create the scoreboard
				// for
				// the player first
				if (player.getScoreboard().getObjective(DisplaySlot.SIDEBAR) == null)
				{
					createScoreBoard(player);
				} else
				{
					// We are checking to see if the players world is specified
					// in
					// the config, if so that means that world has special
					// scoreboards
					if (plugin.getConfig().contains("Info Board." + String.valueOf(rotation) + "." + player.getWorld().getName()))
						world = player.getWorld().getName();

					// If vault is on this server and permissions for vault were
					// found, we'll try looking for their group
					if (Bukkit.getServer().getPluginManager().getPlugin("Vault") != null && Main.permission != null)
					{
						try
						{
							// we'll get their group
							rank = Main.permission.getPlayerGroups(player.getWorld(), player.getName())[0];
							// Then we'll see if the config contains a special
							// scoreboard for their group, if not we'll reset
							// the
							// group to default
							if (plugin.getConfig().getString("Info Board." + String.valueOf(rotation) + "." + world + "." + rank + ".Title") == null)
								rank = "default";
						} catch (UnsupportedOperationException UOE)
						{
							rank = "default";
						}
					}
					// If the title of this scoreboard is blank, don't do
					// anything
					if (plugin.getConfig().getString("Info Board." + String.valueOf(rotation) + "." + world + "." + rank + ".Title") == null)
						return true;
					// Instead of creating a new scoreboard, we just want to
					// update
					// their current one with new values, so we'll just have to
					// get
					// their scoreboards and objectives
					Scoreboard infoBoard = player.getScoreboard();
					Objective infoObjective = player.getScoreboard().getObjective(DisplaySlot.SIDEBAR);

					int row = 0;
					int spaces = 0;
					// Next i get the list of rows so i can go through and make
					// sure
					// i only update what needs to be updated
					List<String> list = plugin.getConfig().getStringList("Info Board." + String.valueOf(rotation) + "." + world + "." + rank + ".Rows");
					// We'll also create an array of rows to remove incase we
					// find
					// any
					ArrayList<String> remove = new ArrayList<String>();

					// Loop through the scoreboards rows
					for (OfflinePlayer op : infoBoard.getPlayers())
					{
						Iterator<String> iter = list.iterator();
						boolean onlist = false;
						// Now we loop through our list, the reason being is
						// that we
						// can't just see if the list contains as the list is
						// the
						// unachanged variables, so we'll never find the changed
						// variables in there
						while (iter.hasNext())
						{
							// Lets make sure this line is already on the
							// scoreboard, and/or is a empty line and/or is a
							// message about enabling scroll first
							String s = iter.next();
							if (getLine(s, player).equalsIgnoreCase(op.getName()) || ChatColor.stripColor(op.getName()) == null || op.getName().contains("Enable Scroll"))
								onlist = true;

							// If the line is a scroll message, we'll just leave
							// it
							// an let the scroll timer deal with it
							else if (plugin.ScrollManager.getScrollers(player) != null)
								for (Scroller scroller : plugin.ScrollManager.getScrollers(player))
								{
									scroller.getLastMessage().equals(op.getName());
									onlist = true;
								}

						}
						// If the row wasn't found on the list we'll add it to
						// the
						// remove list, because we can't edit the list well
						// we're
						// looping through it
						if (!onlist)
							if (!remove.contains(op.getName()))
								remove.add(op.getName());
					}
					// If the "To Remove" list isn't empty, loop through the
					// list
					// and remove and rows that we determined had to be updated
					if (!remove.isEmpty())
						for (String s : remove)
							infoBoard.resetScores(Bukkit.getOfflinePlayer(s));

					// Now we reset the list just to be safe
					list = plugin.getConfig().getStringList("Info Board." + String.valueOf(rotation) + "." + world + "." + rank + ".Rows");

					// And we go through and see if the scoreboard doesn't
					// contain
					// the line with the changed variables, if it doesn't we
					// through
					// that line onto the scoreboard
					for (String s : list)
					{
						String string = getLine(s, player);
						if (!infoBoard.getPlayers().contains(Bukkit.getOfflinePlayer(string)))
						{
							// Refer to createScoreBoard(...) to see what all of
							// this is doing
							boolean set = true;
							Score score = null;
							String line = list.get(row);

							if (getLine(line, player).equalsIgnoreCase(" "))
							{
								String space = "&" + spaces;
								spaces++;
								score = infoObjective.getScore(Bukkit.getOfflinePlayer(getLine(space, player)));
							} else
							{
								if (line.contains("~!"))
								{
									// If variable is unkown or 0, dont show
									String l = (line.split("<")[1]).split(">")[0];
									String l1 = getLine("<" + l + ">", player);
									if (l1.equalsIgnoreCase("Unkown") || l1.equalsIgnoreCase("") || l1.equalsIgnoreCase("0"))
									{
										set = false;
									}
									line = line.replaceAll("~!<" + l + ">", "");
								}
								if (line.contains("~@"))
								{
									// If variable is unkown or 0, do show
									String l = (line.split("<")[1]).split(">")[0];
									String l1 = getLine("<" + l + ">", player);
									if (l1.equalsIgnoreCase("Unkown")  || l1.equalsIgnoreCase("")|| l1.equalsIgnoreCase("0"))
									{
										set = true;
									}
									line = line.replaceAll("~!<" + l + ">", "");
								}
								if (line.contains("<split>"))
								{
									String a = line.split("<split>")[0];
									String b = line.split("<split>")[1];

									score = infoObjective.getScore(Bukkit.getOfflinePlayer(getLine(a, player)));
									try
									{
										numberScore = Integer.valueOf(getLine(b.replaceAll(" ", ""), player));
									} catch (NumberFormatException ne)
									{
										numberScore = 0;
									}
								} else if (line.contains(";"))
								{
									String a = line.split(";")[0];
									String b = line.split(";")[1];

									score = infoObjective.getScore(Bukkit.getOfflinePlayer(getLine(a, player)));
									try
									{
										numberScore = Integer.valueOf(getLine(b.replaceAll(" ", ""), player));
									} catch (NumberFormatException ne)
									{
										numberScore = 0;
									}
								} else
								{
									score = infoObjective.getScore(Bukkit.getOfflinePlayer(getLine(line, player)));
								}
							}
							if (set)
							{
								if (numberScore == -1)
									numberScore = list.size() - 1 - row;
								if (!score.getPlayer().getName().startsWith("<scroll>"))
								{
									score.setScore(1);
									score.setScore(numberScore);
								}
								numberScore = -1;
							}
						}
						row++;
					}
					rank = "default";
				}
			}
		}
		return true;
	}

	public boolean createScoreBoard(Player player) {
		// Before we make the scoreboard lets make sure the player is okay to
		// see it
		if (player.hasPermission("InfoBoard.View") && !hidefrom.contains(player.getName()) && !plugin.getConfig().getStringList("Disabled Worlds").contains(player.getWorld().getName()) && (player.getScoreboard().getObjective(DisplaySlot.SIDEBAR) == null || player.getScoreboard().getObjective(DisplaySlot.SIDEBAR).getName().equalsIgnoreCase("InfoBoard")))
		{

			// Does the config contain a scoreboard meant for the world the
			// players in
			if (plugin.getConfig().contains("Info Board." + String.valueOf(rotation) + "." + player.getWorld().getName()))
			{
				// If yes set the world string to the worlds name
				world = player.getWorld().getName();
				// If not we'll keep it saying Global
			}
			// Is there vault on the server? If so lets make sure it has a
			// permissions plugin ready
			if (Bukkit.getServer().getPluginManager().getPlugin("Vault") != null && Main.permission != null)
				try
				{
					// We'll try getting the players group
					rank = Main.permission.getPlayerGroups(player.getWorld(), player.getName())[0];
					// if the config doesn't contain a specific scoreboard for
					// the player we'll reset the rank string to default
					if (plugin.getConfig().getString("Info Board." + String.valueOf(rotation) + "." + world + "." + rank + ".Title") == null)
						rank = "default";
				} catch (UnsupportedOperationException UOE)
				{
					// If it was unable to get the players group, just say it's
					// the default group
					rank = "default";
				}

			// If the next scoreboard doesn't exist just return true, and do
			// nothing
			if (plugin.getConfig().getString("Info Board." + String.valueOf(rotation) + "." + world + "." + rank + ".Title") == null)
				return true;

			// If the player has an objective in the sidebar
			if (player.getScoreboard().getObjective(DisplaySlot.SIDEBAR) != null)
			{
				// unregister and remove that display(Normally the last page of
				// InfoBoard
				player.getScoreboard().getObjective(DisplaySlot.SIDEBAR).unregister();
				player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
			}
			// Create a new scoreboard and set it to the sidebar display
			ScoreboardManager manager = Bukkit.getScoreboardManager();

			Scoreboard infoBoard = manager.getNewScoreboard();
			Objective infoObjective = infoBoard.registerNewObjective("InfoBoard", "dummy");
			infoObjective.setDisplaySlot(DisplaySlot.SIDEBAR);

			// Remove and scrolling texts that the player may have had
			plugin.ScrollManager.reset(player);

			// Now we go to the title setting method thats down below
			setTitle(player, infoObjective);

			int row;
			int spaces = 0;

			// I create a list variable to go through so i can remember what row
			// number i'm on by using the for loop below
			List<String> list = plugin.getConfig().getStringList("Info Board." + String.valueOf(rotation) + "." + world + "." + rank + ".Rows");
			for (row = 0; row != plugin.getConfig().getStringList("Info Board." + String.valueOf(rotation) + "." + world + "." + rank + ".Rows").size(); row++)
			{
				// Set the basics
				boolean set = true;
				Score score = null;
				// Get the line from the list using the row
				String line = list.get(row);
				// If the line is just a space, it means empty line, so we'll
				// just set it as only a color code
				if (getLine(line, player).equalsIgnoreCase(" "))
				{
					String space = "&" + spaces;
					spaces++;
					score = infoObjective.getScore(Bukkit.getOfflinePlayer(getLine(space, player)));
				} else
				{
					// If the line is more then just a space, we'll check the
					// other variables
					// ~! in front of a variable means it'll only show the line
					// if the variable isn't "0" or "Unkown"
					if (line.contains("~!"))
					{
						String l = (line.split("<")[1]).split(">")[0];
						String l1 = getLine("<" + l + ">", player);
						if (l1.equalsIgnoreCase("Unkown")  || l1.equalsIgnoreCase("")|| l1.equalsIgnoreCase("0"))
							set = false;

						line = line.replaceAll("~!<" + l + ">", "");
					}
					// ~@ in front of a variable means it'll only show the line
					// if the variable is "0" or "Unkown"
					if (line.contains("~@"))
					{
						String l = (line.split("<")[1]).split(">")[0];
						String l1 = getLine("<" + l + ">", player);
						if (l1.equalsIgnoreCase("Unkown")  || l1.equalsIgnoreCase("") || l1.equalsIgnoreCase("0"))
						{
							set = true;
						}
						line = line.replaceAll("~!<" + l + ">", "");
					}
					// Now for the scrolling lines
					if (line.startsWith("<scroll>"))
					{
						// Replace <scroll> with "" and create the scroll
						// object, but first lets make sure they have scroll on,
						// because if its not then that means the timer was
						// never started
						if (plugin.getConfig().getBoolean("Scrolling Text.Enable"))
						{
							line = line.replaceAll("<scroll>", "");
							line = plugin.ScrollManager.createScroller(player, line).getScrolled();
						} else
							// We'll just tell them to enable scroll before
							// trying to use scroll
							line = "Enable Scroll 1st";
					}
					// If the line contains <split> (They want to set their own
					// score for the line
					if (line.contains("<split>"))
					{
						// We'll split the line and the score, then set them
						String a = line.split("<split>")[0];
						String b = line.split("<split>")[1];

						score = infoObjective.getScore(Bukkit.getOfflinePlayer(getLine(a, player)));
						// Lets make sure it is a number first, if not we'll set
						// it as 0
						try
						{
							numberScore = Integer.valueOf(getLine(b.replaceAll(" ", ""), player));
						} catch (NumberFormatException ne)
						{
							numberScore = 0;
						}
					}// If the line contains ; (Same thing as <split> but looks
						// nicer in a config)
					else if (line.contains(";"))
					{
						String a = line.split(";")[0];
						String b = line.split(";")[1];

						score = infoObjective.getScore(Bukkit.getOfflinePlayer(getLine(a, player)));
						try
						{
							numberScore = Integer.valueOf(getLine(b.replaceAll(" ", ""), player));
						} catch (NumberFormatException ne)
						{
							numberScore = 0;
						}
					}// If the line doesn't contain any form of a split just set
						// the line to the line

					else
						score = infoObjective.getScore(Bukkit.getOfflinePlayer(getLine(line, player)));

				}
				// If it was determined that we are in fact showing this line,
				// we'll give it a number that will help sort it on the
				// scoreboard so it doesn't change the order
				if (set)
				{
					if (numberScore == -1)
						numberScore = list.size() - 1 - row;

					// I set it as 1 first, so if "numberScore" is 0, it'll
					// still show the value as 0
					score.setScore(1);
					score.setScore(numberScore);

					// Then i set numberScore back to -1(Which means it wants an
					// auto number
					numberScore = -1;
				}
			}
			// then we just set the scoreboard for the player
			player.setScoreboard(infoBoard);
		}
		// and reset the variables to get the propper scoreboard
		rank = "default";
		world = "global";
		return true;
	}

	private void setTitle(Player player, Objective infoObjective) {

		// Lets see if the title is supposed to scroll, first we'll get it from
		// the config
		String title = plugin.getConfig().getString("Info Board." + String.valueOf(rotation) + "." + world + "." + rank + ".Title");
		// Then we'll see if it is a scrolling line
		if (title.startsWith("<scroll>") && plugin.getConfig().getBoolean("Scrolling Text.Enable"))
		{
			// Replace <scroll> with ""
			title = title.replaceAll("<scroll>", "");
			// and create a Title scroller
			title = plugin.ScrollManager.createTitleScroller(player, title).getScrolled();

		} else
			// If it's not a scrolling line, we'll just get the line normally
			// with the variables
			title = getLine(title, player);

		// And now we set the title
		infoObjective.setDisplayName(title);

	}

	public static String getLine(String string, Player user) {

		// Replace all the variables
		String newString = GetVariables.replaceVariables(string, user);

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

}
