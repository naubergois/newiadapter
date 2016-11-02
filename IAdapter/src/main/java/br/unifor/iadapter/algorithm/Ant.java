package br.unifor.iadapter.algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.unifor.iadapter.threadGroup.workload.WorkLoad;

/**
 * The little workers that build solutions: They belong to a colony. This is an
 * Abstract Type so you must extend it in order to fill the characteristics of
 * your optimization problem.
 * <p>
 * <p>
 * Some convenient methods to define are:
 * <ul>
 * <li>isSolutionReady(), to define when the Ant must stop adding components to
 * its solution.
 * <li>getSolutionCost(), to define the cost of the current solution. It will
 * help to decide the best solution built so far.
 * <li>getHeuristicValue(), to explote problem domain information while
 * constructing solutions.
 * <li>getNeighbourhood(), this returns a list of possible components to add to
 * the solution.
 * <li>getPheromoneTrailValue(), returns the pheromone trail value associated to
 * a Solution Component.
 * <li>setPheromoneTrailValue(), to assign a pheromone value to a Solution
 * Component.
 * </ul>
 *
 * @param <C>
 *            Class for components of a solution.
 * @param <E>
 *            Class representing the Environment.
 * @author Carlos G. Gavidia
 */
public abstract class Ant<C, E extends Environment> {

	private static final int DONT_CHECK_NUMBERS = -1;
	private static final int ONE_POLICY = 1;

	private int currentIndex = 0;

	private List<AntPolicy<C, E>> policies = new ArrayList<AntPolicy<C, E>>();

	// TODO(cgavidia): Temporarly, we're using an array of items. It will later
	// evolve to an array of solution components, or a List.
	private C[] solution;

	// TODO(cgavidia): This is redundant. Or it should be implemented as another
	// data structure.
	private Map<C, Boolean> visitedComponents = new HashMap<C, Boolean>();

	/**
	 * Mark a node as visited.
	 *
	 * @param visitedNode
	 *            Visited node.
	 */
	public void visitNode(C visitedNode) {
		if (currentIndex < getSolution().length) {

			getSolution()[currentIndex] = visitedNode;
			visitedComponents.put(visitedNode, true);
			currentIndex++;
		} else {
			throw new SolutionConstructionException("Couldn't add component " + visitedNode.toString() + " at index "
					+ currentIndex + ": Solution length is: " + getSolution().length + ". \nPartial solution is "
					+ this.getSolutionAsString());
		}

	}

	/**
	 * Resets the visited vector, clears current solution components and sets
	 * the index to 0.
	 */
	public void clear() {

		this.setCurrentIndex(0);

		for (int i = 0; i < getSolution().length; i++) {
			getSolution()[i] = null;
		}

		visitedComponents.clear();
	}

	/**
	 * Gets th solution built as a String.
	 *
	 * @return Solution as a String.
	 */
	public String getSolutionAsString() {
		String solutionString = "";
		for (int i = 0; i < solution.length; i++) {
			if (solution[i] != null) {
				solutionString = solutionString + " " + solution[i].toString();
			}
		}
		return solutionString;
	}

	public void addPolicy(AntPolicy<C, E> antPolicy) {
		this.policies.add(antPolicy);
	}

	AntPolicy<C, E> getAntPolicy(AntPolicyType policyType, int expectedNumber) {
		int numberOfPolicies = 0;
		AntPolicy<C, E> selectedPolicy = null;

		for (AntPolicy<C, E> policy : policies) {
			if (policyType.equals(policy.getPolicyType())) {
				selectedPolicy = policy;
				numberOfPolicies += 1;
			}
		}

		if (expectedNumber > 0 && numberOfPolicies != expectedNumber) {
			throw new ConfigurationException("The number of " + policyType + " policies was " + numberOfPolicies
					+ ". We were expecting " + expectedNumber);
		}

		return selectedPolicy;
	}

	/**
	 * Selects a node and marks it as visited.
	 *
	 * @param environment
	 *            Environment where the ant is building a solution.
	 * @param configurationProvider
	 *            Configuration provider.
	 */
	public void selectNextNode(E environment, ConfigurationProvider configurationProvider) {

		AntPolicy<C, E> selectNodePolicity = getAntPolicy(AntPolicyType.NODE_SELECTION, ONE_POLICY);

		// TODO(cgavidia): With this approach, the policy is a shared resource
		// between ants. This doesn't allow parallelism.
		selectNodePolicity.setAnt(this);
		boolean policyResult = selectNodePolicity.applyPolicy(environment, configurationProvider);
		if (!policyResult) {
			throw new ConfigurationException("The node selection policy " + selectNodePolicity.getClass().getName()
					+ " wasn't able to select a node.");
		}
	}

	/**
	 * Improves the quality of the solution produced.
	 *
	 * @param environment
	 *            Environment where the ant is building a solution.
	 * @param configurationProvider
	 *            Configuration provider.
	 */
	public void doAfterSolutionIsReady(E environment, ConfigurationProvider configurationProvider) {
		AntPolicy<C, E> selectNodePolicity = getAntPolicy(AntPolicyType.AFTER_SOLUTION_IS_READY, DONT_CHECK_NUMBERS);

		if (selectNodePolicity != null) {
			// TODO(cgavidia): With this approach, the policy is a shared
			// resource
			// between ants. This doesn't allow parallelism.
			selectNodePolicity.setAnt(this);
			selectNodePolicity.applyPolicy(environment, configurationProvider);
		}
	}

	/**
	 * Verifies if a component. is already included in the solution.
	 *
	 * @param component
	 *            Component to verify.
	 * @return True if the node is already visited. False otherwise.
	 */

	public boolean isNodeVisited(C component) {
		boolean visited = false;

		Boolean inVisitedMap = visitedComponents.get(component);

		if (inVisitedMap != null && inVisitedMap) {
			visited = inVisitedMap;
		}

		return visited;
	}

	public boolean isNodeValid(C node) {
		return true;
	}

	/**
	 * Sets the current index for the Ant.
	 *
	 * @param currentIndex
	 *            Current index.
	 */
	public void setCurrentIndex(int currentIndex) {
		this.currentIndex = currentIndex;
	}

	/**
	 * Gets the current index for the Ant while constructing a solution.
	 *
	 * @return Current index.
	 */
	public int getCurrentIndex() {
		return currentIndex;
	}

	/**
	 * Returns true when the solution build by the current Ant is finished.
	 *
	 * @param environment
	 *            Environment instance with problem information.
	 * @return True if the solution is finished, false otherwise.
	 */
	public abstract boolean isSolutionReady(E environment);

	/**
	 * Calculates the cost associated to the solution build, which is needed to
	 * determine the performance of the Ant.
	 *
	 * @param environment
	 *            Environment instance with problem information.
	 * @return The cost of the solution built.
	 */
	public abstract double getSolutionCost(E environment);

	/**
	 * Calculates the heuristic contribution for the cost of the solution by
	 * adding a component at an specific position.
	 *
	 * @param solutionComponent
	 *            Solution component.
	 * @param positionInSolution
	 *            Position of this component in the solution.
	 * @param environment
	 *            Environment instance with problem information.
	 * @return Heurisitic contribution.
	 */
	public abstract Double getHeuristicValue(C solutionComponent, Integer positionInSolution, E environment);

	/**
	 * The components that are available for selection while an Ant is
	 * constructing its solution.
	 *
	 * @param environment
	 *            Environment instance with problem information.
	 * @return List of available components.
	 */
	public abstract List<C> getNeighbourhood(E environment);

	// TODO(cgavidia): Maybe we should move this to Environment.

	/**
	 * Returns the pheromone value associated to a solution component at an
	 * specific position
	 *
	 * @param solutionComponent
	 *            Solution component.
	 * @param positionInSolution
	 *            Position of this component in the solution.
	 * @param environment
	 *            Environment instance with problem information.
	 * @return Pheromone value.
	 */
	public abstract Double getPheromoneTrailValue(C solutionComponent, Integer positionInSolution, E environment);

	/**
	 * Updates the value of a cell on the pheromone matrix.
	 *
	 * @param solutionComponent
	 *            Solution component.
	 * @param positionInSolution
	 *            Position of this component in the solution.
	 * @param environment
	 *            Environment instance with problem information.
	 * @param value
	 *            New pheromone value.
	 */
	public abstract void setPheromoneTrailValue(C solutionComponent, Integer positionInSolution, E environment,
			Double value);

	// TODO(cgavidia): For convenience, we're accesing this data structures
	// directly.
	public C[] getSolution() {
		return solution;
	}

	public void setSolution(C[] solution) {
		this.solution = solution;
	}

	public Map<C, Boolean> getVisited() {
		return visitedComponents;
	}

	public void setVisited(Map<C, Boolean> visited) {
		this.visitedComponents = visited;
	}

	static final int SOLUTION_LENGTH = 3;
	private static final int COMMON_COST = 2;
	private static final int NUMBER_OF_ANTS = 3;
	private static final int BEST_COST = 1;
	protected static final Integer SAMPLE_NODE = 10;
	private AntColony<Integer, Environment> dummyColony;
	private Environment environment;
	
	private static AntColony<Integer, FlowShopEnvironment> getAntColony(ProblemConfiguration configurationProvider) {
		return new AntColony<Integer, FlowShopEnvironment>(configurationProvider.getNumberOfAnts()) {
			@Override
			protected Ant<Integer, FlowShopEnvironment> createAnt(FlowShopEnvironment environment) {
				AntForFlowShop ant = new AntForFlowShop(environment.getNumberOfJobs());
				return ant;
			}
		};
	}

	public static void main(String[] args) throws InvalidInputException {
		int SOLUTION_LENGTH = 3;
		int COMMON_COST = 2;
		int NUMBER_OF_ANTS = 3;
		int BEST_COST = 1;
		Integer SAMPLE_NODE = 10;
		AntColony<Integer, Environment> dummyColony;
		Environment environment;
		dummyColony = new AntColony<Integer, Environment>(NUMBER_OF_ANTS) {

			@Override
			protected Ant<Integer, Environment> createAnt(Environment environment) {
				return DummyFactory.createDummyAnt(2, 3);
			}
		};
	
		
		double[][] problemGraph=new double[4][3];
		problemGraph[0][0]=12;
		problemGraph[0][1]=2000;
		problemGraph[0][2]=3000;
		problemGraph[0][2]=2000;
		
		problemGraph[1][0]=11;
		problemGraph[1][1]=2000;
		problemGraph[1][1]=4000;
		problemGraph[1][1]=5000;
		
		
		problemGraph[2][0]=3;
		problemGraph[2][1]=60000;
		
		problemGraph[3][0]=1;
		problemGraph[3][1]=160000;
		
		
		
		  ProblemConfiguration configurationProvider = new ProblemConfiguration(problemGraph);
	      AntColony<Integer, FlowShopEnvironment> colony = getAntColony(configurationProvider);
	      FlowShopEnvironment environment1 = new FlowShopEnvironment(problemGraph);
	      configurationProvider.setEnvironment(environment1);

	      AcoProblemSolver<Integer, FlowShopEnvironment> solver = new AcoProblemSolver<>();
	      solver.initialize(environment1, colony, configurationProvider);

	      solver.addDaemonActions(
	              new StartPheromoneMatrix<Integer, FlowShopEnvironment>(),
	              new FlowShopUpdatePheromoneMatrix());
	      solver.getAntColony().addAntPolicies(
	              new PseudoRandomNodeSelection<Integer, FlowShopEnvironment>(),
	              new ApplyLocalSearch());

	      
	      try {
			solver.solveProblem();
			
		} catch (javax.naming.ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	
		/*int pheromoneRows = 3;
		int pheromoneColumns = 4;
		environment = DummyFactory.createDummyEnvironment(problemGraph, pheromoneRows, pheromoneColumns);
		Ant<Integer, Environment> theBestAnt = DummyFactory.createDummyAnt(1, 3);

		dummyColony.buildColony(environment);
		dummyColony.getHive().add(theBestAnt);

		System.out.println(dummyColony.getBestPerformingAnt(environment).getSolutionCost(environment));
		System.out.println(dummyColony.getBestPerformingAnt(environment).getSolutionAsString());
		System.out.println(dummyColony.getBestPerformingAnt(environment).getSolution().toString());*/

	}

}
