package br.unifor.iadapter.algorithm;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.jorphan.collections.ListedHashTree;

import br.unifor.iadapter.database.MySQLDatabase;
import br.unifor.iadapter.neighborhood.NeighborhoodUtil;
import br.unifor.iadapter.sa.SimulateAnnealing;
import br.unifor.iadapter.threadGroup.workload.WorkLoad;

public class SAAlgorithm extends AbstractAlgorithm {

	private static int temperature;
	public static WorkLoad currentWorkLoad;

	/**
	 * TempInicial() ? Função que calcula a temperatura inicial;
	 *
	 * @return double
	 */
	public double tempInicial() {
		return Math.random() * 100;
	}

	@Override
	public List<WorkLoad> strategy(List<WorkLoad> list, int populationSize, List<String> testCases, int generation,
			int maxUsers, String testPlan, int mutantProbability, int bestIndividuals, boolean collaborative,
			ListedHashTree script, int maxResponseTime) {
		
		if (list.size()==0){
			try {
				MySQLDatabase.listWorkLoadsForNewGenerationByMethodAllGenerations(testPlan,this);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (currentWorkLoad == null) {
			currentWorkLoad = list.get(0);
		}

		List<WorkLoad> neighborhoods=null;
		try {
			neighborhoods = NeighborhoodUtil.getNeighborHood(currentWorkLoad, this, testCases, generation,
					maxUsers, testPlan, populationSize);
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

		if (this.getListWorkLoads().size() > 0) {
			System.out.println("SA Temperature:" + temperature);
			SimulateAnnealing.sa(this, neighborhoods, tempInicial(), 0.9);
		}
		
		List<WorkLoad> returnWorkload=new ArrayList<WorkLoad>();
		returnWorkload.add(currentWorkLoad);

		return returnWorkload;
	}

	public SAAlgorithm() {
		this.setMethodName("SA");
	}

}
