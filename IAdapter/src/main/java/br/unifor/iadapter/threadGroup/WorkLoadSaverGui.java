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

import javax.swing.Box;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.jmeter.samplers.Clearable;
import org.apache.jmeter.testelement.AbstractTestElement;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jmeter.visualizers.gui.AbstractListenerGui;
import org.apache.jorphan.gui.JLabeledTextField;

/**
 * Create a ResultSaver test element, which saves the sample information in set
 * of files
 *
 */
public class WorkLoadSaverGui extends AbstractListenerGui implements Clearable {

	private static final long serialVersionUID = 240L;

	public WorkLoadSaverGui() {
		super();
		init();
	}

	/**
	 * @see org.apache.jmeter.gui.JMeterGUIComponent#getStaticLabel()
	 */

	public String getLabelResource() {
		return "WorkLoadSaver"; // $NON-NLS-1$
	}

	/**
	 * @see org.apache.jmeter.gui.JMeterGUIComponent#configure(TestElement)
	 */
	@Override
	public void configure(TestElement el) {
		super.configure(el);

	}

	/**
	 * @see org.apache.jmeter.gui.JMeterGUIComponent#createTestElement()
	 */

	public TestElement createTestElement() {
		WorkLoadSaver resultSaver = new WorkLoadSaver();
		modifyTestElement(resultSaver);
		return resultSaver;
	}

	/**
	 * Modifies a given TestElement to mirror the data in the gui components.
	 *
	 * @see org.apache.jmeter.gui.JMeterGUIComponent#modifyTestElement(TestElement)
	 */

	public void modifyTestElement(TestElement te) {
		super.configureTestElement(te);

	}

	/**
	 * Implements JMeterGUIComponent.clearGui
	 */
	@Override
	public void clearGui() {
		super.clearGui();

	}

	private void init() {
		/*
		 * setLayout(new BorderLayout()); setBorder(makeBorder()); Box box =
		 * Box.createVerticalBox(); box.add(makeTitlePanel());
		 * box.add(createFilenamePrefixPanel());
		 * box.add(createVariableNamePanel()); errorsOnly = new JCheckBox(
		 * JMeterUtils.getResString("resultsaver_errors")); // $NON-NLS-1$
		 * box.add(errorsOnly); successOnly = new JCheckBox(
		 * JMeterUtils.getResString("resultsaver_success")); // $NON-NLS-1$
		 * box.add(successOnly); skipAutoNumber = new JCheckBox(
		 * JMeterUtils.getResString("resultsaver_skipautonumber")); //
		 * $NON-NLS-1$ box.add(skipAutoNumber); skipSuffix = new JCheckBox(
		 * JMeterUtils.getResString("resultsaver_skipsuffix")); // $NON-NLS-1$
		 * box.add(skipSuffix); addTimestamp = new JCheckBox(
		 * JMeterUtils.getResString("resultsaver_addtimestamp")); // $NON-NLS-1$
		 * box.add(addTimestamp); numberPadLength = new JLabeledTextField(
		 * JMeterUtils.getResString("resultsaver_numberpadlen"));// $NON-NLS-1$
		 * box.add(numberPadLength); add(box, BorderLayout.NORTH);
		 */
	}

	// Needed to avoid Class cast error in Clear.java

	public void clearData() {
	}

}
