package pl.ench.mymcworld.klasy.warehouses;

public class KlasyDataForPlayer {
	private int id = -1;
	private String name = null;
	private int lvl = -1;
	private int exp = -1;
	private int expToNextLvl = -1;
	private String path = null;
	private int maxlvl = -1;
	
	public KlasyDataForPlayer(int Id, String Name, int Lvl, int Exp, int ETNL, String Path, int Maxlvl){
		id = Id;
		name = Name;
		lvl = Lvl;
		exp = Exp;
		expToNextLvl = ETNL;
		path = Path;
		maxlvl = Maxlvl;
	}
	
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
	
	public boolean checkValues(){
		if(name == null || (path == null || path.equalsIgnoreCase("null"))) return false;
		if(!(exp >= 0) || !(this.expToNextLvl >= 0) || !(this.id >= 0) || !(this.lvl >= 0) || !(this.maxlvl >= 0)) return false;
		return true;
	}
}
