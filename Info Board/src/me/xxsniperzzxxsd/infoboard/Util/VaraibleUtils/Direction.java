package me.xxsniperzzxxsd.infoboard.Util.VaraibleUtils;

import org.bukkit.entity.Player;


public class Direction {
	
	public static String getCardinalDirection(Player player) {
        double rotation = player.getLocation().getYaw() + 360.0;
       
        System.out.println(rotation);
         if (0 <= rotation && rotation < 22.5) {
            return "South";
        } else if (22.5 <= rotation && rotation < 67.5) {
            return "SouthWest";
        } else if (67.5 <= rotation && rotation < 112.5) {
            return "West";
        } else if (112.5 <= rotation && rotation < 157.5) {
            return "NorthWest";
        } else if (157.5 <= rotation && rotation < 202.5) {
            return "North";
        } else if (202.5 <= rotation && rotation < 247.5) {
            return "NorthEast";
        } else if (247.5 <= rotation && rotation < 292.5) {
            return "East";
        } else if (292.5 <= rotation && rotation < 337.5) {
            return "SouthEast";
        } else if (337.5 <= rotation && rotation < 360.0) {
            return "South";
        } else {
            return null;
        }
    }
}
