package br.rederyze.login.entidades;

import java.io.File;
import java.io.IOException;

import br.rederyze.login.utilidades.DataManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class LPlayer {

	private String pass;
	private String nick;
	private boolean isRegistered;

	public static LPlayer get(Player player) {
		return new LPlayer(player.getName().toLowerCase());
	}

	public static LPlayer get(String player) {
		return new LPlayer(player.toLowerCase());
	}
	
	private LPlayer() {}

	private LPlayer(String player) {
		FileConfiguration lpConfig = getlpConfig(player);
		this.pass = lpConfig.getString("Senha");
		this.nick = lpConfig.getString("Nick");
		this.isRegistered = lpConfig.getBoolean("Registrado");
	}

	public String getSenha() {
		return pass;
	}
	
	public void setSenha(String pass) {
		saveLpValue("Senha", pass);
		this.pass = pass;
	}
		
	public boolean isRegistered() {
		return isRegistered;
	}
	
	public void setRegistered(boolean registered) {
		saveLpValue("Registrado", registered);
		this.isRegistered = registered;
	}
	
	private void saveLpValue(String key, Object value) {
		String player = nick.toLowerCase();
		File lpFile = DataManager.getFile(player, "playerdata");
		FileConfiguration lpConfig = DataManager.getConfig(lpFile);
		lpConfig.set(key, value);
		try {
			lpConfig.save(lpFile);
		} catch (IOException e) {
			Bukkit.getConsoleSender().sendMessage("");
			Bukkit.getConsoleSender().sendMessage("§c[Login] Nao foi possivel salvar o Valor !" + value + " na Key " + key + " no arquivo" + lpFile.getName() + "!");
			Bukkit.getConsoleSender().sendMessage("");
			e.printStackTrace();
		}
	}

	private FileConfiguration getlpConfig(String player) {
		return DataManager.getConfig(DataManager.getFile(player, "playerdata"));
	}

}
