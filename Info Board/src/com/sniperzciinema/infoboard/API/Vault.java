
package com.sniperzciinema.infoboard.API;

import org.bukkit.Bukkit;


public class Vault {
	
	private static boolean hasVaultOnServer() {
		return Bukkit.getPluginManager().getPlugin("Vault") != null;
	}
}
