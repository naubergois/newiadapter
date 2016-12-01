/*Copyright [2016] [Francisco Nauber Bernardo Gois]

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.*/

package br.unifor.iadapter.util;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import org.apache.commons.math3.analysis.function.Abs;
import org.apache.jmeter.engine.util.CompoundVariable;
import org.apache.jmeter.gui.AbstractJMeterGuiComponent;
import org.apache.jmeter.gui.tree.JMeterTreeNode;
import org.apache.jmeter.gui.util.PowerTableModel;
import org.apache.jmeter.samplers.SampleSaveConfiguration;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.testelement.property.CollectionProperty;
import org.apache.jmeter.testelement.property.JMeterProperty;
import org.apache.jmeter.testelement.property.NullProperty;
import org.apache.jmeter.testelement.property.StringProperty;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.collections.ListedHashTree;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.log.Logger;
import org.jgap.Chromosome;
import org.jgap.IChromosome;
import org.jgap.Population;

import br.unifor.iadapter.agent.Agent;
import br.unifor.iadapter.algorithm.AbstractAlgorithm;
import br.unifor.iadapter.database.MySQLDatabase;
import br.unifor.iadapter.percentiles.PercentileCounter;
import br.unifor.iadapter.searchclass.SearchClass;
import br.unifor.iadapter.threadGroup.workload.WorkLoad;
import br.unifor.iadapter.threadGroup.workload.WorkLoadThreadGroup;

public abstract class JMeterPluginsUtils {
	private static final Logger log = LoggingManager.getLoggerForClass();

	private static String PLUGINS_PREFIX = "jp@gc - ";
	private static boolean prefixPlugins = true;
	public static final String WIKI_BASE = "http://jmeter-plugins.org/wiki/";

	// just prefix all the labels to be distinguished
	public static String prefixLabel(String label) {
		return prefixPlugins ? PLUGINS_PREFIX + label : label;
	}

	public static List<WorkLoad> getListWorkLoadFromPopulation(AbstractAlgorithm algorithm, Population population,
			ListedHashTree tree, int generation, int generationTrack) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException {
		List<TestElement> listElement = FindService.searchWorkLoadControllerWithNoGui(tree);
		List<WorkLoad> list = new ArrayList<WorkLoad>();
		List<Chromosome> listC = population.getChromosomes();
		for (Chromosome chromosome : listC) {
			list.add(WorkLoadUtil.getWorkLoadFromChromosome(algorithm, chromosome, listElement, generation));
		}

		return list;
	}

	public static List<WorkLoad> getListWorkLoadFromPopulation(AbstractAlgorithm algorithm, List<Chromosome> listC,
			ListedHashTree tree, int generation, int generationTrack) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException {
		List<TestElement> listElement = FindService.searchWorkLoadControllerWithNoGui(tree);
		List<WorkLoad> list = new ArrayList<WorkLoad>();

		for (Chromosome chromosome : listC) {
			list.add(WorkLoadUtil.getWorkLoadFromChromosome(algorithm, chromosome, listElement, generation));
		}

		return list;
	}

	public static int randInt(int min, int max) {

		// NOTE: Usually this should be a field rather than a method
		// variable so that it is not re-seeded every call.
		Random rand = new Random();

		// nextInt is normally exclusive of the top value,
		// so add 1 to make it inclusive
		int randomNum = rand.nextInt((max - min) + 1) + min;

		return randomNum;
	}

	public static List<WorkLoad> getListWorkLoadFromPopulationTestPlan(AbstractAlgorithm algorithm, ListedHashTree tree,
			List<IChromosome> listC, boolean gui, int generation) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException {

		List<TestElement> listElement = null;
		if (!(gui)) {
			listElement = FindService.searchWorkLoadControllerWithNoGui(tree);
		} else {
			List<JMeterTreeNode> nodes = FindService.searchWorkLoadControllerWithGui();
			listElement = WorkLoadThreadGroup.testNodeToTestElement(nodes);
		}
		List<WorkLoad> list = new ArrayList<WorkLoad>();

		for (IChromosome chromosome : listC) {

			list.add(WorkLoadUtil.getWorkLoadFromChromosome(algorithm, chromosome, listElement, generation));
		}

		return list;

	}

	public static String getStackTrace(Exception ex) {
		StackTraceElement[] stack = ex.getStackTrace();
		StringBuilder res = new StringBuilder();
		for (StackTraceElement aStack : stack) {
			res.append("at ");
			res.append(aStack.toString());
			res.append("\n");
		}
		return res.toString();
	}

	static {
		String prefixPluginsCfg = JMeterUtils.getProperty("jmeterPlugin.prefixPlugins");
		if (prefixPluginsCfg != null) {
			JMeterPluginsUtils.prefixPlugins = "true".equalsIgnoreCase(prefixPluginsCfg.trim());
		}
	}

	public static void tableModelRowsToDerby( PowerTableModel model, WorkLoadThreadGroup gp,
			String generation) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

		for (int row = 0; row < model.getRowCount(); row++) {
			List<Object> item = getArrayListForArray(model.getRowData(row));
			System.out.println("Algorithm: "+item.toString());
			System.out.println("Algorithm: "+item.get(22).toString());
			System.out.println("Algorithm: "+item.get(23).toString());
			System.out.println("Algorithm: "+item.get(24).toString());
			
			AbstractAlgorithm algorithm=SearchClass.get(item.get(22).toString());
			if (item != null) {
				MySQLDatabase.insertWorkLoads(algorithm, item, gp.getName(), generation);
			} else {
				log.info("Null object");
			}
		}

	}

	public static CollectionProperty tableModelRowsToCollectionProperty(PowerTableModel model, String propname) {
		CollectionProperty rows = new CollectionProperty(propname, new ArrayList<Object>());
		for (int row = 0; row < model.getRowCount(); row++) {
			List<Object> item = getArrayListForArray(model.getRowData(row));
			if (item != null) {
				for (int i = 0; i < item.size(); i++) {
					String object = String.valueOf(item.get(i));
					if (object == null) {
						item.set(i, "0");
					} else {
						item.set(i, String.valueOf(object));
					}

				}

				rows.addItem(item);
			} else {
				log.info("Objeto nulo");
			}
		}
		return rows;
	}

	public static CollectionProperty listWorkLoadToCollectionProperty(List<WorkLoad> model, String propname) {
		CollectionProperty rows = new CollectionProperty(propname, new ArrayList<Object>());
		if (model != null) {
			for (int row = 0; row < model.size(); row++) {
				Object[] item = WorkLoadUtil.getObjectList(model.get(row));
				if (item != null) {
					for (int i = 0; i < item.length; i++) {
						String object = String.valueOf(item[i]);
						if (object == null) {
							item[i] = "0";
						} else {
							item[i] = String.valueOf(object);
						}

					}

					rows.addItem(item);
				} else {
					log.info("Objeto nulo");
				}
			}
		}
		return rows;
	}

	public static void updateFit(List<WorkLoad> list, String testPlan, String generation, String maxTime,
			WorkLoadThreadGroup tg) {
		for (WorkLoad workload : list) {
			try {
				workload.setFit(MySQLDatabase.updateFitValue(workload.getName(), testPlan, generation,
						Long.valueOf(maxTime), tg, Long.valueOf(tg.getResponseTimeMaxPenalty())));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static CollectionProperty tableModelRowsToCollectionPropertyEval(PowerTableModel model, String propname) {
		CollectionProperty rows = new CollectionProperty(propname, new ArrayList<Object>());
		for (int row = 0; row < model.getRowCount(); row++) {
			List<Object> item = getArrayListForArrayEval(model.getRowData(row));
			rows.addItem(item);
		}
		return rows;
	}

	public static void collectionPropertyToDerby(CollectionProperty prop, WorkLoadThreadGroup gp, String generation)
			throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

		for (int rowN = 0; rowN < prop.size(); rowN++) {
			ArrayList<String> rowObject = (ArrayList<String>) prop.get(rowN).getObjectValue();

			String className = rowObject.get(22);
			AbstractAlgorithm algorithm = SearchClass.get(className);

			MySQLDatabase.createWorkLoadIfNotExist(algorithm, rowObject, gp.getName(), generation);
		}

	}

	public static void collectionPropertyToTableModelRows(CollectionProperty prop, PowerTableModel model) {
		model.clearData();
		for (int rowN = 0; rowN < prop.size(); rowN++) {
			ArrayList<String> rowObject = (ArrayList<String>) prop.get(rowN).getObjectValue();
			model.addRow(rowObject.toArray());
		}
		model.fireTableDataChanged();
	}

	public static void derbyAgentToTableModelRows(PowerTableModel model) throws ClassNotFoundException, SQLException {
		model.clearData();
		List<Agent> list = MySQLDatabase.selectAgents();
		for (int rowN = 0; rowN < list.size(); rowN++) {
			Agent agent = list.get(rowN);

			ArrayList<String> rowObject = new ArrayList<String>();
			rowObject.add(agent.getName());
			rowObject.add(agent.getRunning());
			rowObject.add(agent.getIp());
			rowObject.add(agent.getDate());
			model.addRow(rowObject.toArray());
		}
		model.fireTableDataChanged();
	}

	public static void updateResponseTime(HashMap<String, String> responseTimes,
			HashMap<String, PercentileCounter> counters, HashMap<String, String> totalErrors, List<WorkLoad> list,
			WorkLoadThreadGroup tg) throws ClassNotFoundException, SQLException {
		for (WorkLoad workLoad : list) {
			String responseTime = responseTimes.get(workLoad.getName());
			String erros = totalErrors.get(workLoad.getName());
			PercentileCounter counter = counters.get(workLoad.getName());
			MySQLDatabase.updateResponseTime(responseTime, workLoad.getName(), tg.getName(),
					String.valueOf(tg.getGeneration()), counter, erros);
		}
	}

	public static void updateSamples(HashMap<String, String> responseMaxTimes, WorkLoadThreadGroup tg,
			String generation) throws ClassNotFoundException, SQLException {

		Set<String> keys = responseMaxTimes.keySet();
		for (String key : keys) {
			List<Object> listInsert = new ArrayList<Object>();

			String responseTime = responseMaxTimes.get(key);

			String[] list = key.split("##@");
			String workloadName = list[0];
			String sampleName = list[1];
			listInsert.add(sampleName);
			listInsert.add(responseTime);
			listInsert.add("message");
			listInsert.add(workloadName);
			MySQLDatabase.insertSample(listInsert, tg.getName(), String.valueOf(tg.getGeneration()));
		}

	}

	public static void updateErrorValue(HashMap<String, String> errors, List<WorkLoad> list, WorkLoadThreadGroup tg)
			throws ClassNotFoundException, SQLException {
		for (WorkLoad workLoad : list) {
			log.info(errors.toString());
			String error = errors.get(workLoad.getName());
			log.info(workLoad.getName() + "-" + error);
			MySQLDatabase.updateError(error, workLoad.getName(), tg.getName(), String.valueOf(tg.getGeneration()));
		}

	}

	public static List<WorkLoad> collectionPropertyToWorkLoad(WorkLoadThreadGroup utg) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException {

		List<WorkLoad> workLoadList = new ArrayList<WorkLoad>();
		JMeterProperty threadValues = utg.getData();
		if (!(threadValues instanceof NullProperty)) {
			CollectionProperty prop = (CollectionProperty) threadValues;

			for (int rowN = 0; rowN < prop.size(); rowN++) {
				ArrayList<String> rowObject = (ArrayList<String>) prop.get(rowN).getObjectValue();
				WorkLoad workload = WorkLoadUtil.getWorkLoad(rowObject);
				workLoadList.add(workload);

			}
		}

		return workLoadList;

	}

	public static void collectionPropertyToTableModelRows(CollectionProperty prop, PowerTableModel model,
			@SuppressWarnings("rawtypes") Class[] columnClasses) {
		model.clearData();
		for (int rowN = 0; rowN < prop.size(); rowN++) {
			@SuppressWarnings("unchecked")
			ArrayList<StringProperty> rowStrings = (ArrayList<StringProperty>) prop.get(rowN).getObjectValue();
			ArrayList<Object> rowObject = new ArrayList<Object>(rowStrings.size());

			for (int i = 0; i < columnClasses.length && i < rowStrings.size(); i++) {
				rowObject.add(convertToClass(rowStrings.get(i), columnClasses[i]));
			}

			if (rowObject.size() < columnClasses.length) {
				for (int i = rowObject.size(); i < columnClasses.length; i++) {
					rowObject.add(new Object());
				}
			}
			model.addRow(rowObject.toArray());
		}
		model.fireTableDataChanged();
	}

	private static List<Object> getArrayListForArray(Object[] rowData) {
		ArrayList<Object> res = new ArrayList<Object>();
		for (int n = 0; n < rowData.length; n++) // note that we MUST use
													// ArrayList
		{
			res.add(rowData[n]);
		}

		return res;
	}

	private static List<Object> getArrayListForArrayEval(Object[] rowData) {
		ArrayList<Object> res = new ArrayList<Object>();
		for (int n = 0; n < rowData.length; n++) // note that we must use
													// ArrayList
		{
			res.add(new CompoundVariable(rowData[n].toString()).execute());
		}
		return res;
	}

	public static String byteBufferToString(ByteBuffer buf) {
		byte[] dst = byteBufferToByteArray(buf);
		return new String(dst);
	}

	public static byte[] byteBufferToByteArray(ByteBuffer buf) {
		ByteBuffer str = buf.duplicate();
		// System.err.println("Before "+str);
		str.rewind();
		// str.flip();
		// System.err.println("After "+str);
		byte[] dst = new byte[str.limit()];
		str.get(dst);
		return dst;
	}

	public static String replaceRNT(String str) {
		// FIXME: stop using bad way...
		str = str.replaceAll("\\\\\\\\", "VERY BAD WAY");
		// System.err.println(str);
		str = str.replaceAll("\\\\t", "\t");
		// str=str.replaceAll("(^|[^\\\\])\\\\t", "$1\t");
		// System.err.println(str);
		str = str.replaceAll("\\\\n", "\n");
		// System.err.println(str);
		str = str.replaceAll("\\\\r", "\r");
		str = str.replaceAll("VERY BAD WAY", "\\\\");
		return str;
	}

	public static String getWikiLinkText(String wikiPage) {
		if (!java.awt.Desktop.isDesktopSupported()) {
			return "Plugin help available here: " + WIKI_BASE + wikiPage;
		} else {
			return "";
		}
	}

	private static Object convertToClass(StringProperty value, Class<?> aClass) {
		if (Boolean.class.equals(aClass)) {
			return Boolean.valueOf(value.getStringValue());
		}
		return value;
	}

	/**
	 * Find in panel appropriate place and put hyperlink there. I know that it
	 * is stupid way. But the result is so good!
	 *
	 * @param panel
	 *            - supposed to be result of makeTitlePanel()
	 * @param helpPage
	 *            wiki page name, not full URL
	 * @return original panel
	 * @see AbstractJMeterGuiComponent
	 */
	public static Component addHelpLinkToPanel(Container panel, String helpPage) {
		if (!java.awt.Desktop.isDesktopSupported()) {
			return panel;
		}

		JLabel icon = new JLabel();
		icon.setIcon(new javax.swing.ImageIcon(JMeterPluginsUtils.class.getResource("information.png")));

		JLabel link = new JLabel("Help on this plugin");
		link.setForeground(Color.blue);
		link.setFont(link.getFont().deriveFont(Font.PLAIN));
		link.setCursor(new Cursor(Cursor.HAND_CURSOR));
		link.addMouseListener(new URIOpener(
				WIKI_BASE + helpPage + "/?utm_source=jmeter&utm_medium=helplink&utm_campaign=" + helpPage));
		Border border = BorderFactory.createMatteBorder(0, 0, 1, 0, java.awt.Color.blue);
		link.setBorder(border);

		JLabel version = new JLabel("v" + getVersion());
		version.setFont(version.getFont().deriveFont(Font.PLAIN).deriveFont(11F));
		version.setForeground(Color.GRAY);

		Container innerPanel = findComponentWithBorder((JComponent) panel, EtchedBorder.class);

		JPanel panelLink = new JPanel(new GridBagLayout());

		GridBagConstraints gridBagConstraints;

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.insets = new java.awt.Insets(0, 1, 0, 0);
		panelLink.add(icon, gridBagConstraints);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.insets = new java.awt.Insets(0, 2, 3, 0);
		panelLink.add(link, gridBagConstraints);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 4);
		panelLink.add(version, gridBagConstraints);

		if (innerPanel != null) {
			innerPanel.add(panelLink);
		} else {
			panel.add(panelLink);
		}
		return panel;
	}

	private static Container findComponentWithBorder(JComponent panel, Class<?> aClass) {
		for (int n = 0; n < panel.getComponentCount(); n++) {
			if (panel.getComponent(n) instanceof JComponent) {
				JComponent comp = (JComponent) panel.getComponent(n);
				if (comp.getBorder() != null && aClass.isAssignableFrom(comp.getBorder().getClass())) {
					return comp;
				}

				Container con = findComponentWithBorder(comp, aClass);
				if (con != null) {
					return con;
				}
			}
		}
		return null;
	}

	public static void doBestCSVSetup(SampleSaveConfiguration conf) {
		conf.setAsXml(false);
		conf.setFieldNames(true);

		conf.setFormatter(null);
		conf.setSamplerData(false);
		conf.setRequestHeaders(false);
		conf.setFileName(false);
		conf.setIdleTime(false);
		conf.setSuccess(true);
		conf.setMessage(true);
		conf.setEncoding(false);
		conf.setThreadCounts(true);
		conf.setFieldNames(true);
		conf.setAssertions(false);
		conf.setResponseData(false);
		conf.setSubresults(false);
		conf.setLatency(true);
		conf.setLabel(true);

		conf.setThreadName(true);
		conf.setBytes(true);
		conf.setHostname(false);
		conf.setAssertionResultsFailureMessage(false);
		conf.setResponseHeaders(false);
		conf.setUrl(false);
		conf.setTime(true);
		conf.setTimestamp(true);
		conf.setCode(true);
		conf.setDataType(false);
		conf.setSampleCount(false);
	}

	public static void openInBrowser(String string) {
		if (java.awt.Desktop.isDesktopSupported()) {
			try {
				java.awt.Desktop.getDesktop().browse(new URI(string));
			} catch (IOException ignored) {
				log.debug("Failed to open in browser", ignored);
			} catch (URISyntaxException ignored) {
				log.debug("Failed to open in browser", ignored);
			}
		}
	}

	public static float getFloatFromString(String stringValue, float defaultValue) {
		float ret;
		if (stringValue != null) {
			try {
				ret = Float.valueOf(stringValue);
			} catch (NumberFormatException ex) {
				ret = defaultValue;
			}
		} else {
			ret = defaultValue;
		}

		return ret;
	}

	public static int getSecondsForShortString(String string) {
		int res = 0;
		string = string.trim();

		String c;
		String curNum = "";
		for (int n = 0; n < string.length(); n++) {
			c = String.valueOf(string.charAt(n));
			if (c.matches("\\d")) {
				curNum += c;
			} else {
				int mul;
				switch (c.charAt(0)) {
				case 's':
				case 'S':
					mul = 1;
					break;
				case 'm':
				case 'M':
					mul = 60;
					break;
				case 'h':
				case 'H':
					mul = 60 * 60;
					break;
				case 'd':
				case 'D':
					mul = 60 * 60 * 24;
					break;
				default:
					throw new NumberFormatException("Shorthand string does not allow using '" + c + "'");
				}
				res += Integer.parseInt(curNum) * mul;
				curNum = "";
			}
		}

		if (!curNum.isEmpty()) {
			res += Integer.parseInt(curNum);
		}

		return res;
	}

	public static String getVersion() {

		return "1.0";
	}

	private static class URIOpener extends MouseAdapter {

		private final String uri;

		public URIOpener(String aURI) {
			uri = aURI;
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			if ((e.getModifiers() & InputEvent.BUTTON1_MASK) == InputEvent.BUTTON1_MASK) {
				openInBrowser(uri);
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}
	}

	/**
	 * Get a String value (environment) with default if not present.
	 *
	 * @param propName
	 *            the name of the environment variable.
	 * @param defaultVal
	 *            the default value.
	 * @return The PropDefault value
	 */
	public static String getEnvDefault(String propName, String defaultVal) {
		String ans = defaultVal;
		String value = System.getenv(propName);
		if (value != null) {
			ans = value.trim();
		} else if (defaultVal != null) {
			ans = defaultVal.trim();
		}
		return ans;
	}

	public static void prepareJMeterEnv(String homeDir) {
		if (JMeterUtils.getJMeterHome() != null) {
			log.warn("JMeter env exists. No one should see this normally.");
			return;
		}

		log.debug("Orig jmeter home dir: " + homeDir);
		File dir = new File(homeDir);
		while (dir != null && dir.exists() && dir.getName().equals("ext")
				&& dir.getParentFile().getName().equals("lib")) {
			dir = dir.getParentFile();
		}

		if (dir == null || !dir.exists()) {
			throw new IllegalArgumentException("CMDRunner.jar must be placed in <jmeter>/lib/ext directory");
		}

		homeDir = dir.getParent();

		if (!isJMeterHome(homeDir)) {
			homeDir = getJMeterHomeFromCP(System.getProperty("java.class.path"));
		}

		log.debug("Final jmeter home dir: " + homeDir);
		JMeterUtils.setJMeterHome(homeDir);
		initializeProperties();
	}

	/**
	 * Had to copy this method from JMeter class 'cause they provide no ways to
	 * re-use this code
	 *
	 * @see org.apache.jmeter.JMeter
	 */
	private static void initializeProperties() {
		JMeterUtils.loadJMeterProperties(
				JMeterUtils.getJMeterHome() + File.separator + "bin" + File.separator + "jmeter.properties");

		// JMeterUtils.initLogging();
		JMeterUtils.initLocale();

		Properties jmeterProps = JMeterUtils.getJMeterProperties();

		// Add local JMeter properties, if the file is found
		String userProp = JMeterUtils.getPropDefault("user.properties", "");
		if (userProp.length() > 0) {
			FileInputStream fis = null;
			try {
				File file = JMeterUtils.findFile(userProp);
				if (file.canRead()) {
					log.info("Loading user properties from: " + file.getCanonicalPath());
					fis = new FileInputStream(file);
					Properties tmp = new Properties();
					tmp.load(fis);
					jmeterProps.putAll(tmp);
					LoggingManager.setLoggingLevels(jmeterProps);// Do what
																	// would be
																	// done
																	// earlier
				}
			} catch (IOException e) {
				log.warn("Error loading user property file: " + userProp, e);
			} finally {
				try {
					if (fis != null) {
						fis.close();
					}
				} catch (IOException ex) {
					log.warn("There was problem closing file stream", ex);
				}
			}
		}

		// Add local system properties, if the file is found
		String sysProp = JMeterUtils.getPropDefault("system.properties", "");
		if (sysProp.length() > 0) {
			FileInputStream fis = null;
			try {
				File file = JMeterUtils.findFile(sysProp);
				if (file.canRead()) {
					log.info("Loading system properties from: " + file.getCanonicalPath());
					fis = new FileInputStream(file);
					System.getProperties().load(fis);
				}
			} catch (IOException e) {
				log.warn("Error loading system property file: " + sysProp, e);
			} finally {
				try {
					if (fis != null) {
						fis.close();
					}
				} catch (IOException ex) {
					log.warn("There was problem closing file stream", ex);
				}
			}
		}
	}

	private static boolean isJMeterHome(String homeDir) {
		File f = new File(homeDir + File.separator + "lib" + File.separator + "ext");
		return f.exists() && f.isDirectory();
	}

	public static String getJMeterHomeFromCP(String classpathSTR) {
		log.debug("Trying to get JMeter home from classpath");

		// FIXME: This dirty way of doing it should be changed as it is OS
		// sensitive

		String splitter;

		if (classpathSTR.indexOf(';') != -1) {
			splitter = ";";
		} else {
			splitter = ":";
		}

		String[] paths = classpathSTR.split(splitter);
		for (String string : paths) {
			log.debug("Testing " + string);
			if (string.endsWith("ApacheJMeter_core.jar")) {
				File f = new File(string);
				return f.getParentFile().getParentFile().getParentFile().getAbsolutePath();
			}
		}
		throw new Error("Failed to find JMeter home dir from classpath");
	}

}
