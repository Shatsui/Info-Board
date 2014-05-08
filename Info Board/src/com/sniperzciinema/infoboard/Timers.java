
package com.sniperzciinema.infoboard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.sniperzciinema.infoboard.Scoreboard.Create;
import com.sniperzciinema.infoboard.Scoreboard.Update;
import com.sniperzciinema.infoboard.Util.Files;


public class Timers {
	
	private static int	total	= 0;
	private static int	timer	= 0;
	private static int	scoreBoard;
	
	public static void reset() {
		total = 0;
		timer = 0;
		Bukkit.getScheduler().cancelTask(scoreBoard);
		start();
	}
	
	public static void setPage(int page) {
		InfoBoard.rotation = page;
		timer = 0;
		total = InfoBoard.me.getConfig().getInt("Info Board." + page + ".Show Time");
	}
	
	public static void start() {
		// Start Rotation Timer
		scoreBoard = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(InfoBoard.me, new Runnable()
		{
			
			@Override
			public void run() {
				// System.out.println(timer +"/" + total);
				// Add one to the timer if it doesnt equal the total
				if (!(Timers.timer >= Timers.total))
					Timers.timer++;
				// If they do equal, say that we're ready for a reset
				else if (Timers.timer >= Timers.total)
				{
					
					// Add on to scoreboard
					InfoBoard.rotation++;
					Timers.total = Files.getConfig().getInt("Info Board." + String.valueOf(InfoBoard.rotation) + ".Show Time");
					Timers.timer = 0;
					
					if (Timers.total == 0)
					{
						InfoBoard.rotation = 1;
						Timers.timer = 0;
						Timers.total = Files.getConfig().getInt("Info Board." + String.valueOf(InfoBoard.rotation) + ".Show Time");
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
