package br.unifor.iadapter.algorithm;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The procedure for pheromone update for MMAS. It keeps the pheromone values in
 * the matrix between a maximum and a minimum, and only allows pheromone deposit
 * to the best performing ant.
 * <p>
 * <p>
 * It is not executed online, but at the end of the iteration.
 *
 * @param <C> Class for components of a solution.
 * @param <E> Class representing the Environment.
 * @author Carlos G. Gavidia
 */
public abstract class UpdatePheromoneMatrixForMaxMin<C, E extends Environment>
        extends DaemonAction<C, E> {
    // TODO(cgavidia): Generics can be used on Configuration Provider types.

    private static Logger logger = Logger
            .getLogger(UpdatePheromoneMatrixForMaxMin.class.getName());

    /**
     * Instantiates the Update Pheromone Matrix Policy.
     */
    public UpdatePheromoneMatrixForMaxMin() {
        super(DaemonActionType.AFTER_ITERATION_CONSTRUCTION);

    }

    @Override
    public void applyDaemonAction(ConfigurationProvider provider) {

        MaxMinConfigurationProvider configurationProvider = (MaxMinConfigurationProvider) provider;
        logger.log(Level.FINE, "UPDATING PHEROMONE TRAILS");
        logger.log(Level.FINE, "Performing evaporation on all edges");
        logger.log(Level.FINE,
                "Evaporation ratio: " + configurationProvider.getEvaporationRatio());

        double[][] pheromoneMatrix = getEnvironment().getPheromoneMatrix();
        int matrixRows = pheromoneMatrix.length;
        int matrixColumns = pheromoneMatrix[0].length;

        for (int i = 0; i < matrixRows; i++) {
            for (int j = 0; j < matrixColumns; j++) {
                double newValue = pheromoneMatrix[i][j]
                        * configurationProvider.getEvaporationRatio();

                if (newValue >= getMinimumPheromoneValue(configurationProvider)) {
                    pheromoneMatrix[i][j] = newValue;
                } else {
                    pheromoneMatrix[i][j] = getMinimumPheromoneValue(configurationProvider);
                }

                validatePheromoneValue(pheromoneMatrix[i][j]);
            }
        }

        logger.log(Level.FINE, "Depositing pheromone on Best Ant trail.");

        Ant<C, E> bestAnt = getAntColony().getBestPerformingAnt(getEnvironment());

        C[] bestSolution = bestAnt.getSolution();

        // TODO(cgavidia): From here, we can factor the policy of only best ant
        // deposits pheromone.

        for (int componentIndex = 0; componentIndex < bestSolution.length; componentIndex += 1) {
            C solutionComponent = bestSolution[componentIndex];
            // TODO(cgavidia): This makes me think a solution type is necessary...
            double newValue = getNewPheromoneValue(bestAnt, componentIndex,
                    solutionComponent, configurationProvider);

            if (newValue <= getMaximumPheromoneValue(configurationProvider)) {
                bestAnt.setPheromoneTrailValue(solutionComponent, componentIndex, getEnvironment(),
                        newValue);
            } else {
                bestAnt.setPheromoneTrailValue(solutionComponent, componentIndex, getEnvironment(),
                        getMaximumPheromoneValue(configurationProvider));
            }

            validatePheromoneValue(bestAnt.getPheromoneTrailValue(solutionComponent, componentIndex, getEnvironment()));
        }

        logger.log(Level.FINE, "After pheromone update: " + Arrays.deepToString(getEnvironment().getPheromoneMatrix()));

    }

    private void validatePheromoneValue(double v) {
        if (Double.isInfinite(v) || Double.isNaN(v)) {
            throw new ConfigurationException("The pheromone value calculated is not a valid number: " +
                    v);
        }
    }

    /**
     * The maximum value permitted for a pheromone matrix cell.
     *
     * @param configurationProvider Algorithm configuration.
     * @return Pheromone threshold.
     */
    protected double getMaximumPheromoneValue(MaxMinConfigurationProvider configurationProvider) {
        return configurationProvider.getMaximumPheromoneValue();
    }

    /**
     * The minimum value permitted for a pheromone matrix cell.
     *
     * @param configurationProvider Algorithm configuration.
     * @return Pheromone threshold.
     */
    protected double getMinimumPheromoneValue(MaxMinConfigurationProvider configurationProvider) {
        return configurationProvider.getMinimumPheromoneValue();
    }

    /**
     * The new value to be included in the pheromone matrix, depending on a component and its position on the solution.
     *
     * @param ant                   Ant performing the deposit.
     * @param positionInSolution    Component in the solution.
     * @param solutionComponent     Position of the component in the solution.
     * @param configurationProvider Algorithm configuration.
     * @return New pheromone value.
     */
    protected abstract double getNewPheromoneValue(Ant<C, E> ant,
                                                   int positionInSolution, C solutionComponent,
                                                   MaxMinConfigurationProvider configurationProvider);

}
