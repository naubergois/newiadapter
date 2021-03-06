package br.unifor.iadapter.neighborhood;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.unifor.iadapter.algorithm.AbstractAlgorithm;
import br.unifor.iadapter.algorithm.ReinforcementLearning;
import br.unifor.iadapter.database.MySQLDatabase;
import br.unifor.iadapter.threadGroup.FactoryWorkLoad;
import br.unifor.iadapter.threadGroup.workload.WorkLoad;
import br.unifor.iadapter.util.WorkLoadUtil;

public class NeighborhoodUtil {

	public static List<WorkLoad> getNeighBorHoodsFirstItemOfList(AbstractAlgorithm algorithm, List<WorkLoad> list,
			int populationSize, List<String> testCases, int generation, int maxUsers, String testPlan)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException, ClassNotFoundException {
		List<WorkLoad> neighborhoods = new ArrayList<WorkLoad>();
		List<WorkLoad> neighborhoodsAux = NeighborhoodUtil.getNeighborHood(list.get(0), algorithm, testCases,
				generation, maxUsers, testPlan, populationSize);
		neighborhoods.addAll(neighborhoodsAux);
		return neighborhoods;
	}

	public static List<WorkLoad> getNeighBorHoodsFirstItemOfListQ(AbstractAlgorithm algorithm, List<WorkLoad> list,
			int populationSize, List<String> testCases, int generation, int maxUsers, String testPlan,
			int maxResponseTime) throws InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException {
		List<WorkLoad> neighborhoods = new ArrayList<WorkLoad>();
		WorkLoad neighborhoodsAux = ReinforcementLearning.getNeighborQ(algorithm, list.get(0), testPlan, testCases,
				generation, maxUsers, maxResponseTime);

		neighborhoods.add(neighborhoodsAux);
		return neighborhoods;
	}

	public static List<WorkLoad> getNeighBorHoodsFirstItemOfListSamePath(AbstractAlgorithm algorithm,
			List<WorkLoad> list, int populationSize, List<String> testCases, int generation, int maxUsers,
			String testPlan) throws InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException {
		List<WorkLoad> neighborhoods = new ArrayList<WorkLoad>();
		List<WorkLoad> neighborhoodsAux = NeighborhoodUtil.getNeighborHoodSamePath(list.get(0), algorithm, testCases,
				generation, maxUsers, testPlan, populationSize);
		neighborhoods.addAll(neighborhoodsAux);
		return neighborhoods;
	}

	public static List<WorkLoad> getNeighBorHoodsFirstItemOfListSamePathQ(AbstractAlgorithm algorithm,
			List<WorkLoad> list, int populationSize, List<String> testCases, int generation, int maxUsers,
			String testPlan, int maxResponseTime)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException, ClassNotFoundException {
		List<WorkLoad> neighborhoods = new ArrayList<WorkLoad>();
		List<WorkLoad> neighborhoodsAux = NeighborhoodUtil.getNeighborHoodSamePathQ(list.get(0), algorithm, testCases,
				generation, maxUsers, testPlan, populationSize, maxResponseTime);
		neighborhoods.addAll(neighborhoodsAux);
		return neighborhoods;
	}

	public static List<WorkLoad> getNeighBorHoodsAllList(AbstractAlgorithm algorithm, List<WorkLoad> list,
			int populationSize, List<String> testCases, int generation, int maxUsers, String testPlan)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException, ClassNotFoundException {
		List<WorkLoad> neighborhoods = new ArrayList<WorkLoad>();
		for (WorkLoad workLoad : list) {
			List<WorkLoad> neighborhoodsAux = NeighborhoodUtil.getNeighborHood(workLoad, algorithm, testCases,
					generation, maxUsers, testPlan, populationSize);
			neighborhoods.addAll(neighborhoodsAux);

		}

		return neighborhoods;
	}

	public static List<WorkLoad> getNeighBorHoodsAllListQ(AbstractAlgorithm algorithm, List<WorkLoad> list,
			int populationSize, List<String> testCases, int generation, int maxUsers, String testPlan,
			int maxResponseTime) throws InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException {
		List<WorkLoad> neighborhoods = new ArrayList<WorkLoad>();
		for (WorkLoad workLoad : list) {
			List<WorkLoad> neighborhoodsAux = NeighborhoodUtil.getNeighborHoodQ(workLoad, algorithm, testCases,
					generation, maxUsers, testPlan, populationSize, maxResponseTime);
			neighborhoods.addAll(neighborhoodsAux);

		}

		return neighborhoods;
	}

	public static WorkLoad getNeighBorHoodMutant(AbstractAlgorithm algorithm, WorkLoad workload, List<String> nodes,
			int maxUsers, int generation)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException, ClassNotFoundException {

		List<Integer> parameters = new ArrayList<Integer>();
		parameters.add(WorkLoadUtil.getIndexByType(workload.getType()));

		parameters = WorkLoadUtil.mutateParameter(parameters, nodes, workload);

		int newUsers = WorkLoadUtil.randInt(0, maxUsers);

		parameters.add(newUsers);
		parameters.add(workload.getGeneration() + 1);

		WorkLoad newWorkload = createWorkLoadMutant(algorithm, nodes, parameters, generation, newUsers);

		return newWorkload;
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
	 * @throws ClassNotFoundException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public static WorkLoad getNeighBorHood(WorkLoad workload, AbstractAlgorithm algorithm, List<String> scenarios,
			int maxUsers, Integer generationTrack)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException, ClassNotFoundException {

		List<Integer> parameters = new ArrayList<Integer>();
		parameters.add(WorkLoadUtil.getIndexByType(workload.getType()));

		System.out.println("Parameter old: " + parameters);

		parameters = WorkLoadUtil.mutateParameter(parameters, scenarios, workload);

		System.out.println("Parameter new: " + parameters);

		int newUsers = (workload.getNumThreads());

		parameters.add(newUsers);
		parameters.add(workload.getGeneration() + 1);

		WorkLoad newWorkload = WorkLoadUtil.createWorkLoad(algorithm, scenarios, parameters, generationTrack, workload);

		return newWorkload;
	}

	public static WorkLoad getNeighBorHood(WorkLoad workload, AbstractAlgorithm algorithm, List<String> scenarios,
			int maxUsers, Integer generationTrack, int func, int newFunc, int users)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException, ClassNotFoundException {

		List<Integer> parameters = new ArrayList<Integer>();
		parameters.add(WorkLoadUtil.getIndexByType(workload.getType()));

		System.out.println("Parameter old: " + parameters);

		parameters = WorkLoadUtil.mutateParameter(parameters, scenarios, workload, func, newFunc);

		System.out.println("Parameter new: " + parameters);

		int newUsers = (workload.getNumThreads());

		parameters.add(newUsers);
		parameters.add(workload.getGeneration() + 1);

		WorkLoad newWorkload = WorkLoadUtil.createWorkLoad(algorithm, scenarios, parameters, generationTrack, workload);

		return newWorkload;
	}

	public static WorkLoad getNeighBorHoodUpDown(WorkLoad workload, AbstractAlgorithm algorithm, List<String> scenarios,
			int maxUsers, Integer generationTrack, int func, int newFunc, int users, boolean up,String testPlan)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException, ClassNotFoundException {

		List<Integer> parameters = new ArrayList<Integer>();
		parameters.add(WorkLoadUtil.getIndexByType(workload.getType()));

		System.out.println("Parameter old: " + parameters);

		parameters = WorkLoadUtil.mutateParameter(parameters, scenarios, workload, func, newFunc);

		System.out.println("Parameter new: " + parameters);

		int newUsers = (workload.getNumThreads());

		int delta = 0;
		
		int epsilon=0;
		
		
		
		try {
			epsilon = MySQLDatabase.selectEpsilonQ(testPlan);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

		if (epsilon > 0) {
			if (up) {
				delta = WorkLoadUtil.randInt(1, 4);
			} else {
				delta = WorkLoadUtil.randInt(-4, -1);
			}

		} else {
			if (up) {
				delta = WorkLoadUtil.randInt(1, 3);
			} else {
				delta = WorkLoadUtil.randInt(-3, -1);
			}
		}

		parameters.add(newUsers);
		parameters.add(workload.getGeneration() + 1);

		WorkLoad newWorkload = WorkLoadUtil.createWorkLoad(algorithm, scenarios, parameters, generationTrack, workload,
				delta);

		return newWorkload;
	}

	public static WorkLoad getNeighBorHoodSame(WorkLoad workload, AbstractAlgorithm algorithm, List<String> scenarios,
			int maxUsers, Integer generationTrack, int func, int newFunc, int users, boolean up)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException, ClassNotFoundException {

		List<Integer> parameters = new ArrayList<Integer>();
		parameters.add(WorkLoadUtil.getIndexByType(workload.getType()));

		System.out.println("Parameter old: " + parameters);

		parameters = WorkLoadUtil.mutateParameter(parameters, scenarios, workload, func, newFunc);

		System.out.println("Parameter new: " + parameters);

		int newUsers = (workload.getNumThreads());

		parameters.add(newUsers);
		parameters.add(workload.getGeneration() + 1);

		WorkLoad newWorkload = WorkLoadUtil.createWorkLoad(algorithm, scenarios, parameters, generationTrack, workload,
				0);

		return newWorkload;
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
	 * @throws ClassNotFoundException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public static WorkLoad getNeighBorHoodSamePath(WorkLoad workload, AbstractAlgorithm algorithm,
			List<String> scenarios, int maxUsers, Integer generationTrack)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException, ClassNotFoundException {

		List<Integer> parameters = new ArrayList<Integer>();
		parameters.add(WorkLoadUtil.getIndexByType(workload.getType()));

		int newUsers = (workload.getNumThreads());

		parameters.add(newUsers);
		parameters.add(workload.getGeneration() + 1);

		WorkLoad newWorkload = WorkLoadUtil.createWorkLoadSamePath(algorithm, scenarios, parameters, generationTrack,
				workload);

		return newWorkload;
	}

	public static WorkLoad createWorkLoadMutant(AbstractAlgorithm algorithm, List<String> nodes,
			List<Integer> parametros, int generation, int maxUsersParameter)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException, ClassNotFoundException {
		WorkLoad workload = null;
		workload = FactoryWorkLoad.createWorkLoad();
		workload.setFunction1(WorkLoadUtil.getNameFromString(nodes, parametros.get(1)));
		workload.setFunction2(WorkLoadUtil.getNameFromString(nodes, parametros.get(2)));
		workload.setFunction3(WorkLoadUtil.getNameFromString(nodes, parametros.get(3)));
		workload.setFunction4(WorkLoadUtil.getNameFromString(nodes, parametros.get(4)));
		workload.setFunction5(WorkLoadUtil.getNameFromString(nodes, parametros.get(5)));
		workload.setFunction6(WorkLoadUtil.getNameFromString(nodes, parametros.get(6)));
		workload.setFunction7(WorkLoadUtil.getNameFromString(nodes, parametros.get(7)));
		workload.setFunction8(WorkLoadUtil.getNameFromString(nodes, parametros.get(8)));
		workload.setFunction9(WorkLoadUtil.getNameFromString(nodes, parametros.get(9)));
		workload.setFunction10(WorkLoadUtil.getNameFromString(nodes, parametros.get(10)));

		int maxUser = maxUsersParameter / 10;

		int users1 = WorkLoadUtil.randInt(0, maxUser);
		int users2 = WorkLoadUtil.randInt(0, maxUser);
		int users3 = WorkLoadUtil.randInt(0, maxUser);
		int users4 = WorkLoadUtil.randInt(0, maxUser);
		int users5 = WorkLoadUtil.randInt(0, maxUser);
		int users6 = WorkLoadUtil.randInt(0, maxUser);
		int users7 = WorkLoadUtil.randInt(0, maxUser);
		int users8 = WorkLoadUtil.randInt(0, maxUser);
		int users9 = WorkLoadUtil.randInt(0, maxUser);
		int users10 = WorkLoadUtil.randInt(0, maxUser);

		workload.setUsers1(users1);
		workload.setUsers2(users2);
		workload.setUsers3(users3);
		workload.setUsers4(users4);
		workload.setUsers5(users5);
		workload.setUsers6(users6);
		workload.setUsers7(users7);
		workload.setUsers8(users8);
		workload.setUsers9(users9);
		workload.setUsers10(users10);

		workload.setSearchMethod(algorithm.getMethodName());
		workload.setGeneration(parametros.get(12));
		workload.setActive(true);

		workload.setNumThreads(workload.getUsers1() + workload.getUsers2() + workload.getUsers3() + workload.getUsers4()
				+ workload.getUsers5() + workload.getUsers6() + workload.getUsers7() + workload.getUsers8()
				+ workload.getUsers9() + workload.getUsers10());

		if (workload.getNumThreads() == 0) {
			workload.setNumThreads(1);
			workload.setUsers1(1);
		}

		workload.setName(algorithm.getMethodName() + ":Mutant" + ":" + generation + ":" + workload.getType() + "-"
				+ workload.getNumThreads() + "-" + workload.getFunction1() + "-" + workload.getFunction2() + "-"
				+ workload.getFunction3() + "-" + workload.getFunction4() + "-" + workload.getFunction5() + "-"
				+ workload.getFunction6() + "-" + workload.getFunction7() + "-" + workload.getFunction8() + "-"
				+ workload.getFunction9() + "-" + workload.getFunction10());

		return workload;
	}

	/***
	 * Get neighborhoods from a specified workload
	 * 
	 * @param workload
	 * @param nodes
	 * @param tg
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public static List<WorkLoad> getNeighborHood(WorkLoad workload, AbstractAlgorithm algorithm, List<String> scenarios,
			int generation, int maxUsers, String testPlan, int populationSize)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException, ClassNotFoundException {

		// int usersWorst = 0;
		// try {
		// usersWorst = MySQLDatabase.listWorstWorkload(testPlan);
		// } catch (ClassNotFoundException e) {
		// e.printStackTrace();
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }

		// if (usersWorst > 0) {

		// maxUsers = usersWorst;
		// }

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

	public static List<WorkLoad> getNeighborHoodQ(WorkLoad workload, AbstractAlgorithm algorithm,
			List<String> scenarios, int generation, int maxUsers, String testPlan, int populationSize,
			int maxResponseTime) throws InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException {

		// int usersWorst = 0;
		// try {
		// usersWorst = MySQLDatabase.listWorstWorkload(testPlan);
		// } catch (ClassNotFoundException e) {
		// e.printStackTrace();
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }

		// if (usersWorst > 0) {

		// maxUsers = usersWorst;
		// }

		if (workload.getNumThreads() < 0) {
			workload.setNumThreads(0);
		}

		List<WorkLoad> list = new ArrayList<WorkLoad>();

		for (int i = 0; i < populationSize; i++) {
			WorkLoad neighbor = ReinforcementLearning.getNeighborQ(algorithm, workload, testPlan, scenarios, generation,
					maxUsers, maxResponseTime);
			neighbor.setGeneration(generation);
			System.out.println("neighbor: " + neighbor);
			list.add(neighbor);
		}

		return list;

	}

	/*
	 * Get neighborhoods from a specified workload
	 * 
	 * @param workload
	 * 
	 * @param nodes
	 * 
	 * @param tg
	 * 
	 * @return
	 * 
	 * @throws ClassNotFoundException
	 * 
	 * @throws SecurityException
	 * 
	 * @throws NoSuchMethodException
	 * 
	 * @throws InvocationTargetException
	 * 
	 * @throws IllegalArgumentException
	 * 
	 * @throws IllegalAccessException
	 * 
	 * @throws InstantiationException
	 */
	public static List<WorkLoad> getNeighborHoodSamePath(WorkLoad workload, AbstractAlgorithm algorithm,
			List<String> scenarios, int generation, int maxUsers, String testPlan, int populationSize)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException, ClassNotFoundException {

		// int usersWorst = 0;
		// try {
		// usersWorst = MySQLDatabase.listWorstWorkload(testPlan);
		// } catch (ClassNotFoundException e) {
		// e.printStackTrace();
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }

		// if (usersWorst > 0) {

		// maxUsers = usersWorst;
		// }

		if (workload.getNumThreads() < 0) {
			workload.setNumThreads(0);
		}

		List<WorkLoad> list = new ArrayList<WorkLoad>();

		for (int i = 0; i < populationSize; i++) {
			WorkLoad neighbor = getNeighBorHoodSamePath(workload, algorithm, scenarios, maxUsers, generation);
			neighbor.setGeneration(generation);
			System.out.println("neighbor: " + neighbor);
			list.add(neighbor);
		}

		return list;

	}

	public static List<WorkLoad> getNeighborHoodSamePathQ(WorkLoad workload, AbstractAlgorithm algorithm,
			List<String> scenarios, int generation, int maxUsers, String testPlan, int populationSize,
			int maxResponseTime) throws InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException {

		// int usersWorst = 0;
		// try {
		// usersWorst = MySQLDatabase.listWorstWorkload(testPlan);
		// } catch (ClassNotFoundException e) {
		// e.printStackTrace();
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }

		// if (usersWorst > 0) {

		// maxUsers = usersWorst;
		// }

		if (workload.getNumThreads() < 0) {
			workload.setNumThreads(0);
		}

		List<WorkLoad> list = new ArrayList<WorkLoad>();

		for (int i = 0; i < populationSize; i++) {
			WorkLoad neighbor = ReinforcementLearning.getNeighborQ(algorithm, workload, testPlan, scenarios, generation,
					maxUsers, maxResponseTime);
			neighbor.setGeneration(generation);
			System.out.println("neighbor: " + neighbor);
			list.add(neighbor);
		}

		return list;

	}

}
