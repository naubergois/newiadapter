package br.unifor.iadapter.algorithm;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

import org.apache.jorphan.collections.ListedHashTree;

import br.unifor.iadapter.database.MySQLDatabase;
import br.unifor.iadapter.neighborhood.NeighborhoodUtil;
import br.unifor.iadapter.tabu.TabuSearch;
import br.unifor.iadapter.threadGroup.workload.WorkLoad;

public class TabuAlgorithm extends AbstractAlgorithm {

	@Override
	public List<WorkLoad> strategy(List<WorkLoad> list, int populationSize, List<String> testCases, int generation,
			int maxUsers, String testPlan, int mutantProbability, int bestIndividuals, boolean collaborative,
			ListedHashTree script, int maxResponseTime) {
		List<WorkLoad> oldList = list;

		if (generation > 2) {

			try {
				list = MySQLDatabase.listWorkLoadsForNewGenerationByMethodAllGenerations(testPlan, this, populationSize);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		budget("Tabu",(int) list.get(0).getFit());
		

		if (list.size() == 0) {
			list = oldList;
		}
		List<WorkLoad> listVerified = TabuSearch.verify(list);
		TabuSearch.addTabuTable(list.get(0));
		List<WorkLoad> neighborhoods = null;
		try {
			neighborhoods = NeighborhoodUtil.getNeighBorHoodsFirstItemOfList(this, listVerified, populationSize,
					testCases, generation, maxUsers, testPlan);
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

		return neighborhoods;
	}

	public TabuAlgorithm() {
		this.setMethodName("TS");
	}

	@Override
	public void budget(String searchMethod, int maxFit) {
		// TODO Auto-generated method stub
		try {
			MySQLDatabase.insertOBudget(searchMethod, maxFit);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
