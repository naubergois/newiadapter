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

import java.awt.BorderLayout;
import java.util.Arrays;
import java.util.Collection;

import org.apache.jmeter.control.gui.AbstractControllerGui;
import org.apache.jmeter.gui.util.MenuFactory;
import org.apache.jmeter.testelement.TestElement;

/**
 * This defines a simple Test Fragment GUI that can be used instead of a Thread
 * Group to allow for a non-execution part of the Test Plan that can be saved
 * and referenced by a Module or Include Controller.
 */

public class WorkLoadTransactionControllerGui extends AbstractControllerGui {

	private static final long serialVersionUID = 240L;

	public WorkLoadTransactionControllerGui() {
		init();
	}

	/**
	 * Implements JMeterGUIComponent.createTestElement()
	 */
	
	public TestElement createTestElement() {
		WorkLoadTransactionController controller = new WorkLoadTransactionController();
		setEnabled(false);
		modifyTestElement(controller);
		return controller;
	}

	/**
	 * Implements JMeterGUIComponent.modifyTestElement(TestElement)
	 */

	public void modifyTestElement(TestElement controller) {

		configureTestElement(controller);
	}

	public String getLabelResource() {
		return "WorkLoad Transaction"; // $NON-NLS-1$
	}

	/**
	 * Initialize the GUI components and layout for this component.
	 */
	private void init() {
		setLayout(new BorderLayout(0, 5));
		setBorder(makeBorder());
		add(makeTitlePanel(), BorderLayout.NORTH);
	}

	/**
	 * Over-ride this so that we add ourselves to the Test Fragment Category
	 * instead.
	 */
	@Override
	public Collection<String> getMenuCategories() {
		return Arrays.asList(new String[] { MenuFactory.FRAGMENTS });
	}

}
