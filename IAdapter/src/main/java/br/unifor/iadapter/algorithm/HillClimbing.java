package br.unifor.iadapter.algorithm;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.jmeter.testelement.TestElement;
import org.apache.jorphan.collections.ListedHashTree;

import br.unifor.iadapter.neighborhood.NeighborhoodUtil;
import br.unifor.iadapter.threadGroup.workload.WorkLoad;
import br.unifor.iadapter.threadGroup.workload.WorkLoadThreadGroup;
import br.unifor.iadapter.util.WorkLoadUtil;

public class HillClimbing extends AbstractAlgorithm {
	
	public static WorkLoad currentWorkLoad;

	@Override
	public List<WorkLoad> strategy(List<WorkLoad> list, int populationSize, List<String> testCases, int generation,
			int maxUsers, String testPlan, int mutantProbability, int bestIndividuals, boolean collaborative,
			ListedHashTree script,int maxResponseTime){
		
		if(currentWorkLoad==null){
			currentWorkLoad=list.get(0);
		}
		
		for (WorkLoad newPlace : list){
			if (newPlace.getFit()>currentWorkLoad.getFit()){
				currentWorkLoad=newPlace;
			}
		}
		
		

		List<WorkLoad> newList = new ArrayList<WorkLoad>();
		newList.add(currentWorkLoad);
		
		
		 

		List<WorkLoad> newNeighboors=null;
		try {
			newNeighboors = NeighborhoodUtil.getNeighBorHoodsFirstItemOfListSamePath(this, newList, populationSize, testCases, generation, maxUsers, testPlan);
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
		return newNeighboors;
	}

	public HillClimbing() {
		setMethodName("HC");
	}

}
