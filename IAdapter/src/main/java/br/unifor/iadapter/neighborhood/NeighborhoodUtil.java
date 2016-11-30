package br.unifor.iadapter.neighborhood;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.jorphan.collections.ListedHashTree;

import br.unifor.iadapter.algorithm.AbstractAlgorithm;
import br.unifor.iadapter.database.MySQLDatabase;
import br.unifor.iadapter.threadGroup.workload.WorkLoad;
import br.unifor.iadapter.util.WorkLoadUtil;

public class NeighborhoodUtil {
	
	
	public static List<WorkLoad> getNeighBorHoodsFirstItemOfList(AbstractAlgorithm algorithm,List<WorkLoad> list, int populationSize, List<String> testCases, int generation,
			int maxUsers, String testPlan){
		List<WorkLoad> neighborhoods = new ArrayList<WorkLoad>();
		List<WorkLoad> neighborhoodsAux = NeighborhoodUtil.getNeighborHood(list.get(0), algorithm, testCases, generation,
				maxUsers, testPlan, populationSize);
		neighborhoods.addAll(neighborhoodsAux);
		return neighborhoods;
	}
	
	
	public static List<WorkLoad> getNeighBorHoodsAllList(AbstractAlgorithm algorithm,List<WorkLoad> list, int populationSize, List<String> testCases, int generation,
			int maxUsers, String testPlan){
		List<WorkLoad> neighborhoods = new ArrayList<WorkLoad>();
		for (WorkLoad workLoad : list) {
			List<WorkLoad> neighborhoodsAux = NeighborhoodUtil.getNeighborHood(workLoad, algorithm, testCases, generation,
					maxUsers, testPlan, populationSize);
			neighborhoods.addAll(neighborhoodsAux);
			
		}
		
		
		return neighborhoods;
	}
	
	
	/**
	 * Get neighBorHood
	 * 
	 * @param workload
	 * @param nodes
	 *            WorloadControllers in script
	 * @param maxUsers
	 * @param generationTrack
	 * @return
	 */
	public static WorkLoad getNeighBorHood(WorkLoad workload, AbstractAlgorithm algorithm, List<String> scenarios,
			int maxUsers, Integer generationTrack) {

		List<Integer> parameters = new ArrayList<Integer>();
		parameters.add(WorkLoadUtil.getIndexType(workload.getType()));

		System.out.println("Parameter old: " + parameters);

		parameters = WorkLoadUtil.mutateParameter(parameters, scenarios, workload);

		System.out.println("Parameter new: " + parameters);

		int newUsers = (workload.getNumThreads());

		parameters.add(newUsers);
		parameters.add(workload.getGeneration() + 1);

		WorkLoad newWorkload = WorkLoadUtil.createWorkLoad(algorithm, scenarios, parameters, generationTrack, workload);

		return newWorkload;
	}
	
	
	/***
	 * Get neighborhoods from a specified workload
	 * 
	 * @param workload
	 * @param nodes
	 * @param tg
	 * @return
	 */
	public static List<WorkLoad> getNeighborHood(WorkLoad workload, AbstractAlgorithm algorithm, List<String> scenarios,
			int generation, int maxUsers, String testPlan, int populationSize) {

		//int usersWorst = 0;
		//try {
		//	usersWorst = MySQLDatabase.listWorstWorkload(testPlan);
		//} catch (ClassNotFoundException e) {
		//	e.printStackTrace();
		//} catch (SQLException e) {
		//	e.printStackTrace();
		//}

		//if (usersWorst > 0) {

		//	maxUsers = usersWorst;
		//}

		if (workload.getNumThreads() < 0) {
			workload.setNumThreads(0);
		}

		List<WorkLoad> list = new ArrayList<WorkLoad>();

		for (int i = 0; i < populationSize; i++) {
			WorkLoad neighbor = getNeighBorHood(workload, algorithm, scenarios, maxUsers, generation);
			neighbor.setGeneration(generation);
			System.out.println("neighbor: " + neighbor);
			list.add(neighbor);
		}

		return list;

	}

}
