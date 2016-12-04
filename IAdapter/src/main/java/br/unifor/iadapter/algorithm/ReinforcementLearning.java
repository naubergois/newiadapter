package br.unifor.iadapter.algorithm;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.jorphan.collections.ListedHashTree;
import org.bouncycastle.jcajce.provider.digest.GOST3411.HashMac;

import com.mongodb.util.Hash;

import br.unifor.iadapter.neighborhood.NeighborhoodUtil;
import br.unifor.iadapter.threadGroup.workload.WorkLoad;
import br.unifor.iadapter.util.WorkLoadUtil;

public class ReinforcementLearning extends AbstractAlgorithm {
	
	class Operation{
		WorkLoad old;
		WorkLoad newW;
		int func;
		int newFunc;
		boolean calculated;
		int oldFit;
		int newFit;
	}

	public static int trainingTimes;

	public static List<WorkLoad> oldWorkLoad=new ArrayList<WorkLoad>();

	public static List<WorkLoad> newWorkLoad=new ArrayList<WorkLoad>();
	
	public static HashMap<String,Operation> operations=new HashMap<String,Operation>();
	
	
	
	public static void reward(){
		
	}
	
	
	public static HashMap<String,Integer> rewards=new HashMap<String,Integer>();
	
	public WorkLoad neighbor(WorkLoad owkr,List<String> testCases,int maxUsers,int generation){
		int func=WorkLoadUtil.randInt(0, 9);
		int newFunc=WorkLoadUtil.randInt(0, testCases.size()-1);
		WorkLoad newW=null;
		try {
			newW=NeighborhoodUtil.getNeighBorHood(owkr,this, testCases, maxUsers, generation, func, newFunc, maxUsers);
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
		newWorkLoad.add(newW);
		Operation op=new Operation();
		op.func=func;
		op.newFunc=newFunc;
		op.old=owkr;
		op.newW=newW;
		operations.put(newW.getName(), op);
		return newW;
	}

	@Override
	public List<WorkLoad> strategy(List<WorkLoad> list, int populationSize, List<String> testCases, int generation,
			int maxUsers, String testPlan, int mutantProbability, int bestIndividuals, boolean collaborative,
			ListedHashTree script, int maxResponseTime) {
		
		
		
		
		if ((oldWorkLoad.size()==0)||(oldWorkLoad==null)){
			oldWorkLoad=list;
			
			operations=new HashMap<>();
			
			for (WorkLoad owkr : oldWorkLoad) {
				
			
				neighbor(owkr, testCases, maxUsers, generation);
				
			}
			
		}else{
			oldWorkLoad=list;
			newWorkLoad=new ArrayList<>();
			
			
			
			for (WorkLoad owkr : oldWorkLoad) {
				Operation op=operations.get(owkr);
				WorkLoad owkrOld=op.old;
				int func=op.func;
				int newfunc=op.newFunc;
				int point=0;
				System.out.println("Old "+ owkrOld);
				System.out.println("Old fit"+ owkrOld.getFit());
				System.out.println("new "+ owkr);
				System.out.println("new fit"+ owkr.getFit());
				if (owkr.getFit()>owkrOld.getFit()){
					
					
					if (rewards.containsKey(func+"."+newfunc)){
						point=rewards.get(func+"."+newfunc);
						
						
					}
					point+=2;
					System.out.println("add 2 ");
					rewards.put(func+"."+newfunc, point);
				}
				else{
					if (rewards.containsKey(func+"."+newfunc)){
						point=rewards.get(func+"."+newfunc);
						
						
					}
					point-=2;
					System.out.println("minus 2 ");
					rewards.put(func+"."+newfunc, point);
					
				}
				neighbor(owkr,  testCases, maxUsers, generation);
				
				
			}
		}
		
		System.out.println(rewards);
		
		

		// TODO Auto-generated method stub
		return newWorkLoad;
	}

}
