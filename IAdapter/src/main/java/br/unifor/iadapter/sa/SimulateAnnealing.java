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
	public static int sa(int users, List<WorkLoad> newPlaces, int maxUsers,
			List<WorkLoad> list, int generation, WorkLoadThreadGroup tg,
			List<TestElement> nodes) {
		int newUsers = users;
		for (WorkLoad newPlace : newPlaces) {

			WorkLoad place = tg.getWorkloadCurrentSA();
			if (users > 0) {
				if ((place != null) && (newPlace != null)) {
					double deltaC = place.getFit() - newPlace.getFit();
					if (deltaC < 0) {
						tg.setWorkloadCurrentSA(newPlace);

					} else {
						Random random = new Random();
						double randomDouble = random.nextDouble();
						SimulateAnnealing.tries += 1;
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
		}

		if (SimulateAnnealing.tries > 3) {
			Random signal = new Random();
			int signalInt = signal.nextInt(2);
			if (signalInt == 0) {
				Random incrementRandom = new Random();
				int newTempIncrement = incrementRandom.nextInt(11);
				newUsers = tg.getWorkloadCurrentSA().getNumThreads()
						- newTempIncrement;
			} else {
				Random incrementRandom = new Random();
				int newTempIncrement = incrementRandom.nextInt(11);
				newUsers = tg.getWorkloadCurrentSA().getNumThreads()
						+ newTempIncrement;

			}
			SimulateAnnealing.tries = 0;
		}

		int populationSize = Integer.valueOf(tg.getPopulationSize());
		for (int i = 0; i < populationSize; i++) {

			WorkLoad workLoad = WorkLoadUtil.getNeighBorHoodSA(
					tg.getWorkloadCurrentSA(), nodes, newUsers, generation);

			list.add(workLoad);
		}

		return newUsers;
	}

}
