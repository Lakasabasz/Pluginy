package pl.ench.mymcworld.klasy;

import org.bukkit.entity.Player;

public class CmdklasyExecuteReload {
	
	public static boolean klasyReloadExecute(Player p){
		if(!FileManager.reloadFiles()){
			return false;
		}
		if(!ConfigManager.configReload()){
			return false;
		}
		if(!ConfigManager.reloadPlayers()){
			return false;
		}
		return true;
	}

}
