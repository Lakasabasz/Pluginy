package pl.ench.mymcworld.klasy.listeners;

import java.util.List;

import org.bukkit.GameMode;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import pl.ench.mymcworld.klasy.ConfigManager;
import pl.ench.mymcworld.klasy.FileManager;
import pl.ench.mymcworld.klasy.Main;
import pl.ench.mymcworld.klasy.Utils;
import pl.ench.mymcworld.klasy.warehouses.ItemToDrop;
import pl.ench.mymcworld.klasy.warehouses.KlasyDataForPlayer;
import pl.ench.mymcworld.klasy.warehouses.PlayersData;

public class ListenerOnBlockBreak implements Listener {
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e){
		Player p = e.getPlayer();
		PlayersData pd = null;
		if(p.getGameMode().equals(GameMode.CREATIVE))
			return;
		
		for(int i = 0; i < Main.players.size(); i++){
			PlayersData pdT0 = Main.players.get(i);
			if(pdT0.getNick().equalsIgnoreCase(p.getName().toLowerCase())){
				pd = pdT0;
				break;
			}
		}
		if(pd == null){
			Utils.sendInfo("Gracz " + p.getName() + " wykopa³ blok, a nie istnieje w systemie");
			return;
		}
		
		if(!pd.isHasklasy()){
			p.sendMessage(Utils.getMessage("prefix") + " " + Utils.getMessage("noClassNoDrop"));
			return;
		}
		
		for(ItemToDrop itd : pd.getItdList()){
			if(itd.getFB().equals(e.getBlock().getType())){
				if(Utils.getChance(itd.getChance())){
					ItemStack is = itd.getDrop();
					e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), is);
				}
				
				List<KlasyDataForPlayer> lkdfp = pd.getKdfpList();
				KlasyDataForPlayer kdfp = lkdfp.get(lkdfp.size() - 1);
				int exp = kdfp.getExp() + 1;
				if(exp >= kdfp.getExpToNextLvl() && kdfp.getLvl() < kdfp.getMaxlvl()){
					exp = exp - kdfp.getExpToNextLvl();
					kdfp.setLvl(kdfp.getLvl() + 1);
					kdfp.setExpToNextLvl(kdfp.getLvl() * 2);
					p.sendMessage(Utils.getMessage("prefix") + " " + Utils.getMessage("promotion").replace("{CLASSLVL}", "" + kdfp.getLvl()));
				}
				kdfp.setExp(exp);
				lkdfp.set(lkdfp.size() - 1, kdfp);
				pd.setKdfpList(lkdfp);
				
				YamlConfiguration pl = FileManager.getPlayers();
				pl.set(e.getPlayer().getName().toLowerCase() + "." + kdfp.getPath() + ".lvl", kdfp.getLvl());
				pl.set(e.getPlayer().getName().toLowerCase() + "." + kdfp.getPath() + ".exp", exp);
				FileManager.setPlayers(pl);
				ConfigManager.reloadPlayers();
			}
		}
	}
}
