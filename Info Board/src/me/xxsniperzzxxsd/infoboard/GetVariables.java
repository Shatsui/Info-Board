
package me.xxsniperzzxxsd.infoboard;

import me.xxsniperzzxxsd.infoboard.Util.Files;
import me.xxsniperzzxxsd.infoboard.Util.Messages;
import me.xxsniperzzxxsd.infoboard.Variables.ALTVariables;
import me.xxsniperzzxxsd.infoboard.Variables.AncientRPGVariables;
import me.xxsniperzzxxsd.infoboard.Variables.CommandPointsVariables;
import me.xxsniperzzxxsd.infoboard.Variables.CrankedVariables;
import me.xxsniperzzxxsd.infoboard.Variables.EssentialsVariables;
import me.xxsniperzzxxsd.infoboard.Variables.FactionsVariables;
import me.xxsniperzzxxsd.infoboard.Variables.GriefPreventionVariables;
import me.xxsniperzzxxsd.infoboard.Variables.HeroesVariables;
import me.xxsniperzzxxsd.infoboard.Variables.InfectedVariables;
import me.xxsniperzzxxsd.infoboard.Variables.JobsVariables;
import me.xxsniperzzxxsd.infoboard.Variables.LWCVariables;
import me.xxsniperzzxxsd.infoboard.Variables.McTownsVariables;
import me.xxsniperzzxxsd.infoboard.Variables.MiniGamesVariables;
import me.xxsniperzzxxsd.infoboard.Variables.MultiverseVariables;
import me.xxsniperzzxxsd.infoboard.Variables.OnTimeVariables;
import me.xxsniperzzxxsd.infoboard.Variables.PVPArenaVariables;
import me.xxsniperzzxxsd.infoboard.Variables.PVPStatsVariables;
import me.xxsniperzzxxsd.infoboard.Variables.PlayerPointsVariables;
import me.xxsniperzzxxsd.infoboard.Variables.PlayerVariables;
import me.xxsniperzzxxsd.infoboard.Variables.PlotMeVariables;
import me.xxsniperzzxxsd.infoboard.Variables.PrisionRankupVariables;
import me.xxsniperzzxxsd.infoboard.Variables.ServerVariables;
import me.xxsniperzzxxsd.infoboard.Variables.SimpleClansVariables;
import me.xxsniperzzxxsd.infoboard.Variables.SkillzVariables;
import me.xxsniperzzxxsd.infoboard.Variables.StatsVariables;
import me.xxsniperzzxxsd.infoboard.Variables.TownyVariables;
import me.xxsniperzzxxsd.infoboard.Variables.VaultVariables;
import me.xxsniperzzxxsd.infoboard.Variables.WorldGuardVariables;
import me.xxsniperzzxxsd.infoboard.Variables.mcMMOVariables;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;


public class GetVariables {

	public static String replaceVariables(String string, Player player) {
		String newString = string;

		// Server Variables
		newString = ServerVariables.replaceVariables(newString);

		// Player Variables
		newString = PlayerVariables.replaceVariables(newString, player);

		// Infected Support
		if (Bukkit.getServer().getPluginManager().getPlugin("Infected") != null)
		{
			if (newString.contains("<infected"))
				newString = InfectedVariables.replaceVariables(newString, player);
		}

		// Essentials Support
		if (Bukkit.getServer().getPluginManager().getPlugin("Essentials") != null)
		{
			if (newString.contains("<ess"))
				newString = EssentialsVariables.replaceVariables(newString, player);
		}

		// AncientRPG Support
		if (Bukkit.getServer().getPluginManager().getPlugin("AncientRPG") != null)
		{
			if (newString.contains("<ancientrpg"))
				newString = AncientRPGVariables.replaceVariables(newString, player);
		}

		// LWC Support
		if (Bukkit.getServer().getPluginManager().getPlugin("LWC") != null)
		{
			if (newString.contains("<lwc"))
				newString = LWCVariables.replaceVariables(newString, player);
		}

		// Cranked Support
		if (Bukkit.getServer().getPluginManager().getPlugin("Cranked") != null)
		{
			if (newString.contains("<cranked"))
				newString = CrankedVariables.replaceVariables(newString, player);
		}
		// Cranked Support
		if (Bukkit.getServer().getPluginManager().getPlugin("CommandPoints") != null)
		{
			if (newString.contains("<commandpoints"))
				newString = CommandPointsVariables.replaceVariables(newString, player);
		}
		// McTowns Support
		if (Bukkit.getServer().getPluginManager().getPlugin("MCTowns") != null)
		{
			if (newString.contains("<mctowns"))
				newString = McTownsVariables.replaceVariables(newString, player);
		}
		// Towny Support
		if (Bukkit.getServer().getPluginManager().getPlugin("Towny") != null)
		{
			if (newString.contains("<towny"))
				newString = TownyVariables.replaceVariables(newString, player);
		}
		// Factions Support
		if (Bukkit.getServer().getPluginManager().getPlugin("Factions") != null)
		{
			if (newString.contains("<factions"))
				newString = FactionsVariables.replaceVariables(newString, player);
		}
		// GriefPrevention Support
		if (Bukkit.getServer().getPluginManager().getPlugin("GriefPrevention") != null)
		{
			if (newString.contains("<griefprevention"))
				newString = GriefPreventionVariables.replaceVariables(newString, player);
		}
		// Heroes Support
		if (Bukkit.getServer().getPluginManager().getPlugin("Heroes") != null)
		{
			if (newString.contains("<heroes"))
				newString = HeroesVariables.replaceVariables(newString, player);
		}
		// mcMMO Support
		if (Bukkit.getServer().getPluginManager().getPlugin("mcMMO") != null)
		{
			if (newString.contains("<mcmmo"))
				newString = mcMMOVariables.replaceVariables(newString, player);
		}
		// Vault Support
		if (Bukkit.getServer().getPluginManager().getPlugin("Vault") != null)
		{
			if (newString.contains("<vault") || newString.contains("<rank") || newString.contains("<money"))
				newString = VaultVariables.replaceVariables(newString, player);
		}
		// Jobs Support
		if (Bukkit.getServer().getPluginManager().getPlugin("Jobs") != null)
		{
			if (newString.contains("<jobs"))
				newString = JobsVariables.replaceVariables(newString, player);
		}
		// PlayerPoints Support
		if (Bukkit.getServer().getPluginManager().getPlugin("PlayerPoints") != null)
		{
			if (newString.contains("<playerpoints"))
				newString = PlayerPointsVariables.replaceVariables(newString, player);
		}
		// PlayerPoints Support
		if (Bukkit.getServer().getPluginManager().getPlugin("pvpstats") != null)
		{
			if (newString.contains("<pvpstats"))
				newString = PVPStatsVariables.replaceVariables(newString, player);
		}
		// PlotMe Support
		if (Bukkit.getServer().getPluginManager().getPlugin("PlotMe") != null)
		{
			if (newString.contains("<plotme"))
				newString = PlotMeVariables.replaceVariables(newString, player);
		}
		// PVPArena Support
		if (Bukkit.getServer().getPluginManager().getPlugin("pvparena") != null)
		{
			if (newString.contains("<pvparena"))
				newString = PVPArenaVariables.replaceVariables(newString, player);

		}
		// PrisonRankup Support
		if (Bukkit.getServer().getPluginManager().getPlugin("PrisonRankup") != null)
		{
			if (newString.contains("<prisonrankup"))
				newString = PrisionRankupVariables.replaceVariables(newString, player);
		}
		// Stats Support
		if (Bukkit.getServer().getPluginManager().getPlugin("Stats") != null)
		{
			if (newString.contains("<stats"))
				newString = StatsVariables.replaceVariables(newString, player);
		}

		// SimpleClans Support
		if (Bukkit.getServer().getPluginManager().getPlugin("SimpleClans") != null)
		{
			if (newString.contains("<simpleclans"))
				newString = SimpleClansVariables.replaceVariables(newString, player);
		}

		if (Bukkit.getServer().getPluginManager().getPlugin("Skillz") != null)
		{
			if (newString.contains("<skillz"))
				newString = SkillzVariables.replaceVariables(newString, player);

		}
		// WorldGuard Support
		if (Bukkit.getServer().getPluginManager().getPlugin("WorldGuard") != null)
		{
			if (newString.contains("<worldguard"))
				newString = WorldGuardVariables.replaceVariables(newString, player);
		}
		// MiniGames Support
		if (Bukkit.getServer().getPluginManager().getPlugin("MiniGames") != null)
		{
			if (newString.contains("<minigames"))
				newString = MiniGamesVariables.replaceVariables(newString, player);
		}
		// Multiverse Support
		if (Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core") != null)
		{
			if (newString.contains("<multiverse"))
				newString = MultiverseVariables.replaceVariables(newString, player);
		}
		// OnTime Support
		if (Bukkit.getServer().getPluginManager().getPlugin("OnTime") != null)
		{
			if (newString.contains("<ontime"))
				newString = OnTimeVariables.replaceVariables(newString, player);
		}
		// UTF-8
		newString = ALTVariables.replaceVariables(newString);

		// Custom Variables
		for (String custom : Files.getConfig().getConfigurationSection("Custom Variables").getKeys(true))
		{
			if (newString.contains(custom))
				newString = newString.replaceAll(custom, Messages.getLine(Files.getConfig().getString("Custom Variables." + custom), player));
		}

		return newString;
	}
}
