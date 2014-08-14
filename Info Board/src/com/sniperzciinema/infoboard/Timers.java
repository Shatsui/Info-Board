package com.sniperzciinema.infoboard;

import com.sniperzciinema.infoboard.Scoreboard.Create;
import com.sniperzciinema.infoboard.Scoreboard.Update;
import com.sniperzciinema.infoboard.Scroll.ScrollText;
import com.sniperzciinema.infoboard.Util.Settings;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;


public class Timers {

	private int showTime, time, rotation;

	public Timers() {
		this.time = 0;
		this.rotation = 1;
		this.showTime = InfoBoard.getFileManager().getBoard().getInt("Info Board." + this.rotation + ".Show Time");
	}

	/**
	 * Get the current page
	 *
	 * @return page
	 */
	public int getPage() {
		return this.rotation;
	}

	/**
	 * Reset timers back to default
	 */
	public void reset() {
		this.time = 0;
		this.rotation = 1;
		this.showTime = InfoBoard.getFileManager().getBoard().getInt("Info Board." + String.valueOf(this.rotation) + ".Show Time");

		Bukkit.getScheduler().cancelTasks(InfoBoard.me);
		start();
	}

	/**
	 * Manually set the page showing
	 *
	 * @param page
	 */
	public void setPage(int page) {
		this.rotation = page;
		this.time = -1;
		this.showTime = InfoBoard.getFileManager().getBoard().getInt("Info Board." + String.valueOf(this.rotation) + ".Show Time");
	}

	/**
	 * Start all the timers
	 */
	public void start() {

		// ============================================ PAGE ROTATION =====================================================
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(InfoBoard.me, new Runnable() {

			@Override
			public void run() {
				if (Timers.this.time >= Timers.this.showTime) {
					setPage(getPage() + 1);

					if (Timers.this.showTime == 0)
						setPage(1);

					// Set scoreboard of current InfoBoard.rotation
					for (Player p : Bukkit.getOnlinePlayers())
						if (p.hasPermission("InfoBoard.View"))
							Create.createScoreBoard(p);
				}

				// Add one to the timer
				Timers.this.time++;
			}
		}, 2, 20L);

		// =================================================== UPDATE BOARD'S VALUE ==========================================================
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(InfoBoard.me, new Runnable() {

			@Override
			public void run() {

				for (Player p : Bukkit.getOnlinePlayers())
					if (p.hasPermission("InfoBoard.View"))
						Update.updateScoreBoard(p);

			}
		}, 2, (long) InfoBoard.getFileManager().getConfig().getDouble("Update Time") * 20);

		// ===================================================== SCROLLING TEXT ===============================================================
		if (Settings.scrollingEnabled())
			Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(InfoBoard.me, new Runnable() {

				@Override
				public void run() {
					for (Player p : Bukkit.getOnlinePlayers())
						if (p.hasPermission("InfoBoard.View"))
							ScrollText.scroll(p);
				}
			}, 2, (long) (InfoBoard.getFileManager().getConfig().getDouble("Scrolling Text.Shift Time") * 20));

	}

	public void stop() {
		this.time = 0;
		this.rotation = 1;
		this.showTime = InfoBoard.getFileManager().getBoard().getInt("Info Board." + String.valueOf(this.rotation) + ".Show Time");

		Bukkit.getScheduler().cancelTasks(InfoBoard.me);
	}
}
