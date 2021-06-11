package net.astrocube.tnt.event;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

import javax.annotation.Nullable;

public class PlayerDisqualificationEvent extends PlayerEvent {

	private static final HandlerList HANDLER_LIST = new HandlerList();
	private final String match;

	public PlayerDisqualificationEvent(Player player, String match) {
		super(player);
		this.match = match;
	}

	public static HandlerList getHandlerList() {
		return HANDLER_LIST;
	}

	@Override
	public HandlerList getHandlers() {
		return HANDLER_LIST;
	}

	public String getMatch() {
		return match;
	}
}
