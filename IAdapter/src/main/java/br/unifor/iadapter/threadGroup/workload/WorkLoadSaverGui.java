package br.unifor.iadapter.threadGroup.workload;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import org.apache.jmeter.samplers.Clearable;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.visualizers.gui.AbstractListenerGui;

/**
 * This component is mandatory to iadapter execution
 * 
 */
public class WorkLoadSaverGui extends AbstractListenerGui implements Clearable {

	@Override
	public String getStaticLabel() {

		return "nauber@WorkLoadSaver";
	}

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
		setLayout(new BorderLayout());
		setBorder(makeBorder());
		ImageIcon iadapter = new javax.swing.ImageIcon(getClass().getResource(
				"iadapter.png"));

		JLabel icon = new JLabel(iadapter);

		this.add(BorderLayout.CENTER, icon);

	}

	public void clearData() {
	}

}
