package br.unifor.iadapter.algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AntForFlowShop extends Ant<Integer, FlowShopEnvironment> {

	private static final double VALUE_NOT_USED = 1.0;

	/**
	 * Creates an Ant specialized in the Flow Shop Scheduling problem.
	 *
	 * @param graphLenght
	 *            Number of rows of the Problem Graph.
	 */
	public AntForFlowShop(int graphLenght) {
		super();
		this.setSolution(new Integer[graphLenght]);
		this.setVisited(new HashMap<Integer, Boolean>());
	}

	@Override
	public boolean isSolutionReady(FlowShopEnvironment environment) {
		return getCurrentIndex() == environment.getNumberOfJobs();
	}

	/**
	 * Gets the makespan of the Ants built solution.
	 *
	 * @return Makespan of the solution.
	 */
	@Override
	public double getSolutionCost(FlowShopEnvironment environment) {
		return getBestFit(getSolution(), environment.getProblemGraph());
	}

	@Override
	public Double getHeuristicValue(Integer solutionComponent, Integer positionInSolution,
			FlowShopEnvironment environment) {
		return VALUE_NOT_USED;
	}

	@Override
	public List<Integer> getNeighbourhood(FlowShopEnvironment environment) {
		List<Integer> neighbours = new ArrayList<>();
		for (int l = 0; l < environment.getNumberOfJobs(); l++) {
			neighbours.add(l);
		}
		return neighbours;
	}

	@Override
	public Double getPheromoneTrailValue(Integer solutionComponent, Integer positionInSolution,
			FlowShopEnvironment environment) {

		double[][] pheromoneMatrix = environment.getPheromoneMatrix();
		return pheromoneMatrix[solutionComponent][positionInSolution];
	}

	@Override
	public void setPheromoneTrailValue(Integer solutionComponent, Integer positionInSolution,
			FlowShopEnvironment environment, Double value) {
		double[][] pheromoneMatrix = environment.getPheromoneMatrix();

		pheromoneMatrix[solutionComponent][positionInSolution] = value;
	}

	/**
	 * Calculates the MakeSpan for the generated schedule.
	 *
	 * @param schedule
	 *            Schedule
	 * @param jobInfo
	 *            Job Info.
	 * @return Makespan.
	 */
	public static double getBestFit(Integer[] schedule, double[][] jobInfo) {
		int size = jobInfo[0].length;
		double[] cost = new double[size];
		double fitValue = 0;

		// System.out.println(jobInfo);

		for (Integer job : schedule) {
			for (int i = 0; i < size; i++) {
				fitValue = jobInfo[job][i];
				if (i == 0) {
					cost[i] = cost[i] + fitValue;
				} else {
					if (cost[i] > cost[i - 1]) {
						cost[i] = cost[i] + fitValue;
					} else {
						cost[i] = cost[i - 1] + fitValue;
					}
				}
			}
		}
		return cost[size - 1];
	}

}
