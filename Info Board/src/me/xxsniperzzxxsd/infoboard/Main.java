
package me.xxsniperzzxxsd.infoboard;

import java.util.ArrayList;

import me.xxsniperzzxxsd.infoboard.Util.Files;
import me.xxsniperzzxxsd.infoboard.Util.Lag;
import me.xxsniperzzxxsd.infoboard.Util.ScrollManager;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;


public class Main extends JavaPlugin {

	public String ib = "" + ChatColor.RED + ChatColor.BOLD + "➳" + ChatColor.GRAY;

	public ArrayList<String> disabledPlayers = new ArrayList<String>();
	public static Configuration config;

	public static Economy economy;
	public static Permission permission;

	public ScrollManager ScrollManager;
	public ScoreBoard ScoreBoard;
	public ScrollText ScrollText;

	public int total = 0;
	public int timer = 0;

	public void onEnable() {

		PlayerListener PlayerListener = new PlayerListener(this);
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(PlayerListener, this);

		getCommand("InfoBoard").setExecutor(new Commands(this));

		Files.getPlayers().options().copyDefaults(true);
		Files.savePlayers();
		Files.getVariables().options().copyDefaults(true);
		Files.saveVariables();
		getConfig().options().copyDefaults(true);

		saveConfig();

		config = getConfig();

		ScrollManager = new ScrollManager();
		ScoreBoard = new ScoreBoard(this);
		ScrollText = new ScrollText(this);

		for (Player p : Bukkit.getOnlinePlayers())
			ScoreBoard.createScoreBoard(p);

		// Start TPS
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Lag(), 100L, 1L);

		// Set up Scoreboard

		if (!(getServer().getPluginManager().getPlugin("Vault") == null))
		{
			setupEconomy();
			setupPermissions();
		}
		// Start Rotation Timer
		getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable()
		{

			@Override
			public void run() {
				// System.out.println(timer +"/" + total);
				// Add one to the timer if it doesnt equal the total
				if (!(timer >= total))
					timer++;
				// If they do equal, say that we're ready for a reset
				else if (timer >= total)
				{

					// Add on to scoreboard
					ScoreBoard.rotation++;
					total = getConfig().getInt("Info Board." + String.valueOf(ScoreBoard.rotation) + ".Show Time");
					timer = 0;

					if (total == 0)
					{
						ScoreBoard.rotation = 1;
						timer = 0;
						total = getConfig().getInt("Info Board." + String.valueOf(ScoreBoard.rotation) + ".Show Time");
					}

					// Set scoreboard of current rotation
					for (Player p : Bukkit.getOnlinePlayers())
						ScoreBoard.createScoreBoard(p);
				}
			}
		}, 0, 20);

		// Update Scores
		getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable()
		{

			@Override
			public void run() {

				for (Player p : Bukkit.getOnlinePlayers())
					ScoreBoard.updateScoreBoard(p);
			}
		}, 0, (long) (getConfig().getDouble("Update Time") * 20));

		if (getConfig().getBoolean("Scrolling Text.Enable"))
		{
			getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable()
			{

				@Override
				public void run() {
					for (Player player : Bukkit.getOnlinePlayers()){
						ScrollText.slideScore(player);
					}
				}
			}, 0, (long) (getConfig().getDouble("Scrolling Text.Shift Time") * 20));
		}
	}

	public void onDisable() {
		Bukkit.getScheduler().cancelTasks(this);
		for (Player player : Bukkit.getOnlinePlayers())
			if (player.getScoreboard().getObjective(DisplaySlot.SIDEBAR) != null)
				if (player.getScoreboard().getObjective(DisplaySlot.SIDEBAR).getName().equalsIgnoreCase("InfoBoard"))
					player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
	}

	private boolean setupPermissions() {
		RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
		if (permissionProvider != null)
		{
			permission = permissionProvider.getProvider();
		}
		return (permission != null);
	}

	private boolean setupEconomy() {
		RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
		if (economyProvider != null)
		{
			economy = economyProvider.getProvider();
		}

		return (economy != null);
	}

}
