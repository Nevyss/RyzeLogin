package br.rederyze.login.utilidades;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author Mior
 * @version 1.0
 * @category utils
 */

public class TitleAPI extends JavaPlugin implements Listener {
	
	private static Method a;
	private static Object enumTIMES;
	private static Object enumTITLE;
	private static Object enumSUBTITLE;
	private static Constructor<?> timeTitleConstructor;
	private static Constructor<?> textTitleConstructor;
	
	public static void sendTitle(Player player, Integer fadeIn, Integer stay, Integer fadeOut, String title, String subtitle) {
		try 
		{
			Object chatTitle = a.invoke(null, "{\"text\":\"" + title + "\"}");
			Object chatSubtitle = a.invoke(null,"{\"text\":\"" + subtitle + "\"}");
			
			Object timeTitlePacket = timeTitleConstructor.newInstance(enumTIMES, null, fadeIn, stay, fadeOut);
			ReflectionUtils.sendPacket(player, timeTitlePacket);

			Object titlePacket = textTitleConstructor.newInstance(enumTITLE, chatTitle);
			ReflectionUtils.sendPacket(player, titlePacket);

			Object subtitlePacket = textTitleConstructor.newInstance(enumSUBTITLE, chatSubtitle);
			ReflectionUtils.sendPacket(player, subtitlePacket);
		} 
		catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	public static void loadAPI() {
		try 
		{
			Class<?> icbc = ReflectionUtils.getNMSClass("IChatBaseComponent");
			Class<?> ppot = ReflectionUtils.getNMSClass("PacketPlayOutTitle");
			Class<?> enumClass;

			if (ppot.getDeclaredClasses().length > 0) {
				enumClass = ppot.getDeclaredClasses()[0];
			} else {
				enumClass = ReflectionUtils.getNMSClass("EnumTitleAction");
			}
			
			if (icbc.getDeclaredClasses().length > 0) {
				a = icbc.getDeclaredClasses()[0].getMethod("a", String.class);
			} else {
				a = ReflectionUtils.getNMSClass("ChatSerializer").getMethod("a", String.class);
			}
			
			enumTIMES = enumClass.getField("TIMES").get(null);
			enumTITLE = enumClass.getField("TITLE").get(null);
			enumSUBTITLE = enumClass.getField("SUBTITLE").get(null);
			timeTitleConstructor = ppot.getConstructor(enumClass, icbc, int.class, int.class, int.class);
			textTitleConstructor = ppot.getConstructor(enumClass, icbc);
		}
		catch (Throwable e) {
			e.printStackTrace();
		}
	}
}