
package com.sniperzciinema.infoboard;

import java.io.IOException;
import java.util.ArrayList;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;

import com.sniperzciinema.infoboard.Scroll.ScrollText;
import com.sniperzciinema.infoboard.Util.Files;
import com.sniperzciinema.infoboard.Util.Metrics;
import com.sniperzciinema.infoboard.Util.Updater;
import com.sniperzciinema.infoboard.Util.VaraibleUtils.Lag;


public class InfoBoard extends JavaPlugin {
	
	public static Plugin						me;
	public boolean									update		= false;
	public String										name			= "InfoBoard";
	
	public String										ib				= "" + ChatColor.RED + ChatColor.BOLD + "➳" + ChatColor.GRAY;
	
	public static Economy						economy;
	public static Permission				permission;
	public static boolean						economyB;
	public static boolean						permissionB;
	
	public static ScrollText				ScrollText;
	public static ArrayList<String>	hidefrom	= new ArrayList<String>();
	public static int								rotation	= 1;
	
	@Override
	public void onDisable() {
		Bukkit.getScheduler().cancelTasks(this);
		for (Player player : Bukkit.getOnlinePlayers())
			if (player.getScoreboard().getObjective(DisplaySlot.SIDEBAR) != null)
				if (player.getScoreboard().getObjective(DisplaySlot.SIDEBAR).getName().equalsIgnoreCase("InfoBoard"))
					player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
	}
	
	@Override
	public void onEnable() {
		InfoBoard.me = this;
		InfoBoard.ScrollText = new ScrollText();
		try
		{
			Metrics metrics = new Metrics(this);
			metrics.start();
			System.out.println("Metrics was started!");
		}
		catch (IOException e)
		{
			System.out.println("Metrics was unable to start...");
		}
		PlayerListener PlayerListener = new PlayerListener(this);
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(PlayerListener, this);
		
		getCommand("InfoBoard").setExecutor(new Commands(this));
		
		Files.getVariables().options().copyDefaults(true);
		Files.saveVariables();
		getConfig().options().copyDefaults(true);
		
		saveConfig();
		
		if (getConfig().getBoolean("Check for Updates"))
		{
			try
			{
				Updater updater = new Updater(this, 65787, getFile(), Updater.UpdateType.NO_DOWNLOAD, false);
				
				this.update = updater.getResult() == Updater.UpdateResult.UPDATE_AVAILABLE;
				this.name = updater.getLatestName();
				
			}
			catch (Exception ex)
			{
				System.out.println("The auto-updater tried to contact dev.bukkit.org, but was unsuccessful.");
			}
			if (this.update)
				System.out.println("Theres a new update for InfoBoard(v" + this.name + ").");
		}
		
		// Start TPS
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Lag(), 100L, 1L);
		
		// Set up Scoreboard
		
		if (!(getServer().getPluginManager().getPlugin("Vault") == null))
		{
			setupEconomy();
			setupPermissions();
		}
		Timers.start();
		
	}
	
	private boolean setupEconomy() {
		RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
		if (economyProvider != null)
			InfoBoard.economy = economyProvider.getProvider();
		if (InfoBoard.economy != null)
			InfoBoard.economyB = true;
		
		return (InfoBoard.economy != null);
	}
	
	private boolean setupPermissions() {
		RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
		if (permissionProvider != null)
			InfoBoard.permission = permissionProvider.getProvider();
		if (InfoBoard.permission != null)
			InfoBoard.permissionB = true;
		
		return (InfoBoard.permission != null);
	}
	
}
