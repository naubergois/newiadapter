package br.unifor.iadapter.algorithm;

import java.util.List;

import org.apache.jorphan.collections.ListedHashTree;

import br.unifor.iadapter.neighborhood.NeighborhoodUtil;
import br.unifor.iadapter.tabu.TabuSearch;
import br.unifor.iadapter.threadGroup.workload.WorkLoad;

public class TabuAlgorithm extends AbstractAlgorithm {
	
	

	@Override
	public List<WorkLoad> strategy(List<WorkLoad> list, int populationSize, List<String> testCases, int generation,
			int maxUsers, String testPlan, int mutantProbability, int bestIndividuals, boolean collaborative,
			ListedHashTree script) {
		
		
		List<WorkLoad> listVerified=TabuSearch.verify(list);
		TabuSearch.addTabuTable(list.get(0));
		List<WorkLoad> neighborhoods =
				NeighborhoodUtil.getNeighBorHoodsFirstItemOfList(this, listVerified, populationSize, testCases, generation, maxUsers, testPlan);
		
				
		return neighborhoods;
	}
	
	public TabuAlgorithm(){
		this.setMethodName("TS");
	}

}
