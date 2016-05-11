package pl.ench.mymcworld.klasy;

import org.bukkit.entity.Player;

public class CmdklasyExecuteReload {
	
	public static boolean klasyReloadExecute(Player p){
		return ConfigManager.configReload();
	}

}
