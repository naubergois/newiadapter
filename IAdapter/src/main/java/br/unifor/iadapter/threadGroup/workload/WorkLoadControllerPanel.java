package br.unifor.iadapter.threadGroup.workload;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.jmeter.control.gui.AbstractControllerGui;
import org.apache.jmeter.testelement.TestElement;

/**
 * The user interface for a controller which specifies that its subcomponents
 * should be executed while a condition holds. This component can be used
 * standalone or embedded into some other component.
 *
 */

public class WorkLoadControllerPanel extends AbstractControllerGui {

	@Override
	public String getStaticLabel() {

		return "nauberphdwork@WorkLoadController";
	}

	private static final long serialVersionUID = 240L;

	/**
	 * A field allowing the user to specify the number of times the controller
	 * should loop.
	 */

	/**
	 * Boolean indicating whether or not this component should display its name.
	 * If true, this is a standalone component. If false, this component is
	 * intended to be used as a subpanel for another component.
	 */
	private boolean displayName = true;

	/**
	 * Create a new LoopControlPanel as a standalone component.
	 */
	public WorkLoadControllerPanel() {
		this(true);
	}

	/**
	 * Create a new IfControllerPanel as either a standalone or an embedded
	 * component.
	 *
	 * @param displayName
	 *            indicates whether or not this component should display its
	 *            name. If true, this is a standalone component. If false, this
	 *            component is intended to be used as a subpanel for another
	 *            component.
	 */
	public WorkLoadControllerPanel(boolean displayName) {
		this.displayName = displayName;
		init();
	}

	/**
	 * A newly created component can be initialized with the contents of a Test
	 * Element object by calling this method. The component is responsible for
	 * querying the Test Element object for the relevant information to display
	 * in its GUI.
	 *
	 * @param element
	 *            the TestElement to configure
	 */
	@Override
	public void configure(TestElement element) {
		super.configure(element);
		if (element instanceof WorkLoadController) {
			WorkLoadController ifController = (WorkLoadController) element;

		}

	}

	/**
	 * Implements JMeterGUIComponent.createTestElement()
	 */

	public TestElement createTestElement() {
		WorkLoadController controller = new WorkLoadController();
		modifyTestElement(controller);
		return controller;
	}

	/**
	 * Implements JMeterGUIComponent.modifyTestElement(TestElement)
	 */

	public void modifyTestElement(TestElement controller) {
		configureTestElement(controller);
		if (controller instanceof WorkLoadController) {
			WorkLoadController ifController = (WorkLoadController) controller;

		}
	}

	/**
	 * Implements JMeterGUIComponent.clearGui
	 */
	@Override
	public void clearGui() {
		super.clearGui();

	}

	public String getLabelResource() {
		return "WorkLoadController"; // $NON-NLS-1$
	}

	/**
	 * Initialize the GUI components and layout for this component.
	 */
	private void init() {
		// Standalone
		if (displayName) {
			setLayout(new BorderLayout(0, 5));
			setBorder(makeBorder());
			add(makeTitlePanel(), BorderLayout.NORTH);

			JPanel mainPanel = new JPanel(new BorderLayout());

			add(mainPanel, BorderLayout.CENTER);

		} else {
			// Embedded
			setLayout(new BorderLayout());

		}

		ImageIcon iadapter = new javax.swing.ImageIcon(getClass().getResource(
				"iadapter.png"));

		JLabel icon = new JLabel(iadapter);

		this.add(BorderLayout.CENTER, icon);
	}

	/**
	 * Create a GUI panel containing the condition.
	 *
	 * @return a GUI panel containing the condition components
	 */

}
