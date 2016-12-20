package br.unifor.iadapter.algorithm;

import java.util.ArrayList;
import java.util.List;

import org.apache.jorphan.collections.ListedHashTree;

import br.unifor.iadapter.threadGroup.workload.WorkLoad;

public class InitialPopulationAlgorithm extends AbstractAlgorithm {

	@Override
	public List<WorkLoad> strategy(List<WorkLoad> list, int populationSize, List<String> testCases, int generation,
			int maxUsers, String testPlan, int mutantProbability, int bestIndividuals, boolean collaborative,
			ListedHashTree script,int maxResponseTime){
		if (generation==1){
		return list;
		}else{
			return new ArrayList<WorkLoad>();
		}
	}
	
	public InitialPopulationAlgorithm(){
		super();
		setMethodName("Initial");
	}

	

}
