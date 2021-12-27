package br.rederyze.login.comandos;

import br.rederyze.login.entidades.LPlayer;
import br.rederyze.login.entidades.Login;
import br.rederyze.login.utilidades.Encryption;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class CommandLogin extends Login implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender s, Command cmd, String lbl, String[] args) {
		
		if (!(s instanceof Player player)) {
			s.sendMessage("§cO console nao tem permissao para utilizar este comando.");
			return true;
		}
		
		LPlayer lp = LPlayer.get(s.getName());

		if (!lp.isRegistered()) {
			s.sendMessage("");
			s.sendMessage(" §cVocê ainda não está registrado no servidor.");
			s.sendMessage("");
			s.sendMessage(" §7Use §e/registrar (senha)");
			s.sendMessage(" §7para que você faça seu");
			s.sendMessage(" §7seu cadastro.");
			s.sendMessage("");
			player.playSound(player.getLocation(), Sound.VILLAGER_NO ,2,1);
			return true;
		}

		if (!BLOCK.contains(player)) {
			s.sendMessage("");
			s.sendMessage(" §cVocê já está logado no servidor.");
			s.sendMessage("");
			player.playSound(player.getLocation(), Sound.VILLAGER_NO ,2,1);
			return true;
		}

		if (args.length != 1) {
			s.sendMessage("");
			s.sendMessage(" §cComando Inexistente.");
			s.sendMessage("");
			s.sendMessage(" §7Use §e/logar (senha)");
			s.sendMessage(" §7para que você faça seu");
			s.sendMessage(" §7sua Conexão.");
			s.sendMessage("");
			player.playSound(player.getLocation(), Sound.VILLAGER_NO ,2,1);
			return true;
		}

		String bancosenha = lp.getSenha();
		String senhadigitada = Encryption.encrypt(args[0]);

		if (!bancosenha.equals(senhadigitada)) {
			int tentativas = LIST.get(player);

			if (tentativas > 0) {
				s.sendMessage("");
				s.sendMessage(" §cSenha incorreta! Você possui mais " + tentativas + " tentantivas.");
				s.sendMessage("");
				player.playSound(player.getLocation(), Sound.VILLAGER_NO ,2,1);
				LIST.replace(player, tentativas - 1);
				return true;
			} else {
				player.kickPlayer("\n§cSessão expirada, tente novamente.");
				errorLogin(player);
				return true;
			}
		}

		Inventory inv = Bukkit.createInventory(player, 3*9, "§7Clique na Maçã!");
		inv.setItem(13, new ItemStack(Material.APPLE));
		inv.setItem(11, new ItemStack(Material.BARRIER));
		inv.setItem(15, new ItemStack(Material.BEDROCK));
		player.openInventory(inv);

		if (!Login.contentConfirm(player)){
			player.openInventory(inv);
			return true;
		}

		player.playSound(player.getLocation(), Sound.ORB_PICKUP ,2,1);
		successLogin(player);
		Login.contentConfirm(player);

		return true;
	}
}