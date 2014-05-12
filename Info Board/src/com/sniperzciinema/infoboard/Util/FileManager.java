
package com.sniperzciinema.infoboard.Util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.sniperzciinema.infoboard.InfoBoard;


public class FileManager {
	
	private YamlConfiguration	variable;
	private File							boardFile;
	private File							variableFile;
	private YamlConfiguration	board;
	
	public FileManager()
	{
		
		getVariables().options().copyDefaults(true);
		saveVariables();
		getBoard().options().copyDefaults(true);
		saveBoard();
		getConfig().options().copyDefaults(true);
		saveConfig();
	}
	
	public FileConfiguration getConfig() {
		return InfoBoard.me.getConfig();
	}
	
	/**
	 * Get the variables file
	 * 
	 * @return
	 */
	public FileConfiguration getVariables() {
		if (variable == null)
		{
			reloadVariables();
			saveVariables();
		}
		return variable;
	}
	
	/**
	 * Get the board file
	 * 
	 * @return
	 */
	public FileConfiguration getBoard() {
		if (board == null)
		{
			reloadBoard();
			saveBoard();
		}
		return board;
	}
	
	/**
	 * Get the config
	 */
	public void reloadConfig() {
		InfoBoard.me.reloadConfig();
	}
	
	/**
	 * Reload variables file
	 */
	public void reloadVariables() {
		if (variableFile == null)
			variableFile = new File(Bukkit.getPluginManager().getPlugin("Info-Board").getDataFolder(),
					"Variables.yml");
		variable = YamlConfiguration.loadConfiguration(variableFile);
		// Look for defaults in the jar
		InputStream defConfigStream = Bukkit.getPluginManager().getPlugin("Info-Board").getResource("Variables.yml");
		if (defConfigStream != null)
		{
			YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
			variable.setDefaults(defConfig);
		}
	}
	
	/**
	 * Reload Boards file
	 */
	public void reloadBoard() {
		if (boardFile == null)
			boardFile = new File(Bukkit.getPluginManager().getPlugin("Info-Board").getDataFolder(),
					"Board.yml");
		board = YamlConfiguration.loadConfiguration(boardFile);
		// Look for defaults in the jar
		InputStream defConfigStream = Bukkit.getPluginManager().getPlugin("Info-Board").getResource("Board.yml");
		if (defConfigStream != null)
		{
			YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
			if (!boardFile.exists() || (boardFile.length() == 0))
				board.setDefaults(defConfig);
		}
	}
	
	/**
	 * Save Config
	 */
	public void saveConfig() {
		InfoBoard.me.saveConfig();
	}
	
	/**
	 * Save Variables file
	 */
	public void saveVariables() {
		if ((variable == null) || (variableFile == null))
			return;
		try
		{
			getVariables().save(variableFile);
		}
		catch (IOException ex)
		{
			Bukkit.getLogger().log(Level.SEVERE, "Could not save config " + variableFile, ex);
		}
	}
	
	/**
	 * Save Board file
	 */
	public void saveBoard() {
		if ((board == null) || (boardFile == null))
			return;
		try
		{
			getBoard().save(boardFile);
		}
		catch (IOException ex)
		{
			Bukkit.getLogger().log(Level.SEVERE, "Could not save config " + boardFile, ex);
		}
	}
	
}
