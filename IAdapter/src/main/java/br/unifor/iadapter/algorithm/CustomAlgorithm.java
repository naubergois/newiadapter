package br.unifor.iadapter.algorithm;

import java.util.ArrayList;
import java.util.List;

import org.apache.jorphan.collections.ListedHashTree;

import br.unifor.iadapter.database.MySQLDatabase;
import br.unifor.iadapter.neighborhood.NeighborhoodUtil;
import br.unifor.iadapter.threadGroup.workload.WorkLoad;

public class CustomAlgorithm extends AbstractAlgorithm {

	
	private double maxWeight = 1;
	

	public boolean addItem(double weight) {
		if ((weight) >= (0.8*maxWeight)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<WorkLoad> strategy(List<WorkLoad> list, int populationSize, List<String> testCases, int generation,
			int maxUsers, String testPlan, int mutantProbability, int bestIndividuals, boolean collaborative,
			ListedHashTree script,int maxResponseTime) {

	
		maxWeight = list.get(0).getFit();

		List<WorkLoad> newList = new ArrayList<WorkLoad>();

		try {
			for (WorkLoad newPlace : MySQLDatabase.listWorkLoadsASC(testPlan, generation)) {
				if (addItem(newPlace.getFit())) {
					newList.add(newPlace);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		List<WorkLoad> newNeighboors = NeighborhoodUtil.getNeighBorHoodsAllList(this, newList, populationSize,
				testCases, generation, maxUsers, testPlan);

		
		return newNeighboors;
	}

	public CustomAlgorithm() {
		setMethodName("Grered");
	}

}
