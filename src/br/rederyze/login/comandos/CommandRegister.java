package br.rederyze.login.comandos;

import br.rederyze.login.entidades.LPlayer;
import br.rederyze.login.entidades.Login;
import br.rederyze.login.utilidades.Encryption;
import br.rederyze.login.utilidades.TitleAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class CommandRegister extends Login implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender s, Command cmd, String lbl, String[] args) {
		
		if (!(s instanceof Player p)) {
			s.sendMessage("§cO console nao tem permissao para utilizar este comando.");
			return true;
		}
		
		LPlayer lp = LPlayer.get(s.getName());

		if (lp.isRegistered()) {
			s.sendMessage("");
			s.sendMessage(" §cVocê já está registrado no servidor.");
			s.sendMessage("");
			p.playSound(p.getLocation(), Sound.VILLAGER_NO ,2,1);
			return true;
		}

		if (args.length != 1) {
			s.sendMessage("");
			s.sendMessage(" §cComando Inexistente.");
			s.sendMessage("");
			s.sendMessage(" §7Use §e/registrar (senha)");
			s.sendMessage(" §7para que você faça seu");
			s.sendMessage(" §7seu Cadastro.");
			s.sendMessage("");
			p.playSound(p.getLocation(), Sound.VILLAGER_NO ,2,1);
			return true;
		}

		if (args[0].length() < 5) {
			s.sendMessage("");
			s.sendMessage(" §eNos Desculpe");
			s.sendMessage("");
			s.sendMessage(" §7Senha está muito curta!");
			s.sendMessage(" §7Sua senha deve conter no mínimo");
			s.sendMessage(" §e5 Caracteres§7.");
			s.sendMessage("");
			p.playSound(p.getLocation(), Sound.VILLAGER_NO ,2,1);
			return true;
		}

		if (args[0].length() > 15) {
			s.sendMessage("");
			s.sendMessage(" §eSenha muito longa!");
			s.sendMessage("");
			s.sendMessage(" §7A sua senha deve conter");
			s.sendMessage(" §7no máximo §e15 caracteres§7.");
			s.sendMessage("");
			p.playSound(p.getLocation(), Sound.VILLAGER_NO ,2,1);
			return true;
		}

		for (int i = 0; i < args[0].length(); i++) {
			char c = args[0].charAt(i);
			if (c == 'a' || c == 'A' || c == 'b' || c == 'B' || c == 'c' || c == 'C' || c == 'd' || c == 'D' || c == 'e'
					|| c == 'E' || c == 'f' || c == 'F' || c == 'g' || c == 'G' || c == 'h' || c == 'H' || c == 'i'
					|| c == 'I' || c == 'j' || c == 'J' || c == 'k' || c == 'K' || c == 'l' || c == 'L' || c == 'm'
					|| c == 'M' || c == 'n' || c == 'N' || c == 'o' || c == 'O' || c == 'p' || c == 'P' || c == 'q'
					|| c == 'Q' || c == 'r' || c == 'R' || c == 's' || c == 'S' || c == 't' || c == 'T' || c == 'u'
					|| c == 'U' || c == 'v' || c == 'V' || c == 'w' || c == 'w' || c == 'x' || c == 'X' || c == 'y'
					|| c == 'W' || c == 'z' || c == 'Z' || c == '0' || c == '1' || c == '2' || c == '3' || c == '4'
					|| c == '5' || c == '6' || c == '7' || c == '8' || c == '9' || c == '!' || c == '@' || c == '$'
					|| c == '%' || c == '&' || c == '*' || c == '+' || c == '-' || c == '_' || c == '=' || c == '?'
					|| c == '>' || c == '<' || c == '/' || c == '.' || c == ',' || c == '(' || c == ')') {
				continue;
			} else {
				s.sendMessage("");
				s.sendMessage("§cO caractere '" + c + "' não pode ser usado na senha!");
				s.sendMessage("");
				p.playSound(p.getLocation(), Sound.VILLAGER_NO ,2,1);
				return true;
			}
		}

		Inventory inv = Bukkit.createInventory(p, 3*9, "§7Clique na Maçã!");
		inv.setItem(13, new ItemStack(Material.APPLE));
		inv.setItem(11, new ItemStack(Material.BARRIER));
		inv.setItem(15, new ItemStack(Material.BEDROCK));
		p.openInventory(inv);

		if (!Login.contentConfirm(p)){
			p.openInventory(inv);
			return true;
		}

		s.sendMessage(MensagemRegistrado);
		String pass = Encryption.encrypt(args[0]);
		lp.setSenha(pass);
		lp.setRegistered(true);
		TitleAPI.sendTitle((Player) s, 1, 30, 1, TitleRegistrado, SubtitleRegistrado);
		p.playSound(p.getLocation(), Sound.ORB_PICKUP ,2,1);
		Login.contentConfirm(p);
		successRegister((Player) s);

		return true;
	}
}