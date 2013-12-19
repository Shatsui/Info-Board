
package me.xxsniperzzxxsd.infoboard.Util.VaraibleUtils;

import nl.lolmewn.stats.api.Stat;
import nl.lolmewn.stats.api.StatsAPI;
import nl.lolmewn.stats.player.StatData;
import nl.lolmewn.stats.player.StatsPlayer;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.RegisteredServiceProvider;


public class StatsHandler {

	private StatsAPI statsAPI;

	public StatsHandler()
	{
		RegisteredServiceProvider<StatsAPI> stats = Bukkit.getServer().getServicesManager().getRegistration(nl.lolmewn.stats.api.StatsAPI.class);
		this.statsAPI = stats.getProvider();
	}

	public int getTotalBlocksBroken(final String player) {
		return (int) statsAPI.getTotalBlocksBroken(player);
	}

	public int getTotalBlocksPlaced(final String player) {
		return (int) statsAPI.getTotalBlocksPlaced(player);
	}

	public int getTotalPlayTime(final String playerName) {
		final StatsPlayer player = getStats(playerName);
		StatData stat;

		int value = 0;

		for (final World w : Bukkit.getServer().getWorlds())
		{
			stat = player.getStatData(statsAPI.getStat("Playtime"), w.getName(), true);

			for (final Object[] vars : stat.getAllVariables())
			{
				value += stat.getValue(vars);
			}

		}

		return value;
	}

	public StatsPlayer getStats(String player) {
		return statsAPI.getStatsPlayer(player);
	}
	public StatData getStatsData(String player, World world, String stat){
		if(statsAPI.isUsingBetaFunctions())
			return getStats(player).getStatData(statsAPI.getStat(stat), world.getName(), true);
		else
			return getStats(player).getStatData(statsAPI.getStat(stat), true);
	}

	public StatData getStatType(final Stat statType, final String player) {
		final StatsPlayer sPlayer = getStats(player);
		return sPlayer.getStatData(statType, false);
	}

	public int getBlocksStat(String playerName, int id, int damageValue, boolean place, String statType) {

		StatData blockStat;
		int value = 0;
		String stat = "Block break";
		boolean checkDamageValue = false;

		if (damageValue > 0)
		{
			checkDamageValue = true;
		}
		if(place)
			stat = "Block place";

		// We want global (no specific world) so we loop over every world.
		for (World serverWorld : Bukkit.getServer().getWorlds())
		{
			blockStat = getStatsData(playerName, serverWorld, stat);

			for (final Object[] vars : blockStat.getAllVariables())
			{

				if (checkDamageValue)
				{
					// VAR 0 = blockID, VAR 1 = damageValue, VAR 2 = (1 =
					// break, 0 = place)
					if ((Integer) vars[0] == id && (Byte) vars[1] == damageValue)
					{
						value += blockStat.getValue(vars);
					}
				} else
				{
					if ((Integer) vars[0] == id)
					{
						value += blockStat.getValue(vars);
					}
				}
			}

		}

		return value;
	}

	public int getTotalMobsKilled(String playerName, String mobName) {

		StatData blockStat;
		final EntityType mob = getEntityType(mobName);
		boolean checkEntityType = false;
		int value = 0;

		if (mob != null && !mobName.equals("all"))
		{
			checkEntityType = true;
		}

		for (World serverWorld : Bukkit.getServer().getWorlds())
		{
			blockStat = getStatsData(playerName, serverWorld, "Kill");

			for (final Object[] vars : blockStat.getAllVariables())
			{

				// var 0 is mob type

				if (checkEntityType)
				{
					if (getEntityType(vars[0].toString()) != null && getEntityType(vars[0].toString()).equals(mob))
					{
						value += blockStat.getValue(vars);
					}
				} else
				{
					value += blockStat.getValue(vars);
				}
			}

		}

		return value;
	}
	public int getTotalDeaths(String playerName, String cause) {

		StatData blockStat;
		boolean checkCause = false;
		int value = 0;

		if (cause!= null && !cause.equals("all"))
		{
			checkCause = true;
		}

		for (World serverWorld : Bukkit.getServer().getWorlds())
		{
			blockStat = getStatsData(playerName, serverWorld, "Death");

			for (final Object[] vars : blockStat.getAllVariables())
			{

				// var 0 is cause

				if (checkCause)
				{
					if (vars[0].toString() != null && vars[0].toString().toLowerCase().equals(cause))
					{
						value += blockStat.getValue(vars);
					}
				} else
				{
					value += blockStat.getValue(vars);
				}
			}

		}

		return value;
	}

	public EntityType getEntityType(final String entityName) {
		try
		{
			return EntityType.valueOf(entityName.toUpperCase());
		} catch (final Exception e)
		{
			return null;
		}
	}

}