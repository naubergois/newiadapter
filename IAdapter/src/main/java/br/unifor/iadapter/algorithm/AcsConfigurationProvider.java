package br.unifor.iadapter.algorithm;

/**
 * A configuration provider, but for Ant Colony System algorithm.
 *
 * @author Carlos G. Gavidia
 */
public interface AcsConfigurationProvider extends ConfigurationProvider {

    double getBestChoiceProbability();
}
