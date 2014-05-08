
package com.sniperzciinema.infoboard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.sniperzciinema.infoboard.Util.Files;
import com.sniperzciinema.infoboard.Util.Messages;
import com.sniperzciinema.infoboard.Variables.ALTVariables;
import com.sniperzciinema.infoboard.Variables.AncientRPGVariables;
import com.sniperzciinema.infoboard.Variables.CommandPointsVariables;
import com.sniperzciinema.infoboard.Variables.CrankedVariables;
import com.sniperzciinema.infoboard.Variables.EssentialsVariables;
import com.sniperzciinema.infoboard.Variables.FactionsVariables;
import com.sniperzciinema.infoboard.Variables.GriefPreventionVariables;
import com.sniperzciinema.infoboard.Variables.HeroesVariables;
import com.sniperzciinema.infoboard.Variables.InfectedVariables;
import com.sniperzciinema.infoboard.Variables.JobsVariables;
import com.sniperzciinema.infoboard.Variables.LWCVariables;
import com.sniperzciinema.infoboard.Variables.MarriageVariables;
import com.sniperzciinema.infoboard.Variables.MathVariables;
import com.sniperzciinema.infoboard.Variables.McTownsVariables;
import com.sniperzciinema.infoboard.Variables.MiniGamesVariables;
import com.sniperzciinema.infoboard.Variables.MultiverseVariables;
import com.sniperzciinema.infoboard.Variables.OnTimeVariables;
import com.sniperzciinema.infoboard.Variables.PVPArenaVariables;
import com.sniperzciinema.infoboard.Variables.PVPStatsVariables;
import com.sniperzciinema.infoboard.Variables.PlayerPointsVariables;
import com.sniperzciinema.infoboard.Variables.PlayerVariables;
import com.sniperzciinema.infoboard.Variables.PlotMeVariables;
import com.sniperzciinema.infoboard.Variables.PointsAPIVariables;
import com.sniperzciinema.infoboard.Variables.PrisionRankupVariables;
import com.sniperzciinema.infoboard.Variables.PvpLevelsVariables;
import com.sniperzciinema.infoboard.Variables.ServerVariables;
import com.sniperzciinema.infoboard.Variables.SimpleClansVariables;
import com.sniperzciinema.infoboard.Variables.SkillzVariables;
import com.sniperzciinema.infoboard.Variables.StatsVariables;
import com.sniperzciinema.infoboard.Variables.TownyVariables;
import com.sniperzciinema.infoboard.Variables.VanishNoPacketVariables;
import com.sniperzciinema.infoboard.Variables.VaultVariables;
import com.sniperzciinema.infoboard.Variables.WorldGuardVariables;
import com.sniperzciinema.infoboard.Variables.mcMMOVariables;


public class GetVariables {
	
	public static String replaceVariables(String string, Player player) {
		String newString = string;
		
		// Math Variables
		if (newString.contains("<math"))
			newString = MathVariables.replaceVariables(newString, player);
		
		// Server Variables
		newString = ServerVariables.replaceVariables(newString);
		
		// Player Variables
		newString = PlayerVariables.replaceVariables(newString, player);
		
		// Infected Support
		if (Bukkit.getServer().getPluginManager().getPlugin("Infected") != null)
			if (newString.contains("<infected"))
				newString = InfectedVariables.replaceVariables(newString, player);
		
		// Essentials Support
		if (Bukkit.getServer().getPluginManager().getPlugin("Essentials") != null)
			if (newString.contains("<ess"))
				newString = EssentialsVariables.replaceVariables(newString, player);
		
		// AncientRPG Support
		if (Bukkit.getServer().getPluginManager().getPlugin("AncientRPG") != null)
			if (newString.contains("<ancientrpg"))
				newString = AncientRPGVariables.replaceVariables(newString, player);
		
		// LWC Support
		if (Bukkit.getServer().getPluginManager().getPlugin("LWC") != null)
			if (newString.contains("<lwc"))
				newString = LWCVariables.replaceVariables(newString, player);
		
		// Marriage Support
		if (Bukkit.getServer().getPluginManager().getPlugin("Marriage") != null)
			if (newString.contains("<marriage"))
				newString = MarriageVariables.replaceVariables(newString, player);
		
		// Cranked Support
		if (Bukkit.getServer().getPluginManager().getPlugin("Cranked") != null)
			if (newString.contains("<cranked"))
				newString = CrankedVariables.replaceVariables(newString, player);
		// Cranked Support
		if (Bukkit.getServer().getPluginManager().getPlugin("CommandPoints") != null)
			if (newString.contains("<commandpoints"))
				newString = CommandPointsVariables.replaceVariables(newString, player);
		// McTowns Support
		if (Bukkit.getServer().getPluginManager().getPlugin("MCTowns") != null)
			if (newString.contains("<mctowns"))
				newString = McTownsVariables.replaceVariables(newString, player);
		// Towny Support
		if (Bukkit.getServer().getPluginManager().getPlugin("Towny") != null)
			if (newString.contains("<towny"))
				newString = TownyVariables.replaceVariables(newString, player);
		// Factions Support
		if (Bukkit.getServer().getPluginManager().getPlugin("Factions") != null)
			if (newString.contains("<factions"))
				newString = FactionsVariables.replaceVariables(newString, player);
		// GriefPrevention Support
		if (Bukkit.getServer().getPluginManager().getPlugin("GriefPrevention") != null)
			if (newString.contains("<griefprevention"))
				newString = GriefPreventionVariables.replaceVariables(newString, player);
		// Heroes Support
		if (Bukkit.getServer().getPluginManager().getPlugin("Heroes") != null)
			if (newString.contains("<heroes"))
				newString = HeroesVariables.replaceVariables(newString, player);
		// mcMMO Support
		if (Bukkit.getServer().getPluginManager().getPlugin("mcMMO") != null)
			if (newString.contains("<mcmmo"))
				newString = mcMMOVariables.replaceVariables(newString, player);
		// Vault Support
		if (Bukkit.getServer().getPluginManager().getPlugin("Vault") != null)
			if (newString.contains("<vault") || newString.contains("<rank") || newString.contains("<money"))
				newString = VaultVariables.replaceVariables(newString, player);
		// Jobs Support
		if (Bukkit.getServer().getPluginManager().getPlugin("Jobs") != null)
			if (newString.contains("<jobs"))
				newString = JobsVariables.replaceVariables(newString, player);
		// PlayerPoints Support
		if (Bukkit.getServer().getPluginManager().getPlugin("PlayerPoints") != null)
			if (newString.contains("<playerpoints"))
				newString = PlayerPointsVariables.replaceVariables(newString, player);
		// PlayerPoints Support
		if (Bukkit.getServer().getPluginManager().getPlugin("pvpstats") != null)
			if (newString.contains("<pvpstats"))
				newString = PVPStatsVariables.replaceVariables(newString, player);
		// PlotMe Support
		if (Bukkit.getServer().getPluginManager().getPlugin("PlotMe") != null)
			if (newString.contains("<plotme"))
				newString = PlotMeVariables.replaceVariables(newString, player);
		// PVPArena Support
		if (Bukkit.getServer().getPluginManager().getPlugin("pvparena") != null)
			if (newString.contains("<pvparena"))
				newString = PVPArenaVariables.replaceVariables(newString, player);
		// PvpLevels Support
		if (Bukkit.getServer().getPluginManager().getPlugin("PvpLevels") != null)
			if (newString.contains("<pvplevels"))
				newString = PvpLevelsVariables.replaceVariables(newString, player);
		// PointsAPI Support
		if (Bukkit.getServer().getPluginManager().getPlugin("PointsAPI") != null)
			if (newString.contains("<pointsapi"))
				newString = PointsAPIVariables.replaceVariables(newString, player);
		// PrisonRankup Support
		if (Bukkit.getServer().getPluginManager().getPlugin("PrisonRankup") != null)
			if (newString.contains("<prisonrankup"))
				newString = PrisionRankupVariables.replaceVariables(newString, player);
		// Stats Support
		if (Bukkit.getServer().getPluginManager().getPlugin("Stats") != null)
			if (newString.contains("<stats"))
				newString = StatsVariables.replaceVariables(newString, player);
		
		// SimpleClans Support
		if (Bukkit.getServer().getPluginManager().getPlugin("SimpleClans") != null)
			if (newString.contains("<simpleclans"))
				newString = SimpleClansVariables.replaceVariables(newString, player);
		
		if (Bukkit.getServer().getPluginManager().getPlugin("Skillz") != null)
			if (newString.contains("<skillz"))
				newString = SkillzVariables.replaceVariables(newString, player);
		// WorldGuard Support
		if (Bukkit.getServer().getPluginManager().getPlugin("WorldGuard") != null)
			if (newString.contains("<worldguard"))
				newString = WorldGuardVariables.replaceVariables(newString, player);
		// VanishNoPacket Support
		if (Bukkit.getServer().getPluginManager().getPlugin("VanishNoPacket") != null)
			if (newString.contains("<vanish"))
				newString = VanishNoPacketVariables.replaceVariables(newString, player);
		// MiniGames Support
		if (Bukkit.getServer().getPluginManager().getPlugin("MiniGames") != null)
			if (newString.contains("<minigames"))
				newString = MiniGamesVariables.replaceVariables(newString, player);
		// Multiverse Support
		if (Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core") != null)
			if (newString.contains("<multiverse"))
				newString = MultiverseVariables.replaceVariables(newString, player);
		// OnTime Support
		if (Bukkit.getServer().getPluginManager().getPlugin("OnTime") != null)
			if (newString.contains("<ontime"))
				newString = OnTimeVariables.replaceVariables(newString, player);
		
		// UTF-8
		newString = ALTVariables.replaceVariables(newString);
		
		// Custom Variables
		for (String custom : Files.getConfig().getConfigurationSection("Custom Variables").getKeys(true))
			if (newString.contains(custom))
				newString = newString.replaceAll(custom, Messages.getLine(Files.getConfig().getString("Custom Variables." + custom), player));
		
		return newString;
	}
}
