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

package br.unifor.iadapter.tabu;

import java.util.ArrayList;
import java.util.List;

import org.apache.jmeter.testelement.TestElement;

import br.unifor.iadapter.threadGroup.workload.WorkLoad;
import br.unifor.iadapter.util.WorkLoadUtil;

public class TabuSearch {

	private static int tabuExpires;

	private static List<TabuElement> tabuTable = new ArrayList<TabuElement>();

	public static List<WorkLoad> verify(List<WorkLoad> list,
			List<TestElement> nodes) {
		List<WorkLoad> workLoadForRemove = new ArrayList<WorkLoad>();
		for (WorkLoad workLoad : list) {
			for (TabuElement tabuElement : tabuTable) {
				TabuElement tabu = WorkLoadUtil.convertTabu(workLoad, nodes);
				if (tabu.getTotal() == tabuElement.getTotal()) {
					workLoadForRemove.add(workLoad);
				}
			}
		}
		TabuSearch.setTabuExpires(TabuSearch.getTabuExpires() + 1);
		if (TabuSearch.getTabuExpires() > 5) {
			TabuSearch.setTabuTable(new ArrayList<TabuElement>());
			TabuSearch.setTabuExpires(0);
		}
		list.removeAll(workLoadForRemove);
		return list;
	}

	public static void addTabuTable(WorkLoad workLoad, List<TestElement> nodes) {
		tabuTable.add(WorkLoadUtil.convertTabu(workLoad, nodes));

	}

	public static int getTabuExpires() {
		return tabuExpires;
	}

	public static void setTabuExpires(int tabuExpires) {
		TabuSearch.tabuExpires = tabuExpires;
	}

	public static List<TabuElement> getTabuTable() {
		return tabuTable;
	}

	public static void setTabuTable(List<TabuElement> tabuTable) {
		TabuSearch.tabuTable = tabuTable;
	}

}
