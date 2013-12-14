
package me.xxsniperzzxxsd.infoboard.Util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;


public class Files {

	// Set up all the needed things for files
	public static YamlConfiguration variableF = null;
	public static File variableFile = null;
	public static YamlConfiguration playerF = null;
	public static File playerFile = null;

	// /////////////////////////////////////////////////////////////////////////////////////////////////
	// Reload Players File
	public static void reloadPlayers() {
		if (playerFile == null)
			playerFile = new File(
					Bukkit.getPluginManager().getPlugin("Info Board").getDataFolder(),
					"Players.yml");
		playerF = YamlConfiguration.loadConfiguration(playerFile);
		// Look for defaults in the jar
		InputStream defConfigStream = Bukkit.getPluginManager().getPlugin("Info Board").getResource("Players.yml");
		if (defConfigStream != null)
		{
			YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
			playerF.setDefaults(defConfig);
		}
	}

	// Get Players file
	public static FileConfiguration getPlayers() {
		if (playerF == null)
		{
			reloadPlayers();
			savePlayers();
		}
		return playerF;
	}

	// Save Players File
	public static void savePlayers() {
		if (playerF == null || playerFile == null)
			return;
		try
		{
			getPlayers().save(playerFile);
		} catch (IOException ex)
		{
			Bukkit.getLogger().log(Level.SEVERE, "Could not save config " + playerFile, ex);
		}
	}

	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////

	// Reload Variables File
	public static void reloadVariables() {
		if (variableFile == null)
			variableFile = new File(
					Bukkit.getPluginManager().getPlugin("Info Board").getDataFolder(),
					"Variables.yml");
		variableF = YamlConfiguration.loadConfiguration(variableFile);
		// Look for defaults in the jar
		InputStream defConfigStream = Bukkit.getPluginManager().getPlugin("Info Board").getResource("Variables.yml");
		if (defConfigStream != null)
		{
			YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
			variableF.setDefaults(defConfig);
		}
	}

	// Get Variables file
	public static FileConfiguration getVariables() {
		if (variableF == null)
		{
			reloadVariables();
			saveVariables();
		}
		return variableF;
	}

	// Save Variables File
	public static void saveVariables() {
		if (variableF == null || variableFile == null)
			return;
		try
		{
			getVariables().save(variableFile);
		} catch (IOException ex)
		{
			Bukkit.getLogger().log(Level.SEVERE, "Could not save config " + variableFile, ex);
		}
	}

}