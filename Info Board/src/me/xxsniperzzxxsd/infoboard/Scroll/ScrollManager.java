
package me.xxsniperzzxxsd.infoboard.Scroll;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;


public class ScrollManager {

	private static HashMap<Player, ArrayList<Scroller>>	scrollers	= new HashMap<Player, ArrayList<Scroller>>();
	private static HashMap<Player, Scroller>			title		= new HashMap<Player, Scroller>();

	public static Scroller createScroller(Player p, String message) {
		Scroller sc = new Scroller(p, message);
		ArrayList<Scroller> scs;
		if (ScrollManager.scrollers.containsKey(p))
			scs = ScrollManager.scrollers.get(p);
		else
			scs = new ArrayList<Scroller>();
		scs.add(sc);
		ScrollManager.scrollers.put(p, scs);
		return sc;
	}

	public static Scroller createTitleScroller(Player p, String message) {

		Scroller sc = new Scroller(p, message);
		ScrollManager.title.put(p, sc);

		return sc;
	}

	public static ArrayList<Scroller> getScrollers(Player p) {
		return ScrollManager.scrollers.get(p);
	}

	public static Scroller getTitleScroller(Player p) {
		return ScrollManager.title.get(p);
	}

	public static void reset(Player p) {
		if (getScrollers(p) != null)
			for (Scroller sc : getScrollers(p))
			{
				String lastString = sc.getLastMessage();
				p.getScoreboard().resetScores(Bukkit.getOfflinePlayer(lastString));
			}
		ScrollManager.scrollers.remove(p);
		ScrollManager.title.remove(p);
	}

	private ScrollText	ScrollText	= new ScrollText();

	/**
	 * @return the scrollText
	 */
	public ScrollText getScrollText() {
		return this.ScrollText;
	}

	/**
	 * @param scrollText
	 *            the scrollText to set
	 */
	public void setScrollText(ScrollText scrollText) {
		this.ScrollText = scrollText;
	}
}
