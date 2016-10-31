/*Copyright [2016] [Francisco Nauber Bernardo Gois]

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.*/
package br.unifor.iadapter.sa;

import java.util.List;
import java.util.Random;

import org.apache.jmeter.testelement.TestElement;

import br.unifor.iadapter.threadGroup.workload.WorkLoad;
import br.unifor.iadapter.threadGroup.workload.WorkLoadThreadGroup;
import br.unifor.iadapter.util.WorkLoadUtil;

public class SimulateAnnealing {

	private static int tries;

	//Simulate Annealing method
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
				int newTempIncrement = incrementRandom.nextInt(Integer
						.valueOf(tg.getThreadNumberMax()));

				newUsers = tg.getWorkloadCurrentSA().getNumThreads()
						- newTempIncrement;

				if (newUsers <= 0) {
					newUsers = 1;
				}
			} else {
				Random incrementRandom = new Random();
				int newTempIncrement = incrementRandom.nextInt(Integer
						.valueOf(tg.getThreadNumberMax()));
				newUsers = tg.getWorkloadCurrentSA().getNumThreads()
						+ newTempIncrement;
				if (newUsers > (Integer.valueOf(tg.getThreadNumberMax()))) {
					newUsers = Integer.valueOf(tg.getThreadNumberMax());
				}

			}
			SimulateAnnealing.tries = 0;
		}

		int populationSize = Integer.valueOf(tg.getPopulationSize());
		for (int i = 0; i < populationSize; i++) {

			WorkLoad workLoad = WorkLoadUtil.getNeighBorHoodSA(
					tg.getWorkloadCurrentSA(), nodes, newUsers, tg.getGeneration()+1);

			list.add(workLoad);
		}

		return newUsers;
	}

}
