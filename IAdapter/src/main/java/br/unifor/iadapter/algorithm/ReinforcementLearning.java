package br.unifor.iadapter.algorithm;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.jorphan.collections.ListedHashTree;

import com.sun.javafx.scene.traversal.Algorithm;

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

	static class Operation {
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

	public static Integer epsilon = 100;

	public static void reward() {

	}

	public static HashMap<String, HashMap<String, Double>> Q = new HashMap<String, HashMap<String, Double>>();

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
		// newW.setGeneration(newW.getGeneration()+1);
		System.out.println("Generation " + newW.getGeneration());
		Operation op = new Operation();
		op.func = func;
		op.newFunc = newFunc;
		op.old = owkr;
		op.newW = newW;
		operations.put(newW.getName(), op);
		return newW;
	}

	public static WorkLoad neighborUp(WorkLoad owkr, List<String> testCases, int maxUsers, int generation, int newFunc,
			AbstractAlgorithm algorithm) {
		int func = WorkLoadUtil.randInt(0, 9);

		WorkLoad newW = null;
		try {
			newW = NeighborhoodUtil.getNeighBorHoodUpDown(owkr, algorithm, testCases, maxUsers, generation, func,
					newFunc, maxUsers, true);
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
		Operation op = new Operation();
		op.func = func;
		op.newFunc = newFunc;
		op.old = owkr;
		op.newW = newW;
		operations.put(newW.getName(), op);
		return newW;
	}

	public static WorkLoad neighborDown(WorkLoad owkr, List<String> testCases, int maxUsers, int generation,
			int newFunc, AbstractAlgorithm algorithm) {
		int func = WorkLoadUtil.randInt(0, 9);

		WorkLoad newW = null;
		try {
			newW = NeighborhoodUtil.getNeighBorHoodUpDown(owkr, algorithm, testCases, maxUsers, generation, func,
					newFunc, maxUsers, false);
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
		Operation op = new Operation();
		op.func = func;
		op.newFunc = newFunc;
		op.old = owkr;
		op.newW = newW;
		operations.put(newW.getName(), op);
		return newW;
	}

	public static WorkLoad neighborSame(WorkLoad owkr, List<String> testCases, int maxUsers, int generation,
			int newFunc, AbstractAlgorithm algorithm) {
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
		Operation op = new Operation();
		op.func = func;
		op.newFunc = newFunc;
		op.old = owkr;
		op.newW = newW;
		operations.put(newW.getName(), op);
		return newW;
	}

	public static void learn(WorkLoad owkr, String testPlan, int range) {

		if (operations.containsKey(owkr.getName())) {
			Operation op = operations.get(owkr.getName());
			WorkLoad owkrOld = op.old;

			int newfunc = op.newFunc;
			double point = 0;
			System.out.println("Old " + owkrOld);
			System.out.println("Old fit" + owkrOld.getFit());
			System.out.println("new " + owkr);
			System.out.println("new fit" + owkr.getFit());
			String upDown = "";
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

		List<Q> map = new ArrayList<>();
		try {
			map = MySQLDatabase.selectListQ(testPlan, String.valueOf(range));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Map " + map);
		ReinforcementLearning.epsilon--;

		WorkLoad newW = null;

		if ((map.size() == 0)) {
			int booleanT;

			if (workLoad.getPercentile90() <= maxResponseTime) {
				booleanT = 0;
			} else {
				booleanT = 1;
			}

			if (booleanT == 0) {
				int func = WorkLoadUtil.randInt(0, 9);
				int newFunc = WorkLoadUtil.randInt(0, testCases.size() - 1);
				try {
					newW = NeighborhoodUtil.getNeighBorHoodUpDown(workLoad, algorithm, testCases, maxUsers, generation,
							func, newFunc, maxUsers, true);
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

			}
			if (booleanT == 1) {
				int func = WorkLoadUtil.randInt(0, 9);
				int newFunc = WorkLoadUtil.randInt(0, testCases.size() - 1);
				try {
					newW = NeighborhoodUtil.getNeighBorHoodUpDown(workLoad, algorithm, testCases, maxUsers, generation,
							func, newFunc, maxUsers, false);
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

			}

		} else {

			Q q = map.get(0);

			String key = q.state;

			System.out.println("Key " + key);

			String[] actions = key.split("#");

			System.out.println("Action " + actions[0]);

			System.out.println("newFunc " + actions[1]);

			int newFunc = Integer.valueOf(actions[1]);

			if (actions[0].equals("up")) {
				newW = neighborUp(workLoad, testCases, maxUsers, generation, newFunc, algorithm);

			}
			if (actions[0].equals("down")) {
				newW = neighborDown(workLoad, testCases, maxUsers, generation, newFunc, algorithm);

			}
			if (actions[0].equals("same")) {
				newW = neighborSame(workLoad, testCases, maxUsers, generation, newFunc, algorithm);

			}

			System.out.println("New Workload  " + newWorkLoad);
			System.out.println("Reward " + Q);
		}
		return newW;
	}

	@Override
	public List<WorkLoad> strategy(List<WorkLoad> list, int populationSize, List<String> testCases, int generation,
			int maxUsers, String testPlan, int mutantProbability, int bestIndividuals, boolean collaborative,
			ListedHashTree script, int maxResponseTime) {

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
				// System.out.println("Operation " + operations);

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

				if (operations.containsKey(owkr.getName())) {

					Operation op = operations.get(owkr.getName());
					WorkLoad owkrOld = op.old;

					int newfunc = op.newFunc;
					double point = 0;
					System.out.println("Old " + owkrOld);
					System.out.println("Old fit" + owkrOld.getFit());
					System.out.println("new " + owkr);
					System.out.println("new fit" + owkr.getFit());
					String upDown = "";
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

				int random = WorkLoadUtil.randInt(0, 100);
				System.out.println("Epsilon " + epsilon);
				System.out.println("Random " + random);

				List<Q> map = new ArrayList<>();
				try {
					map = MySQLDatabase.selectListQ(testPlan, String.valueOf(range));
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				System.out.println("Map " + map);
				ReinforcementLearning.epsilon--;

				if ((random <= epsilon) || (map.size() == 0)) {
					int booleanT = WorkLoadUtil.randInt(0, 1);
					if (booleanT == 0) {
						int newFunc = WorkLoadUtil.randInt(0, testCases.size() - 1);
						neighborUp(owkr, testCases, maxUsers, generation, newFunc, this);

					}
					if (booleanT == 1) {
						int newFunc = WorkLoadUtil.randInt(0, testCases.size() - 1);
						neighborDown(owkr, testCases, maxUsers, generation, newFunc, this);

					}

					neighbor(owkr, testCases, maxUsers, generation);
				} else {

					Q q = map.get(0);

					String key = q.state;

					System.out.println("Key " + key);

					String[] actions = key.split("#");

					System.out.println("Action " + actions[0]);

					System.out.println("newFunc " + actions[1]);

					int newFunc = Integer.valueOf(actions[1]);

					if (actions[0].equals("up")) {
						neighborUp(owkr, testCases, maxUsers, generation, newFunc, this);

					}
					if (actions[0].equals("down")) {
						neighborDown(owkr, testCases, maxUsers, generation, newFunc, this);

					}
					if (actions[0].equals("same")) {
						neighborSame(owkr, testCases, maxUsers, generation, newFunc, this);

					}

					System.out.println("New Workload  " + newWorkLoad);
					System.out.println("Reward " + Q);
				}

			}

		}

		// TODO Auto-generated method stub
		return newWorkLoad;
	}

}
