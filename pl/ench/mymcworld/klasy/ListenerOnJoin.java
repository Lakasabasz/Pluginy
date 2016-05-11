package pl.ench.mymcworld.klasy;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class ListenerOnJoin implements Listener{
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		Player p = e.getPlayer();
		String name = p.getName().toLowerCase();
		
		if(Main.getInst().getConfig().get("Player." + name + ".hasklasy") == null){
			Main.getInst().getConfig().set("Player." + name + ".hasklasy", false);
			Main.getInst().saveConfig();
			System.out.println("[Klasy/Drop] Zapisano config");
			System.out.println("[Klasy/Drop] " + p.getName() + " zosta³ zarejestrowany w systemie");
			return;
		} else {
			return;
		}
	}

}
