package pl.ench.mymcworld.klasy;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.inventory.ItemStack;

public class ListenerOnBlockPlace implements Listener {

	@EventHandler
	public static void onPlaceSign(SignChangeEvent e){
		if(ConfigManager.debugMode) Utils.sendInfo("Zg³oszono event");
		if(e.getBlock().getType().equals(Material.WALL_SIGN)){
			if(e.getLine(0).equalsIgnoreCase("[klasy/drop]") && e.getPlayer().hasPermission("mymcworld.placesign")){
				if(e.getLine(1).equalsIgnoreCase("list") || e.getLine(1).equalsIgnoreCase("add") || e.getLine(1).equalsIgnoreCase("show")){
					e.getPlayer().sendMessage(Utils.getMessage("prefix") + " " + Utils.getMessage("newSignCreated"));
					if(ConfigManager.debugMode) Utils.sendInfo("Ustawiono tabliczke");
					e.setLine(0, "§1[klasy/drop]");
				} else if(e.getLine(1) != null) {
					e.getPlayer().sendMessage(Utils.getMessage("prefix") + " " + Utils.getMessage("signSyntaxError"));
					if(ConfigManager.debugMode) Utils.sendInfo("B³êdna 2 linia");
				} else {
					return;
				}
			} else if(!e.getPlayer().hasPermission("mymcworld.placesign")){
				e.getPlayer().sendMessage(Utils.getMessage("prefix") + " " + Utils.getMessage("noSignPlacePermissions"));
				Block b = e.getBlock();
				b.setType(Material.AIR);
				
				e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.WALL_SIGN, 1));
			} else if(!e.getLine(0).equalsIgnoreCase("[klasy/drop]")) {
				if(ConfigManager.debugMode) Utils.sendInfo(e.getLine(0));
				return;
			} else {
				return;
			}
		} else {
			if(ConfigManager.debugMode) Utils.sendInfo("Materia³ który u¿yto to: " + e.getBlock().getType().toString());
			return;
		}
	}
}
