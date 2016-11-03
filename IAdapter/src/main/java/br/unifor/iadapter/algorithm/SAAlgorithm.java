package br.unifor.iadapter.algorithm;

import java.util.ArrayList;
import java.util.List;

import org.apache.jorphan.collections.ListedHashTree;

import br.unifor.iadapter.neighborhood.NeighborhoodUtil;
import br.unifor.iadapter.sa.SimulateAnnealing;
import br.unifor.iadapter.threadGroup.workload.WorkLoad;

public class SAAlgorithm extends AbstractAlgorithm {

	private static int temperature;
	public static WorkLoad currentWorkLoad;

	@Override
	public List<WorkLoad> strategy(List<WorkLoad> list, int populationSize, List<String> testCases, int generation,
			int maxUsers, String testPlan, int mutantProbability, int bestIndividuals, boolean collaborative,
			ListedHashTree script,int maxResponseTime){

		

		if (temperature <= 0) {
			temperature = maxUsers;
		}
		if (generation == 1) {
			temperature = maxUsers;
		}
		
		if (this.getListWorkLoads().size() > 0) {
			System.out.println("SA Temperature:" + temperature);
			SimulateAnnealing.sa(this, list, temperature);
		}
		
		List<WorkLoad> neighborhoods =NeighborhoodUtil.getNeighborHood(currentWorkLoad, this, testCases, generation, maxUsers, testPlan, populationSize);

		
		temperature = temperature - 5;
		return neighborhoods;
	}

	public SAAlgorithm() {
		this.setMethodName("SA");
	}

}
