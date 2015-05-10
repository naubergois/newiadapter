package br.unifor.iadapter.genetic;

import java.util.List;

import org.jgap.Chromosome;
import org.jgap.Population;
import org.jgap.impl.CrossoverOperator;
import org.jgap.impl.MutationOperator;

import br.unifor.iadapter.database.MySQLDatabase;
import br.unifor.iadapter.threadGroup.workload.WorkLoad;
import br.unifor.iadapter.threadGroup.workload.WorkLoadThreadGroup;
import br.unifor.iadapter.util.JMeterPluginsUtils;

public class GeneticAlgorithm {

	public static List<WorkLoad> newGeneration(WorkLoadThreadGroup tg,
			List<WorkLoad> listOldPopulation) {

		try {

			Population population = GeneWorkLoad.population(listOldPopulation,
					tg.getTree());

			List<Chromosome> bestI = GeneWorkLoad.selectBestIndividualsList(
					population, Integer.valueOf(tg.getBestIndividuals()));

			CrossoverOperator operator = GeneWorkLoad.crossOverPopulation(
					population, bestI);

			MutationOperator operator1 = GeneWorkLoad.mutationPopulation(
					population, bestI);

			MySQLDatabase.insertLog(tg.getName() + "Generation "
					+ operator.getCrossOverRate());
			MySQLDatabase.insertLog(tg.getName() + "CrossOverRate "
					+ operator.getCrossOverRate());
			MySQLDatabase.insertLog(tg.getName() + "CrossOverRatePercent"
					+ operator.getCrossOverRatePercent());

			return JMeterPluginsUtils.getListWorkLoadFromPopulationTestPlan(
					population.getChromosomes(), tg.getTree(),
					tg.getGeneration(), tg.getName(),
					Integer.valueOf(tg.getThreadNumberMax()));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

}
