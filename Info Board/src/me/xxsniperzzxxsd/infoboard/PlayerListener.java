
package me.xxsniperzzxxsd.infoboard;

import me.xxsniperzzxxsd.infoboard.Util.Files;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.DisplaySlot;


public class PlayerListener implements Listener {

	public Main plugin;

	public PlayerListener(Main instance)
	{
		plugin = instance;
	}

	@EventHandler
	public void onPlayerDie(PlayerDeathEvent event) {
		if (event.getEntity().getKiller() instanceof Player)
		{
			if (!Main.config.getStringList("Disabled Worlds").contains(event.getEntity().getWorld().getName()))
			{
				Player killer = (Player) event.getEntity().getKiller();

				Files.getPlayers().set(killer.getName() + ".Kills", Files.getPlayers().getInt(killer.getName() + ".Kills") + 1);
				Files.savePlayers();
			}

			Files.getPlayers().set(event.getEntity().getName() + ".Deaths", Files.getPlayers().getInt(event.getEntity().getName() + ".Deaths") + 1);
			Files.savePlayers();
		}
	}

	@EventHandler
	public void onPlayerChangeWorld(PlayerChangedWorldEvent event) {
		event.getPlayer().getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		if (event.getPlayer().isOp() && plugin.update)
		{
			System.out.println("Theres a new update for InfoBoard(v" + plugin.name + ").");
		}
	}
}