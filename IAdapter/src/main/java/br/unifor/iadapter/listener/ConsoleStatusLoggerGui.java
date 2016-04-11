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

package br.unifor.iadapter.listener;

import java.awt.BorderLayout;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.visualizers.gui.AbstractListenerGui;

import br.unifor.iadapter.util.JMeterPluginsUtils;

public class ConsoleStatusLoggerGui extends AbstractListenerGui {

    public static final String WIKIPAGE = "WorkLoadMonitor";

    public ConsoleStatusLoggerGui() {
        super();
        init();
    }

    @Override
    public String getStaticLabel() {
        return "WorkLoad Monitor";
    }

   
    public String getLabelResource() {
        return getClass().getCanonicalName();
    }

   
    public TestElement createTestElement() {
        TestElement te = new ConsoleStatusLogger();
        modifyTestElement(te);
        te.setComment(JMeterPluginsUtils.getWikiLinkText(WIKIPAGE));
        return te;
    }

   
    public void modifyTestElement(TestElement te) {
        super.configureTestElement(te);
    }

    private void init() {
        setLayout(new BorderLayout(0, 5));
        setBorder(makeBorder());
        add(JMeterPluginsUtils.addHelpLinkToPanel(makeTitlePanel(), WIKIPAGE), BorderLayout.NORTH);
        JTextArea info = new JTextArea();
        info.setEditable(false);
        info.setWrapStyleWord(true);
        info.setOpaque(false);
        info.setLineWrap(true);
        info.setColumns(20);

        JScrollPane jScrollPane1 = new javax.swing.JScrollPane();
        jScrollPane1.setViewportView(info);
        jScrollPane1.setBorder(null);
        
        info.setText("This is a simple listener that prints short summary log to console while JMeter is running in non-GUI mode. "
                + "It also writes the same info into jmeter.log in GUI mode."
                + "\n\nNote that response time and latency values printed are averages.");

        add(jScrollPane1, BorderLayout.CENTER);
    }
}
