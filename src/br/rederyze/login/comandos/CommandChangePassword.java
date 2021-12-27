package br.rederyze.login.comandos;

import br.rederyze.login.entidades.LPlayer;
import br.rederyze.login.entidades.Login;
import br.rederyze.login.utilidades.Encryption;
import br.rederyze.login.utilidades.TitleAPI;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandChangePassword extends Login implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender s, Command cmd, String lbl, String[] args) {
		
		if (!(s instanceof Player player)) {
			s.sendMessage("§cO console nao tem permissao para utilizar este comando.");
			return true;
		}

		if (BLOCK.contains(player)) {
			s.sendMessage("");
			s.sendMessage(" §eNos desculpe!");
			s.sendMessage("");
			s.sendMessage(" §7Não permitimos que você");
			s.sendMessage(" §7execute comandos enquanto");
			s.sendMessage(" §7estiver se conectando.");
			s.sendMessage("");
			player.playSound(player.getLocation(), Sound.VILLAGER_NO ,2,1);
			return true;
		}
		
		LPlayer lp = LPlayer.get(s.getName());

		if (args.length != 1) {
			s.sendMessage("");
			s.sendMessage(" §eComando inexistente!");
			s.sendMessage("");
			s.sendMessage(" §7Use §e/alterarsenha <nova-senha>");
			s.sendMessage(" §7para mudar sua senha.");
			s.sendMessage("");
			player.playSound(player.getLocation(), Sound.VILLAGER_NO ,2,1);
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
			player.playSound(player.getLocation(), Sound.VILLAGER_NO ,2,1);
			return true;
		}

		if (args[0].length() > 15) {
			s.sendMessage("");
			s.sendMessage(" §eSenha muito longa!");
			s.sendMessage("");
			s.sendMessage(" §7A sua senha deve conter");
			s.sendMessage(" §7no máximo §e15 caracteres§7.");
			s.sendMessage("");
			player.playSound(player.getLocation(), Sound.VILLAGER_NO ,2,1);
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
				s.sendMessage(" §ePor favor!");
				s.sendMessage("");
				s.sendMessage(" §7Não use caracteres como §e" + c);
				s.sendMessage(" §7Para o caso de você esquecer no futuro");
				s.sendMessage("");
				player.playSound(player.getLocation(), Sound.VILLAGER_NO ,2,1);
				return true;
			}
		}

		String pass = Encryption.encrypt(args[0]);
		if (pass.equals(lp.getSenha())) {
			s.sendMessage("");
			s.sendMessage(" §eOps!");
			s.sendMessage("");
			s.sendMessage(" §7Sua senha já é §e" + args[0] + ",");
			s.sendMessage(" §7Por favor use uma senha nova.");
			s.sendMessage("");
			player.playSound(player.getLocation(), Sound.VILLAGER_NO ,2,1);
			return true;
		}
		s.sendMessage("");
		s.sendMessage(" §eParabéns!");
		s.sendMessage("");
		s.sendMessage(" §7Sua senha foi redefinida com sucesso!");
		s.sendMessage(" §7Sua nova senha é §e" + args[0]);
		s.sendMessage("");
		lp.setSenha(pass);
		player.playSound(player.getLocation(), Sound.ORB_PICKUP ,2,1);
		TitleAPI.sendTitle(player, 1, 30,1,"§a§lSucesso","§7Senha redefinida");
		return true;
	}
}