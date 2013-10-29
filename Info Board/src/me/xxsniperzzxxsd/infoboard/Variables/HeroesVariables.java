
package me.xxsniperzzxxsd.infoboard.Variables;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.herocraftonline.heroes.Heroes;
import com.herocraftonline.heroes.characters.CharacterManager;
import com.herocraftonline.heroes.characters.Hero;
import com.herocraftonline.heroes.characters.classes.HeroClass;
import com.herocraftonline.heroes.characters.party.HeroParty;


public class HeroesVariables {

	public static String replaceVariables(String string, Player player) {
		String newString = string;


		Heroes heroes = (Heroes) Bukkit.getPluginManager().getPlugin("Heroes");
		CharacterManager cm = heroes.getCharacterManager();
		Hero hero = cm.getHero(player);
		HeroParty hp = hero.getParty();
		HeroClass hc = hero.getHeroClass();

		if (newString.contains("<heroesmana>"))
			newString = newString.replaceAll("<heroesmana>", String.valueOf(hero.getMana()));
		if (newString.contains("<heroeslevel>"))
			newString = newString.replaceAll("<heroeslevel>", String.valueOf(hero.getLevel()));
		if (newString.contains("<heroesmaxmana>"))
			newString = newString.replaceAll("<heroesmaxmana>", String.valueOf(hero.getMaxMana()));
		if (newString.contains("<heroesmanaregen>"))
			newString = newString.replaceAll("<heroesmanaregen>", String.valueOf(hero.getManaRegen()));
		if (newString.contains("<heroesismaster>"))
			newString = newString.replaceAll("<heroesismaster>", String.valueOf(hero.isMaster(hc)));
		
		if (newString.contains("<heroespartyleader>"))
			newString = newString.replaceAll("<heroespartyleader>", String.valueOf(hp.getLeader()));
		if (newString.contains("<heroespartysize>"))
			newString = newString.replaceAll("<heroespartysize>", String.valueOf(hp.getMembers().size()));
		if (newString.contains("<heroespartyisnopvp>"))
			newString = newString.replaceAll("<heroespartyisnopvp>", String.valueOf(hp.isNoPvp()));
		
		if (newString.contains("<heroesclass>"))
			newString = newString.replaceAll("<heroesclass>", String.valueOf(hc.getName()));
		if (newString.contains("<heroesclasstier>"))
			newString = newString.replaceAll("<heroesclasstier>", String.valueOf(hc.getTier()));
		if (newString.contains("<heroesclassbasehealth>"))
			newString = newString.replaceAll("<heroesclassbasehealth>", String.valueOf(hc.getBaseMaxHealth()));
		if (newString.contains("<heroesclassbasemana>"))
			newString = newString.replaceAll("<heroesclassbasemana>", String.valueOf(hc.getBaseMaxMana()));
		if (newString.contains("<heroesclassexpchange>"))
			newString = newString.replaceAll("<heroesclassexpchange>", String.valueOf(hc.getExpModifier()));
		if (newString.contains("<heroesclassexploss>"))
			newString = newString.replaceAll("<heroesclassexploss>", String.valueOf(hc.getExpLoss()));
		if (newString.contains("<heroesclassmaxlevel>"))
			newString = newString.replaceAll("<heroesclassmaxlevel>", String.valueOf(hc.getMaxLevel()));
		
		
		return newString;
	}
}
