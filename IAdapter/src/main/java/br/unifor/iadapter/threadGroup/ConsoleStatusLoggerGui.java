package br.unifor.iadapter.threadGroup;

import java.awt.BorderLayout;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.visualizers.gui.AbstractListenerGui;

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
