
package me.xxsniperzzxxsd.infoboard;

import java.io.IOException;
import java.util.ArrayList;

import me.xxsniperzzxxsd.infoboard.Scoreboard.Create;
import me.xxsniperzzxxsd.infoboard.Scoreboard.Update;
import me.xxsniperzzxxsd.infoboard.Scroll.ScrollText;
import me.xxsniperzzxxsd.infoboard.Util.Files;
import me.xxsniperzzxxsd.infoboard.Util.Metrics;
import me.xxsniperzzxxsd.infoboard.Util.Updater;
import me.xxsniperzzxxsd.infoboard.Util.VaraibleUtils.Lag;
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


public class InfoBoard extends JavaPlugin {

	public static Plugin me;
	public boolean update = false;
	public String name = "InfoBoard";

	public String ib = "" + ChatColor.RED + ChatColor.BOLD + "➳" + ChatColor.GRAY;

	public static Economy economy;
	public static Permission permission;

	public ScrollText ScrollText;
	public static ArrayList<String> hidefrom = new ArrayList<String>();
	public static int rotation = 1;

	public int total = 0;
	public int timer = 0;

	public void onEnable() {
		me = this;
		ScrollText = new ScrollText();
		try
		{
			Metrics metrics = new Metrics(this);
			metrics.start();
			System.out.println("Metrics was started!");
		} catch (IOException e)
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

		if (getConfig().getBoolean("Check for updates"))
		{
			try
			{
				Updater updater = new Updater(this, 65787, getFile(),
						Updater.UpdateType.NO_DOWNLOAD, false);

				update = updater.getResult() == Updater.UpdateResult.UPDATE_AVAILABLE;
				name = updater.getLatestName();

			} catch (Exception ex)
			{
				System.out.println("The auto-updater tried to contact dev.bukkit.org, but was unsuccessful.");
			}
			if (update)
				System.out.println("Theres a new update for InfoBoard(v" + name + ").");
		}

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
					rotation++;
					total = getConfig().getInt("Info Board." + String.valueOf(rotation) + ".Show Time");
					timer = 0;

					if (total == 0)
					{
						rotation = 1;
						timer = 0;
						total = getConfig().getInt("Info Board." + String.valueOf(rotation) + ".Show Time");
					}

					// Set scoreboard of current rotation
					for (Player p : Bukkit.getOnlinePlayers())
						if (p.hasPermission("InfoBoard.View"))
							Create.createScoreBoard(p);
				}
			}
		}, 0, 20);

		// Update Scores
		getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable()
		{

			@Override
			public void run() {

				for (Player p : Bukkit.getOnlinePlayers())
					if (p.hasPermission("InfoBoard.View"))
						Update.updateScoreBoard(p);
			}
		}, 0, (long) (getConfig().getDouble("Update Time") * 20));

		if (getConfig().getBoolean("Scrolling Text.Enable"))
		{
			getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable()
			{

				@Override
				public void run() {
					for (Player p : Bukkit.getOnlinePlayers())
						if (p.hasPermission("InfoBoard.View"))
							ScrollText.slideScore(p);
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
