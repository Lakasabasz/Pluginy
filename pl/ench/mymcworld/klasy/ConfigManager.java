package pl.ench.mymcworld.klasy;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.ConfigurationSection;

import pl.ench.mymcworld.klasy.warehouses.DropData;
import pl.ench.mymcworld.klasy.warehouses.DropDataForClass;
import pl.ench.mymcworld.klasy.warehouses.KlasyData;
import pl.ench.mymcworld.klasy.warehouses.KlasyDataForPlayer;
import pl.ench.mymcworld.klasy.warehouses.PlayersData;

public class ConfigManager {

	public static String historyDirName;
	public static boolean debugMode;
	
	public static boolean configReload(){
		
		Main.klasy = new ArrayList<KlasyData>();
		Main.players = new ArrayList<PlayersData>();
		historyDirName = new String();
		debugMode = false;
		
		if(Main.klasy.size() > 0){
			Utils.sendInfo("Czyszczenie Main.klasy z " + Main.klasy.size() + " pozycji");
			Main.klasy.clear();
		}
		
		
		if(Main.getInst().getConfig().get("historyDir") == null){ //Forlder logów
			System.out.println("[Klasy/Drop] Config uszkodzony. Brak historyDir.");
		} else {
			historyDirName = Main.getInst().getConfig().getString("historyDir");
		}
		
		if(Main.getInst().getConfig().get("debug") == null){ //Tryb debugowabua
			System.out.println("[Klasy/Drop] Config uszkodzony. Brak debug.");
		} else {
			debugMode = Main.getInst().getConfig().getBoolean("debug");
		}
		
		ConfigurationSection klasy = Main.getInst().getConfig().getConfigurationSection("klasy");//Odczyt danych o klasach
		for(String path0 : klasy.getKeys(false)){
			if((klasy.get(path0) == null) ||
				(klasy.get(path0 + ".name") == null) ||
				(klasy.get(path0 + ".id") == null) ||
				(klasy.get(path0 + ".maxlvl") == null) ||
				(klasy.get(path0 + ".drop") == null) ||
				(klasy.get(path0 + ".drop.from") == null) ||
				(klasy.get(path0 + ".drop.items") == null)){
				Utils.sendInfo("Uszkodzony config w klasie " + path0);
				return false;
			}
			
			KlasyData kd = new KlasyData();
			kd.setName(klasy.getString(path0 + ".name"));
			kd.setId(klasy.getInt(path0 + ".id"));
			kd.setMaxlvl(klasy.getInt(path0 + ".maxlvl"));
			
			if(debugMode){
				Utils.sendInfo("Klasa: " + kd.getName() + ", id: " + kd.getId() + ", maxlvl: " + kd.getMaxlvl()); //Debug
			}
			
			DropDataForClass ddfc = new DropDataForClass();
			ddfc.setFrom(klasy.getString(path0 + ".drop.from"));
			ConfigurationSection ity = klasy.getConfigurationSection(path0 + ".drop.items");
			for(String path1 : ity.getKeys(false)){
				if((ity.get(path1 + ".itemId") == null) || (ity.get(path1 + ".chance") == null)){
					Utils.sendInfo("Uszkodzony config w klasie " + path0 + " w czêœci " + path1);
					return false;
				}
				DropData dd = new DropData(ity.getString(path1 + ".itemId"), ity.getDouble(path1 + ".chance"));
				ddfc.addItemsToList(dd);
			}
			kd.setDrop(ddfc);
			Main.klasy.add(kd);
		}
		if(debugMode)
			Utils.sendInfo("Podsumowanie ³adowania danych o klasach:");
			for(KlasyData kd : Main.klasy){
				Utils.sendInfo("* Klasa: " + kd.getName() + ", id: " + kd.getId() + ", maxlvl: " + kd.getMaxlvl() + " drop: " + kd.getDrop().getItemsDrop().size() + " pozycji");
				for(DropData dd0 : kd.getDrop().getItemsDrop()){
					Utils.sendInfo("- " + dd0.getId() + " (" + dd0.getChance() + ")");
				}
			}
		
		if(debugMode){
			Utils.printDataLoaded();
		}
		
		return true;
	}
	
	public String getHdn(){
		return historyDirName;
	}
	
	public static boolean reloadPlayers(){
		Main.players = new ArrayList<PlayersData>();
		if(FileManager.getPlayers() == null) return true;
		for(String nick : FileManager.getPlayers().getKeys(false)){
			PlayersData pd = new PlayersData();
			ConfigurationSection singlePlayer = FileManager.getPlayers().getConfigurationSection(nick);
			
			pd.setNick(nick);
			
			// Checking player's section
			if(singlePlayer.get("hasklasy") == null) {Utils.sendError("ConfigManager, function: reloadPlayers, 110 - " + nick + ".hasklasy is null"); return false;}
			if((singlePlayer.getBoolean("hasklasy", true) == true) && (singlePlayer.getBoolean("hasklasy", false) == false)){ Utils.sendError("ConfigManager, function: reloadPlayers, 111 - " + nick + ".hasklasy is bad value"); return false;}
			
			// First is ok, adding do pd
			pd.setHasklasy(singlePlayer.getBoolean("hasklasy"));
			
			List<KlasyDataForPlayer> lkdfp = new ArrayList<KlasyDataForPlayer>();
			
			// Checking player's class
			for(String classPath : singlePlayer.getKeys(false)){
				// Ignoring classPath == "hasklasy"
				if(classPath.equalsIgnoreCase("hasklasy")) continue;
				if((singlePlayer.get(classPath + ".lvl") == null) || (singlePlayer.get(classPath + ".exp") == null) || (singlePlayer.get(classPath + ".id") == null)) {Utils.sendError("ConfigManager, function: reloadPlayers, 120 - " + nick + "." + classPath + " is null"); return false;}
				if((singlePlayer.getInt(classPath + ".lvl", -1) == -1) || (singlePlayer.getInt(classPath + ".exp", -1) == -1) || (singlePlayer.getInt(classPath + ".id", -1) == -1)) {Utils.sendError("ConfigManager, function: reloadPlayers, 121 - " + nick + "." + classPath + " is bad value"); return false;}
				
				// All is ok, adding to lkdfp
				int id = singlePlayer.getInt(classPath + ".id");
				int lvl = singlePlayer.getInt(classPath + ".lvl");
				int exp = singlePlayer.getInt(classPath + ".exp");
				int maxlvl = -1;
				String name = null;
				
				for(KlasyData kd : Main.klasy){
					if(kd.getId() == id){
						name = kd.getName();
						maxlvl = kd.getMaxlvl();
						break;
					}
				}
				if(name == null || maxlvl == -1){
					Utils.sendError("ConfigManager, function: reloadPlayers, 140: Not match");
					return false;
				}
				
				KlasyDataForPlayer kdfp = new KlasyDataForPlayer(id, name, lvl, exp, lvl * 2, classPath, maxlvl);
				if(!kdfp.checkValues()) return false;
				lkdfp.add(kdfp);
			}
			pd.setKdfpList(lkdfp);
			PlayersData q = Utils.loadBlocks(pd);
			if(q == null) {Utils.sendError("Cannot set block in variable"); return false;}
			if(!q.checkValues()) {Utils.sendError("Variable could not pass checkValues test"); return false;}
			Main.players.add(q);
		}
		return true;
	}
}
