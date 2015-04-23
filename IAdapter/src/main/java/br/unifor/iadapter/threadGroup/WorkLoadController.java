/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package br.unifor.iadapter.threadGroup;

import java.io.Serializable;

import org.apache.jmeter.control.Controller;
import org.apache.jmeter.control.GenericController;
import org.apache.jmeter.control.NextIsNullException;
import org.apache.jmeter.samplers.Sampler;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.testelement.property.StringProperty;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.log.Logger;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

/**
 *
 *
 * This is a Conditional Controller; it will execute the set of statements
 * (samplers/controllers, etc) while the 'condition' is true.
 * <p>
 * In a programming world - this is equivalent of :
 * 
 * <pre>
 * if (condition) {
 *          statements ....
 *          }
 * </pre>
 * 
 * In JMeter you may have :
 * 
 * <pre>
 * Thread-Group (set to loop a number of times or indefinitely,
 *    ... Samplers ... (e.g. Counter )
 *    ... Other Controllers ....
 *    ... IfController ( condition set to something like - ${counter} &lt; 10)
 *       ... statements to perform if condition is true
 *       ...
 *    ... Other Controllers /Samplers }
 * </pre>
 */

// for unit test code @see TestIfController

public class WorkLoadController extends GenericController implements
		Serializable {

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "[" + this.getName() + "]";
	}

	private static final Logger logger = LoggingManager.getLoggerForClass();

	private static final long serialVersionUID = 240L;

	private static final String CONDITION = "IfController.condition"; //$NON-NLS-1$

	private static final String EVALUATE_ALL = "IfController.evaluateAll"; //$NON-NLS-1$

	private static final String USE_EXPRESSION = "IfController.useExpression"; //$NON-NLS-1$

	/**
	 * constructor
	 */
	public WorkLoadController() {
		super();
	}

	/**
	 * constructor
	 * 
	 * @param condition
	 *            The condition for this controller
	 */
	public WorkLoadController(String condition) {
		super();
		this.setCondition(condition);
	}

	/**
	 * Condition Accessor - this is gonna be like <code>${count} &lt; 10</code>
	 * 
	 * @param condition
	 *            The condition for this controller
	 */
	public void setCondition(String condition) {
		setProperty(new StringProperty(CONDITION, condition));
	}

	/**
	 * Condition Accessor - this is gonna be like <code>${count} &lt; 10</code>
	 * 
	 * @return the condition associated with this controller
	 */
	public String getCondition() {
		return getPropertyAsString(CONDITION);
	}

	/**
	 * evaluate the condition clause log error if bad condition
	 */
	private boolean evaluateCondition(String cond) {

		if (cond.equals("TRUE")) {
			return true;
		} else {
			return false;
		}
	}

	private static boolean evaluateExpression(String cond) {
		return cond.equalsIgnoreCase("true"); // $NON-NLS-1$
	}

	/**
	 * This is overriding the parent method. IsDone indicates whether the
	 * termination condition is reached. I.e. if the condition evaluates to
	 * False - then isDone() returns TRUE
	 */
	@Override
	public boolean isDone() {
		// boolean result = true;
		// try {
		// result = !evaluateCondition();
		// } catch (Exception e) {
		// logger.error(e.getMessage(), e);
		// }
		// setDone(true);
		// return result;
		// setDone(false);
		return false;
	}

	protected void initializeSubControllers() {
		for (TestElement te : subControllersAndSamplers) {
			if (te instanceof GenericController) {
				((Controller) te).initialize();
			}
		}
	}

	/**
	 * @see org.apache.jmeter.control.Controller#next()
	 */
	@Override
	public Sampler next() {
		// We should only evalute the condition if it is the first
		// time ( first "iteration" ) we are called.
		// For subsequent calls, we are inside the IfControllerGroup,
		// so then we just pass the control to the next item inside the if
		// control
		boolean result = true;
		if (isEvaluateAll() || isFirst()) {
			result = isUseExpression() ? evaluateExpression(getCondition())
					: evaluateCondition(getCondition());
		}

		if (result) {
			return super.next();
		}
		// If-test is false, need to re-initialize indexes
		try {
			initializeSubControllers();
			return nextIsNull();
		} catch (NextIsNullException e1) {
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void triggerEndOfLoop() {
		initializeSubControllers();
		super.triggerEndOfLoop();
	}

	public boolean isEvaluateAll() {
		return getPropertyAsBoolean(EVALUATE_ALL, false);
	}

	public void setEvaluateAll(boolean b) {
		setProperty(EVALUATE_ALL, b);
	}

	public boolean isUseExpression() {
		return getPropertyAsBoolean(USE_EXPRESSION, false);
	}

	public void setUseExpression(boolean selected) {
		setProperty(USE_EXPRESSION, selected, false);
	}
}
