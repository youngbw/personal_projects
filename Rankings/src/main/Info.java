package main;

import java.util.ArrayList;
import java.util.Observable;

public class Info extends Observable {

	private String src;
	private String info;
	private int rank;
	
	private ArrayList<String> checked;
	
	
	public Info() {
		this("", 100, "");
	}
	
	public Info(String info, int rank, String src) {
		this.info = info;
		this.rank = rank;
		this.src = src;
		checked = new ArrayList<String>();
	}
	
	public void addEntry(String check) {
		checked.add(check);
	}
	
	public ArrayList<String> getChecked() {
		return checked;
	}
	
	public void resetList() {
		this.checked = new ArrayList<String>();
	}
	
	public int getRank() {
		return this.rank;
	}
	
	public String getInfo() {
		return this.info;
	}
	
	public String getSrc() {
		return this.src;
	}
	
	public void setSrc(String str) {
		this.src = str;
	}
	
	public void setRank(int num) {
		this.rank = num;
	}
	
	public void setInfo(String str) {
		this.info = str;
	}
	
	public void changed() {
		setChanged();
		notifyObservers();
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("" + this.rank);
		int num = this.rank;

		if (num < 10) {
			sb.append("-");
		}
		num /= 10;
		sb.append("---");
		sb.append(this.info + "\n");
		return sb.toString();
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null) return false;
		if (o instanceof Info) {
			Info myInfo = (Info)o;
			if (myInfo.getRank() == this.getRank() && myInfo.getInfo().equals(this.getInfo())) {
				return true;
			}
		}
		return false;
	}
	
}
