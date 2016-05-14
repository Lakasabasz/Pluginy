package pl.ench.mymcworld.klasy;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import pl.ench.mymcworld.klasy.warehouses.KlasyData;
import pl.ench.mymcworld.klasy.warehouses.KlasyDataForPlayer;
import pl.ench.mymcworld.klasy.warehouses.PlayersData;

public class CmdklasyExecuteAdd {

	public static void klasyAddExecute(Player p, int idKlasy){
		
		PlayersData pd = null;
		for(PlayersData pd0 : Main.players){
			if(p.getName().toLowerCase().equalsIgnoreCase(pd0.getNick().toLowerCase())){
				pd = pd0;
				break;
			}
		}
		
		if(pd == null){
			p.sendMessage(Utils.getMessage("prefix") +  " " + Utils.getMessage("playerNotExist").replace("{PALYERNAME}", p.getName()));
			Utils.sendError("Gracz " + p.getName() + " nie istnieje w systemie");
			return;
		}
		
		KlasyData kd = null;
		for(KlasyData kd0 : Main.klasy){
			if(kd0.getId() == idKlasy){
				kd = kd0;
				break;
			}
		}
		
		if(kd == null){
			p.sendMessage(Utils.getMessage("prefix") + " " + Utils.getMessage("classNotExist").replace("{CLASSID}", Integer.toString(idKlasy)));
			Utils.sendInfo("[klasy/drop] Klasa o id " + idKlasy + " nie istnieje");
			return;
		}
		
		if(pd.isHasklasy()){
			if(pd.getKdfpList().size() == Main.klasy.size()){
				p.sendMessage(Utils.getMessage("prefix") +  " " + Utils.getMessage("maxClassReached"));
				return;
			}
			
			for(KlasyDataForPlayer kdfp : pd.getKdfpList()){
				if(kd.getId() == kdfp.getId()){
					p.sendMessage(Utils.getMessage("prefix") +  " " + Utils.getMessage("selectedClassExist"));
					return;
				}
			}
			
			KlasyDataForPlayer kdfp = pd.getKdfpList().get(pd.getKdfpList().size() - 1);
			if(kdfp.getLvl() < kdfp.getMaxlvl()){
				p.sendMessage(Utils.getMessage("prefix") +  " " + Utils.getMessage("toLowLevel"));
				return;
			}
			kdfp.setExp(0);
			kdfp.setId(kd.getId());
			kdfp.setLvl(0);
			kdfp.setPath(kdfp.getPath() + "I");
			
			YamlConfiguration players = FileManager.getPlayers();
			players.set(p.getName().toLowerCase() + "." + kdfp.getPath() + ".lvl", 0);
			players.set(p.getName().toLowerCase() + "." + kdfp.getPath() + ".exp", 0);
			players.set(p.getName().toLowerCase() + "." + kdfp.getPath() + ".id", kdfp.getId());
			FileManager.setPlayers(players);
			
			if(!ConfigManager.reloadPlayers()){
				
			}
			p.sendMessage(Utils.getMessage("prefix") + " " + Utils.getMessage("classAddSucces").replace("{CLASSNAME}", kdfp.getName()));
			return;
		} else {
			KlasyDataForPlayer kdfp = new KlasyDataForPlayer(kd.getId(), kd.getName(), 0, 0, 0, "klasaI", kd.getMaxlvl());
			
			pd.addKdfpRecord(kdfp);
			for(PlayersData pd0 : Main.players){
				if(pd.getNick().toLowerCase().equalsIgnoreCase(pd0.getNick().toLowerCase())){
					int index = Main.players.indexOf(pd0);
					Main.players.set(index, pd);
					break;
				}
			}
			
			YamlConfiguration players = FileManager.getPlayers();
			players.set(p.getName().toLowerCase() + ".hasklasy", true);
			players.set(p.getName().toLowerCase() + ".klasaI.lvl", 0);
			players.set(p.getName().toLowerCase() + ".klasaI.exp", 0);
			players.set(p.getName().toLowerCase() + ".klasaI.id", kdfp.getId());
			FileManager.setPlayers(players);
			
			ConfigManager.reloadPlayers();
			p.sendMessage(Utils.getMessage("prefix") + " " + Utils.getMessage("classAddSucces").replace("{CLASSNAME}", kdfp.getName()));
			return;
		}
	}
}
