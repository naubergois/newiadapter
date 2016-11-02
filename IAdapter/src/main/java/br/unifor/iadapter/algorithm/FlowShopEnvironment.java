package br.unifor.iadapter.algorithm;

import java.util.List;
import java.util.logging.Logger;

import br.unifor.iadapter.threadGroup.workload.WorkLoad;

class FlowShopEnvironment extends Environment {

    private static Logger logger = Logger.getLogger(FlowShopEnvironment.class
            .getName());

    private int numberOfJobs;

    /**
     * Environment for the Flow Shop Scheduling Problem.
     *
     * @param problemGraph Graph representation of the problem.
     * @throws InvalidInputException When the graph is incorrectly formed.
     */
    public FlowShopEnvironment(double[][] problemGraph)
            throws InvalidInputException {
        super(problemGraph);
        this.numberOfJobs = problemGraph.length;

        logger.info("Number of Jobs: " + numberOfJobs);
    }

    public int getNumberOfJobs() {
        return getProblemGraph().length;
    }

    @Override
    protected boolean isProblemGraphValid() {
                return true;
    }

    
    @Override
    protected double[][] createPheromoneMatrix()
            throws MethodNotImplementedException {
        int jobs = getNumberOfJobs();

        return new double[jobs][jobs];
    }
}
