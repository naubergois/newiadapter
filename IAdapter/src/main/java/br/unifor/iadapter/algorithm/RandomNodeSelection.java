package br.unifor.iadapter.algorithm;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

/**
 * This is the node selection policy used in Ant System algorithms, also known as Random Proportional Rule.
 *
 * @param <C> Class for components of a solution.
 * @param <E> Class representing the Environment.
 * @author Carlos G. Gavidia
 */
public class RandomNodeSelection<C, E extends Environment> extends
        AntPolicy<C, E> {

    public RandomNodeSelection() {
        super(AntPolicyType.NODE_SELECTION);
    }

    @Override
    public boolean applyPolicy(E environment, ConfigurationProvider configurationProvider) {
        Random random = new Random();
        C nextNode = null;

        double value = random.nextDouble();
        double total = 0;

        HashMap<C, Double> componentsWithProbabilities = this
                .getComponentsWithProbabilities(environment, configurationProvider);
        Iterator<Map.Entry<C, Double>> componentWithProbabilitiesIterator = componentsWithProbabilities
                .entrySet().iterator();
        while (componentWithProbabilitiesIterator.hasNext()) {
            Map.Entry<C, Double> componentWithProbability = componentWithProbabilitiesIterator
                    .next();

            Double probability = componentWithProbability.getValue();
            if (probability.isNaN()) {
                throw new ConfigurationException("The probability for component " + componentWithProbability.getKey() +
                        " is not a number.");
            }

            total += probability;

            if (total >= value) {
                nextNode = componentWithProbability.getKey();
                getAnt().visitNode(nextNode);
                return true;
            }
        }

        return false;
    }

    /**
     * Gets a probabilities vector, containing probabilities to move to each node
     * according to pheromone matrix.
     *
     * @param environment           Environment that ants are traversing.
     * @param configurationProvider Configuration provider.
     * @return Probabilities for the adjacent nodes.
     */
    public HashMap<C, Double> getComponentsWithProbabilities(E environment,
                                                             ConfigurationProvider configurationProvider) {
        HashMap<C, Double> componentsWithProbabilities = new HashMap<C, Double>();

        double denominator = Double.MIN_VALUE;
        for (C possibleMove : getAnt().getNeighbourhood(environment)) {

            if (!getAnt().isNodeVisited(possibleMove)
                    && getAnt().isNodeValid(possibleMove)) {

                Double heuristicTimesPheromone = getHeuristicTimesPheromone(
                        environment, configurationProvider, possibleMove);

                denominator += heuristicTimesPheromone;
                componentsWithProbabilities.put(possibleMove, 0.0);
            }
        }

        Double totalProbability = 0.0;
        Iterator<Map.Entry<C, Double>> componentWithProbabilitiesIterator = componentsWithProbabilities
                .entrySet().iterator();
        while (componentWithProbabilitiesIterator.hasNext()) {
            Map.Entry<C, Double> componentWithProbability = componentWithProbabilitiesIterator
                    .next();
            C component = componentWithProbability.getKey();

            Double numerator = getHeuristicTimesPheromone(environment,
                    configurationProvider, component);
            Double probability = numerator / denominator;
            totalProbability += probability;

            componentWithProbability.setValue(probability);
        }

        if (componentsWithProbabilities.size() < 1) {
            return doIfNoComponentsFound(environment, configurationProvider);
        }
        double delta = 0.001;
        if (Math.abs(totalProbability - 1.0) > delta) {
            throw new ConfigurationException("The sum of probabilities for the possible components is " +
                    totalProbability + ". We expect this value to be closer to 1.");
        }

        return componentsWithProbabilities;
    }


    protected HashMap<C, Double> doIfNoComponentsFound(E environment,
                                                       ConfigurationProvider configurationProvider) {
        throw new SolutionConstructionException(
                "We have no suitable components to add to the solution from current position."
                        + "\n Previous Component: "
                        + getAnt().getSolution()[getAnt().getCurrentIndex() - 1]
                        + " at position " + (getAnt().getCurrentIndex() - 1)
                        + "\n Environment: " + environment.toString()
                        + "\nPartial solution : " + getAnt().getSolutionAsString());
    }

    private Double getHeuristicTimesPheromone(E environment,
                                              ConfigurationProvider configurationProvider, C possibleMove) {
        Double heuristicValue = getAnt().getHeuristicValue(possibleMove,
                getAnt().getCurrentIndex(), environment);
        Double pheromoneTrailValue = getAnt().getPheromoneTrailValue(possibleMove,
                getAnt().getCurrentIndex(), environment);

        return Math.pow(heuristicValue,
                configurationProvider.getHeuristicImportance())
                * Math.pow(pheromoneTrailValue,
                configurationProvider.getPheromoneImportance());
    }
}
