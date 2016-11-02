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

import br.unifor.iadapter.algorithm.AbstractAlgorithm;
import br.unifor.iadapter.algorithm.SAAlgorithm;
import br.unifor.iadapter.threadGroup.workload.WorkLoad;

public class SimulateAnnealing {

	

	// Simulate Annealing method
	// temp is users
	public static void sa(AbstractAlgorithm algorithm, List<WorkLoad> newPlaces, int temperature) {

		for (WorkLoad newPlace : newPlaces) {

			WorkLoad place = SAAlgorithm.currentWorkLoad;
			if (temperature > 0) {
				if ((place != null) && (newPlace != null)) {
					double deltaC = place.getFit() - newPlace.getFit();
					if (deltaC < 0) {
						SAAlgorithm.currentWorkLoad = newPlace;

					} else {
						Random random = new Random();
						double randomDouble = random.nextDouble();
						
						double exponential = Math.exp(-1 * (deltaC / temperature));
						if (randomDouble > exponential) {
							SAAlgorithm.currentWorkLoad = newPlace;

						}

					}
				} else {
					if (newPlace != null) {
						SAAlgorithm.currentWorkLoad = newPlace;
					}

				}

			}
		}

	}

}
