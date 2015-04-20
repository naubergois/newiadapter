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

	private JTextField filename;

	private JTextField variableName;

	private JCheckBox errorsOnly;

	private JCheckBox successOnly;

	private JCheckBox skipAutoNumber;

	private JCheckBox skipSuffix;

	private JCheckBox addTimestamp;

	private JLabeledTextField numberPadLength;

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
		filename.setText(el.getPropertyAsString(WorkLoadSaver.FILENAME));
		errorsOnly.setSelected(el
				.getPropertyAsBoolean(WorkLoadSaver.ERRORS_ONLY));
		successOnly.setSelected(el
				.getPropertyAsBoolean(WorkLoadSaver.SUCCESS_ONLY));
		skipAutoNumber.setSelected(el
				.getPropertyAsBoolean(WorkLoadSaver.SKIP_AUTO_NUMBER));
		skipSuffix.setSelected(el
				.getPropertyAsBoolean(WorkLoadSaver.SKIP_SUFFIX));
		variableName.setText(el.getPropertyAsString(
				WorkLoadSaver.VARIABLE_NAME, ""));
		addTimestamp.setSelected(el
				.getPropertyAsBoolean(WorkLoadSaver.ADD_TIMESTAMP));
		numberPadLength.setText(el.getPropertyAsString(
				WorkLoadSaver.NUMBER_PAD_LENGTH, ""));
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
		te.setProperty(WorkLoadSaver.FILENAME, filename.getText());
		te.setProperty(WorkLoadSaver.ERRORS_ONLY, errorsOnly.isSelected());
		te.setProperty(WorkLoadSaver.SKIP_AUTO_NUMBER,
				skipAutoNumber.isSelected());
		te.setProperty(WorkLoadSaver.SKIP_SUFFIX, skipSuffix.isSelected());
		te.setProperty(WorkLoadSaver.SUCCESS_ONLY, successOnly.isSelected());
		te.setProperty(WorkLoadSaver.ADD_TIMESTAMP, addTimestamp.isSelected(),
				false);
		AbstractTestElement at = (AbstractTestElement) te;
		at.setProperty(WorkLoadSaver.VARIABLE_NAME, variableName.getText(), ""); //$NON-NLS-1$
		at.setProperty(WorkLoadSaver.NUMBER_PAD_LENGTH,
				numberPadLength.getText(), ""); //$NON-NLS-1$
	}

	/**
	 * Implements JMeterGUIComponent.clearGui
	 */
	@Override
	public void clearGui() {
		super.clearGui();

		skipAutoNumber.setSelected(false);
		skipSuffix.setSelected(false);
		filename.setText(""); //$NON-NLS-1$
		errorsOnly.setSelected(false);
		successOnly.setSelected(false);
		addTimestamp.setSelected(false);
		variableName.setText(""); //$NON-NLS-1$
		numberPadLength.setText(""); //$NON-NLS-1$
	}

	private void init() {
		setLayout(new BorderLayout());
		setBorder(makeBorder());
		Box box = Box.createVerticalBox();
		box.add(makeTitlePanel());
		box.add(createFilenamePrefixPanel());
		box.add(createVariableNamePanel());
		errorsOnly = new JCheckBox(
				JMeterUtils.getResString("resultsaver_errors")); // $NON-NLS-1$
		box.add(errorsOnly);
		successOnly = new JCheckBox(
				JMeterUtils.getResString("resultsaver_success")); // $NON-NLS-1$
		box.add(successOnly);
		skipAutoNumber = new JCheckBox(
				JMeterUtils.getResString("resultsaver_skipautonumber")); // $NON-NLS-1$
		box.add(skipAutoNumber);
		skipSuffix = new JCheckBox(
				JMeterUtils.getResString("resultsaver_skipsuffix")); // $NON-NLS-1$
		box.add(skipSuffix);
		addTimestamp = new JCheckBox(
				JMeterUtils.getResString("resultsaver_addtimestamp")); // $NON-NLS-1$
		box.add(addTimestamp);
		numberPadLength = new JLabeledTextField(
				JMeterUtils.getResString("resultsaver_numberpadlen"));// $NON-NLS-1$
		box.add(numberPadLength);
		add(box, BorderLayout.NORTH);
	}

	private JPanel createFilenamePrefixPanel() {
		JLabel label = new JLabel(
				JMeterUtils.getResString("resultsaver_prefix")); // $NON-NLS-1$

		filename = new JTextField(10);
		filename.setName(WorkLoadSaver.FILENAME);
		label.setLabelFor(filename);

		JPanel filenamePanel = new JPanel(new BorderLayout(5, 0));
		filenamePanel.add(label, BorderLayout.WEST);
		filenamePanel.add(filename, BorderLayout.CENTER);
		return filenamePanel;
	}

	private JPanel createVariableNamePanel() {
		JLabel label = new JLabel(
				JMeterUtils.getResString("resultsaver_variable")); // $NON-NLS-1$

		variableName = new JTextField(10);
		variableName.setName(WorkLoadSaver.VARIABLE_NAME);
		label.setLabelFor(variableName);

		JPanel filenamePanel = new JPanel(new BorderLayout(5, 0));
		filenamePanel.add(label, BorderLayout.WEST);
		filenamePanel.add(variableName, BorderLayout.CENTER);
		return filenamePanel;
	}

	// Needed to avoid Class cast error in Clear.java

	public void clearData() {
	}

}
