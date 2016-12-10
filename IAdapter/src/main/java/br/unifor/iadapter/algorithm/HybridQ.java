package br.unifor.iadapter.algorithm;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.jorphan.collections.ListedHashTree;

import br.unifor.iadapter.database.MySQLDatabase;
import br.unifor.iadapter.neighborhood.NeighborhoodUtil;
import br.unifor.iadapter.threadGroup.workload.WorkLoad;

public class HybridQ extends AbstractAlgorithm {

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

		HillClimbingQ hill = new HillClimbingQ();

		List<WorkLoad> workloads = hill.strategy(list, populationSize, testCases, generation, maxUsers, testPlan,
				mutantProbability, bestIndividuals, collaborative, script, maxResponseTime);

		TabuAlgorithmQ tabu = new TabuAlgorithmQ();

		List<WorkLoad> workload1 = tabu.strategy(list, populationSize, testCases, generation, maxUsers, testPlan,
				mutantProbability, bestIndividuals, collaborative, script, maxResponseTime);

		GeneticAlgorithmImplementation genetic = new GeneticAlgorithmImplementation();

		List<WorkLoad> geneticList = genetic.strategy(list, populationSize, testCases, generation, maxUsers, testPlan,
				mutantProbability, bestIndividuals, collaborative, script, maxResponseTime);

		SAAlgorithmQ sa = new SAAlgorithmQ();

		List<WorkLoad> workload2 = sa.strategy(geneticList, populationSize, testCases, generation, maxUsers, testPlan,
				mutantProbability, bestIndividuals, collaborative, script, maxResponseTime);

		List<WorkLoad> listBest = new ArrayList<WorkLoad>();

		listBest.add(list.get(0));
		listBest.add(workloads.get(0));
		listBest.add(workload1.get(0));
		listBest.add(geneticList.get(0));
		listBest.add(workload2.get(0));

		List<WorkLoad> neighborhood = new ArrayList<WorkLoad>();
		try {
			neighborhood = NeighborhoodUtil.getNeighBorHoodsAllListQ(this, list, populationSize, testCases, generation,
					maxUsers, testPlan);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// TODO Auto-generated method stub
		listBest.addAll(neighborhood);
		for (WorkLoad workLoad : listBest) {

			if (!(workLoad.getName().contains("HybridQ"))) {

				workLoad.setName("HybridQ" + workLoad.getName());
			}
			workLoad.setSearchMethod(this.getMethodName());
		}

		/*Collections.sort(listBest, new Comparator<WorkLoad>() {
			@Override
			public int compare(WorkLoad workload1, WorkLoad workload2) {
				if (workload1.getFit() < workload2.getFit()) {
					return 1;
				}
				if (workload1.getFit() == workload2.getFit()) {
					return 0;
				}
				if (workload1.getFit() > workload2.getFit()) {
					return -1;
				}
				return 0;

			}
		});*/
		
		//List<WorkLoad> returnWorkLoad=new ArrayList();
		//for(int i=0;i<populationSize;i++){
			//returnWorkLoad.add(listBest.get(i));
		//}

		return listBest;
	}

	public HybridQ() {
		setMethodName("HybridQ");
	}

}
