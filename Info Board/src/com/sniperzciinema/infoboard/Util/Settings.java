
package com.sniperzciinema.infoboard.Util;

import java.util.List;


public class Settings {
	
	public static boolean isPageValid(int page, String worldName, String rankName) {
		return Files.getConfig().getString("Info Board." + String.valueOf(page) + "." + worldName + "." + rankName + ".Title") != null;
	}
	
	public static boolean isWorldDisabled(String world) {
		return Files.getConfig().getStringList("Disabled Worlds").contains(world) || (world == null);
	}
	
	public static List<String> getRegionsDisabled() {
		return Files.getConfig().getStringList("WorldGuard.Prevent Showing In");
	}
	
	public static boolean doesWorldHaveScoreBoard(int rotation, String world) {
		boolean hasBoard = false;
		for (String s : Files.getConfig().getConfigurationSection("Info Board." + String.valueOf(rotation)).getKeys(true))
		{
			if (!s.contains("."))
				if (s.equalsIgnoreCase(world))
				{
					hasBoard = true;
					break;
				}
		}
		return hasBoard;
	}
	
	public static boolean doesGlobalHaveScoreBoard(int rotation) {
		boolean hasBoard = false;
		for (String s : Files.getConfig().getConfigurationSection("Info Board." + String.valueOf(rotation)).getKeys(true))
		{
			if (!s.contains("."))
				if (s.equalsIgnoreCase("global"))
				{
					hasBoard = true;
					break;
				}
		}
		return hasBoard;
	}
	
	public static boolean doesRankHaveScoreBoard(int rotation, String world, String rank) {
		boolean hasBoard = false;
		for (String s : Files.getConfig().getConfigurationSection("Info Board." + String.valueOf(rotation) + "." + world).getKeys(true))
		{
			if (!s.contains("."))
				if (s.equalsIgnoreCase(rank) || s.equalsIgnoreCase("default"))
				{
					hasBoard = true;
					break;
				}
		}
		return hasBoard;
	}
	
}
