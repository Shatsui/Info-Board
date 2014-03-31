package me.xxsniperzzxxsd.infoboard;

import me.xxsniperzzxxsd.infoboard.Scoreboard.Create;
import me.xxsniperzzxxsd.infoboard.Scoreboard.Update;
import me.xxsniperzzxxsd.infoboard.Util.Files;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;


public class Timers {
	public int total = 0;
	public int timer = 0;
	
	public void start(){
	// Start Rotation Timer
			Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(InfoBoard.me, new Runnable()
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
						InfoBoard.rotation++;
						total = Files.getConfig().getInt("Info Board." + String.valueOf(InfoBoard.rotation) + ".Show Time");
						timer = 0;

						if (total == 0)
						{
							InfoBoard.rotation = 1;
							timer = 0;
							total = Files.getConfig().getInt("Info Board." + String.valueOf(InfoBoard.rotation) + ".Show Time");
						}

						// Set scoreboard of current InfoBoard.rotation
						for (Player p : Bukkit.getOnlinePlayers())
							if (p.hasPermission("InfoBoard.View"))
								Create.createScoreBoard(p);
					}
				}
			}, 0, 20);

			// Update Scores
			Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(InfoBoard.me, new Runnable()
			{

				@Override
				public void run() {

					for (Player p : Bukkit.getOnlinePlayers())
						if (p.hasPermission("InfoBoard.View"))
							Update.updateScoreBoard(p);
				}
			}, 0, (long) Files.getConfig().getDouble("Update Time") * 20);

			if (Files.getConfig().getBoolean("Scrolling Text.Enable"))
			{
				Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(InfoBoard.me, new Runnable()
				{

					@Override
					public void run() {
						for (Player p : Bukkit.getOnlinePlayers())
							if (p.hasPermission("InfoBoard.View"))
								InfoBoard.ScrollText.slideScore(p);
					}
				}, 0, (long) (Files.getConfig().getDouble("Scrolling Text.Shift Time") * 20));
			}
	}
}
