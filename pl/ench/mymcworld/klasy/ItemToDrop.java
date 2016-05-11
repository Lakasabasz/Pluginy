package pl.ench.mymcworld.klasy;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ItemToDrop {
	
	private Material fromBlock;
	private ItemStack drop;
	private double chance;
	
	public void setFB(Material fb){
		fromBlock = fb;
	}
	
	public void setDrop(ItemStack drop){
		this.drop = drop;
	}
	
	public void setChance(double ch){
		chance = ch;
	}
	
	public Material getFB(){
		return fromBlock;
	}
	
	public ItemStack getDrop(){
		return drop;
	}
	
	public double getChance(){
		return chance;
	}

}
