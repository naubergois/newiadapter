package br.unifor.iadapter.algorithm;

import java.util.ArrayList;
import java.util.List;

import org.apache.jmeter.testelement.TestElement;
import org.apache.jorphan.collections.ListedHashTree;

import br.unifor.iadapter.threadGroup.workload.WorkLoad;
import br.unifor.iadapter.threadGroup.workload.WorkLoadThreadGroup;

public abstract class AbstractAlgorithm implements IAlgorithm {

	private List<WorkLoad> listWorkLoads = new ArrayList<WorkLoad>();

	public List<WorkLoad> getListWorkLoads() {
		return listWorkLoads;
	}

	public void setListWorkLoads(List<WorkLoad> listWorkLoads) {
		this.listWorkLoads = listWorkLoads;
	}

	private String limit;

	public String getLimit() {
		return limit;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	private String methodName;

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public abstract List<WorkLoad> strategy(List<WorkLoad> list, int populationSize, List<String> testCases, int generation,
			int maxUsers, String testPlan, int mutantProbability, int bestIndividuals, boolean collaborative,
			ListedHashTree script,int maxResponseTime) ;
	


}
