package br.unifor.iadapter.threadGroup;

import org.apache.jmeter.engine.StandardJMeterEngine;
import org.apache.jmeter.reporters.ResultCollector;
import org.apache.jmeter.samplers.SampleSaveConfiguration;
import org.apache.jmeter.testelement.property.ObjectProperty;
import org.apache.jmeter.threads.JMeterContext;
import org.apache.jmeter.threads.ListenerNotifier;
import org.apache.jorphan.collections.ListedHashTree;

public class SingletonEngine {
	private static final String SAVE_CONFIG = "saveConfig"; // $NON-NLS-1$
	private static Thread engine;
	public static int groupCount;
	public static ListenerNotifier notifier;
	public static ListedHashTree threadGroupTree;
	public static StandardJMeterEngine engine1;

	public static void getEngineThread(JMeterContext context)
			throws InterruptedException {

		if (engine == null) {
			engine = new Thread(context.getEngine());
			engine.start();
		} else {
			new ResultCollector().setProperty(new ObjectProperty(SAVE_CONFIG,
					new SampleSaveConfiguration()));
			engine.join();
			engine = new Thread(context.getEngine());
			engine.start();
		}

	}

}
