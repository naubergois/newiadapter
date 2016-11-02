package br.unifor.iadapter.algorithm;

import java.util.Arrays;
import java.util.logging.Logger;

/**
 * A simple initialization procedure for a Pheromone Matrix. It just assigns all
 * cells the value provided by Configuration.
 *
 * @param <C> Class for components of a solution.
 * @param <E> Class representing the Environment.
 * @author Carlos G. Gavidia
 */
public class StartPheromoneMatrix<C, E extends Environment> extends
        DaemonAction<C, E> {

    private static Logger logger = Logger
            .getLogger(StartPheromoneMatrix.class.getName());

    public StartPheromoneMatrix() {
        super(DaemonActionType.INITIAL_CONFIGURATION);
    }

    @Override
    public void applyDaemonAction(ConfigurationProvider configurationProvider) {
        logger.fine("INITIALIZING PHEROMONE MATRIX");

        double initialPheromoneValue = getInitialPheromoneValue(configurationProvider);
        if (initialPheromoneValue == 0.0) {
            logger.warning("An initial pheromone value of zero can affect the node selection process.");
        }

        logger.info("Initial pheromone value: " + initialPheromoneValue);

        getEnvironment().populatePheromoneMatrix(initialPheromoneValue);
        logger.fine("Pheromone matrix after initilizatation :" + Arrays.deepToString(getEnvironment().getPheromoneMatrix()));
    }

    protected double getInitialPheromoneValue(
            ConfigurationProvider configurationProvider) {
        return configurationProvider.getInitialPheromoneValue();
    }
}
