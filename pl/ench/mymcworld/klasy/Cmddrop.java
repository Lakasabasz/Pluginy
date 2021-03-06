package pl.ench.mymcworld.klasy;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import pl.ench.mymcworld.klasy.warehouses.ItemToDrop;
import pl.ench.mymcworld.klasy.warehouses.PlayersData;

public class Cmddrop implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] arg3) {
		if(sender.hasPermission("mymcworld.drop")){
			if(ConfigManager.debugMode){
				Utils.sendInfo("Lista graczy jest r�wna " + Main.players.size());
			}
			
			for(PlayersData pd : Main.players){
				if(sender.getName().toLowerCase().equalsIgnoreCase(pd.getNick().toLowerCase())){
					if (!pd.isHasklasy()){
						sender.sendMessage(Utils.getMessage("prefix") + " " + Utils.getMessage("noClassNoDrop"));
						return false;
					} else {
						sender.sendMessage(Utils.getMessage("countDrop").replace("{DROPCOUNT}", "" + pd.getItdList().size()));
					}
					for(ItemToDrop itd : pd.getItdList()){
						sender.sendMessage(Utils.getMessage("dropData").replace("{DROPNAME}", itd.getDrop().getType().toString()).replace("{DROPCHANCE}", Double.toString(itd.getChance())));
					}
					break;
				}
				else{
					if(ConfigManager.debugMode)
						Utils.sendInfo(sender.getName() + " - " + pd.getNick());
				}
			}
			return true;
		} else {
			sender.sendMessage(Utils.getMessage("prefix") + " " + Utils.getMessage("noPermissionsCommand"));
			return false;
		}
	}

}
