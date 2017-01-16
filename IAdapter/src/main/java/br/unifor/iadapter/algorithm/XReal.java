//  XReal.java
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
import jmetal.core.Solution;
import jmetal.encodings.solutionType.ArrayRealAndBinarySolutionType;
import jmetal.encodings.solutionType.ArrayRealSolutionType;
import jmetal.encodings.solutionType.BinaryRealSolutionType;
import jmetal.encodings.solutionType.RealSolutionType;
import jmetal.encodings.variable.ArrayReal;
import jmetal.util.Configuration;
import jmetal.util.JMException;

/**
 * Wrapper for accessing real-coded solutions
 */
public class XReal {
	private WorkLoad solution_ ;
	

	/**
	 * Constructor
	 */
	public XReal() {
	} // Constructor

	/**
	 * Constructor
	 * @param solution
	 */
	public XReal(WorkLoad solution) {
		this() ;
	
		solution_ = solution ;
	}

	/**
	 * Gets value of a encodings.variable
	 * @param index Index of the encodings.variable
	 * @return The value of the encodings.variable
	 * @throws JMException
	 */
	public double getValue(int index) throws JMException {
		return solution_.getObjectiveValue(index);
	}

	/**
	 * Sets the value of a encodings.variable
	 * @param index Index of the encodings.variable
	 * @param value Value to be assigned
	 * @throws JMException
	 */
	/*public void setValue(int index, double value) throws JMException {
		if (type_.getClass() == RealSolutionType.class)
			solution_.getDecisionVariables()[index].setValue(value) ;
		else if (type_.getClass() == ArrayRealSolutionType.class)
			((ArrayReal)(solution_.getDecisionVariables()[0])).array_[index]=value ;
		else if (type_.getClass() == ArrayRealAndBinarySolutionType.class)
			((ArrayReal)(solution_.getDecisionVariables()[0])).array_[index]=value ;
		else
			Configuration.logger_.severe("jmetal.util.wrapper.XReal.setValue, solution type " +
					type_ + "+ invalid") ;		
	} */ // setValue	

	
} // XReal