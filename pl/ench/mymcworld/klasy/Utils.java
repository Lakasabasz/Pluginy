package pl.ench.mymcworld.klasy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.md_5.bungee.api.ChatColor;
import pl.ench.mymcworld.klasy.warehouses.DropData;
import pl.ench.mymcworld.klasy.warehouses.DropDataForClass;
import pl.ench.mymcworld.klasy.warehouses.ItemToDrop;
import pl.ench.mymcworld.klasy.warehouses.KlasyData;
import pl.ench.mymcworld.klasy.warehouses.KlasyDataForPlayer;
import pl.ench.mymcworld.klasy.warehouses.PlayersData;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Utils {

	private static final Random RAND = new Random();
	
	public static double getRandDouble(double min, double max) throws IllegalArgumentException{
		Validate.isTrue(max > min, "[klasy/drop] error Utils 10");
		return RAND.nextDouble() * (max - min) + min;
	}
	
	public static boolean getChance(double chance){
		return (chance >= 100) || (chance >= getRandDouble(0, 100));
	}
	
	public static void sendInfo(String msg){
		if(msg == null || msg.isEmpty()){
			System.out.println("[Klasy/Drop] Wywo³anie funkcji Utils.sendInfo z parametrem zerowym");
			return;
		}
		System.out.println("[Klasy/Drop] " + msg);
	}
	
	public static String getMessage(String path){
		if(path == null || path.isEmpty()){
			System.out.println("[Klasy/Drop] Wywo³anie funkcji Utils.getMessages z parametrem zerowym");
			return "";
		}
		if(FileManager.getMsg().get(path) == null){
			System.out.println("[Klasy/Drop] Wywo³anie funkcji Utils.getMessages z b³êdnym path");
			return "";
		}
		return ChatColor.translateAlternateColorCodes('&', FileManager.getMsg().getString(path));
	}
	
	public static PlayersData loadBlocks(PlayersData pd){//Z Main.klasy do Main.players
		List<ItemToDrop> itdl = new ArrayList<ItemToDrop>();
		if(ConfigManager.debugMode){
			Utils.sendInfo("Gracz " + pd.getNick() + " ma " + pd.getKdfpList().size() + " pozycje w pd.kdfplist");
		}
		for(KlasyDataForPlayer kdfp : pd.getKdfpList()){
			
			int id = kdfp.getId();
			String classname = null;
			DropDataForClass ddfc = null;
			
			for(KlasyData kd : Main.klasy){
				if(id == kd.getId()){
					ddfc = kd.getDrop();
					classname = kd.getName();
					break;
				}
			}
			
			if(ddfc == null){
				Utils.sendInfo("Gracz " + pd.getNick() + " ma b³êdne id klasy " + id);
				return null;
			} else if(ddfc != null && ConfigManager.debugMode) {
				Utils.sendInfo("Po³¹czono klasê gracza z klasami w systemie (trafiona klasa to: " + classname + "). Gracz ma " + ddfc.getItemsDrop().size() + " dropów w ddfc");
			}
			
			for(DropData dd : ddfc.getItemsDrop()){
				if(ConfigManager.debugMode){
					Utils.sendInfo("Dane dd: " + dd.getId() + ", " + dd.getChance());
				}
				ItemToDrop itd = new ItemToDrop();
				Material fb = Material.matchMaterial(ddfc.getFrom());
				if(fb == null){
					Utils.sendInfo("Z³y materia³ typu from");
					Bukkit.getPluginManager().disablePlugin(Main.getInst());
					return null;
				}
				itd.setFB(fb);
				itd.setChance(dd.getChance() * kdfp.getLvl());
				Material drop = Material.matchMaterial(dd.getId());
				if(drop == null){
					Utils.sendInfo("Z³y materia³ typu drop. Zosta³ podany: " + dd.getId());
					Bukkit.getPluginManager().disablePlugin(Main.getInst());
				} else {
					Utils.sendInfo("Podany drop: " + drop.toString());
				}
				ItemStack is = new ItemStack(drop, 1, (short) 0);
				itd.setDrop(is);
				itdl.add(itd);
			}
		}
		if(ConfigManager.debugMode){
			Utils.sendInfo("Gracz " + pd.getNick() + " summarycznie ma " + itdl.size() + " dropów");
		}
		pd.setItdList(itdl);
		
		return pd;
	}
	
	public static void printDataLoaded(){
		if(Main.players.size() == 0){
			Utils.sendInfo("Players list is empty");
		}
		for(PlayersData pd : Main.players){
			Utils.sendInfo(pd.getNick() + ",");
		}
	}
}
