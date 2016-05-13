package pl.ench.mymcworld.klasy.warehouses;

import java.util.List;

public class PlayersData {
	private List<KlasyDataForPlayer> kdfpList;
	private List<ItemToDrop> itdList;
	private boolean hasklasy;
	private String nick;

	public List<KlasyDataForPlayer> getKdfpList() {
		return kdfpList;
	}

	public void setKdfpList(List<KlasyDataForPlayer> kdfpList) {
		this.kdfpList = kdfpList;
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
