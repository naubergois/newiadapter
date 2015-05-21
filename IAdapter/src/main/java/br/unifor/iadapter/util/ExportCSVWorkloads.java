package br.unifor.iadapter.util;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import br.unifor.iadapter.threadGroup.workload.WorkLoad;

public class ExportCSVWorkloads {

	HashMap<String, Double> generationFITSum = new HashMap<String, Double>();
	HashMap<String, String> generationGenes = new HashMap<String, String>();
	HashMap<String, Integer> generationCount = new HashMap<String, Integer>();
	HashMap<String, Integer> generationUserCount = new HashMap<String, Integer>();
	HashMap<String, Integer> functionCount = new HashMap<String, Integer>();
	HashMap<String, Integer> functions = new HashMap<String, Integer>();
	HashMap<String, Double> generationFITAverage = new HashMap<String, Double>();
	HashMap<String, Double> generationFITAveragePerUser = new HashMap<String, Double>();
	HashMap<String, Double> generationFITMax = new HashMap<String, Double>();
	HashMap<String, Double> generationFITMin = new HashMap<String, Double>();

	private void verifyHashMapsFunction(String function) {
		if (functions.containsKey(function)) {
			int countFunction = functions.get(function);
			countFunction++;
			functions.put(function, countFunction);
		} else {
			int countFunction = 0;
			countFunction++;
			functions.put(function, countFunction);
		}
	}

	private void verifyHashMapsFunctionCount(String function) {
		if (functionCount.containsKey(function)) {
			int countFunction = functionCount.get(function);
			countFunction++;
			functionCount.put(function, countFunction);
		} else {
			int countFunction = 0;
			countFunction++;
			functionCount.put(function, countFunction);
		}
	}

	public String export(List<WorkLoad> list) {

		StringBuffer buffer = new StringBuffer();
		buffer.append("Generation,AverageFitPerUSer,AverageFit,FitMax,FitMin,Genes,Name,Fit,Users\n");
		for (WorkLoad workLoad : list) {
			String name = workLoad.getName();
			int newGenerationAux = WorkLoadUtil.getGenerationFromName(name);
			if (generationFITSum.containsKey(String.valueOf(newGenerationAux))) {
				double sum = generationFITSum.get(String
						.valueOf(newGenerationAux));
				int count = generationCount.get(String
						.valueOf(newGenerationAux));
				int countUser = generationUserCount.get(String
						.valueOf(newGenerationAux));
				double max = generationFITMax.get(String
						.valueOf(newGenerationAux));
				double min = generationFITMin.get(String
						.valueOf(newGenerationAux));
				if (workLoad.getFit() > max) {
					max = workLoad.getFit();
					generationFITMax.put(String.valueOf(newGenerationAux), max);
				}
				if (workLoad.getFit() < min) {
					min = workLoad.getFit();
					generationFITMin.put(String.valueOf(newGenerationAux), min);
				}
				count++;
				countUser += workLoad.getNumThreads();
				sum += workLoad.getFit();
				generationFITSum.put(String.valueOf(newGenerationAux), sum);
				generationCount.put(String.valueOf(newGenerationAux), count);
				generationUserCount.put(String.valueOf(newGenerationAux),
						countUser);

			} else {
				double sum = 0;
				int count = 1;
				sum += workLoad.getFit();
				int countUser = workLoad.getNumThreads();
				double max = workLoad.getFit();
				double min = workLoad.getFit();

				generationFITSum.put(String.valueOf(newGenerationAux), sum);
				generationCount.put(String.valueOf(newGenerationAux), count);
				generationFITMax.put(String.valueOf(newGenerationAux), max);
				generationFITMin.put(String.valueOf(newGenerationAux), min);
				generationUserCount.put(String.valueOf(newGenerationAux),
						countUser);
			}

			String function = workLoad.getFunction1();
			verifyHashMapsFunction(function);
			function = function + newGenerationAux;
			verifyHashMapsFunctionCount(function);

			function = workLoad.getFunction2();
			verifyHashMapsFunction(function);
			function = function + newGenerationAux;
			verifyHashMapsFunctionCount(function);

			function = workLoad.getFunction3();
			verifyHashMapsFunction(function);
			function = function + newGenerationAux;
			verifyHashMapsFunctionCount(function);

			function = workLoad.getFunction4();
			verifyHashMapsFunction(function);
			function = function + newGenerationAux;
			verifyHashMapsFunctionCount(function);

			function = workLoad.getFunction5();
			verifyHashMapsFunction(function);
			function = function + newGenerationAux;
			verifyHashMapsFunctionCount(function);

			function = workLoad.getFunction6();
			verifyHashMapsFunction(function);
			function = function + newGenerationAux;
			verifyHashMapsFunctionCount(function);

			function = workLoad.getFunction7();
			verifyHashMapsFunction(function);
			function = function + newGenerationAux;
			verifyHashMapsFunctionCount(function);

			function = workLoad.getFunction8();
			verifyHashMapsFunction(function);
			function = function + newGenerationAux;
			verifyHashMapsFunctionCount(function);

			function = workLoad.getFunction9();
			verifyHashMapsFunction(function);
			function = function + newGenerationAux;
			verifyHashMapsFunctionCount(function);

			function = workLoad.getFunction10();
			verifyHashMapsFunction(function);
			function = function + newGenerationAux;
			verifyHashMapsFunctionCount(function);
		}

		Set keys = generationFITSum.keySet();

		Set keysFunctionSet = functions.keySet();

		for (Object object : keys) {
			String key = object.toString();
			double fitsum = generationFITSum.get(key);
			int count = generationCount.get(key);
			int totalUsers = generationUserCount.get(key);
			double average = fitsum / count;
			double averagePerUser = fitsum / totalUsers;
			generationFITAverage.put(key, average);
			generationFITAveragePerUser.put(key, averagePerUser);

			for (Object object1 : keysFunctionSet) {
				String keyFunction = object1.toString() + key;
				if (functionCount.containsKey(keyFunction)) {
					if (generationGenes.containsKey(key)) {
						String geneDescription = generationGenes.get(key);
						geneDescription += "-" + object1.toString() + "="
								+ functionCount.get(keyFunction) + " ";
						generationGenes.put(key, geneDescription);
					} else {
						String geneDescription = "-" + object1.toString() + "="
								+ functionCount.get(keyFunction) + " ";
						generationGenes.put(key, geneDescription);

					}
				}
			}

		}

		for (WorkLoad workLoad : list) {

			String name = workLoad.getName();
			int newGenerationAux = WorkLoadUtil.getGenerationFromName(name);
			String users = String.valueOf(workLoad.getNumThreads());
			String fit = String.valueOf(workLoad.getFit());
			String func1 = workLoad.getFunction1();
			String func2 = workLoad.getFunction2();
			String func3 = workLoad.getFunction3();
			String func4 = workLoad.getFunction4();
			String func5 = workLoad.getFunction5();
			String func6 = workLoad.getFunction6();
			String func7 = workLoad.getFunction7();
			String func8 = workLoad.getFunction8();
			String func9 = workLoad.getFunction9();
			String func10 = workLoad.getFunction10();
			int users1 = workLoad.getUsers1();
			int users2 = workLoad.getUsers2();
			int users3 = workLoad.getUsers3();
			int users4 = workLoad.getUsers4();
			int users5 = workLoad.getUsers5();
			int users6 = workLoad.getUsers6();
			int users7 = workLoad.getUsers7();
			int users8 = workLoad.getUsers8();
			int users9 = workLoad.getUsers9();
			int users10 = workLoad.getUsers10();

			double average = generationFITAverage.get(String
					.valueOf(newGenerationAux));
			double averagePerUser = generationFITAveragePerUser.get(String
					.valueOf(newGenerationAux));
			String geneString = generationGenes.get(String
					.valueOf(newGenerationAux));
			Double fitMax = generationFITMax.get(String
					.valueOf(newGenerationAux));
			Double fitMin = generationFITMin.get(String
					.valueOf(newGenerationAux));
			buffer.append(newGenerationAux + "," + averagePerUser + ","
					+ average + "," + fitMax + "," + fitMin + "," + geneString
					+ "," + name + "," + fit + "," + users + "\n");

		}

		generationFITSum = new HashMap<String, Double>();
		generationGenes = new HashMap<String, String>();
		generationCount = new HashMap<String, Integer>();
		generationUserCount = new HashMap<String, Integer>();
		functionCount = new HashMap<String, Integer>();
		functions = new HashMap<String, Integer>();
		generationFITAverage = new HashMap<String, Double>();
		generationFITAveragePerUser = new HashMap<String, Double>();
		generationFITMax = new HashMap<String, Double>();
		generationFITMin = new HashMap<String, Double>();

		return buffer.toString();
	}
}
