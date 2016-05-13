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
		if(Main.players.size() > 0){
			Utils.sendInfo("Czyszczenie Main.players z " + Main.players.size() + " pozycji");
			Main.players.clear();
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
		
		//Odczyt danych o graczach
		ConfigurationSection player = Main.getInst().getConfig().getConfigurationSection("Player");//Pozycja Player
		for(String path0 : player.getKeys(false)){ //Lista graczy
			if((player.get(path0) == null) ||
				(player.get(path0 + ".hasklasy") == null)){
				Utils.sendInfo("Uszkodzony config w klasie " + path0);
				return false;
			}
			
			PlayersData pd = new PlayersData();
			pd.setNick(path0);
			
			if(debugMode){
				Utils.sendInfo("Dane z pd.getNick(): " + pd.getNick());
				Utils.sendInfo("Dane z path0: " + path0);
			}

			List<KlasyDataForPlayer> lkdfp = new ArrayList<KlasyDataForPlayer>();
			
			ConfigurationSection gracz = player.getConfigurationSection(path0);
			for(String path1 : gracz.getKeys(false)){
				
				if(path1.equalsIgnoreCase("hasklasy")){
					pd.setHasklasy(gracz.getBoolean("hasklasy"));
					continue;
				}
				
				if((gracz.get(path1) == null)
					|| (gracz.get(path1 + ".lvl") == null)
					|| (gracz.get(path1 + ".exp") == null)
					|| (gracz.get(path1 + ".id") == null)){
					Utils.sendInfo("Uszkodzony config w sekcji players gracza " + path0 + " klasie " + path1);
					continue;
				}
				
				String n = new String();
				int l = -1;
				for(KlasyData kdT : Main.klasy){
					if(kdT.getId() == gracz.getInt(path1 + ".id")){
						n = kdT.getName();
						l = kdT.getMaxlvl();
						break;
					}
				}
				KlasyDataForPlayer kdfp = new KlasyDataForPlayer(gracz.getInt(path1 + ".id", -1), n, gracz.getInt(path1 + ".lvl"), gracz.getInt(path1 + ".exp"), gracz.getInt(path1 + ".exp") * 2, path1, l);
				if(!kdfp.checkValues()) return false;
				lkdfp.add(kdfp);
			}
			pd.setKdfpList(lkdfp);
			
			if(pd.isHasklasy()){
				PlayersData pd0 = Utils.loadBlocks(pd);
				
				if(pd0 == null){
					Utils.sendInfo("Wyst¹pi³ b³¹d");
					return false;
				} else {
					Main.players.add(pd0);
				}
			} else {
				Main.players.add(pd);
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
		//Odczyt danych o graczach
		ConfigurationSection player = Main.getInst().getConfig().getConfigurationSection("Player");//Pozycja Player
		for(String path0 : player.getKeys(false)){ //Lista graczy
			if((player.get(path0) == null) ||
				(player.get(path0 + ".hasklasy") == null)){
				Utils.sendInfo("Uszkodzony config w klasie " + path0);
				return false;
			}
			
			PlayersData pd = new PlayersData();
			pd.setNick(path0);
			
			if(debugMode){
				Utils.sendInfo("Dane z pd.getNick(): " + pd.getNick());
				Utils.sendInfo("Dane z path0: " + path0);
			}

			List<KlasyDataForPlayer> lkdfp = new ArrayList<KlasyDataForPlayer>();
			
			ConfigurationSection gracz = player.getConfigurationSection(path0);
			for(String path1 : gracz.getKeys(false)){
				
				if(path1.equalsIgnoreCase("hasklasy")){
					pd.setHasklasy(gracz.getBoolean("hasklasy"));
					continue;
				}
				
				if((gracz.get(path1) == null)
					|| (gracz.get(path1 + ".lvl") == null)
					|| (gracz.get(path1 + ".exp") == null)
					|| (gracz.get(path1 + ".id") == null)){
					Utils.sendInfo("Uszkodzony config w sekcji players gracza " + path0 + " klasie " + path1);
					continue;
				}
				
				String n = new String();
				int l = -1;
				for(KlasyData kdT : Main.klasy){
					if(kdT.getId() == gracz.getInt(path1 + ".id")){
						n = kdT.getName();
						l = kdT.getMaxlvl();
						break;
					}
				}
				KlasyDataForPlayer kdfp = new KlasyDataForPlayer(gracz.getInt(path1 + ".id", -1), n, gracz.getInt(path1 + ".lvl"), gracz.getInt(path1 + ".exp"), gracz.getInt(path1 + ".exp") * 2, path1, l);
				if(!kdfp.checkValues()) return false;
				lkdfp.add(kdfp);
			}
			pd.setKdfpList(lkdfp);
			
			if(pd.isHasklasy()){
				PlayersData pd0 = Utils.loadBlocks(pd);
				
				if(pd0 == null){
					Utils.sendInfo("Wyst¹pi³ b³¹d");
					return false;
				} else {
					Main.players.add(pd0);
				}
			} else {
				Main.players.add(pd);
			}
		}
		return true;
	}
}
