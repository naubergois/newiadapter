package br.unifor.iadapter.algorithm;

import java.util.logging.Logger;

/**
 * A simple evaporation policy taken from Ant System. It simply applies the
 * evaporation ratio to all elements on the Pheromone Matrix.
 *
 * @param <C> Class for components of a solution.
 * @param <E> Class representing the Environment.
 * @author Carlos G. Gavidia
 */
public class PerformEvaporation<C, E extends Environment> extends
        DaemonAction<C, E> {

    private static Logger logger = Logger.getLogger(PerformEvaporation.class
            .getName());

    public PerformEvaporation() {
        super(DaemonActionType.AFTER_ITERATION_CONSTRUCTION);
    }

    @Override
    public void applyDaemonAction(ConfigurationProvider configurationProvider) {
        double evaporationRatio = configurationProvider.getEvaporationRatio();

        logger.fine("Performing evaporation on all edges");
        logger.fine("Evaporation ratio: " + evaporationRatio);

        getEnvironment().applyFactorToPheromoneMatrix(evaporationRatio);
    }

}
