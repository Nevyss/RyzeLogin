package br.rederyze.login;

import br.rederyze.login.utilidades.DataManager;
import br.rederyze.login.utilidades.ReflectionUtils;
import br.rederyze.login.utilidades.TitleAPI;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import br.rederyze.login.comandos.CommandChangePassword;
import br.rederyze.login.comandos.CommandLogin;
import br.rederyze.login.comandos.CommandRegister;
import br.rederyze.login.comandos.CommandUnregister;
import br.rederyze.login.entidades.Config;
import br.rederyze.login.playerconfig.PlayerData;
import br.rederyze.login.playerconfig.PlayerLogin;
import br.rederyze.login.playerconfig.PlayerPreLogin;

public class Main extends JavaPlugin {

	private static Main main;

	@Override
	public void onEnable() {
		carregarUtilidades();
		registrarComandos();
		registrarEventos();
		gerarConfigs();
		Bukkit.getConsoleSender().sendMessage(" §aLogin Ativado");
	}

	@Override
	public void onDisable() {
		HandlerList.unregisterAll(this);
	}
	
	private void carregarUtilidades() {
		main = this;
		ReflectionUtils.loadUtils();
		TitleAPI.loadAPI();
	}
	
	private void gerarConfigs() {
		DataManager.createFolder("playerdata");
		saveDefaultConfig();
		Config.loadConfig(this);
	}

	private void registrarComandos() {
		getCommand("login").setExecutor(new CommandLogin()); 
		getCommand("register").setExecutor(new CommandRegister()); 
		getCommand("unregister").setExecutor(new CommandUnregister()); 
		getCommand("changepassword").setExecutor(new CommandChangePassword()); 
	}

	private void registrarEventos() {
		PluginManager pm = Bukkit.getServer().getPluginManager();

		pm.registerEvents(new PlayerPreLogin(), this);
		pm.registerEvents(new PlayerLogin(), this);
		pm.registerEvents(new PlayerData(), this);
	}

	public static Main get() {
		return main;
	}

}
