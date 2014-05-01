
package me.xxsniperzzxxsd.infoboard.Util;

public class Settings {
	
	public static boolean isPageValid(int page, String worldName, String rankName) {
		return Files.getConfig().getString("Info Board." + String.valueOf(page) + "." + worldName + "." + rankName + ".Title") != null;
	}
	
	public static boolean isWorldDisabled(String world) {
		return Files.getConfig().getStringList("Disabled Worlds").contains(world) || (world == null);
	}
}
