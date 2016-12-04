package br.unifor.iadapter.algorithm;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.jorphan.collections.ListedHashTree;

import br.unifor.iadapter.neighborhood.NeighborhoodUtil;
import br.unifor.iadapter.threadGroup.workload.WorkLoad;
import br.unifor.iadapter.util.WorkLoadUtil;

public class ReinforcementLearning extends AbstractAlgorithm {
	
	public ReinforcementLearning(){
		super();
		setMethodName("Reinf");
		
	}
	
	
	class MyComparator implements Comparator {

		Map map;

		public MyComparator(Map map) {
			this.map = map;
		}

		public int compare(Object o1, Object o2) {

			return ((Integer) map.get(o2)).compareTo((Integer) map.get(o1));

		}
	}

	class Operation {
		WorkLoad old;
		WorkLoad newW;
		int func;
		int newFunc;
		boolean calculated;
		int oldFit;
		int newFit;
		int responseTime;
	}

	public static int trainingTimes;

	public static List<WorkLoad> oldWorkLoad = new ArrayList<WorkLoad>();

	public static List<WorkLoad> newWorkLoad = new ArrayList<WorkLoad>();

	public static HashMap<String, Operation> operations = new HashMap<String, Operation>();

	public static Integer epsilon=500;

	public static void reward() {

	}

	public static HashMap<String, HashMap<String,Integer>> rewards = new HashMap<String, HashMap<String,Integer>>();

	public WorkLoad neighbor(WorkLoad owkr, List<String> testCases, int maxUsers, int generation) {
		int func = WorkLoadUtil.randInt(0, 9);
		int newFunc = WorkLoadUtil.randInt(0, testCases.size() - 1);
		WorkLoad newW = null;
		try {
			newW = NeighborhoodUtil.getNeighBorHood(owkr, this, testCases, maxUsers, generation, func, newFunc,
					maxUsers);
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
		//newW.setGeneration(newW.getGeneration()+1);
		System.out.println("Generation "+newW.getGeneration());
		Operation op = new Operation();
		op.func = func;
		op.newFunc = newFunc;
		op.old = owkr;
		op.newW = newW;
		operations.put(newW.getName(), op);
		return newW;
	}
	
	public WorkLoad neighborUp(WorkLoad owkr, List<String> testCases, int maxUsers, int generation, int newFunc) {
		int func = WorkLoadUtil.randInt(0, 9);
		
		WorkLoad newW = null;
		try {
			newW = NeighborhoodUtil.getNeighBorHoodUpDown(owkr, this, testCases, maxUsers, generation, func, newFunc,
					maxUsers,true);	
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
		newW.setGeneration(generation);
		System.out.println("NEW WOrkload "+newW);
		System.out.println("Generation "+newW.getGeneration());
		newWorkLoad.add(newW);
		Operation op = new Operation();
		op.func = func;
		op.newFunc = newFunc;
		op.old = owkr;
		op.newW = newW;
		operations.put(newW.getName(), op);
		return newW;
	}
	
	public WorkLoad neighborDown(WorkLoad owkr, List<String> testCases, int maxUsers, int generation,int newFunc) {
		int func = WorkLoadUtil.randInt(0, 9);
		
		WorkLoad newW = null;
		try {
			newW = NeighborhoodUtil.getNeighBorHoodUpDown(owkr, this, testCases, maxUsers, generation, func, newFunc,
					maxUsers,false);
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
		newW.setGeneration(generation);
		System.out.println("NEW WOrkload "+newW);
		System.out.println("Generation "+newW.getGeneration());
		newWorkLoad.add(newW);
		Operation op = new Operation();
		op.func = func;
		op.newFunc = newFunc;
		op.old = owkr;
		op.newW = newW;
		operations.put(newW.getName(), op);
		return newW;
	}

	@Override
	public List<WorkLoad> strategy(List<WorkLoad> list, int populationSize, List<String> testCases, int generation,
			int maxUsers, String testPlan, int mutantProbability, int bestIndividuals, boolean collaborative,
			ListedHashTree script, int maxResponseTime) {

		if ((oldWorkLoad.size() == 0) || (oldWorkLoad == null)) {
			oldWorkLoad = list;

			operations = new HashMap<>();

			for (WorkLoad owkr : oldWorkLoad) {

				neighbor(owkr, testCases, maxUsers, generation);

			}

		} else {
			oldWorkLoad = list;
			newWorkLoad = new ArrayList<>();

			for (WorkLoad owkr : oldWorkLoad) {
				System.out.println("Operation "+operations);
				
				int range=(int) owkr.getPercentile90();
				
				
				
				Operation op = operations.get(owkr.getName());
				WorkLoad owkrOld = op.old;

				int newfunc = op.newFunc;
				int point = 0;
				System.out.println("Old " + owkrOld);
				System.out.println("Old fit" + owkrOld.getFit());
				System.out.println("new " + owkr);
				System.out.println("new fit" + owkr.getFit());
				String upDown = "";
				if (owkr.getNumThreads() > owkrOld.getNumThreads()) {
					upDown = "up";
				} else {
					upDown = "down";
				}

				if (owkr.getFit() > owkrOld.getFit()) {
					System.out.println("Contains "+rewards.containsKey(String.valueOf(range)));

					if (rewards.containsKey(String.valueOf(range))) {
						HashMap<String, Integer> map=rewards.get(String.valueOf(range));
						if (map.containsKey(upDown + "#" + newfunc)){
							point = map.get(upDown + "#" + newfunc);	
						}
						point += 2;	
						map.put(upDown + "#" + newfunc , point);
						rewards.put(String.valueOf(range),map);

					}else{
						HashMap<String, Integer> map=new HashMap<>();
						point+=2;
						map.put(upDown + "#" + newfunc , point);
						rewards.put(String.valueOf(range),map);
					}
					
					System.out.println("add 2 ");
					
				} else {
					System.out.println("Contains "+rewards.containsKey(String.valueOf(range)));
					if (rewards.containsKey(String.valueOf(range))) {
						HashMap<String, Integer> map=rewards.get(String.valueOf(range));
						if (map.containsKey(upDown + "#" + newfunc)){
							point = map.get(upDown + "#" + newfunc);	
						}
						point -= 2;	
						map.put(upDown + "#" + newfunc , point);
						rewards.put(String.valueOf(range),map);

					}else{
						HashMap<String, Integer> map=new HashMap<>();
						point-=2;
						map.put(upDown + "#" + newfunc , point);
						rewards.put(String.valueOf(range),map);
					}
					
					System.out.println("minus 2 ");

				}

				int random = WorkLoadUtil.randInt(0, 1000);
				System.out.println("Epsilon "+epsilon);
				System.out.println("Random "+random);
				HashMap<String, Integer> map=rewards.get(String.valueOf(range));
				
				System.out.println("Reward "+rewards);
				System.out.println("Map "+map);
				ReinforcementLearning.epsilon--;
				
				if ((random <= epsilon)||(map==null)) {
					neighbor(owkr, testCases, maxUsers, generation);
				} else {
					
					MyComparator comp = new MyComparator(map);
					Map<String, Integer> newMap = new TreeMap(comp);
					newMap.putAll(map);
					
					System.out.println("Order "+newMap);
					
					String key=(String) newMap.keySet().toArray()[0];
					
					System.out.println("Key "+key);
					
					String[] actions=key.split("#");
					
					System.out.println("Action "+actions[0]);
					
					System.out.println("newFunc "+actions[1]);
					
					int newFunc=Integer.valueOf(actions[1]);
					
					
					
					if (actions[0].equals("up")){
						neighborUp(owkrOld, testCases, maxUsers, generation,newFunc);
						
					}
					if (actions[0].equals("down")){
						neighborDown(owkrOld, testCases, maxUsers, generation,newFunc);
						
					}
					
					System.out.println("New Workload  "+newWorkLoad);
					
					
					

				}
				

			}
			
		}

		
		System.out.println(rewards);

		// TODO Auto-generated method stub
		return newWorkLoad;
	}

}
