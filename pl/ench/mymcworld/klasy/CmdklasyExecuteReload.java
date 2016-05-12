package pl.ench.mymcworld.klasy;

import org.bukkit.entity.Player;

public class CmdklasyExecuteReload {
	
	public static boolean klasyReloadExecute(Player p){
		if(!ConfigManager.configReload()){
			return false;
		}
		if(!FileManager.reloadFiles()){
			return false;
		}
		return true;
	}

}
