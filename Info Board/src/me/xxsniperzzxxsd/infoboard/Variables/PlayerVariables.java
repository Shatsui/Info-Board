
package me.xxsniperzzxxsd.infoboard.Variables;

import me.xxsniperzzxxsd.infoboard.Util.Files;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;


public class PlayerVariables {

	public static String replaceVariables(String string, Player player) {

		String newString = string;

		// Player Variables
		if (newString.contains("<ping>") && (Bukkit.getBukkitVersion().split("-")[0]).equalsIgnoreCase("1.6.4"))
			newString = newString.replaceAll("<ping>", String.valueOf(Bukkit.getOnlinePlayers().length));
		if (newString.contains("<onlineplayers>"))
			newString = newString.replaceAll("<onlineplayers>", String.valueOf(Bukkit.getOnlinePlayers().length));
		if (newString.contains("<player"))
			newString = newString.replaceAll("<player>", player.getName());
		if (newString.contains("<exp>"))
			newString = newString.replaceAll("<exp>", String.valueOf((int) ((double) Math.round(player.getExp() * 100.0D))));
		if (newString.contains("<level"))
			newString = newString.replaceAll("<level>", String.valueOf((int) player.getLevel()));
		if (newString.contains("<hunger>"))
			newString = newString.replaceAll("<hunger>", String.valueOf(player.getFoodLevel()));
		if (newString.contains("<health>"))
			newString = newString.replaceAll("<health>", String.valueOf(player.getHealth()));
		if (newString.contains("<maxhealth>"))
			newString = newString.replaceAll("<maxhealth>", String.valueOf(player.getMaxHealth()));
		if (newString.contains("<kills>"))
			newString = newString.replaceAll("<kills>", String.valueOf(Files.getPlayers().getInt(player.getName() + ".Kills")));
		if (newString.contains("<deaths>"))
			newString = newString.replaceAll("<deaths>", String.valueOf(Files.getPlayers().getInt(player.getName() + ".Deaths")));
		if (newString.contains("<world>"))
			newString = newString.replaceAll("<world>", player.getWorld().getName());
		if (newString.contains("<x>"))
			newString = newString.replaceAll("<x>", String.valueOf((int) player.getLocation().getX()));
		if (newString.contains("<y>"))
			newString = newString.replaceAll("<y>", String.valueOf((int) player.getLocation().getY()));
		if (newString.contains("<z>"))
			newString = newString.replaceAll("<z>", String.valueOf((int) player.getLocation().getZ()));
		if (newString.contains("<lifetime>"))
			newString = newString.replaceAll("<lifetime>", String.valueOf((int) player.getTicksLived() / 20));
		if (newString.contains("<time>"))
			newString = newString.replaceAll("<time>", String.valueOf(player.getWorld().getTime()));
		if (newString.contains("<helmet>"))
			newString = newString.replaceAll("<helmet>", String.valueOf((player.getInventory().getHelmet() == null ? "None" : player.getInventory().getHelmet().getType().name())+ ""));	
		if (newString.contains("<chestplate>"))
			newString = newString.replaceAll("<chestplate>", String.valueOf((player.getInventory().getChestplate() == null ? "None" : player.getInventory().getChestplate().getType().name())+ ""));	
		if (newString.contains("<leggings>"))
			newString = newString.replaceAll("<leggings>", String.valueOf((player.getInventory().getLeggings() == null ? "None" : player.getInventory().getLeggings().getType().name())+ ""));	
		if (newString.contains("<boots>"))
			newString = newString.replaceAll("<boots>", String.valueOf((player.getInventory().getBoots() == null ? "None" : player.getInventory().getBoots().getType().name())+ ""));	
		if (newString.contains("<hand>"))
			newString = newString.replaceAll("<hand>", String.valueOf((player.getInventory().getItemInHand() == null ? "None" : player.getInventory().getItemInHand().getType().name())+ ""));	
		if (newString.contains("<doihave"))
		{
			String perm = newString.split("<doihave")[1].split(">")[0];

			newString = newString.replaceAll("<doihave" + (perm) + ">", String.valueOf(player.hasPermission(perm)));
		}
		return newString;
	}
	
}
