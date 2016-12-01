package br.unifor.iadapter.algorithm;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.jorphan.collections.ListedHashTree;

import br.unifor.iadapter.database.MySQLDatabase;
import br.unifor.iadapter.neighborhood.NeighborhoodUtil;
import br.unifor.iadapter.threadGroup.workload.WorkLoad;

public class Hybrid extends AbstractAlgorithm {

	@Override
	public List<WorkLoad> strategy(List<WorkLoad> list, int populationSize, List<String> testCases, int generation,
			int maxUsers, String testPlan, int mutantProbability, int bestIndividuals, boolean collaborative,
			ListedHashTree script, int maxResponseTime) {

		try {
			list = MySQLDatabase.listBestWorkLoadsForAll(testPlan, populationSize);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		HillClimbing hill = new HillClimbing();

		List<WorkLoad> workloads = hill.strategy(list, populationSize, testCases, generation, maxUsers, testPlan,
				mutantProbability, bestIndividuals, collaborative, script, maxResponseTime);

		TabuAlgorithm tabu = new TabuAlgorithm();

		List<WorkLoad> workload1 = tabu.strategy(list, populationSize, testCases, generation, maxUsers, testPlan,
				mutantProbability, bestIndividuals, collaborative, script, maxResponseTime);

		GeneticAlgorithmImplementation genetic = new GeneticAlgorithmImplementation();

		List<WorkLoad> geneticList = genetic.strategy(list, populationSize, testCases, generation, maxUsers, testPlan,
				mutantProbability, bestIndividuals, collaborative, script, maxResponseTime);

		SAAlgorithm sa = new SAAlgorithm();

		List<WorkLoad> workload2 = sa.strategy(geneticList, populationSize, testCases, generation, maxUsers, testPlan,
				mutantProbability, bestIndividuals, collaborative, script, maxResponseTime);

		List<WorkLoad> listBest = new ArrayList<WorkLoad>();

		listBest.add(workloads.get(0));
		listBest.add(workload1.get(0));
		listBest.add(geneticList.get(0));
		listBest.add(workload2.get(0));

		for (WorkLoad workLoad : listBest) {

			if (!(workLoad.getName().contains("Hybrid"))) {

				workLoad.setName("Hybrid" + workLoad.getName());
			}
			workLoad.setSearchMethod(this.getMethodName());
		}

		// TODO Auto-generated method stub
		return listBest;
	}

	public Hybrid() {
		setMethodName("Hybrid");
	}

}
