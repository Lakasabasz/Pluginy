package pl.ench.mymcworld.klasy.listeners;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import pl.ench.mymcworld.klasy.FileManager;
import pl.ench.mymcworld.klasy.Main;
import pl.ench.mymcworld.klasy.Utils;
import pl.ench.mymcworld.klasy.warehouses.PlayersData;

public class ListenerOnJoin implements Listener{
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		Player p = e.getPlayer();
		String name = p.getName().toLowerCase();
		YamlConfiguration playersYC;
		
		if(FileManager.getPlayers() == null){
			playersYC = new YamlConfiguration();
		} else {
			playersYC = FileManager.getPlayers();
			if(FileManager.getPlayers().get(name) != null) return;
		}
		
		playersYC.set(name + ".hasklasy", false);
		
		if(!FileManager.setPlayers(playersYC)){
			Utils.sendError("Cannot register player");
		}
		
		PlayersData pd = new PlayersData();
		pd.setNick(name);
		pd.setHasklasy(false);
		Main.players.add(pd);
	}
}
