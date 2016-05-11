package pl.ench.mymcworld.klasy;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{
	
	private static Main instance;
	public static List<KlasyData> klasy;
	public static List<PlayersData> players;
	
	public Main(){
		instance = this;
	}

	public void onEnable(){		
		Bukkit.getPluginManager().registerEvents(new ListenerOnBlockBreak(), this);
		Bukkit.getPluginManager().registerEvents(new ListenerOnJoin(), this);
		Bukkit.getPluginManager().registerEvents(new ListenerOnBlockPlace(), this);
		Bukkit.getPluginManager().registerEvents(new ListenerPlayerInteract(), this);
		
		saveDefaultConfig();
		
		FileManager.checkFiles();
		
		if(!ConfigManager.configReload()){
			System.out.println(Utils.getMessage("prefix") + " Nie mo�na za�adowa� configu");
		}
		
		getCommand("klasy").setExecutor(new Cmdklasy());
		getCommand("drop").setExecutor(new Cmddrop());
	}
	
	public static Main getInst(){
		return instance;
	}

}