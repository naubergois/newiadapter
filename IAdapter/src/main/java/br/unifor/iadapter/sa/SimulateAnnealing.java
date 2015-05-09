package br.unifor.iadapter.sa;

import java.util.List;
import java.util.Random;

import org.apache.jmeter.testelement.TestElement;

import br.unifor.iadapter.threadGroup.FactoryWorkLoad;
import br.unifor.iadapter.threadGroup.workload.WorkLoad;
import br.unifor.iadapter.threadGroup.workload.WorkLoadThreadGroup;

public class SimulateAnnealing {

	private static int tries;

	// temp is users
	public static int sa(int users, WorkLoad place, WorkLoad newPlace,
			int maxUsers, List<WorkLoad> list, int generation,
			WorkLoadThreadGroup tg, List<TestElement> nodes) {
		int newUsers = users;
		if (users > 0) {
			if ((place != null) && (newPlace != null)) {
				double deltaC = newPlace.getFit() - place.getFit();
				if (deltaC > 0) {
					place = newPlace;
				} else {
					Random random = new Random();
					double randomDouble = random.nextDouble();
					SimulateAnnealing.tries += 1;
					if (SimulateAnnealing.tries > 3) {
						newUsers -= 10;
						SimulateAnnealing.tries = 0;
					}
					double exponential = Math.exp(-1 * (deltaC / users));
					if (randomDouble > exponential) {
						place = newPlace;
					}

				}
			} else {
				if (newPlace != null) {
					place = newPlace;
				}
			}

		}
		for (int i = 0; i < 2; i++) {
			WorkLoad workLoad = pertub(users, tg, nodes, generation);
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
			List<TestElement> nodes, int generation) {

		WorkLoad workload = new WorkLoad();
		Random random = new Random();
		Integer random1 = random.nextInt(WorkLoad.getTypes().length);
		Integer random2 = 0;

		random2 = random.nextInt((int) nodes.size() + 1);

		Integer random3 = 0;

		random3 = random.nextInt((int) nodes.size() + 1);

		Integer random4 = 0;

		random4 = random.nextInt((int) nodes.size() + 1);

		Integer random5 = 0;

		random5 = random.nextInt((int) nodes.size() + 1);

		Integer random6 = 0;

		random6 = random.nextInt((int) nodes.size() + 1);

		Integer random7 = 0;

		random7 = random.nextInt((int) nodes.size() + 1);

		Integer random8 = 0;

		random8 = random.nextInt((int) nodes.size() + 1);

		Integer random9 = 0;

		random9 = random.nextInt((int) nodes.size() + 1);

		Integer random10 = 0;

		random10 = random.nextInt((int) nodes.size() + 1);

		Integer random11 = 0;

		random11 = random.nextInt((int) nodes.size() + 1);

		if (random1 == 0) {
			workload = FactoryWorkLoad.createWorkLoad(WorkLoad.getTypes()[0]);

		}
		if (random1 == 1) {
			workload = FactoryWorkLoad.createWorkLoad(WorkLoad.getTypes()[1]);

		}

		workload.setFunction1(getName(nodes, random2));
		workload.setFunction2(getName(nodes, random3));
		workload.setFunction3(getName(nodes, random4));
		workload.setFunction4(getName(nodes, random5));
		workload.setFunction5(getName(nodes, random6));
		workload.setFunction6(getName(nodes, random7));
		workload.setFunction7(getName(nodes, random8));
		workload.setFunction8(getName(nodes, random9));
		workload.setFunction9(getName(nodes, random10));
		workload.setFunction10(getName(nodes, random11));
		workload.setNumThreads(maxUsers);
		workload.setName("SA:" + "G" + generation + ":" + workload.getType()
				+ "-" + workload.getNumThreads() + "-"
				+ workload.getFunction1() + "-" + workload.getFunction2() + "-"
				+ workload.getFunction3() + "-" + workload.getFunction4() + "-"
				+ workload.getFunction5() + "-" + workload.getFunction6() + "-"
				+ workload.getFunction7() + "-" + workload.getFunction8() + "-"
				+ workload.getFunction9() + "-" + workload.getFunction10());

		workload.setSearchMethod("SA");
		workload.setGeneration(generation);
		workload.setActive(true);
		workload.calcUsers();
		return workload;

	}
}
