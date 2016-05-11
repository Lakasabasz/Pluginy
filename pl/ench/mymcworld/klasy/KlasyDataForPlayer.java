package pl.ench.mymcworld.klasy;

public class KlasyDataForPlayer {
	private int id;
	private String name;
	private int lvl;
	private int exp;
	private int expToNextLvl;
	private String path;
	private int maxlvl;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getExp() {
		return exp;
	}
	public void setExp(int exp) {
		this.exp = exp;
	}
	public int getLvl() {
		return lvl;
	}
	public void setLvl(int lvl) {
		this.lvl = lvl;
	}
	public int getExpToNextLvl() {
		return expToNextLvl;
	}
	public void setExpToNextLvl(int expToNextLvl) {
		this.expToNextLvl = expToNextLvl;
	}
	public void setPath(String path1) {
		this.path = path1;
		
	}
	public String getPath(){
		return path;
	}
	public int getMaxlvl() {
		return maxlvl;
	}
	public void setMaxlvl(int maxlvl) {
		this.maxlvl = maxlvl;
	}
	
	
}
