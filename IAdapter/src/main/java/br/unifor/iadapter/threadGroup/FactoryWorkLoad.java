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

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import br.unifor.iadapter.searchclass.SearchClassWorkLoad;
import br.unifor.iadapter.threadGroup.workload.WorkLoad;
import br.unifor.iadapter.util.WorkLoadUtil;

public class FactoryWorkLoad {

	public static WorkLoad createWorkLoad() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException {

		SearchClassWorkLoad search = new SearchClassWorkLoad();

		List<String> classes = search.getClasses();
		
		System.out.println(" Load workloads "+classes);

		int index = WorkLoadUtil.randInt(0, classes.size()-1);

		String name = classes.get(index);

		WorkLoad workload = SearchClassWorkLoad.getInstance(name);
		
		workload.setType(workload.getClass().getCanonicalName());
		
		System.out.println(" Instance of workload "+workload.getClass());

		return workload;

	}
	
	public static WorkLoad createWorkLoad(String type) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException {

		WorkLoad workload = SearchClassWorkLoad.getInstance(type);

		return workload;

	}
	
	
	

}
