package br.unifor.iadapter.threadGroup;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.apache.jmeter.control.LoopController;
import org.apache.jmeter.control.gui.LoopControlPanel;
import org.apache.jmeter.gui.GuiPackage;
import org.apache.jmeter.gui.tree.JMeterTreeNode;
import org.apache.jmeter.gui.util.PowerTableModel;
import org.apache.jmeter.gui.util.VerticalPanel;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.testelement.property.CollectionProperty;
import org.apache.jmeter.testelement.property.JMeterProperty;
import org.apache.jmeter.testelement.property.NullProperty;
import org.apache.jmeter.testelement.property.PropertyIterator;
import org.apache.jmeter.threads.AbstractThreadGroup;
import org.apache.jmeter.threads.JMeterThread;
import org.apache.jmeter.threads.gui.AbstractThreadGroupGui;
import org.apache.jorphan.collections.HashTree;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.log.Logger;

public class WorkLoadThreadGroupGUI extends AbstractThreadGroupGui implements
		TableModelListener, CellEditorListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String WIKIPAGE = "WorkLoadThreadGroup";
	private static final Logger log = LoggingManager.getLoggerForClass();
	/**
     *
     */
	protected ConcurrentHashMap<String, AbstractGraphRow> model;
	private GraphPanelChart chart;
	/**
     *
     */

	public static final String[] columnIdentifiers = new String[] { "Name",
			"Kind", "Users", "Response Time", "Error", "Fit", "Function1",
			"Function2", "Function3", "Function4", "Function5", "Function6",
			"Function7", "Function8", "Function9", "Function10" };
	/**
     *
     */
	@SuppressWarnings("rawtypes")
	public static final Class[] columnClasses = new Class[] { String.class,
			String.class, String.class, String.class, String.class,
			String.class, String.class, String.class, String.class,
			String.class, String.class, String.class, String.class,
			String.class, String.class, String.class };
	public static final String[] defaultValues = new String[] { "1", "1", "1",
			"1", "1", "1", "None", "None", "None", "None", "None", "None",
			"None", "None", "None", "None" };

	private LoopControlPanel loopPanel;
	protected PowerTableModel wtableModel;
	protected JTable grid;
	protected JPanel buttons;

	private PropertyIterator scheduleIT;

	JTabbedPane tabbedPane = new JTabbedPane();

	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}

	public void setTabbedPane(JTabbedPane tabbedPane) {
		this.tabbedPane = tabbedPane;
	}

	protected final void init() {

		JMeterPluginsUtils.addHelpLinkToPanel(this, WIKIPAGE);
		JPanel containerPanel = new VerticalPanel();

		containerPanel.add(createParamsPanel(), BorderLayout.NORTH);
		containerPanel.add(GuiBuilderHelper.getComponentWithMargin(
				createChart(), 2, 2, 0, 2), BorderLayout.CENTER);
		tabbedPane.addTab("Main", containerPanel);
		add(tabbedPane, BorderLayout.CENTER);

		createControllerPanel();
	}

	private JTable createGrid() {
		grid = new JTable();
		grid.getDefaultEditor(String.class).addCellEditorListener(this);
		createTableModel();
		grid.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		grid.setMinimumSize(new Dimension(200, 100));

		return grid;
	}

	private JPanel createParamsPanel() {
		JPanel panel = new JPanel(new BorderLayout(5, 5));
		panel.setBorder(BorderFactory.createTitledBorder("Threads Schedule"));
		panel.setPreferredSize(new Dimension(200, 200));

		JScrollPane scroll = new JScrollPane(createGrid());
		scroll.setPreferredSize(scroll.getMinimumSize());
		panel.add(scroll, BorderLayout.CENTER);
		buttons = new JPanel();

		JButton button1 = new JButton("Create Workloads");
		buttons.add(button1);

		JButton button2 = new JButton("Refresh");
		buttons.add(button2);

		JButton button3 = new JButton("Clear");
		buttons.add(button3);

		JButton button4 = new JButton("Clear");
		buttons.add(button4);

		button1.addActionListener(new AddRowWorkloadAction(this, grid,
				wtableModel, null, null));
		button2.addActionListener(new RefreshRowWorkloadAction(this));

		button3.addActionListener(new ClearRowWorkloadAction(this, grid,
				wtableModel, null, null));

		button4.addActionListener(new DeleteRowAction(this, grid, wtableModel,
				null));
		panel.add(buttons, BorderLayout.SOUTH);

		return panel;
	}

	public String getLabelResource() {
		return this.getClass().getSimpleName();
	}

	@Override
	public String getStaticLabel() {
		return JMeterPluginsUtils.prefixLabel("WorkLoad Thread Group");
	}

	public TestElement createTestElement() {

		WorkLoadThreadGroup tg = new WorkLoadThreadGroup();
		modifyTestElement(tg);
		tg.setComment(JMeterPluginsUtils.getWikiLinkText(WIKIPAGE));

		return tg;
	}

	public PowerTableModel getWtableModel() {
		return wtableModel;
	}

	public void setWtableModel(PowerTableModel wtableModel) {
		this.wtableModel = wtableModel;
	}

	public void modifyTestElement(TestElement tg) {
		if (tg instanceof WorkLoadThreadGroup) {
			WorkLoadThreadGroup utg = (WorkLoadThreadGroup) tg;

			if (grid == null) {
				createGrid();
			}

			if (grid.isEditing()) {
				grid.getCellEditor().stopCellEditing();
			}

			CollectionProperty rows = JMeterPluginsUtils
					.tableModelRowsToCollectionProperty(wtableModel,
							WorkLoadThreadGroup.DATA_PROPERTY);
			/*
			 * try { JMeterPluginsUtils.tableModelRowsToDerby(wtableModel); }
			 * catch (Exception e) { e.printStackTrace(); }
			 */
			utg.setData(rows);

			LoopController controler = (LoopController) loopPanel
					.createTestElement();
			controler.setLoops(1);
			utg.setSamplerController(controler);

		}
		super.configureTestElement(tg);
	}

	public static void updateModelWithProperty(WorkLoadThreadGroupGUI gui) {
		GuiPackage gp = GuiPackage.getInstance();
		if (gp != null) {
			JMeterTreeNode root = (JMeterTreeNode) gp.getTreeModel().getRoot();
			List<JMeterTreeNode> lista = WorkLoadThreadGroup
					.findWorkLoadThreadGroup(root, new ArrayList());
			for (JMeterTreeNode jMeterTreeNode : lista) {
				WorkLoadThreadGroup utg = (WorkLoadThreadGroup) jMeterTreeNode
						.getTestElement();
				JMeterProperty threadValues = utg.getData();
				if (!(threadValues instanceof NullProperty)) {
					CollectionProperty columns = (CollectionProperty) threadValues;

					gui.getWtableModel().removeTableModelListener(gui);
					JMeterPluginsUtils.collectionPropertyToTableModelRows(
							columns, gui.getWtableModel());
					gui.getWtableModel().addTableModelListener(gui);
					gui.getWtableModel().fireTableDataChanged();
					gui.updateUI();
				}
			}
		}
	}

	@Override
	public void configure(TestElement tg) {

		super.configure(tg);

		WorkLoadThreadGroup utg = (WorkLoadThreadGroup) tg;

		JMeterProperty threadValues = utg.getData();
		if (!(threadValues instanceof NullProperty)) {
			CollectionProperty columns = (CollectionProperty) threadValues;

			wtableModel.removeTableModelListener(this);
			JMeterPluginsUtils.collectionPropertyToTableModelRows(columns,
					wtableModel);
			/*
			 * try { JMeterPluginsUtils.collectionPropertyToDerby(columns); }
			 * catch (Exception e) { e.printStackTrace(); }
			 */
			wtableModel.addTableModelListener(this);
			updateUI();
		} else {
			log.warn("Received null property instead of collection");
		}

		TestElement te = (TestElement) tg.getProperty(
				AbstractThreadGroup.MAIN_CONTROLLER).getObjectValue();
		if (te != null) {
			loopPanel.configure(te);
		}

	}

	@Override
	public void updateUI() {
		super.updateUI();

		if (wtableModel == null) {
			createTableModel();
		}

	}

	private void updateChart(WorkLoadThreadGroup tg) {
		tg.testStarted();
		if (model == null) {
			createChart();
		}

		model.clear();
		GraphRowSumValues row = new GraphRowSumValues();
		row.setColor(Color.RED);
		row.setDrawLine(true);
		row.setMarkerSize(AbstractGraphRow.MARKER_SIZE_NONE);
		row.setDrawThickLines(true);

		final HashTree hashTree = new HashTree();
		hashTree.add(new LoopController());
		JMeterThread thread = new JMeterThread(hashTree, null, null);

		long now = System.currentTimeMillis();

		chart.setxAxisLabelRenderer(new DateTimeRenderer(
				DateTimeRenderer.HHMMSS, now - 1)); // -1 because
													// row.add(thread.getStartTime()
													// - 1, 0)
		chart.setForcedMinX(now);

		row.add(now, 0);

		// users in
		int numThreads = tg.getNumThreads();
		log.debug("Num Threads: " + numThreads);
		for (int n = 0; n < numThreads; n++) {
			thread.setThreadNum(n);
			thread.setThreadName(Integer.toString(n));
			tg.scheduleThread(thread, now);
			row.add(thread.getStartTime() - 1, 0);
			row.add(thread.getStartTime(), 1);
		}

		tg.testStarted();

		for (int n = 0; n < tg.getNumThreads(); n++) {
			thread.setThreadNum(n);
			thread.setThreadName(Integer.toString(n));
			tg.scheduleThread(thread, now);
			row.add(thread.getEndTime() - 1, 0);
			row.add(thread.getEndTime(), -1);
		}

		model.put("Expected parallel users count", row);
		chart.invalidateCache();
		chart.repaint();
	}

	private JPanel createControllerPanel() {
		loopPanel = new LoopControlPanel(false);
		LoopController looper = (LoopController) loopPanel.createTestElement();
		looper.setLoops(1);
		looper.setContinueForever(true);
		loopPanel.configure(looper);
		return loopPanel;
	}

	public static Component createTabChart(JTabbedPane tab1, WorkLoad workLoad) {
		JPanel panel = new JPanel();

		GraphPanelChart chart1 = new GraphPanelChart(false, true);
		ConcurrentHashMap<String, AbstractGraphRow> model1 = new ConcurrentHashMap<String, AbstractGraphRow>();
		chart1.setRows(model1);
		chart1.getChartSettings().setDrawFinalZeroingLines(true);
		chart1.setxAxisLabel("Elapsed time");
		chart1.setYAxisLabel("Number of active threads");
		chart1.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		panel.add(chart1);

		tab1.addTab("Test1", chart1);

		GraphRowSumValues row = new GraphRowSumValues();
		row.setColor(Color.RED);
		row.setDrawLine(true);
		row.setMarkerSize(AbstractGraphRow.MARKER_SIZE_NONE);
		row.setDrawThickLines(true);

		WorkLoadThreadGroupGUI.plotGraph(row, workLoad);

		final HashTree hashTree = new HashTree();
		hashTree.add(new LoopController());

		log.debug("Num Threads: " + 50);

		model1.put("Expected parallel users count", row);
		chart1.invalidateCache();
		chart1.repaint();

		return chart1;

	}

	private Component createChart() {
		chart = new GraphPanelChart(false, true);
		model = new ConcurrentHashMap<String, AbstractGraphRow>();
		chart.setRows(model);
		chart.getChartSettings().setDrawFinalZeroingLines(true);
		chart.setxAxisLabel("Elapsed time");
		chart.setYAxisLabel("Number of active threads");
		chart.setBorder(javax.swing.BorderFactory
				.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		return chart;
	}

	public void tableChanged(TableModelEvent e) {
		updateUI();
	}

	private void createTableModel() {
		wtableModel = new PowerTableModel(columnIdentifiers, columnClasses);
		wtableModel.addTableModelListener(this);

		if (grid == null) {
			createGrid();
		}
		grid.setModel(wtableModel);

	}

	public void editingStopped(ChangeEvent e) {
		updateUI();
	}

	@Override
	public void clearGui() {
		super.clearGui();
		if (wtableModel != null) {
			wtableModel.clearData();
		}
	}

	public void editingCanceled(ChangeEvent arg0) {

	}

	public WorkLoadThreadGroupGUI() {
		super();
		init();
	}

	public static void plotGraph(GraphRowSumValues row, WorkLoad workLoad) {

		for (int n = 0; n < workLoad.getNumThreads(); n++) {

			long initialTime = System.currentTimeMillis();

			long now = workLoad.getStartTimeStrategy(n);
			row.add(now - initialTime, 0);
			row.add(now - initialTime, 1);

			long nowEnd = workLoad.getEndTimeStrategy(n);
			row.add(nowEnd - initialTime, 0);
			row.add(nowEnd - initialTime, -1);
		}

	}

}
