package br.rederyze.login.entidades;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import br.rederyze.login.eventos.PlayerAuthLoginEvent;
import br.rederyze.login.eventos.PlayerAuthRegisterEvent;
import br.rederyze.login.eventos.PlayerKickLoginEvent;

public class Login extends Config {

	protected static HashMap<Player, Integer> LIST = new HashMap<>();
	protected static HashSet<Player> BLOCK = new HashSet<>();
	protected static HashMap<Player, ToggledPlayerInventory> INVS = new HashMap<>();
	private static List<Player> confirm = new ArrayList<>();

	public static boolean contentConfirm(Player p){
		return confirm.contains(p);
	}

	public static boolean removeConfirm(Player p){
		return confirm.remove(p);
	}

	public static boolean addConfirm(Player p){
		return confirm.add(p);
	}
	
	protected void successLogin(Player player) {
		changeStatusLeft(player);
		PlayerAuthLoginEvent event = new PlayerAuthLoginEvent(player);
		Bukkit.getPluginManager().callEvent(event);
	}
	
	protected void successRegister(Player player) {
		changeStatusLeft(player);
		PlayerAuthRegisterEvent event = new PlayerAuthRegisterEvent(player);
		Bukkit.getPluginManager().callEvent(event);
	}
	
	protected static void errorLogin(Player player) {
		PlayerKickLoginEvent event = new PlayerKickLoginEvent(player);
		Bukkit.getPluginManager().callEvent(event);
	}
	
	protected static void changeStatusJoin(Player player) {
		ToggledPlayerInventory inv = new ToggledPlayerInventory(player);
		INVS.put(player, inv);
		LIST.put(player, TentativasParaLogin);
		BLOCK.add(player);
		player.setWalkSpeed(0.0f);
		player.setAllowFlight(true);
		player.setFlying(true);
		player.setCanPickupItems(false);
		player.setGameMode(GameMode.ADVENTURE);
	}
	
	protected static void changeStatusLeft(Player player) {
		INVS.get(player).devolverItens();
		LIST.remove(player);
		INVS.remove(player);
		BLOCK.remove(player);
		player.setWalkSpeed(0.2f);
		player.setFlying(false);
		player.setAllowFlight(false);
		player.setCanPickupItems(true);
		player.setGameMode(GameMode.ADVENTURE);
	}

}