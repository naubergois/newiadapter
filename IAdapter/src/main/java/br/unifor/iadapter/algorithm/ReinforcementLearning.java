package br.unifor.iadapter.algorithm;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.jorphan.collections.ListedHashTree;

import br.unifor.iadapter.database.MySQLDatabase;
import br.unifor.iadapter.neighborhood.NeighborhoodUtil;
import br.unifor.iadapter.threadGroup.workload.WorkLoad;
import br.unifor.iadapter.util.WorkLoadUtil;

public class ReinforcementLearning extends AbstractAlgorithm {

	private static double alpha = 0.01;

	public ReinforcementLearning() {
		super();
		setMethodName("Reinf");

	}

	class MyComparator implements Comparator {

		Map map;

		public MyComparator(Map map) {
			this.map = map;
		}

		public int compare(Object o1, Object o2) {

			return ((Double) map.get(o2)).compareTo((Double) map.get(o1));

		}
	}

	public static int trainingTimes;

	public static List<WorkLoad> oldWorkLoad = new ArrayList<WorkLoad>();

	public static List<WorkLoad> newWorkLoad = new ArrayList<WorkLoad>();

	

	public static void reward() {

	}

	public static HashMap<String, HashMap<String, Double>> Q = new HashMap<String, HashMap<String, Double>>();

	public WorkLoad neighbor(WorkLoad owkr, List<String> testCases, int maxUsers, int generation,String testPlan) {
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
		// newW.setGeneration(newW.getGeneration()+1);
		System.out.println("Generation " + newW.getGeneration());
		
		
		try {
			int id=MySQLDatabase.getWorkLoadID(owkr.getName(), testPlan);
			MySQLDatabase.insertOperation(newW.getName(), testPlan, func, newFunc,id );
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newW;
	}

	public static WorkLoad neighborUp(WorkLoad owkr, List<String> testCases, int maxUsers, int generation, int newFunc,
			AbstractAlgorithm algorithm, String testPlan) {
		int func = WorkLoadUtil.randInt(0, 9);

		WorkLoad newW = null;
		try {
			newW = NeighborhoodUtil.getNeighBorHoodUpDown(owkr, algorithm, testCases, maxUsers, generation, func,
					newFunc, maxUsers, true, testPlan);
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
		System.out.println("NEW WOrkload " + newW);
		System.out.println("Generation " + newW.getGeneration());
		newWorkLoad.add(newW);
		try {
			int id=MySQLDatabase.getWorkLoadID(owkr.getName(), testPlan);
			MySQLDatabase.insertOperation(newW.getName(), testPlan, func, newFunc,id );
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newW;
	}

	public static WorkLoad neighborDown(WorkLoad owkr, List<String> testCases, int maxUsers, int generation,
			int newFunc, AbstractAlgorithm algorithm, String testPlan) {
		int func = WorkLoadUtil.randInt(0, 9);

		WorkLoad newW = null;
		try {
			newW = NeighborhoodUtil.getNeighBorHoodUpDown(owkr, algorithm, testCases, maxUsers, generation, func,
					newFunc, maxUsers, false, testPlan);
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
		System.out.println("NEW WOrkload " + newW);
		System.out.println("Generation " + newW.getGeneration());
		newWorkLoad.add(newW);
		try {
			int id=MySQLDatabase.getWorkLoadID(owkr.getName(), testPlan);
			MySQLDatabase.insertOperation(newW.getName(), testPlan, func, newFunc,id );
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newW;
	}

	public static WorkLoad neighborSame(WorkLoad owkr, List<String> testCases, int maxUsers, int generation,
			int newFunc, AbstractAlgorithm algorithm,String testPlan) {
		int func = WorkLoadUtil.randInt(0, 9);

		WorkLoad newW = null;
		try {
			newW = NeighborhoodUtil.getNeighBorHoodSame(owkr, algorithm, testCases, maxUsers, generation, func, newFunc,
					maxUsers, false);
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
		System.out.println("NEW WOrkload " + newW);
		System.out.println("Generation " + newW.getGeneration());
		newWorkLoad.add(newW);
		try {
			int id=MySQLDatabase.getWorkLoadID(owkr.getName(), testPlan);
			MySQLDatabase.insertOperation(newW.getName(), testPlan, func, newFunc,id );
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newW;
	}

	public static void learn(WorkLoad owkr, String testPlan, int range) {
		
		Operation op=null;
		try {
			op = MySQLDatabase.selectOperation(owkr.getName(), testPlan);
		} catch (ClassNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		if (op!=null) {
			
			WorkLoad owkrOld = op.old;

			int newfunc = op.newFunc;
			double point = 0;
			System.out.println("Old " + owkrOld);
			System.out.println("Old fit" + owkrOld.getFit());
			System.out.println("new " + owkr);
			System.out.println("new fit" + owkr.getFit());
			String upDown = "";
			int epsilon = 0;
			try {
				epsilon = MySQLDatabase.selectEpsilonQ(testPlan);
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (epsilon > 0) {
				if (owkr.getNumThreads() > (owkrOld.getNumThreads())) {
					upDown = "up";
				}

				if ((owkr.getNumThreads() == (owkrOld.getNumThreads()))) {
					upDown = "same";
				}

				if ((owkr.getNumThreads() < (owkrOld.getNumThreads()))) {
					upDown = "down";
				}

			} else {
				if (owkr.getNumThreads() > (1.2 * owkrOld.getNumThreads())) {
					upDown = "up";
				}

				if ((owkr.getNumThreads() < (1.2 * owkrOld.getNumThreads()))
						&& (owkr.getNumThreads() > (0.8 * owkrOld.getNumThreads()))) {
					upDown = "same";
				}

				if ((owkr.getNumThreads() < (0.8 * owkrOld.getNumThreads()))) {
					upDown = "down";
				}
			}
			// String
			// scenarioString=WorkLoadUtil.getWorkLoadScenarios(owkr,testCases);
			HashMap<String, Q> listQ = new HashMap();
			try {
				listQ = MySQLDatabase.selectQ(testPlan, String.valueOf(range));
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			System.out.println("List Q" + listQ);

			if (owkr.getFit() > owkrOld.getFit()) {
				point = 0;
				if (listQ.containsKey(upDown + "#" + newfunc)) {

					Q q = listQ.get(upDown + "#" + newfunc);
					point = q.q;
				}
				double q = ReinforcementLearning.alpha * 2 + (1 - ReinforcementLearning.alpha) * point;
				try {
					MySQLDatabase.insertQ(String.valueOf(range), testPlan, upDown + "#" + newfunc, q);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else {
				point = 0;

				if (listQ.containsKey(upDown + "#" + newfunc)) {

					Q q = listQ.get(upDown + "#" + newfunc);
					point = q.q;
				}
				double q = ReinforcementLearning.alpha * -2 + (1 - ReinforcementLearning.alpha) * point;
				try {
					MySQLDatabase.insertQ(String.valueOf(range), testPlan, upDown + "#" + newfunc, q);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}

	}

	public static WorkLoad getNeighborQ(AbstractAlgorithm algorithm, WorkLoad workLoad, String testPlan,
			List<String> testCases, int generation, int maxUsers, int maxResponseTime) {

		int range = 1;

		if (workLoad.getPercentile90() > 1.2 * maxResponseTime) {
			range = 3;
		}
		if ((workLoad.getPercentile90() > 0.8 * maxResponseTime)
				&& (workLoad.getPercentile90() <= 1.2 * maxResponseTime)) {
			range = 2;
		}
		if ((workLoad.getPercentile90() <= 0.8 * maxResponseTime)) {
			range = 1;
		}

		learn(workLoad, testPlan, range);

		int epsilon = 0;

		try {
			epsilon = MySQLDatabase.selectEpsilonQ(testPlan);

			System.out.println("Epsilon " + epsilon);
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		List<Q> list = new ArrayList<>();
		try {
			list = MySQLDatabase.selectListQZero(testPlan, range);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		WorkLoad newW = null;

		if ((epsilon > 0) && (list.size() > 0)) {

			Q q = list.get(0);

			newW = getQ(q, workLoad, testCases, maxUsers, generation, algorithm, testPlan);

		} else {

			List<Q> map = new ArrayList<>();
			System.out.println("Map " + map);
			try {
				map = MySQLDatabase.selectListQ(testPlan, String.valueOf(range));
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Q q = map.get(0);

			newW = getQ(q, workLoad, testCases, maxUsers, generation, algorithm, testPlan);
		}
		return newW;
	}

	public static WorkLoad getQ(Q q, WorkLoad workLoad, List<String> testCases, int maxUsers, int generation,
			AbstractAlgorithm algorithm, String testPlan) {

		WorkLoad newW = null;

		String key = q.state;

		System.out.println("Key " + key);

		String[] actions = key.split("#");

		System.out.println("Action " + actions[0]);

		System.out.println("newFunc " + actions[1]);

		int newFunc = Integer.valueOf(actions[1]);

		if (actions[0].equals("up")) {
			newW = neighborUp(workLoad, testCases, maxUsers, generation, newFunc, algorithm, testPlan);

		}
		if (actions[0].equals("down")) {
			newW = neighborDown(workLoad, testCases, maxUsers, generation, newFunc, algorithm, testPlan);

		}
		if (actions[0].equals("same")) {
			newW = neighborSame(workLoad, testCases, maxUsers, generation, newFunc, algorithm,testPlan);

		}

		System.out.println("New Workload  " + newWorkLoad);
		return newW;
	}
	
	public void initQ(String testPlan,List<String> testCases){
		for (int i = 1; i < 4; i = i + 1) {
			for (int j = 0; j < testCases.size(); j++) {
				for (int m = 0; m < 3; m++) {
					if (m == 0) {
						HashMap<String, Double> map = Q.get(String.valueOf(i));
						if (map == null) {
							map = new HashMap<>();
						}
						double q = 0;
						map.put("up#" + j, q);
						try {
							MySQLDatabase.insertQifNotExist(String.valueOf(i), testPlan, "up#" + j, q);
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
					if (m == 1) {
						HashMap<String, Double> map = Q.get(String.valueOf(i));
						if (map == null) {
							map = new HashMap<>();
						}
						double q = 0;
						try {
							MySQLDatabase.insertQifNotExist(String.valueOf(i), testPlan, "down#" + j, q);
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
					if (m == 2) {
						HashMap<String, Double> map = Q.get(String.valueOf(i));
						if (map == null) {
							map = new HashMap<>();
						}
						double q = 0;
						try {
							MySQLDatabase.insertQifNotExist(String.valueOf(i), testPlan, "same#" + j, q);
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				}
			}
		}
	}

	@Override
	public List<WorkLoad> strategy(List<WorkLoad> list, int populationSize, List<String> testCases, int generation,
			int maxUsers, String testPlan, int mutantProbability, int bestIndividuals, boolean collaborative,
			ListedHashTree script, int maxResponseTime) {
		
		budget("Reinf", (int) list.get(0).getFit());

	
		int epsilon = 0;

		try {
			epsilon = MySQLDatabase.selectEpsilonQ(testPlan);
		} catch (ClassNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		if ((oldWorkLoad.size() == 0) || (oldWorkLoad == null)) {
			oldWorkLoad = list;

			initQ(testPlan, testCases);
			

			for (WorkLoad owkr : oldWorkLoad) {

				neighbor(owkr, testCases, maxUsers, generation,testPlan);

			}

		} else {

			oldWorkLoad = list;
			newWorkLoad = new ArrayList<>();

			for (WorkLoad owkr : oldWorkLoad) {

				int range = 1;

				if (owkr.getPercentile90() > 1.2 * maxResponseTime) {
					range = 3;
				}
				if ((owkr.getPercentile90() > 0.8 * maxResponseTime)
						&& (owkr.getPercentile90() <= 1.2 * maxResponseTime)) {
					range = 2;
				}
				if ((owkr.getPercentile90() <= 0.8 * maxResponseTime)) {
					range = 1;
				}

				learn(owkr, testPlan, range);

				WorkLoad newW = getNeighborQ(this, owkr, testPlan, testCases, generation, maxUsers, maxResponseTime);

				newWorkLoad.add(newW);

			}

		}

		// TODO Auto-generated method stub
		return newWorkLoad;
	}

	@Override
	public void budget(String searchMethod, int maxFit) {
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
