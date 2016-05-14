package pl.ench.mymcworld.klasy.warehouses;

import java.util.ArrayList;
import java.util.List;

public class PlayersData {
	private List<KlasyDataForPlayer> kdfpList = null;
	private List<ItemToDrop> itdList = null;
	private boolean hasklasy = false;
	private String nick = null;
	
	public PlayersData(){
		kdfpList = new ArrayList<KlasyDataForPlayer>();
		itdList = new ArrayList<ItemToDrop>();
	}
	
	public boolean checkValues(){
		if(kdfpList == null || itdList == null || nick == null) return false;
		return true;
	}

	public List<KlasyDataForPlayer> getKdfpList() {
		return kdfpList;
	}

	public void setKdfpList(List<KlasyDataForPlayer> kdfpList) {
		this.kdfpList = kdfpList;
	}
	
	public void addKdfpRecord(KlasyDataForPlayer kdfp){
		this.getKdfpList().add(kdfp);
	}

	public List<ItemToDrop> getItdList() {
		return itdList;
	}

	public void setItdList(List<ItemToDrop> itdList) {
		this.itdList = itdList;
	}

	public boolean isHasklasy() {
		return hasklasy;
	}

	public void setHasklasy(boolean hasklasy) {
		this.hasklasy = hasklasy;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}
	
	public boolean equals(PlayersData pd){
		if(!this.nick.toLowerCase().equalsIgnoreCase(pd.getNick())) return false;
		return true;
	}

}
