//  Distance.java
//
//  Author:
//       Antonio J. Nebro <antonio@lcc.uma.es>
//       Juan J. Durillo <durillo@lcc.uma.es>
//
//  Copyright (c) 2011 Antonio J. Nebro, Juan J. Durillo
//
//  This program is free software: you can redistribute it and/or modify
//  it under the terms of the GNU Lesser General Public License as published by
//  the Free Software Foundation, either version 3 of the License, or
//  (at your option) any later version.
//
//  This program is distributed in the hope that it will be useful,
//  but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//  GNU Lesser General Public License for more details.
// 
//  You should have received a copy of the GNU Lesser General Public License
//  along with this program.  If not, see <http://www.gnu.org/licenses/>.

package br.unifor.iadapter.algorithm;

import br.unifor.iadapter.threadGroup.workload.WorkLoad;
import jmetal.util.JMException;
import jmetal.util.comparators.ObjectiveComparator;

/**
 * This class implements some utilities for calculating distances
 */
public class Distance {

	/**
	 * Constructor.
	 */
	public Distance() {
		// do nothing.
	} // Distance

	/**
	 * Returns a matrix with distances between solutions in a
	 * <code>SolutionSet</code>.
	 * 
	 * @param solutionSet
	 *            The <code>SolutionSet</code>.
	 * @return a matrix with distances.
	 */
	public double[][] distanceMatrix(SolutionSet solutionSet) {
		WorkLoad solutionI, solutionJ;

		// The matrix of distances
		double[][] distance = new double[solutionSet.size()][solutionSet.size()];
		// -> Calculate the distances
		for (int i = 0; i < solutionSet.size(); i++) {
			distance[i][i] = 0.0;
			solutionI = solutionSet.get(i);
			for (int j = i + 1; j < solutionSet.size(); j++) {
				solutionJ = solutionSet.get(j);
				distance[i][j] = this.distanceBetweenObjectives(solutionI, solutionJ);
				distance[j][i] = distance[i][j];
			} // for
		} // for

		// ->Return the matrix of distances
		return distance;
	} // distanceMatrix

	/**
	 * Returns the minimum distance from a <code>Solution</code> to a
	 * <code>SolutionSet according to the objective values</code>.
	 * 
	 * @param solution
	 *            The <code>Solution</code>.
	 * @param solutionSet
	 *            The <code>SolutionSet</code>.
	 * @return The minimum distance between solution and the set.
	 * @throws JMException
	 */
	public double distanceToSolutionSetInObjectiveSpace(WorkLoad solution, SolutionSet solutionSet) throws JMException {
		// At start point the distance is the max
		double distance = Double.MAX_VALUE;

		// found the min distance respect to population
		for (int i = 0; i < solutionSet.size(); i++) {
			double aux = this.distanceBetweenObjectives(solution, solutionSet.get(i));
			if (aux < distance)
				distance = aux;
		} // for

		// ->Return the best distance
		return distance;
	} // distanceToSolutionSetinObjectiveSpace

	/**
	 * Returns the minimum distance from a <code>Solution</code> to a
	 * <code>SolutionSet according to the encodings.variable values</code>.
	 * 
	 * @param solution
	 *            The <code>Solution</code>.
	 * @param solutionSet
	 *            The <code>SolutionSet</code>.
	 * @return The minimum distance between solution and the set.
	 * @throws JMException
	 */
	public double distanceToSolutionSetInSolutionSpace(WorkLoad solution, SolutionSet solutionSet) throws JMException {
		// At start point the distance is the max
		double distance = Double.MAX_VALUE;

		// found the min distance respect to population
		for (int i = 0; i < solutionSet.size(); i++) {
			double aux = this.distanceBetweenSolutions(solution, solutionSet.get(i));
			if (aux < distance)
				distance = aux;
		} // for

		// ->Return the best distance
		return distance;
	} // distanceToSolutionSetInSolutionSpace

	/**
	 * Returns the distance between two solutions in the search space.
	 * 
	 * @param solutionI
	 *            The first <code>Solution</code>.
	 * @param solutionJ
	 *            The second <code>Solution</code>.
	 * @return the distance between solutions.
	 * @throws JMException
	 */
	public double distanceBetweenSolutions(WorkLoad solutionI, WorkLoad solutionJ) throws JMException {
		/*
		 * double distance = 0.0; if ((solutionI.getDecisionVariables() != null)
		 * && (solutionJ.getDecisionVariables() != null)) { Variable[]
		 * decisionVariableI = solutionI.getDecisionVariables(); Variable[]
		 * decisionVariableJ = solutionJ.getDecisionVariables();
		 * 
		 * double diff; //Auxiliar var //-> Calculate the Euclidean distance for
		 * (int i = 0; i < decisionVariableI.length; i++){ diff =
		 * decisionVariableI[i].getValue() - decisionVariableJ[i].getValue();
		 * distance += Math.pow(diff,2.0); } // for } //-> Return the euclidean
		 * distance return Math.sqrt(distance);
		 */
		double distance = 0.0;
		
		double diff; // Auxiliar var
		// -> Calculate the Euclidean distance
		for (int i = 0; i < solutionI.getNumberOfObjectives(); i++) {
			diff = solutionI.getObjectiveValue(i) - solutionJ.getObjectiveValue(i);
			distance += Math.pow(diff, 2.0);
		} // for
		// -> Return the euclidean distance
		return Math.sqrt(distance);
	} // distanceBetweenSolutions

	/**
	 * Returns the distance between two solutions in objective space.
	 * 
	 * @param solutionI
	 *            The first <code>Solution</code>.
	 * @param solutionJ
	 *            The second <code>Solution</code>.
	 * @return the distance between solutions in objective space.
	 */
	public double distanceBetweenObjectives(WorkLoad solutionI, WorkLoad solutionJ) {
		double diff; // Auxiliar var
		double distance = 0.0;
		// -> Calculate the euclidean distance
		for (int nObj = 0; nObj < solutionI.getNumberOfObjectives(); nObj++) {
			diff = solutionI.getObjectiveValue(nObj) - solutionJ.getObjectiveValue(nObj);
			distance += Math.pow(diff, 2.0);
		} // for

		// Return the euclidean distance
		return Math.sqrt(distance);
	} // distanceBetweenObjectives.

	/**
	 * Return the index of the nearest solution in the solution set to a given
	 * solution
	 * 
	 * @param solution
	 * @param solutionSet
	 * @return The index of the nearest solution; -1 if the solutionSet is empty
	 */
	public int indexToNearestSolutionInSolutionSpace(WorkLoad solution, SolutionSet solutionSet) {
		int index = -1;
		double minimumDistance = Double.MAX_VALUE;
		try {
			for (int i = 0; i < solutionSet.size(); i++) {
				double distance = 0;
				distance = distanceBetweenSolutions(solution, solutionSet.get(i));
				if (distance < minimumDistance) {
					minimumDistance = distance;
					index = i;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return index;
	}

	/**
	 * Assigns crowding distances to all solutions in a <code>SolutionSet</code>
	 * .
	 * 
	 * @param solutionSet
	 *            The <code>SolutionSet</code>.
	 * @param nObjs
	 *            Number of objectives.
	 */
	public void crowdingDistanceAssignment(SolutionSet solutionSet, int nObjs) {
		int size = solutionSet.size();

		if (size == 0)
			return;

		if (size == 1) {
			solutionSet.get(0).setCrowdingDistance(Double.POSITIVE_INFINITY);
			return;
		} // if

		if (size == 2) {
			solutionSet.get(0).setCrowdingDistance(Double.POSITIVE_INFINITY);
			solutionSet.get(1).setCrowdingDistance(Double.POSITIVE_INFINITY);
			return;
		} // if

		// Use a new SolutionSet to evite alter original solutionSet
		SolutionSet front = new SolutionSet(size);
		for (int i = 0; i < size; i++) {
			front.add(solutionSet.get(i));
		}

		for (int i = 0; i < size; i++)
			front.get(i).setCrowdingDistance(0.0);

		double objetiveMaxn;
		double objetiveMinn;
		double distance;

		for (int i = 0; i < nObjs; i++) {
			// Sort the population by Obj n
			front.sort(new ObjectiveComparator(i));
			objetiveMinn = front.get(0).getObjectiveValue(i);
			objetiveMaxn = front.get(front.size() - 1).getObjectiveValue(i);

			// Set de crowding distance
			front.get(0).setCrowdingDistance(Double.POSITIVE_INFINITY);
			front.get(size - 1).setCrowdingDistance(Double.POSITIVE_INFINITY);

			for (int j = 1; j < size - 1; j++) {
				distance = front.get(j + 1).getObjectiveValue(i) - front.get(j - 1).getObjectiveValue(i);
				distance = distance / (objetiveMaxn - objetiveMinn);
				distance += front.get(j).getCrowdingDistance();
				front.get(j).setCrowdingDistance(distance);
			} // for
		} // for
	} // crowdingDistanceAssing
} // Distance
