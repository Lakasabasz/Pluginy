package pl.ench.mymcworld.klasy;

import java.io.File;

import org.bukkit.configuration.file.YamlConfiguration;

public class FileManager {
	
	private static YamlConfiguration messages;
	private static YamlConfiguration players;

	public static void checkFiles(){
		if(!Main.getInst().getDataFolder().exists()){
			Main.getInst().getDataFolder().mkdir();
		}
		
		if(!new File(Main.getInst().getDataFolder(), "messages.yml").exists()){
			Main.getInst().saveResource("messages.yml", true);
		}
		
		if(!new File(Main.getInst().getDataFolder(), "players.yml").exists()){
			Main.getInst().saveResource("players.yml", true);
		}
		
		messages = YamlConfiguration.loadConfiguration(new File(Main.getInst().getDataFolder(), "messages.yml"));
	}
	
	public static YamlConfiguration getMsg(){
		return messages;
	}
	
	public static YamlConfiguration getPlayers(){
		return players;
	}
	
	public static boolean reloadFiles(){
		YamlConfiguration m = YamlConfiguration.loadConfiguration(new File(Main.getInst().getDataFolder(), "messages.yml"));
		if(m == null) return false;
		messages = m;
		YamlConfiguration p = YamlConfiguration.loadConfiguration(new File(Main.getInst().getDataFolder(), "players.yml"));
		if(p == null) return false;
		players = p;
		return true;
	}
}
