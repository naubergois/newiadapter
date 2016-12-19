package br.unifor.iadapter.algorithm;

import br.unifor.iadapter.threadGroup.workload.WorkLoad;

public class Operation {

	WorkLoad old;
	WorkLoad newW;
	int func;
	int newFunc;
	boolean calculated;
	int oldFit;
	int newFit;
	int responseTime;
	public WorkLoad getOld() {
		return old;
	}
	public void setOld(WorkLoad old) {
		this.old = old;
	}
	public WorkLoad getNewW() {
		return newW;
	}
	public void setNewW(WorkLoad newW) {
		this.newW = newW;
	}
	public int getFunc() {
		return func;
	}
	public void setFunc(int func) {
		this.func = func;
	}
	public int getNewFunc() {
		return newFunc;
	}
	public void setNewFunc(int newFunc) {
		this.newFunc = newFunc;
	}
	public boolean isCalculated() {
		return calculated;
	}
	public void setCalculated(boolean calculated) {
		this.calculated = calculated;
	}
	public int getOldFit() {
		return oldFit;
	}
	public void setOldFit(int oldFit) {
		this.oldFit = oldFit;
	}
	public int getNewFit() {
		return newFit;
	}
	public void setNewFit(int newFit) {
		this.newFit = newFit;
	}
	public int getResponseTime() {
		return responseTime;
	}
	public void setResponseTime(int responseTime) {
		this.responseTime = responseTime;
	}

}
