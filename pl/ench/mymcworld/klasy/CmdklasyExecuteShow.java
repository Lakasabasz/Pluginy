package pl.ench.mymcworld.klasy;

import org.bukkit.entity.Player;

import pl.ench.mymcworld.klasy.warehouses.KlasyDataForPlayer;
import pl.ench.mymcworld.klasy.warehouses.PlayersData;

public class CmdklasyExecuteShow {
	public static void klasyShowExecute(Player p, String name){
		PlayersData pd = null;
		for(PlayersData pd0 : Main.players){
			if(name.toLowerCase().equalsIgnoreCase(pd0.getNick().toLowerCase())){
				pd = pd0;
				break;
			}
		}
		
		if(pd == null){
			p.sendMessage(Utils.getMessage("prefix") + " " + Utils.getMessage("playerNotExist").replace("{PLAYERNAME}", p.getName()));
			return;
		} else if(ConfigManager.debugMode){
			Utils.sendInfo("Znaleziono gracza o nick " + pd.getNick());
		}
		
		if(pd.getKdfpList().size() == 0){
			p.sendMessage(Utils.getMessage("prefix") + " " + Utils.getMessage("noClass"));
			return;
		}
		
		for(KlasyDataForPlayer kdfp : pd.getKdfpList()){
			if(FileManager.getMsg().getBoolean("showText.usePrefix")) p.sendMessage(Utils.getMessage("prefix") + " " + Utils.getMessage("showText.showLine").replace("{CLASSNAME}", kdfp.getName()).replace("{CLASSLVL}", "" + kdfp.getLvl()).replace("{CLASSEXP}", "" + kdfp.getExp()).replace("{CLASSEXPNEXTLVL}", "" + kdfp.getExpToNextLvl()).replace("{CLASSMAXLVL}", "" + kdfp.getMaxlvl()));
			else p.sendMessage(Utils.getMessage("showText.showLine").replace("{CLASSNAME}", kdfp.getName()).replace("{CLASSLVL}", "" + kdfp.getLvl()).replace("{CLASSEXP}", "" + kdfp.getExp()).replace("{CLASSEXPNEXTLVL}", "" + kdfp.getExpToNextLvl()).replace("{CLASSMAXLVL}", "" + kdfp.getMaxlvl()));
		}
	}
}
