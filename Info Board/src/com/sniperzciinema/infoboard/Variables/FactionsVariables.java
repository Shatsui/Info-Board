
package com.sniperzciinema.infoboard.Variables;

import org.bukkit.entity.Player;

import com.massivecraft.factions.Rel;
import com.massivecraft.factions.entity.BoardColls;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.UPlayer;
import com.massivecraft.massivecore.ps.PS;


public class FactionsVariables {
	
	public static String replaceVariables(String string, Player player) {
		String newString = string;
		
		UPlayer uplayer = UPlayer.get(player);
		
		if (newString.contains("<factionsf") && !newString.contains("<factionsfin"))
		{
			Faction faction = uplayer.getFaction();
			
			if (newString.contains("<factionsfname>"))
				newString = newString.replaceAll("<factionsfname>", faction.getName());
			if (newString.contains("<factionsfland>"))
				newString = newString.replaceAll("<factionsfland>", String.valueOf(faction.getLandCount()));
			if (newString.contains("<factionsfpower>"))
				newString = newString.replaceAll("<factionsfpower>", String.valueOf(faction.getPowerRounded()));
			if (newString.contains("<factionsfmaxpower>"))
				newString = newString.replaceAll("<factionsfmaxpower>", String.valueOf(faction.getPowerMaxRounded()));
			if (newString.contains("<factionsfonline>"))
				newString = newString.replaceAll("<factionsfonline>", String.valueOf(faction.getOnlinePlayers().size()));
			if (newString.contains("<factionsfleader>"))
				newString = newString.replaceAll("<factionsfleader>", String.valueOf(faction.getLeader() != null ? faction.getLeader().getName() : "Unknown"));
			if (newString.contains("<factionsfofficers>"))
				newString = newString.replaceAll("<factionsfofficers>", String.valueOf(faction.getUPlayersWhereRole(Rel.OFFICER).size()));
		}
		if (newString.contains("<factionspower>"))
			newString = newString.replaceAll("<factionspower>", String.valueOf(uplayer.getPowerRounded()));
		if (newString.contains("<factionsmaxpower>"))
			newString = newString.replaceAll("<factionsmaxpower>", String.valueOf(uplayer.getPowerMaxRounded()));
		if (newString.contains("<factionsrole>"))
			newString = newString.replaceAll("<factionsrole>", String.valueOf(uplayer.getRole()));
		
		if (newString.contains("<factionsfin"))
		{
			Faction inFaction = BoardColls.get().getFactionAt(PS.valueOf(uplayer.getPlayer().getLocation().getChunk()));
			
			if (newString.contains("<factionsfinname>"))
				newString = newString.replaceAll("<factionsfinname>", String.valueOf(inFaction.getName()));
			if (newString.contains("<factionsfinland>"))
				newString = newString.replaceAll("<factionsfland>", String.valueOf(inFaction.getLandCount()));
			if (newString.contains("<factionsfinpower>"))
				newString = newString.replaceAll("<factionsfinpower>", String.valueOf(inFaction.getPowerRounded()));
			if (newString.contains("<factionsfinmaxpower>"))
				newString = newString.replaceAll("<factionsfinmaxpower>", String.valueOf(inFaction.getPowerMaxRounded()));
			if (newString.contains("<factionsfinonline>"))
				newString = newString.replaceAll("<factionsfinonline>", String.valueOf(inFaction.getOnlinePlayers().size()));
			if (newString.contains("<factionsfinleader>"))
				newString = newString.replaceAll("<factionsfinleader>", String.valueOf(inFaction.getLeader() != null ? inFaction.getLeader().getName() : "Unknown"));
			if (newString.contains("<factionsfinland>"))
				newString = newString.replaceAll("<factionsfinland>", String.valueOf(inFaction.getLandCount()));
			if (newString.contains("<factionsfinofficer>"))
				newString = newString.replaceAll("<factionsfinofficers>", String.valueOf(inFaction.getUPlayersWhereRole(Rel.OFFICER).size()));
		}
		return newString;
	}
}
