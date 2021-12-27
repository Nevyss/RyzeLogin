package br.rederyze.login.task;

import br.rederyze.login.utilidades.TitleAPI;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import br.rederyze.login.Main;
import br.rederyze.login.entidades.Login;
import br.rederyze.login.playerconfig.PlayerLogin;

public class JoinTask extends Login {

	public static void goLogin(Player player) {
		changeStatusJoin(player);
		TitleAPI.sendTitle(player, 1, 80, 1, TitleLogar, SubtitleLogar);

		new BukkitRunnable() {
			@Override
			public void run() {
				if (PlayerLogin.LIST.containsKey(player)) {
					player.kickPlayer(ExcedeuLimiteLogar);
					errorLogin(player);
				}
			}
		}.runTaskLater(Main.get(), TempoParaLogin);
	}

	public static void goRegister(Player player) {
		changeStatusJoin(player);
		TitleAPI.sendTitle(player, 1, TempoParaLogin, 1, TitleRegistrar, SubtitleRegistrar);
		new BukkitRunnable() {
			@Override
			public void run() {
				if (PlayerLogin.LIST.containsKey(player)) {
					player.kickPlayer(ExcedeuLimiteRegistrar);
					errorLogin(player);
				}
			}
		}.runTaskLater(Main.get(), TempoParaLogin);
	}

}