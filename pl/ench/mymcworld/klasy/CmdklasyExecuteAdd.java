package pl.ench.mymcworld.klasy;

import org.bukkit.entity.Player;

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
			Utils.sendInfo("[klasy/drop] Gracz " + p.getName() + " nie istnieje w systemie");
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
			kdfp.setExpToNextLvl(kdfp.getLvl() * 2);
			kdfp.setMaxlvl(kd.getMaxlvl());
			kdfp.setName(kd.getName());
			kdfp.setPath(kdfp.getPath() + "I");
			
			pd.getKdfpList().add(kdfp);
			
			Main.getInst().getConfig().set("Player." + p.getName().toLowerCase() + "." + kdfp.getPath() + ".lvl", 0);
			Main.getInst().getConfig().set("Player." + p.getName().toLowerCase() + "." + kdfp.getPath() + ".exp", 0);
			Main.getInst().getConfig().set("Player." + p.getName().toLowerCase() + "." + kdfp.getPath() + ".id", kdfp.getId());
			Main.getInst().saveConfig();
			
			ConfigManager.reloadPlayers();
			return;
		} else {
			KlasyDataForPlayer kdfp = new KlasyDataForPlayer();
			kdfp.setExp(0);
			kdfp.setExpToNextLvl(0);
			kdfp.setLvl(0);
			kdfp.setPath("klasaI");
			kdfp.setId(kd.getId());
			kdfp.setName(kd.getName());
			kdfp.setMaxlvl(kd.getMaxlvl());
			
			Main.getInst().getConfig().set("Player." + p.getName().toLowerCase() + ".hasklasy", true);
			Main.getInst().getConfig().set("Player." + p.getName().toLowerCase() + "." + kdfp.getPath() + ".lvl", 0);
			Main.getInst().getConfig().set("Player." + p.getName().toLowerCase() + "." + kdfp.getPath() + ".exp", 0);
			Main.getInst().getConfig().set("Player." + p.getName().toLowerCase() + "." + kdfp.getPath() + ".id", kdfp.getId());
			Main.getInst().saveConfig();
			
			ConfigManager.reloadPlayers();
			return;
		}
	}
}