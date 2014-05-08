
package com.sniperzciinema.infoboard.Util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;


import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.sniperzciinema.infoboard.InfoBoard;


public class Files {
	
	// Set up all the needed things for files
	public static YamlConfiguration	variableF			= null;
	public static File							variableFile	= null;
	
	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static FileConfiguration getConfig() {
		return InfoBoard.me.getConfig();
	}
	
	// Get Variables file
	public static FileConfiguration getVariables() {
		if (Files.variableF == null)
		{
			reloadVariables();
			saveVariables();
		}
		return Files.variableF;
	}
	
	public static void reloadConfig() {
		InfoBoard.me.reloadConfig();
	}
	
	// Reload Variables File
	public static void reloadVariables() {
		if (Files.variableFile == null)
			Files.variableFile = new File(
					Bukkit.getPluginManager().getPlugin("Info-Board").getDataFolder(), "Variables.yml");
		Files.variableF = YamlConfiguration.loadConfiguration(Files.variableFile);
		// Look for defaults in the jar
		InputStream defConfigStream = Bukkit.getPluginManager().getPlugin("Info-Board").getResource("Variables.yml");
		if (defConfigStream != null)
		{
			YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
			Files.variableF.setDefaults(defConfig);
		}
	}
	
	public static void saveConfig() {
		InfoBoard.me.saveConfig();
	}
	
	// Save Variables File
	public static void saveVariables() {
		if ((Files.variableF == null) || (Files.variableFile == null))
			return;
		try
		{
			getVariables().save(Files.variableFile);
		}
		catch (IOException ex)
		{
			Bukkit.getLogger().log(Level.SEVERE, "Could not save config " + Files.variableFile, ex);
		}
	}
	
}
