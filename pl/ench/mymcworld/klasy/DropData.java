package pl.ench.mymcworld.klasy;

public class DropData {
	
	private String id;
	private double chance;
	
	public DropData(String id, double chance){
		this.id = id;
		this.chance = chance;
	}
	
	public String getId(){
		return id;
	}
	
	public void setId(String s){
		id = s;
	}
	
	public double getChance(){
		return chance;
	}
	
	public void setChance(double c){
		chance = c;
	}

}
