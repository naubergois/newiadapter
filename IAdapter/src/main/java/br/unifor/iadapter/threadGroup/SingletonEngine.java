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
