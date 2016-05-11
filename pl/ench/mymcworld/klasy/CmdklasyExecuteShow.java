package pl.ench.mymcworld.klasy;

import org.bukkit.entity.Player;

public class CmdklasyExecuteShow {
	public static void klasyShowExecute(Player p){
		PlayersData pd = null;
		for(PlayersData pd0 : Main.players){
			if(p.getName().toLowerCase().equalsIgnoreCase(pd0.getNick().toLowerCase())){
				pd = pd0;
				break;
			}
		}
		
		if(pd == null){
			p.sendMessage(Utils.getMessage("prefix") + " " + Utils.getMessage("playerNotExist").replace("{PLAYERNAME}", p.getName()));
			return;
		}
		
		for(KlasyDataForPlayer kdfp : pd.getKdfpList()){
			if(FileManager.getMsg().getBoolean("showText.usePrefix")) p.sendMessage(Utils.getMessage("prefix") + " " + Utils.getMessage("showText.showLine").replace("{CLASSNAME}", kdfp.getName()).replace("{CLASSLVL}", "" + kdfp.getLvl()).replace("{CLASSEXP}", "" + kdfp.getExp()).replace("{CLASSEXPNEXTLVL}", "" + kdfp.getExpToNextLvl()).replace("{CLASSMAXLVL}", "" + kdfp.getMaxlvl()));
			else p.sendMessage(Utils.getMessage("showText.showLine").replace("{CLASSNAME}", kdfp.getName()).replace("{CLASSLVL}", "" + kdfp.getLvl()).replace("{CLASSEXP}", "" + kdfp.getExp()).replace("{CLASSEXPNEXTLVL}", "" + kdfp.getExpToNextLvl()).replace("{CLASSMAXLVL}", "" + kdfp.getMaxlvl()));
		}
	}
}
