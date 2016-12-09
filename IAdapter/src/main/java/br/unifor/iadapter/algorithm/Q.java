package br.unifor.iadapter.algorithm;

public class Q {
	
	double q;
	String state;
	public double getQ() {
		return q;
	}
	public void setQ(double q) {
		this.q = q;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return " State "+state+" Q "+q; 
	}

}
