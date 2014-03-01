
package me.xxsniperzzxxsd.infoboard.Variables;

import me.xxsniperzzxxsd.infoboard.InfoBoard;

import org.bukkit.entity.Player;


public class VaultVariables {

	public static String replaceVariables(String string, Player player) {
		String newString = string;
		if (InfoBoard.economy != null && InfoBoard.economyB)
		{
			if (newString.contains("<money>"))
				newString = newString.replaceAll("<money>", String.valueOf((int) InfoBoard.economy.getBalance(player.getName())));

			if (newString.contains("<vaultcurrencyplural>"))
				newString = newString.replaceAll("<vaultcurrencyplural>", String.valueOf(InfoBoard.economy.currencyNamePlural()));

			if (newString.contains("<vaultcurrencysingle>"))
				newString = newString.replaceAll("<vaultcurrencysingle>", String.valueOf(InfoBoard.economy.currencyNameSingular()));
		}

		if (InfoBoard.permission != null)
		{
			if (newString.contains("<rank>"))
				newString = newString.replaceAll("<rank>", String.valueOf(InfoBoard.permission.getPlayerGroups(player.getWorld(), player.getName())[0]));

		}
		return newString;
	}
}
