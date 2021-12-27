package br.rederyze.login.comandos;

import java.io.File;

import br.rederyze.login.entidades.Login;
import br.rederyze.login.utilidades.DataManager;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandUnregister extends Login implements CommandExecutor {

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender s, Command cmd, String lbl, String[] args) {
		Player player = (Player) s;

		if (s instanceof Player ) {
			if (BLOCK.contains(player)) {
				s.sendMessage("§cVocê precisa estar logado para poder executar comandos.");
				return true;
			}
		}
		
		if (args.length != 1) {
			s.sendMessage("");
			s.sendMessage(" §cComando Inexistente.");
			s.sendMessage("");
			s.sendMessage(" §7Use §e/excluirconta (nick)");
			s.sendMessage(" §7para que você exclua");
			s.sendMessage(" §7uma conta do Banco de Dados.");
			s.sendMessage("");
			player.playSound(player.getLocation(), Sound.VILLAGER_NO ,2,1);
			return true;
		}

		File file = DataManager.getFile(args[0].toLowerCase(), "playerdata");

		if (!file.exists()) {
			s.sendMessage("");
			s.sendMessage(" §eO player" + args[0]);
			s.sendMessage("");
			s.sendMessage(" §7Não pode ser encontrado ");
			s.sendMessage(" §7em nosso Banco de Dados");
			s.sendMessage("");
			player.playSound(player.getLocation(), Sound.VILLAGER_NO ,2,1);
			return true;
		}

		file.delete();
		s.sendMessage("");
		s.sendMessage(" §eConta excluida com sucesso!");
		s.sendMessage("");
		s.sendMessage(" §7Nick: §e" + args[0]);
		s.sendMessage("");
		player.playSound(player.getLocation(), Sound.ORB_PICKUP ,2,1);

		Player p = Bukkit.getPlayer(args[0]);
		if (p != null) {
			p.kickPlayer("\n§cSua conta foi excluida do nosso Banco de dados\n§cPor favor, faca um novo cadastro.");
		}

		return true;
	}
}
