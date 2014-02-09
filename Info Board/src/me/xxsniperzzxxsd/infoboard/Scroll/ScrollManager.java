
package me.xxsniperzzxxsd.infoboard.Scroll;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;


public class ScrollManager {

	private static HashMap<Player, ArrayList<Scroller>> scrollers = new HashMap<Player, ArrayList<Scroller>>();
	private static HashMap<Player, Scroller> title = new HashMap<Player, Scroller>();
	
	private ScrollText ScrollText= new ScrollText();

	public static Scroller createScroller(Player p, String message) {
		Scroller sc = new Scroller(p, message);
		ArrayList<Scroller> scs;
		if (scrollers.containsKey(p))
			scs = scrollers.get(p);
		else
			scs = new ArrayList<Scroller>();
		scs.add(sc);
		scrollers.put(p, scs);
		return sc;
	}

	public static Scroller createTitleScroller(Player p, String message) {

		Scroller sc = new Scroller(p, message);
		title.put(p, sc);

		return sc;
	}

	public static ArrayList<Scroller> getScrollers(Player p) {
		return scrollers.get(p);
	}

	public static Scroller getTitleScroller(Player p) {
		return title.get(p);
	}

	public static void reset(Player p) {
		if (getScrollers(p) != null)
			for (Scroller sc : getScrollers(p))
			{
				String lastString = sc.getLastMessage();
				p.getScoreboard().resetScores(Bukkit.getOfflinePlayer(lastString));
			}
		scrollers.remove(p);
	}

	/**
	 * @return the scrollText
	 */
	public ScrollText getScrollText() {
		return ScrollText;
	}

	/**
	 * @param scrollText the scrollText to set
	 */
	public void setScrollText(ScrollText scrollText) {
		ScrollText = scrollText;
	}
}
