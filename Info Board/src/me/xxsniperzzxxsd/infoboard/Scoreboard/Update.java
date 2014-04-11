
package me.xxsniperzzxxsd.infoboard.Scoreboard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import me.xxsniperzzxxsd.infoboard.InfoBoard;
import me.xxsniperzzxxsd.infoboard.Scroll.ScrollManager;
import me.xxsniperzzxxsd.infoboard.Scroll.Scroller;
import me.xxsniperzzxxsd.infoboard.Util.Files;
import me.xxsniperzzxxsd.infoboard.Util.Messages;
import me.xxsniperzzxxsd.infoboard.Util.Settings;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;


public class Update {

	public static boolean updateScoreBoard(Player player) {

		String worldName = "global";
		String rankName = "default";
		int value = -1;

		// Lets make sure the player is supposed to see the scoreboard before we
		// do anything to them
		if (!Settings.isWorldDisabled(player.getWorld().getName()) && !InfoBoard.hidefrom.contains(player.getName()) && (player.getScoreboard().getObjective(DisplaySlot.SIDEBAR) == null || player.getScoreboard().getObjective(DisplaySlot.SIDEBAR).getName().equalsIgnoreCase("InfoBoard")))
		{
			if (!player.hasPermission("InfoBoard.View"))
			{
				player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
			} else
			{
				// If the player has been told to update the scoreboard when
				// they don't even have one, we just tell it to create the
				// scoreboard for the player first
				if (player.getScoreboard().getObjective(DisplaySlot.SIDEBAR) == null)
				{
					Create.createScoreBoard(player);
				} else
				{
					// We are checking to see if the players world is specified
					// in
					// the config, if so that means that world has special
					// scoreboards
					if (Files.getConfig().contains("Info Board." + String.valueOf(InfoBoard.rotation) + "." + player.getWorld().getName()))
						worldName = player.getWorld().getName();

					// If vault is on this server and permissions for vault were
					// found, we'll try looking for their group
					if (Bukkit.getServer().getPluginManager().getPlugin("Vault") != null && InfoBoard.permission != null & InfoBoard.permissionB)
					{
						try
						{
							// we'll get their group
							rankName = InfoBoard.permission.getPlayerGroups(player.getWorld(), player.getName())[0];
							// Then we'll see if the config contains a special
							// scoreboard for their group, if not we'll reset
							// the
							// group to default
							if (Files.getConfig().getString("Info Board." + String.valueOf(InfoBoard.rotation) + "." + worldName + "." + rankName + ".Title") == null)
								rankName = "default";
						} catch (UnsupportedOperationException UOE)
						{
							rankName = "default";
						}
					}
					// If the title of this scoreboard is blank, don't do
					// anything
					if (!Settings.isPageValid(InfoBoard.rotation, worldName, rankName))
						return true;

					// Instead of creating a new scoreboard, we just want to
					// update
					// their current one with new values, so we'll just have to
					// get
					// their scoreboards and objectives
					Scoreboard infoBoard = player.getScoreboard();
					Objective infoObjective = player.getScoreboard().getObjective(DisplaySlot.SIDEBAR);

					// Next i get the list of rows so i can go through and make
					// sure i only update what needs to be updated
					List<String> list = Files.getConfig().getStringList("Info Board." + String.valueOf(InfoBoard.rotation) + "." + worldName + "." + rankName + ".Rows");
					// We'll also create an array of rows to remove incase we
					// find any
					ArrayList<String> newLines = new ArrayList<String>();

					Iterator<String> iter = list.iterator();
					while (iter.hasNext())
					{
						String s = iter.next();
						newLines.add(Messages.getLine(ShouldSet.getLine(s, player), player));
					}

					ArrayList<String> onNow = new ArrayList<String>();

					// ----------------------------------------------------------
					// Remove the no longer needed lines
					for (OfflinePlayer op : infoBoard.getPlayers())
						onNow.add(op.getName());
					// Go through and remove specific lines
					for (String string : getLinesToRemove(player, onNow, newLines))
						infoBoard.resetScores(Bukkit.getOfflinePlayer(string));
					// -----------------------------------------------------------------

					int row, spaces = 0;
					onNow = new ArrayList<String>();
					newLines = new ArrayList<String>();

					for (String string : list)
						newLines.add(Messages.getLine(string, player));

					// Loop through the scoreboards rows
					for (OfflinePlayer op : infoBoard.getPlayers())
						onNow.add(op.getName());
					HashMap<Integer, String> toAdd = getLinesToAdd(player, onNow, newLines);

					for (Entry<Integer, String> e : toAdd.entrySet())
					{
						// Refer to createScoreBoard(...) to see what all of
						// this is doing
						Score score = null;
						row = e.getKey();
						String line = e.getValue();
						boolean set = ShouldSet.test(list.get(row), player);
						line = ShouldSet.getLine(list.get(row), player);

						if (set)
						{
							if (line.equalsIgnoreCase(" "))
							{
								String space = "&" + spaces;
								spaces++;
								score = infoObjective.getScore(Bukkit.getOfflinePlayer(Messages.getLine(space, player)));
							} else
							{
								if (line.contains("<split>"))
								{
									String a = line.split("<split>")[0];
									String b = line.split("<split>")[1];

									score = infoObjective.getScore(Bukkit.getOfflinePlayer(Messages.getLine(a, player)));
									try
									{
										value = Integer.valueOf(Messages.getLine(b.replaceAll(" ", ""), player));
									} catch (NumberFormatException ne)
									{
										value = 0;
									}
								} else if (line.contains(";"))
								{
									String a = line.split(";")[0];
									String b = line.split(";")[1];

									score = infoObjective.getScore(Bukkit.getOfflinePlayer(Messages.getLine(a, player)));
									try
									{
										value = Integer.valueOf(Messages.getLine(b.replaceAll(" ", ""), player));
									} catch (NumberFormatException ne)
									{
										value = 0;
									}
								} else
								{
									score = infoObjective.getScore(Bukkit.getOfflinePlayer(Messages.getLine(line, player)));
								}
							}
							if (value == -1)
								value = list.size() - 1 - row;
							if (!score.getPlayer().getName().startsWith("<scroll>"))
							{
								score.setScore(1);
								score.setScore(value);
							}
							value = -1;

							row++;
						}

					}
				}
			}
		}
		return true;
	}

	public static ArrayList<String> getLinesToRemove(Player player, ArrayList<String> onBoard, List<String> list) {
		ArrayList<String> toRemove = new ArrayList<String>();

		for (String line : onBoard)
		{
			if (!list.contains(line) && ChatColor.stripColor(line) != null && ChatColor.stripColor(line).length() != 0 && !line.contains("Enable Scroll"))
			{
				if (ScrollManager.getScrollers(player) != null)
				{
					boolean b = false;
					for (Scroller scroller : ScrollManager.getScrollers(player))
					{
						if (scroller.getLastMessage().equals(line))
							b = true;
					}
					if (!b)
						toRemove.add(line);
				} else
					toRemove.add(line);
			}
		}
		return toRemove;
	}

	public static HashMap<Integer, String> getLinesToAdd(Player player, ArrayList<String> onBoard, List<String> list) {
		HashMap<Integer, String> toAdd = new HashMap<Integer, String>();

		int i = 0;
		for (String line : list)
		{
			if (!onBoard.contains(line) && !line.equals(" ") && !line.contains("<scroll>"))
			{
					toAdd.put(i, line);	
			}
			i++;
		}
		return toAdd;
	}
}
