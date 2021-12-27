package br.rederyze.login.eventos;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerAuthRegisterEvent extends Event {
	
    private static final HandlerList handlers = new HandlerList();
    @Override public HandlerList getHandlers() { return handlers; }
    public static HandlerList getHandlerList() { return handlers; }
    
    private Player player;

    public PlayerAuthRegisterEvent(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
    	return player;
    }
}