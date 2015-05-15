package br.unifor.iadapter.genetic;

/*
 * This file is part of JGAP.
 *
 * JGAP offers a dual license model containing the LGPL as well as the MPL.
 *
 * For licensing information please see the file license.txt included with JGAP
 * or have a look at the top of class org.jgap.Chromosome which representatively
 * includes the JGAP license policy applicable for any file delivered with JGAP.
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import org.jgap.BaseGeneticOperator;
import org.jgap.Configuration;
import org.jgap.Gene;
import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.ICompositeGene;
import org.jgap.IGeneticOperatorConstraint;
import org.jgap.IUniversalRateCalculator;
import org.jgap.InvalidConfigurationException;
import org.jgap.Population;
import org.jgap.RandomGenerator;

public class CrossoverOperator extends BaseGeneticOperator implements
		Comparable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** String containing the CVS revision. Read out via reflection! */
	private final static String CVS_REVISION = "$Revision: 1.45 $";

	/**
	 * The current crossover rate used by this crossover operator (mutual
	 * exclusive to m_crossoverRatePercent and m_crossoverRateCalc).
	 */
	private int m_crossoverRate;

	/**
	 * Crossover rate in percentage of population size (mutual exclusive to
	 * m_crossoverRate and m_crossoverRateCalc).
	 */
	private double m_crossoverRatePercent;

	/**
	 * Calculator for dynamically determining the crossover rate (mutual
	 * exclusive to m_crossoverRate and m_crossoverRatePercent)
	 */
	private IUniversalRateCalculator m_crossoverRateCalc;

	/**
	 * true: x-over before and after a randomly chosen x-over point false: only
	 * x-over after the chosen point.
	 */
	private boolean m_allowFullCrossOver;

	/**
	 * true: also x-over chromosomes with age of zero (newly created
	 * chromosomes)
	 */
	private boolean m_xoverNewAge;

	/**
	 * Constructs a new instance of this CrossoverOperator without a specified
	 * crossover rate, this results in dynamic crossover rate being turned off.
	 * This means that the crossover rate will be fixed at populationsize/2.
	 * <p>
	 * Attention: The configuration used is the one set with the static method
	 * Genotype.setConfiguration.
	 *
	 * @throws InvalidConfigurationException
	 *
	 * @author Chris Knowles
	 * @since 2.0
	 */
	public CrossoverOperator() throws InvalidConfigurationException {
		super(Genotype.getStaticConfiguration());
		init();
	}

	/**
	 * Constructs a new instance of this CrossoverOperator without a specified
	 * crossover rate, this results in dynamic crossover rate being turned off.
	 * This means that the crossover rate will be fixed at populationsize/2.
	 *
	 * @param a_configuration
	 *            the configuration to use
	 * @throws InvalidConfigurationException
	 *
	 * @author Klaus Meffert
	 * @since 3.0
	 */
	public CrossoverOperator(final Configuration a_configuration)
			throws InvalidConfigurationException {
		super(a_configuration);
		init();
	}

	/**
	 * Initializes certain parameters.
	 *
	 * @author Klaus Meffert
	 * @since 3.3.2
	 */
	protected void init() {
		// Set the default crossoverRate.
		// ------------------------------
		m_crossoverRate = 6;
		m_crossoverRatePercent = -1;
		setCrossoverRateCalc(null);
		setAllowFullCrossOver(true);
		setXoverNewAge(true);
	}

	/**
	 * Constructs a new instance of this CrossoverOperator with a specified
	 * crossover rate calculator, which results in dynamic crossover being
	 * turned on.
	 *
	 * @param a_configuration
	 *            the configuration to use
	 * @param a_crossoverRateCalculator
	 *            calculator for dynamic crossover rate computation
	 * @throws InvalidConfigurationException
	 *
	 * @author Chris Knowles
	 * @author Klaus Meffert
	 * @since 3.0 (since 2.0 without a_configuration)
	 */
	public CrossoverOperator(final Configuration a_configuration,
			final IUniversalRateCalculator a_crossoverRateCalculator)
			throws InvalidConfigurationException {
		this(a_configuration, a_crossoverRateCalculator, true);
	}

	/**
	 * Constructs a new instance of this CrossoverOperator with a specified
	 * crossover rate calculator, which results in dynamic crossover being
	 * turned on.
	 *
	 * @param a_configuration
	 *            the configuration to use
	 * @param a_crossoverRateCalculator
	 *            calculator for dynamic crossover rate computation
	 * @param a_allowFullCrossOver
	 *            true: x-over before AND after x-over point, false: only x-over
	 *            after x-over point
	 * @throws InvalidConfigurationException
	 *
	 * @author Klaus Meffert
	 * @since 3.3.2
	 */
	public CrossoverOperator(final Configuration a_configuration,
			final IUniversalRateCalculator a_crossoverRateCalculator,
			boolean a_allowFullCrossOver) throws InvalidConfigurationException {
		super(a_configuration);
		setCrossoverRateCalc(a_crossoverRateCalculator);
		setAllowFullCrossOver(a_allowFullCrossOver);
	}

	/**
	 * Constructs a new instance of this CrossoverOperator with the given
	 * crossover rate.
	 *
	 * @param a_configuration
	 *            the configuration to use
	 * @param a_desiredCrossoverRate
	 *            the desired rate of crossover
	 * @throws InvalidConfigurationException
	 *
	 * @author Chris Knowles
	 * @author Klaus Meffert
	 * @since 3.0 (since 2.0 without a_configuration)
	 */
	public CrossoverOperator(final Configuration a_configuration,
			final int a_desiredCrossoverRate)
			throws InvalidConfigurationException {
		this(a_configuration, a_desiredCrossoverRate, true);
	}

	/**
	 * Constructs a new instance of this CrossoverOperator with the given
	 * crossover rate. No new chromosomes are x-overed.
	 *
	 * @param a_configuration
	 *            the configuration to use
	 * @param a_desiredCrossoverRate
	 *            the desired rate of crossover
	 * @param a_allowFullCrossOver
	 *            true: x-over before AND after x-over point, false: only x-over
	 *            after x-over point
	 * @throws InvalidConfigurationException
	 *
	 * @author Klaus Meffert
	 * @since 3.3.2
	 */
	public CrossoverOperator(final Configuration a_configuration,
			final int a_desiredCrossoverRate, boolean a_allowFullCrossOver)
			throws InvalidConfigurationException {
		this(a_configuration, a_desiredCrossoverRate, a_allowFullCrossOver,
				false);
	}

	/**
	 * Constructs a new instance of this CrossoverOperator with the given
	 * crossover rate.
	 *
	 * @param a_configuration
	 *            the configuration to use
	 * @param a_desiredCrossoverRate
	 *            the desired rate of crossover
	 * @param a_allowFullCrossOver
	 *            true: x-over before AND after x-over point, false: only x-over
	 *            after x-over point
	 * @throws InvalidConfigurationException
	 * @param a_xoverNewAge
	 *            true: also x-over chromosomes with age of zero (newly created
	 *            chromosomes)
	 *
	 * @author Klaus Meffert
	 * @since 3.3.2
	 */
	public CrossoverOperator(final Configuration a_configuration,
			final int a_desiredCrossoverRate,
			final boolean a_allowFullCrossOver, final boolean a_xoverNewAge)
			throws InvalidConfigurationException {
		super(a_configuration);
		if (a_desiredCrossoverRate < 1) {
			throw new IllegalArgumentException(
					"Crossover rate must be greater zero");
		}
		m_crossoverRate = a_desiredCrossoverRate;
		m_crossoverRatePercent = -1;
		setCrossoverRateCalc(null);
		setAllowFullCrossOver(a_allowFullCrossOver);
		setXoverNewAge(a_xoverNewAge);
	}

	/**
	 * Constructs a new instance of this CrossoverOperator with the given
	 * crossover rate. No new chromosomes are x-overed.
	 *
	 * @param a_configuration
	 *            the configuration to use
	 * @param a_crossoverRatePercentage
	 *            the desired rate of crossover in percentage of the population
	 * @throws InvalidConfigurationException
	 *
	 * @author Chris Knowles
	 * @author Klaus Meffert
	 * @since 3.0 (since 2.0 without a_configuration)
	 */
	public CrossoverOperator(final Configuration a_configuration,
			final double a_crossoverRatePercentage)
			throws InvalidConfigurationException {
		this(a_configuration, a_crossoverRatePercentage, true);
	}

	/**
	 * Constructs a new instance of this CrossoverOperator with the given
	 * crossover rate. No new chromosomes are x-overed.
	 *
	 * @param a_configuration
	 *            the configuration to use
	 * @param a_crossoverRatePercentage
	 *            the desired rate of crossover in percentage of the population
	 * @param a_allowFullCrossOver
	 *            true: x-over before AND after x-over point, false: only x-over
	 *            after x-over point
	 * @throws InvalidConfigurationException
	 *
	 * @author Klaus Meffert
	 * @since 3.3.2.
	 */
	public CrossoverOperator(final Configuration a_configuration,
			final double a_crossoverRatePercentage, boolean a_allowFullCrossOver)
			throws InvalidConfigurationException {
		this(a_configuration, a_crossoverRatePercentage, a_allowFullCrossOver,
				false);
	}

	/**
	 * Constructs a new instance of this CrossoverOperator with the given
	 * crossover rate.
	 *
	 * @param a_configuration
	 *            the configuration to use
	 * @param a_crossoverRatePercentage
	 *            the desired rate of crossover in percentage of the population
	 * @param a_allowFullCrossOver
	 *            true: x-over before AND after x-over point, false: only x-over
	 *            after x-over point
	 * @param a_xoverNewAge
	 *            true: also x-over chromosomes with age of zero (newly created
	 *            chromosomes)
	 * @throws InvalidConfigurationException
	 *
	 * @author Klaus Meffert
	 * @since 3.3.2.
	 */
	public CrossoverOperator(final Configuration a_configuration,
			final double a_crossoverRatePercentage,
			final boolean a_allowFullCrossOver, final boolean a_xoverNewAge)
			throws InvalidConfigurationException {
		super(a_configuration);
		if (a_crossoverRatePercentage <= 0.0d) {
			throw new IllegalArgumentException(
					"Crossover rate must be greater zero");
		}
		m_crossoverRatePercent = a_crossoverRatePercentage;
		m_crossoverRate = -1;
		setCrossoverRateCalc(null);
		setAllowFullCrossOver(a_allowFullCrossOver);
		setXoverNewAge(a_xoverNewAge);
	}

	public List<IChromosome> operateBestIndividuals(List chromoList,
			final List a_candidateChromosomes) {
		// Work out the number of crossovers that should be performed.
		// -----------------------------------------------------------

		List<IChromosome> list = new ArrayList<IChromosome>();
		int size = chromoList.size();
		int numCrossovers = 0;
		if (m_crossoverRate >= 0) {
			numCrossovers = size / m_crossoverRate;
		} else if (m_crossoverRateCalc != null) {
			numCrossovers = size / m_crossoverRateCalc.calculateCurrentRate();
		} else {
			numCrossovers = (int) (size * m_crossoverRatePercent);
		}

		Random random = new Random();
		int index1, index2;
		for (int i = 0; i < numCrossovers; i++) {

			index1 = random.nextInt(size);
			index2 = random.nextInt(a_candidateChromosomes.size());
			IChromosome chrom1 = null;
			IChromosome chrom2 = null;

			chrom1 = (IChromosome) chromoList.get(index1);
			chrom2 = (IChromosome) a_candidateChromosomes.get(index2);

			IChromosome firstMate = (IChromosome) chrom1.clone();
			IChromosome secondMate = (IChromosome) chrom2.clone();

			doCrossover(firstMate, secondMate, a_candidateChromosomes);
			list.add(firstMate);
			list.add(secondMate);
		}
		return list;
	}

	protected void doCrossover(IChromosome firstMate, IChromosome secondMate,
			List a_candidateChromosomes) {
		Gene[] firstGenes = firstMate.getGenes();
		Gene[] secondGenes = secondMate.getGenes();
		Random random = new Random();
		int locus = random.nextInt(firstGenes.length);
		// Swap the genes.
		// ---------------
		Gene gene1;
		Gene gene2;
		Object firstAllele;
		for (int j = locus; j < firstGenes.length; j++) {
			// Make a distinction for ICompositeGene for the first gene.
			// ---------------------------------------------------------
			int index = 0;
			if (firstGenes[j] instanceof ICompositeGene) {
				// Randomly determine gene to be considered.
				// -----------------------------------------
				index = random.nextInt(firstGenes[j].size());
				gene1 = ((ICompositeGene) firstGenes[j]).geneAt(index);
			} else {
				gene1 = firstGenes[j];
			}
			// Make a distinction for the second gene if CompositeGene.
			// --------------------------------------------------------
			if (secondGenes[j] instanceof ICompositeGene) {
				gene2 = ((ICompositeGene) secondGenes[j]).geneAt(index);
			} else {
				gene2 = secondGenes[j];
			}
			firstAllele = gene1.getAllele();
			gene1.setAllele(gene2.getAllele());
			gene2.setAllele(firstAllele);
		}
		// Add the modified chromosomes to the candidate pool so that
		// they'll be considered for natural selection during the next
		// phase of evolution.
		// -----------------------------------------------------------
		a_candidateChromosomes.add(firstMate);
		a_candidateChromosomes.add(secondMate);
	}

	/**
	 * Sets the crossover rate calculator.
	 *
	 * @param a_crossoverRateCalculator
	 *            the new calculator
	 *
	 * @author Chris Knowles
	 * @since 2.0
	 */
	private void setCrossoverRateCalc(
			final IUniversalRateCalculator a_crossoverRateCalculator) {
		m_crossoverRateCalc = a_crossoverRateCalculator;
		if (a_crossoverRateCalculator != null) {
			m_crossoverRate = -1;
			m_crossoverRatePercent = -1d;
		}
	}

	/**
	 * Compares the given object to this one.
	 *
	 * @param a_other
	 *            the instance against which to compare this instance
	 * @return a negative number if this instance is "less than" the given
	 *         instance, zero if they are equal to each other, and a positive
	 *         number if this is "greater than" the given instance
	 *
	 * @author Klaus Meffert
	 * @since 2.6
	 */
	public int compareTo(final Object a_other) {
		/** @todo consider Configuration */
		if (a_other == null) {
			return 1;
		}
		CrossoverOperator op = (CrossoverOperator) a_other;
		if (m_crossoverRateCalc == null) {
			if (op.m_crossoverRateCalc != null) {
				return -1;
			}
		} else {
			if (op.m_crossoverRateCalc == null) {
				return 1;
			}
		}
		if (m_crossoverRate != op.m_crossoverRate) {
			if (m_crossoverRate > op.m_crossoverRate) {
				return 1;
			} else {
				return -1;
			}
		}
		if (m_allowFullCrossOver != op.m_allowFullCrossOver) {
			if (m_allowFullCrossOver) {
				return 1;
			} else {
				return -1;
			}
		}
		if (m_xoverNewAge != op.m_xoverNewAge) {
			if (m_xoverNewAge) {
				return 1;
			} else {
				return -1;
			}
		}
		// Everything is equal. Return zero.
		// ---------------------------------
		return 0;
	}

	/**
	 * @param a_allowFullXOver
	 *            x-over before and after a randomly chosen point
	 *
	 * @author Klaus Meffert
	 * @since 3.3.2
	 */
	public void setAllowFullCrossOver(boolean a_allowFullXOver) {
		m_allowFullCrossOver = a_allowFullXOver;
	}

	/**
	 * @return x-over before and after a randomly chosen x-over point
	 *
	 * @author Klaus Meffert
	 * @since 3.3.2
	 */
	public boolean isAllowFullCrossOver() {
		return m_allowFullCrossOver;
	}

	/**
	 * @return the crossover rate set
	 *
	 * @author Klaus Meffert
	 * @since 3.3.2
	 */
	public int getCrossOverRate() {
		return m_crossoverRate;
	}

	/**
	 * @return the crossover rate set
	 *
	 * @author Klaus Meffert
	 * @since 3.3.2
	 */
	public double getCrossOverRatePercent() {
		return m_crossoverRatePercent;
	}

	/**
	 * @param a_xoverNewAge
	 *            true: also x-over chromosomes with age of zero (newly created
	 *            chromosomes)
	 *
	 * @author Klaus Meffert
	 * @since 3.3.2
	 */
	public void setXoverNewAge(boolean a_xoverNewAge) {
		m_xoverNewAge = a_xoverNewAge;
	}

	/**
	 * @return a_xoverNewAge true: also x-over chromosomes with age of zero
	 *         (newly created chromosomes)
	 *
	 * @author Klaus Meffert
	 * @since 3.3.2
	 */
	public boolean isXoverNewAge() {
		return m_xoverNewAge;
	}

	public void operate(Population a_population, List a_candidateChromosomes) {
		// TODO Auto-generated method stub
		
	}
}
