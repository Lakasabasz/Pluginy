package pl.ench.mymcworld.klasy;

public class KlasyData {

	private String name;
	private int id;
	private int maxlvl;
	private DropDataForClass drop;
	
	public String getName(){
		return name;
	}
	
	public void setName(String s){
		name = s;
	}
	
	public int getId(){
		return id;
	}
	
	public void setId(int i){
		id = i;
	}
	
	public int getMaxlvl(){
		return maxlvl;
	}
	
	public void setMaxlvl(int i){
		maxlvl = i;
	}
	
	public DropDataForClass getDrop(){
		return drop;
	}
	
	public void setDrop(DropDataForClass d){
		drop = d;
	}
}
