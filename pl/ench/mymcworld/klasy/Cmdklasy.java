package pl.ench.mymcworld.klasy;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Cmdklasy implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if(!sender.hasPermission("mymcworld.klasy")){
			sender.sendMessage(Utils.getMessage("prefix") + " " + Utils.getMessage("noPermissionsCommand"));
			return false;
		}
		
		if(args.length > 3){
			sender.sendMessage("[klasy/drop] Za du¿o argumentów. Wpisz /klasy aby otrzymaæ pomoc");
			return false;
		} else if(args.length == 0){
			if(sender.hasPermission("mymcworld.klasy.help")){
				CmdklasyExecuteHelp.klasyHelpExecute((Player) sender);
				return true;
			} else{
				sender.sendMessage(Utils.getMessage("prefix") + " " + Utils.getMessage("noPermissionsCommand"));
				return false;
			}
		} else if(args.length == 1){
			if(args[0].equalsIgnoreCase("help")){
				if(sender.hasPermission("mymcworld.klasy.help")){
					CmdklasyExecuteHelp.klasyHelpExecute((Player) sender);
					return true;
				} else{
					sender.sendMessage(Utils.getMessage("prefix") + " " + Utils.getMessage("noPermissionsCommand"));
					return false;
				}
			} else if(args[0].equalsIgnoreCase("list")){
				if(sender.hasPermission("mymcworld.klasy.list")){
					CmdklasyExecuteHelp.klasyListExecute((Player) sender);
					return true;
				} else{
					sender.sendMessage(Utils.getMessage("prefix") + " " + Utils.getMessage("noPermissionsCommand"));
					return false;
				}
			} else if(args[0].equalsIgnoreCase("reload")){
				if(sender.hasPermission("mymcworld.klasy.reload")){
					if(CmdklasyExecuteReload.klasyReloadExecute((Player) sender)){
						sender.sendMessage(Utils.getMessage("prefix") + " " + Utils.getMessage("reloadSucces"));
						Utils.sendInfo("Prze³adowano");
						return true;
					} else {
						sender.sendMessage(Utils.getMessage("prefix") + " " + Utils.getMessage("reloadError"));
						Utils.sendInfo("B³¹d prze³adowania");
						return false;
					}
				} else{
					sender.sendMessage(Utils.getMessage("prefix") + " " + Utils.getMessage("noPermissionsCommand"));
					return false;
				}
			}
		} else if(args.length == 2){
			if(args[0].equalsIgnoreCase("show")){
				if(sender.hasPermission("mymcworld.klasy.show")){
					CmdklasyExecuteShow.klasyShowExecute((Player) sender, args[1]);
					return true;
				} else{
					sender.sendMessage(Utils.getMessage("prefix") + " " + Utils.getMessage("noPermissionsCommand"));
					return false;
				}
			}
		} else if(args.length == 3){
			if(args[0].equalsIgnoreCase("add")){
				if(sender.hasPermission("mymcworld.klasy.add")){
					CmdklasyExecuteAdd.klasyAddExecute((Player) sender, Integer.parseInt(args[2]));
					return true;
				} else{
					sender.sendMessage(Utils.getMessage("prefix") + " " + Utils.getMessage("noPermissionsCommand"));
					return false;
				}
			}
		}
		sender.sendMessage(Utils.getMessage("prefix") + " " + Utils.getMessage("wrongCommand"));
		return false;
	}

}
