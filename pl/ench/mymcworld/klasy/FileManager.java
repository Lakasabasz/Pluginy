package pl.ench.mymcworld.klasy;

import java.io.File;

import org.bukkit.configuration.file.YamlConfiguration;

public class FileManager {
	
	private static YamlConfiguration messages;

	public static void checkFiles(){
		if(!Main.getInst().getDataFolder().exists()){
			Main.getInst().getDataFolder().mkdir();
		}
		
		if(!new File(Main.getInst().getDataFolder(), "messages.yml").exists()){
			Main.getInst().saveResource("messages.yml", true);
		}
		
		messages = YamlConfiguration.loadConfiguration(new File(Main.getInst().getDataFolder(), "messages.yml"));
	}
	
	public static YamlConfiguration getMsg(){
		return messages;
	}
}
