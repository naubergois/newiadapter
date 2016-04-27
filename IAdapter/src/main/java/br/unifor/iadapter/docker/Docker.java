package br.unifor.iadapter.docker;

public class Docker {
	
	private int cpuShare;
		
	public int getCpuShare() {		
		
		return cpuShare;
	}

	public void setCpuShare(int cpuShare) {
		this.cpuShare = cpuShare;
	}

	public int memory;

	public int getMemory() {
		return memory;
	}

	public void setMemory(int memory) {
		this.memory = memory;
	}
	
	
	

}
