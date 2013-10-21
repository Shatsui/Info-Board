
package me.xxsniperzzxxsd.infoboard;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;


public class Commands implements CommandExecutor {

	Main plugin;

	public Commands(Main plugin)
	{
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("InfoBoard"))
		{
			if (args.length > 0)
			{
				if (args[0].equalsIgnoreCase("Hide"))
				{
					if (!sender.hasPermission("InfoBoard.Toggle"))
					{
						sender.sendMessage("");
						sender.sendMessage(plugin.ib + "Invalid Permissions.");
						sender.sendMessage("");
						return true;
					}
					plugin.ScoreBoard.hidefrom.add(sender.getName());
					sender.sendMessage("");
					sender.sendMessage(plugin.ib + "Hiding Info Board.");
					sender.sendMessage("");
					((Player) sender).getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
				} else if (args[0].equalsIgnoreCase("Show"))
				{
					if (!sender.hasPermission("InfoBoard.Toggle"))
					{
						sender.sendMessage("");
						sender.sendMessage(plugin.ib + "Invalid Permissions.");
						sender.sendMessage("");
						return true;
					}
					plugin.ScoreBoard.hidefrom.remove(sender.getName());
					sender.sendMessage("");
					sender.sendMessage(plugin.ib + "Showing Info Board.");
					sender.sendMessage("");
				} else if (args[0].equalsIgnoreCase("Set"))
				{
					if (!sender.hasPermission("InfoBoard.Set"))
					{
						sender.sendMessage("");
						sender.sendMessage(plugin.ib + "Invalid Permissions.");
						sender.sendMessage("");
						return true;
					} else if (args.length == 2)
					{
						if (plugin.getConfig().getString("Info Board." + String.valueOf(args[1]) + "." + plugin.ScoreBoard.rank + ".Title") != null)
						{

							plugin.ScoreBoard.rotation = Integer.valueOf(args[1]);
							plugin.timer = 0;
							sender.sendMessage("");
							sender.sendMessage(plugin.ib + "Rotation set to: " + args[1]);
							sender.sendMessage("");
							for(Player p : Bukkit.getOnlinePlayers())
								plugin.ScoreBoard.createScoreBoard(p);
						} else
						{
							sender.sendMessage("");
							sender.sendMessage(plugin.ib + "Page not found: " + args[1]);
							sender.sendMessage("");
						}
					}
				} else if (args[0].equalsIgnoreCase("Reload"))
				{
					if (!sender.hasPermission("InfoBoard.Reload"))
					{
						sender.sendMessage("");
						sender.sendMessage(plugin.ib + "Invalid Permissions.");
						sender.sendMessage("");
						return true;
					} else
					{
						sender.sendMessage("");
						sender.sendMessage(plugin.ib + ChatColor.GREEN + "Configs been reloaded");
						plugin.reloadConfig();
						for(Player player: Bukkit.getOnlinePlayers()){
							player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
						}
						sender.sendMessage("");
					}
				}

			} else
			{
				sender.sendMessage("");
				sender.sendMessage(plugin.ib + "/IB Hide - Hide the board");
				sender.sendMessage(plugin.ib + "/IB Show - Show the board");
				sender.sendMessage(plugin.ib + "/IB Reload - Reload the config");
				sender.sendMessage(plugin.ib + "/IB Set <Pg> - Set the page to view");
				sender.sendMessage("");
			}

		}
		return true;
	}

}
