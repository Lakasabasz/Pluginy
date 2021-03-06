package pl.ench.mymcworld.klasy;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import pl.ench.mymcworld.klasy.listeners.ListenerOnBlockBreak;
import pl.ench.mymcworld.klasy.listeners.ListenerOnBlockPlace;
import pl.ench.mymcworld.klasy.listeners.ListenerOnJoin;
import pl.ench.mymcworld.klasy.listeners.ListenerPlayerInteract;
import pl.ench.mymcworld.klasy.warehouses.KlasyData;
import pl.ench.mymcworld.klasy.warehouses.PlayersData;

public class Main extends JavaPlugin{
	
	private static Main instance;
	public static List<KlasyData> klasy;
	public static List<PlayersData> players;

	public void onEnable(){		
		instance = this;
		
		Bukkit.getPluginManager().registerEvents(new ListenerOnBlockBreak(), this);
		Bukkit.getPluginManager().registerEvents(new ListenerOnJoin(), this);
		Bukkit.getPluginManager().registerEvents(new ListenerOnBlockPlace(), this);
		Bukkit.getPluginManager().registerEvents(new ListenerPlayerInteract(), this);
		
		saveDefaultConfig();
		
		FileManager.checkFiles();
		if(!ConfigManager.configReload()){Utils.sendError("Cannot load config"); this.setEnabled(false);}
		if(!ConfigManager.reloadPlayers()){Utils.sendError("Cannot reload players"); this.setEnabled(false);}
		
		getCommand("klasy").setExecutor(new Cmdklasy());
		getCommand("drop").setExecutor(new Cmddrop());
	}
	
	public static Main getInst(){
		return instance;
	}

}
