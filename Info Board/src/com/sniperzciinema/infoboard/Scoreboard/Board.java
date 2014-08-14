package com.sniperzciinema.infoboard.Scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;


@SuppressWarnings("deprecation")
public class Board {

	private Scoreboard scoreboard;
	private Objective objective;

	/**
	 * Create a brand new objective and scoreboard
	 */
	public Board() {
		this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
		setup();
	}

	/**
	 * Use the current objective and scoreboard that the player is shown
	 *
	 * @param player
	 */
	public Board(Player player) {
		this.scoreboard = player.getScoreboard();
		this.objective = this.scoreboard.getObjective(DisplaySlot.SIDEBAR);
	}

	/**
	 * Use the current objective and scoreboard
	 *
	 * @param board
	 */
	public Board(Scoreboard board) {
		this.scoreboard = board;
		this.objective = board.getObjective(DisplaySlot.SIDEBAR);
	}

	/**
	 * Add a line to the board
	 *
	 * @param line
	 * @param row
	 */
	public void add(String line, int row) {

		if (row > 0)
			row *= -1;

		if (line.length() > 16)
			addCreatingTeam(line, row);
		else {
			Score score = this.objective.getScore(line);
			score.setScore(1);
			score.setScore(row);
		}
	}

	/**
	 * Add a line well creating a team to go with it(Allows for up to 48 characters in a line(Prefix and Suffix))
	 *
	 * @param line
	 * @param row
	 */
	private void addCreatingTeam(String line, int row) {
		String prefix = null, name = null, suffix = null;

		if (line.length() > 48)
			line = line.substring(0, 47);

		if (line.length() <= 16)
			name = line;

		else if (line.length() <= 32) {
			name = line.substring(0, 16);
			suffix = line.substring(16, line.length());
		} else {
			prefix = line.substring(0, 16);
			name = line.substring(16, 32);
			suffix = line.substring(32, line.length());
		}

		OfflinePlayer op = Bukkit.getOfflinePlayer(name);

		if ((prefix != null) || (suffix != null)) {
			Team team = this.scoreboard.getPlayerTeam(op);

			if (team == null)
				team = this.scoreboard.registerNewTeam(name);

			team.addPlayer(op);
			if (prefix != null)
				team.setPrefix(prefix);

			if (suffix != null)
				team.setSuffix(suffix);
		}
		Score score = this.objective.getScore(op);
		score.setScore(1);
		score.setScore(row);
	}

	/**
	 * Get the score for the line
	 *
	 * @param line
	 * @return score
	 */
	public Score getScore(String line) {
		return this.objective.getScore(line);
	}

	/**
	 * Get the scoreboard
	 *
	 * @return the scoreboard
	 */
	public Scoreboard getScoreboard() {
		return this.scoreboard;
	}

	/**
	 * Get the title
	 *
	 * @return the title
	 */
	public String getTitle() {
		return this.objective.getDisplayName();
	}

	/**
	 * Remove the line from the board
	 *
	 * @param line
	 */
	public void remove(String line) {
		this.scoreboard.resetScores(line);
		if (this.scoreboard.getTeam(line) != null)
			this.scoreboard.getTeam(line).unregister();

	}

	/**
	 * Set the title
	 *
	 * @param title
	 */
	public void setTitle(String title) {
		this.objective.setDisplayName(title);
	}

	/**
	 * Set up the new objective
	 */
	private void setup() {
		this.objective = this.scoreboard.registerNewObjective("InfoBoard", "dummy");
		this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
	}

	/**
	 * Update a line (Remove the current line, add the new line)
	 *
	 * @param line
	 * @param row
	 */
	public void update(String line, int row) {
		String name = null;

		if (line.length() <= 16)
			name = line;

		else if (line.length() <= 32)
			name = line.substring(0, 16);

		else
			name = line.substring(16, 32);

		if (!this.scoreboard.getEntries().contains(line)) {
			for (String op : this.scoreboard.getEntries()) {
				if (this.objective.getScore(op).getScore() == row) {
					remove(op);
					break;
				}
				int a = 0;
			}
			add(line, row);
		}
	}
}
