
package me.xxsniperzzxxsd.infoboard;

import me.xxsniperzzxxsd.infoboard.Scoreboard.Create;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;


public class Commands implements CommandExecutor {

	InfoBoard plugin;

	public Commands(InfoBoard plugin)
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
						return true;
					}
					InfoBoard.hidefrom.add(sender.getName());
					sender.sendMessage("");
					sender.sendMessage(plugin.ib + "Hiding Info Board.");
					((Player) sender).getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
				} else if (args[0].equalsIgnoreCase("Show"))
				{
					if (!sender.hasPermission("InfoBoard.Toggle"))
					{
						sender.sendMessage("");
						sender.sendMessage(plugin.ib + "Invalid Permissions.");
						return true;
					}
					InfoBoard.hidefrom.remove(sender.getName());
					sender.sendMessage("");
					sender.sendMessage(plugin.ib + "Showing Info Board.");
				} else if (args[0].equalsIgnoreCase("Set"))
				{
					if (!sender.hasPermission("InfoBoard.Set"))
					{
						sender.sendMessage("");
						sender.sendMessage(plugin.ib + "Invalid Permissions.");
						return true;
					} else if (args.length == 2)
					{
						String rotate = args[1];

						if (plugin.getConfig().getString("Info Board." + rotate + ".global.default.Title") != null)
						{

							InfoBoard.rotation = Integer.valueOf(rotate);
							sender.sendMessage("");
							sender.sendMessage(plugin.ib + "Rotation set to: " + args[1]);
							plugin.timer = 0;
							plugin.total = plugin.getConfig().getInt("Info Board." + args[1] + ".Show Time");
							for (Player p : Bukkit.getOnlinePlayers())
								if (p.hasPermission("InfoBoard.View"))
									Create.createScoreBoard(p);
						} else
						{
							sender.sendMessage("");
							sender.sendMessage(plugin.ib + "Page not found: " + args[1]);
						}
					}
				} else if (args[0].equalsIgnoreCase("Reload"))
				{
					if (!sender.hasPermission("InfoBoard.Reload"))
					{
						sender.sendMessage("");
						sender.sendMessage(plugin.ib + "Invalid Permissions.");
						return true;
					} else
					{
						sender.sendMessage("");
						sender.sendMessage(plugin.ib + ChatColor.GREEN + "Configs been reloaded");
						plugin.reloadConfig();
						for (Player player : Bukkit.getOnlinePlayers())
						{

							if (player.hasPermission("InfoBoard.View"))
							{
								player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
								Create.createScoreBoard(player);
							}
						}
					}
				}

			} else
			{
				sender.sendMessage("");
				sender.sendMessage(ChatColor.GOLD + "" + ChatColor.STRIKETHROUGH + "============[" + ChatColor.DARK_GREEN + " Info Board v" + plugin.getDescription().getVersion() + ChatColor.GOLD + " " + ChatColor.STRIKETHROUGH + "]============");
				sender.sendMessage(plugin.ib + "/IB Hide " + ChatColor.YELLOW + "- Hide the board");
				sender.sendMessage(plugin.ib + "/IB Show " + ChatColor.YELLOW + "- Show the board");
				sender.sendMessage(plugin.ib + "/IB Reload " + ChatColor.YELLOW + "- Reload the config");
				sender.sendMessage(plugin.ib + "/IB Set <Pg> " + ChatColor.YELLOW + "- Set the page to view");
				if(plugin.update)
					sender.sendMessage(ChatColor.DARK_AQUA + "Theres a new update for InfoBoard(" + ChatColor.YELLOW + plugin.name + ChatColor.DARK_AQUA+").");
			}

		}
		return true;
	}

}
