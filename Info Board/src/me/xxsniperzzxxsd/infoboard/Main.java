
package me.xxsniperzzxxsd.infoboard;

import java.util.ArrayList;

import me.xxsniperzzxxsd.infoboard.Util.Files;
import me.xxsniperzzxxsd.infoboard.Util.Lag;
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

	public String ib = ChatColor.BLACK + "➳ " + ChatColor.YELLOW + "➳ " +ChatColor.BLACK + "➳ "+ ChatColor.GRAY;

	public ArrayList<String> disabledPlayers = new ArrayList<String>();
	public static Configuration config;

	public static Economy economy;
	public static Permission permission;

	private boolean startover = true;

	public int timer, total;

	public ScoreBoard ScoreBoard;
	public ScrollText ScrollText;

	public void onEnable() {

		PlayerListener PlayerListener = new PlayerListener(this);
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(PlayerListener, this);

		getCommand("InfoBoard").setExecutor(new Commands(this));

		Files.getPlayers().options().copyDefaults(true);
		Files.savePlayers();
		Files.getVariables().options().copyDefaults(true);
		Files.saveVariables();
		config = getConfig();
		config.options().copyDefaults(true);
		saveConfig();
		ScoreBoard = new ScoreBoard(this);
		ScrollText = new ScrollText();
		
		// Start TPS
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Lag(), 100L, 1L);

		// Set up Scoreboard

		if (!(getServer().getPluginManager().getPlugin("Vault") == null))
		{
			setupEconomy();
			setupPermissions();
		}
		//Start Rotation Timer
		getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable()
		{

			@Override
			public void run() {
				// If timer is starting over
				if (startover)
				{
					timer = 0;
					total = config.getInt("Info Board." + String.valueOf(ScoreBoard.rotation) + ".Show Time");
					for (Player p : Bukkit.getOnlinePlayers())
						ScoreBoard.createScoreBoard(p);
				}

				startover = false;

				// Add one to the timer if it doesnt equal the total
				if (!(timer >= total))
				{
					timer += 2;
				}
				// If they do equal, add to the scoreboard rotation
				else if (timer >= total)
				{
					if (config.getString("Info Board." + String.valueOf(ScoreBoard.rotation + 1) + "." + ScoreBoard.rank + ".Title") != null)
					{
						ScoreBoard.rotation++;
					} else
						ScoreBoard.rotation = 1;

					startover = true;
				}
			}
		}, 50, config.getInt("Info Board." + String.valueOf(ScoreBoard.rotation) + ".Show Time") * 20);
		
		
		// Update Scores
		getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable()
		{

			@Override
			public void run() {
				for (Player p : Bukkit.getOnlinePlayers())
					ScoreBoard.updateScoreBoard(p);
			}
		}, 70, getConfig().getInt("Update Time") * 20);

		if(config.getBoolean("Scrolling Text.Enable")){
			getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable()
			{
				@Override
				public void run() {
					for(Player player: Bukkit.getOnlinePlayers())
						ScrollText.slideScore(player);
				}
			}, 70, config.getInt("Scrolling Text.Shift Time")* 20);
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
