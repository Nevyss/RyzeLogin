package br.rederyze.login.playerconfig;

import br.rederyze.login.entidades.LPlayer;
import br.rederyze.login.entidades.Login;
import br.rederyze.login.task.JoinTask;
import br.rederyze.login.utilidades.TitleAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class PlayerLogin extends Login implements Listener {


	@EventHandler(priority = EventPriority.MONITOR)
	public void onLogin(PlayerJoinEvent e) {
		Player p = e.getPlayer();


		LPlayer sp = LPlayer.get(p);
		e.setJoinMessage(null);
		
		if (sp.isRegistered())
			JoinTask.goLogin(p);
		else
			JoinTask.goRegister(p);

		Login.addConfirm(p);
		p.setFlying(false);


	}
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true) 
	public void onMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		Inventory inv = Bukkit.createInventory(p, 3*9, "§7Clique na Maçã!");
		inv.setItem(13, new ItemStack(Material.APPLE));
		inv.setItem(11, new ItemStack(Material.BARRIER));
		inv.setItem(15, new ItemStack(Material.BEDROCK));

		PlayerLogin pl = PlayerLogin.this;

		if (BLOCK.contains(e.getPlayer())) {
			if (!isSameBlock(e.getTo(), e.getFrom())) {
				e.getPlayer().teleport(e.getFrom());
				p.setFlying(false);
			}
		}
		if (!BLOCK.contains(e.getPlayer()) && Login.contentConfirm(p)){
			p.openInventory(inv);
			return;
		}
	}

	@EventHandler
	public void itClick(InventoryClickEvent e){
		if(!(e.getWhoClicked() instanceof Player p)) return;
		if(e.getInventory().getTitle().endsWith("§7Clique na Maçã!")){
			ItemStack item = e.getCurrentItem();
			if(item.equals(new ItemStack(Material.APPLE))){
				e.setCancelled(true);
				p.closeInventory();
				p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 1);
				Login.removeConfirm(p);
				TitleAPI.sendTitle(p, 1, 30, 1, TitleLogado, SubtitleLogado);
			}
			if(item.equals(new ItemStack(Material.BEDROCK))){
				p.kickPlayer("\n§cVoce errou o captcha!");
			}
		}
	}
	
	private boolean isSameBlock(Location one, Location two) {
		return 	one.getBlockX() == two.getBlockX() && 
				one.getBlockZ() == two.getBlockZ() && 
				one.getBlockY() == two.getBlockY() && 
				one.getWorld().equals(two.getWorld());
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onCommand(PlayerCommandPreprocessEvent e) {
		if (BLOCK.contains(e.getPlayer())) {
			String cmd = e.getMessage().toLowerCase();
			if (!cmd.startsWith("/login") && !cmd.startsWith("/register") && !cmd.startsWith("/logar") &&
					!cmd.startsWith("/cadastrar") && !cmd.startsWith("/registrar") && !cmd.startsWith("/conectar")
					&& !cmd.startsWith("/trocarsenha") && !cmd.startsWith("/alterarsenha")
					&& !cmd.startsWith("/mudarsenha") && !cmd.startsWith("/desregistrar")
					&& !cmd.startsWith("/excluirconta")) {
				e.getPlayer().sendMessage("");
				e.getPlayer().sendMessage("\n§cVocê precisa estar logado para poder executar comandos.");
				e.getPlayer().sendMessage("");
				e.setCancelled(true);
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onChat(AsyncPlayerChatEvent e) {
		if (BLOCK.contains(e.getPlayer())) {
			e.getPlayer().sendMessage("");
			e.getPlayer().sendMessage(" §cVocê precisa estar logado para poder usar o chat.");
			e.getPlayer().sendMessage("");
			e.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onQuit(PlayerQuitEvent e) {
		LIST.remove(e.getPlayer());
		BLOCK.remove(e.getPlayer());
		if (INVS.containsKey(e.getPlayer())) {
			INVS.get(e.getPlayer()).devolverItens();
		}
	}
	
	@EventHandler (priority = EventPriority.HIGHEST, ignoreCancelled = true) 
	public void onDamage(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			if (BLOCK.contains(e.getEntity())) {
				e.setDamage(0);
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler (priority = EventPriority.HIGHEST, ignoreCancelled = true) 
	public void onPickup(PlayerPickupItemEvent e) {
		if (BLOCK.contains(e.getPlayer())) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler (priority = EventPriority.HIGHEST, ignoreCancelled = true) 
	public void onPickup(PlayerInteractEvent e) {
		if (BLOCK.contains(e.getPlayer())) {
			e.setCancelled(true);
		}
	}
	
}