
package me.xxsniperzzxxsd.infoboard.Variables;

import me.xxsniperzzxxsd.infoboard.Main;

import org.bukkit.entity.Player;


public class VaultVariables {

	public static String replaceVariables(String string, Player player) {
		String newString = string;
		if (Main.economy != null)
		{
			if (newString.contains("<money>"))
				newString = newString.replaceAll("<money>", String.valueOf((int) Main.economy.getBalance(player.getName())));

			if (newString.contains("<vaultcurrencyplural>"))
				newString = newString.replaceAll("<vaultcurrencyplural>", String.valueOf(Main.economy.currencyNamePlural()));

			if (newString.contains("<vaultcurrencysingle>"))
				newString = newString.replaceAll("<vaultcurrencysingle>", String.valueOf(Main.economy.currencyNameSingular()));
		}

		if (Main.permission != null)
		{
			if (newString.contains("<rank>"))
				newString = newString.replaceAll("<rank>", String.valueOf(Main.permission.getPlayerGroups(player.getWorld(), player.getName())[0]));

		}
		return newString;
	}
}
