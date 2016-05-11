package pl.ench.mymcworld.klasy;

import java.util.ArrayList;
import java.util.List;

public class DropDataForClass {
	
	private List<DropData> itemsDrop;
	private String from;
	
	public DropDataForClass(){
		itemsDrop = new ArrayList<DropData>();
		if(!itemsDrop.isEmpty()){
			itemsDrop.clear();
		}
		
		from = new String();
		if(!from.isEmpty()){
			from = "";
		}
	}
	
	public List<DropData> getItemsDrop(){
		return itemsDrop;
	}
	
	public String getFrom(){
		return from;
	}
	
	public void clearItemsDrop(){
		itemsDrop = new ArrayList<DropData>();
	}
	
	public void addItemsToList(DropData d){
		itemsDrop.add(d);
	}
	
	public void setFrom(String f){
		from = f;
	}

}
