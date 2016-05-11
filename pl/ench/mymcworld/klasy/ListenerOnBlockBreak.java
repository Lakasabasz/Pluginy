package pl.ench.mymcworld.klasy;

import java.util.List;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

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
				int exp = kdfp.getExp();
				exp++;
				if(exp >= kdfp.getExpToNextLvl() && kdfp.getLvl() < kdfp.getMaxlvl()){
					exp = exp - kdfp.getExpToNextLvl();
					kdfp.setLvl(kdfp.getLvl() + 1);
					kdfp.setExpToNextLvl(kdfp.getLvl() * 2);
					p.sendMessage(Utils.getMessage("prefix") + " " + Utils.getMessage("promotion").replace("{CLASSLVL}", "" + kdfp.getLvl()));
				}
				kdfp.setExp(exp);
				lkdfp.set(lkdfp.size() - 1, kdfp);
				pd.setKdfpList(lkdfp);
				
				
				Main.getInst().getConfig().set("Player." + e.getPlayer().getName().toLowerCase() + "." + kdfp.getPath() + ".lvl", kdfp.getLvl());
				Main.getInst().getConfig().set("Player." + e.getPlayer().getName().toLowerCase() + "." + kdfp.getPath() + ".exp", kdfp.getExp());
				Main.getInst().saveConfig();
				ConfigManager.reloadPlayers();
			}
		}
	}
}
