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


package br.unifor.iadapter.threadGroup;

import br.unifor.iadapter.threadGroup.workload.StressWorkload;
import br.unifor.iadapter.threadGroup.workload.WorkLoad;

public class FactoryWorkLoad {

	public static WorkLoad createWorkLoad(String type) {
		WorkLoad workload = null;
		if (type.equals(WorkLoad.getTypes()[0])) {
			workload = new WorkLoad();

			workload.setType(WorkLoad.getTypes()[0]);

		}
		if (type.equals(WorkLoad.getTypes()[1])) {
			workload = new StressWorkload();

			workload.setType(WorkLoad.getTypes()[1]);

		}

		return workload;

	}

	
	
}
