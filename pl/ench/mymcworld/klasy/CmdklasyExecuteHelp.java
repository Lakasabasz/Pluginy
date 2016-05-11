package pl.ench.mymcworld.klasy;

import org.bukkit.entity.Player;

public class CmdklasyExecuteHelp {
	public static void klasyHelpExecute(Player p){
		if(FileManager.getMsg().getBoolean("helpText.usePrefix.toFirstLine")) p.sendMessage(Utils.getMessage("prefix") + " " + Utils.getMessage("helpText.in"));
		else p.sendMessage(Utils.getMessage("helpText.in"));
		if(FileManager.getMsg().getBoolean("helpText.usePrefix.toNextLine")){
			p.sendMessage(Utils.getMessage("prefix") + " " + Utils.getMessage("helpText.commandLine").replace("{COMMAND}", "/klasy list").replace("{TEXT}", Utils.getMessage("helpText.commandLineText.list")));
			p.sendMessage(Utils.getMessage("prefix") + " " + Utils.getMessage("helpText.commandLine").replace("{COMMAND}", "/klasy help").replace("{TEXT}", Utils.getMessage("helpText.commandLineText.help")));
			p.sendMessage(Utils.getMessage("prefix") + " " + Utils.getMessage("helpText.commandLine").replace("{COMMAND}", "/klasy reload").replace("{TEXT}", Utils.getMessage("helpText.commandLineText.reload")));
			p.sendMessage(Utils.getMessage("prefix") + " " + Utils.getMessage("helpText.commandLine").replace("{COMMAND}", "/klasy show <nick>").replace("{TEXT}", Utils.getMessage("helpText.commandLineText.show")));
			p.sendMessage(Utils.getMessage("prefix") + " " + Utils.getMessage("helpText.commandLine").replace("{COMMAND}", "/klasy add <nick> <id>").replace("{TEXT}", Utils.getMessage("helpText.commandLineText.add")));
			p.sendMessage(Utils.getMessage("prefix") + " " + Utils.getMessage("helpText.commandLine").replace("{COMMAND}", "/drop").replace("{TEXT}", Utils.getMessage("helpText.commandLineText.drop")));
		} else {
			p.sendMessage(Utils.getMessage("helpText.commandLine").replace("{COMMAND}", "/klasy list").replace("{TEXT}", Utils.getMessage("helpText.commandLineText.list")));
			p.sendMessage(Utils.getMessage("helpText.commandLine").replace("{COMMAND}", "/klasy help").replace("{TEXT}", Utils.getMessage("helpText.commandLineText.help")));
			p.sendMessage(Utils.getMessage("helpText.commandLine").replace("{COMMAND}", "/klasy reload").replace("{TEXT}", Utils.getMessage("helpText.commandLineText.reload")));
			p.sendMessage(Utils.getMessage("helpText.commandLine").replace("{COMMAND}", "/klasy show <nick>").replace("{TEXT}", Utils.getMessage("helpText.commandLineText.show")));
			p.sendMessage(Utils.getMessage("helpText.commandLine").replace("{COMMAND}", "/klasy add <nick> <id>").replace("{TEXT}", Utils.getMessage("helpText.commandLineText.add")));
			p.sendMessage(Utils.getMessage("helpText.commandLine").replace("{COMMAND}", "/drop").replace("{TEXT}", Utils.getMessage("helpText.commandLineText.drop")));
		}
		return;
	}
	
	public static void klasyListExecute(Player p){
		if(Main.klasy.size() == 0){
			return;
		}
		if(FileManager.getMsg().getBoolean("listText.usePrefix.toFirstLine")) p.sendMessage(Utils.getMessage("prefix") + " " + Utils.getMessage("listText.in"));
		else p.sendMessage(Utils.getMessage("listText.in"));
		
		boolean usePrefix = FileManager.getMsg().getBoolean("listText.usePrefix.toNextLine");
		
		for(KlasyData kd : Main.klasy){
			if(usePrefix) p.sendMessage(Utils.getMessage("prefix") + " " + Utils.getMessage("listText.classLine").replace("{CLASSNAME}", kd.getName()).replace("{CLASSID}", Integer.toString(kd.getId())).replace("{CLASSMAXLVL}", Integer.toString(kd.getMaxlvl())).replace("{CLASSDROPFROM}", kd.getDrop().getFrom()));
			else  p.sendMessage(Utils.getMessage("listText.classLine").replace("{CLASSNAME}", kd.getName()).replace("{CLASSID}", Integer.toString(kd.getId())).replace("{CLASSMAXLVL}", Integer.toString(kd.getMaxlvl())).replace("{CLASSDROPFROM}", kd.getDrop().getFrom()));
			for(DropData dd : kd.getDrop().getItemsDrop()){
				if(usePrefix) p.sendMessage(Utils.getMessage("prefix") + " " + Utils.getMessage("listText.dropLine").replace("{DROPNAME}", dd.getId()).replace("{DROPCHANCE}", Double.toString(dd.getChance())));
				else p.sendMessage(Utils.getMessage("listText.dropLine").replace("{DROPNAME}", dd.getId()).replace("{DROPCHANCE}", Double.toString(dd.getChance())));
			}
		}
		return;
	}
}
