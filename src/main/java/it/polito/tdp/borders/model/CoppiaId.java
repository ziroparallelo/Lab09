package it.polito.tdp.borders.model;

public class CoppiaId {

	private int ccode1;
	private int ccode2;
	
	public CoppiaId(int ccode1, int ccode2) {
		super();
		this.ccode1 = ccode1;
		this.ccode2 = ccode2;
	}
	public int getCcode1() {
		return ccode1;
	}
	public void setCcode1(int ccode1) {
		this.ccode1 = ccode1;
	}
	public int getCcode2() {
		return ccode2;
	}
	public void setCcode2(int ccode2) {
		this.ccode2 = ccode2;
	}
	
	
}
