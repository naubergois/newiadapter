package br.unifor.iadapter.sa;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.jmeter.testelement.TestElement;

import br.unifor.iadapter.database.MySQLDatabase;
import br.unifor.iadapter.threadGroup.workload.WorkLoad;
import br.unifor.iadapter.threadGroup.workload.WorkLoadThreadGroup;
import br.unifor.iadapter.util.WorkLoadUtil;

public class SimulateAnnealing {

	private static int tries;

	// temp is users
	public static int sa(int users, WorkLoad newPlace, int maxUsers,
			List<WorkLoad> list, int generation, WorkLoadThreadGroup tg,
			List<TestElement> nodes) {
		int newUsers = users;
		WorkLoad place = tg.getWorkloadCurrentSA();
		if (users > 0) {
			if ((place != null) && (newPlace != null)) {
				double deltaC = newPlace.getFit() - place.getFit();
				if (deltaC > 0) {
					tg.setWorkloadCurrentSA(newPlace);
				} else {
					Random random = new Random();
					double randomDouble = random.nextDouble();
					SimulateAnnealing.tries += 1;
					if (SimulateAnnealing.tries > 3) {
						int delta = maxUsers / 10;
						newUsers -= delta;
						SimulateAnnealing.tries = 0;
					}
					double exponential = Math.exp(-1 * (deltaC / users));
					if (randomDouble > exponential) {
						tg.setWorkloadCurrentSA(newPlace);
					}

				}
			} else {
				if (newPlace != null) {
					tg.setWorkloadCurrentSA(newPlace);
				}

			}

		}

		int populationSize = Integer.valueOf(tg.getPopulationSize());
		for (int i = 0; i < populationSize; i++) {
			int maxUserNoError = Integer.MAX_VALUE;
			try {
				maxUserNoError = MySQLDatabase
						.listMaxUserWithNoErroWorkloadGenetic(tg.getName(),
								String.valueOf(tg.getGeneration()));
			} catch (ClassNotFoundException e) {

				e.printStackTrace();
			} catch (SQLException e) {

				e.printStackTrace();
			}
			if (newUsers > maxUserNoError) {
				newUsers = maxUserNoError;
			}

			WorkLoad workLoad = pertub(newUsers, tg, nodes, generation, place);
			list.add(workLoad);
		}

		return newUsers;
	}

	public static String getName(List<TestElement> nodes, int i) {
		if (i < nodes.size()) {

			String name = nodes.get(i).getName();
			if (name == null) {
				name = nodes.get(i).NAME;
			}
			return name;
		} else {
			return "None";
		}
	}

	public static WorkLoad pertub(int maxUsers, WorkLoadThreadGroup tg,
			List<TestElement> nodes, int generation, WorkLoad workload) {

		List<Integer> parametros = new ArrayList<Integer>();

		Random random = new Random();
		Integer random1 = random.nextInt(WorkLoad.getTypes().length);
		parametros.add(random1);
		Integer random2 = 0;

		random2 = random.nextInt((int) nodes.size());
		parametros.add(random2);

		Integer random3 = 0;

		random3 = random.nextInt((int) nodes.size());
		parametros.add(random3);

		Integer random4 = 0;

		random4 = random.nextInt((int) nodes.size());
		parametros.add(random4);

		Integer random5 = 0;

		random5 = random.nextInt((int) nodes.size());
		parametros.add(random5);

		Integer random6 = 0;

		random6 = random.nextInt((int) nodes.size());
		parametros.add(random6);

		Integer random7 = 0;

		random7 = random.nextInt((int) nodes.size());
		parametros.add(random7);

		Integer random8 = 0;

		random8 = random.nextInt((int) nodes.size());
		parametros.add(random8);

		Integer random9 = 0;

		random9 = random.nextInt((int) nodes.size());
		parametros.add(random9);

		Integer random10 = 0;

		random10 = random.nextInt((int) nodes.size());
		parametros.add(random10);

		Integer random11 = 0;

		random11 = random.nextInt((int) nodes.size());
		parametros.add(random11);

		parametros.add(maxUsers);
		parametros.add(generation);

		WorkLoad workloadResult = WorkLoadUtil.createWorkLoad(nodes,
				parametros, "SA", new Integer(tg.getGenerationTrack()),
				workload);
		return workloadResult;

	}
}
