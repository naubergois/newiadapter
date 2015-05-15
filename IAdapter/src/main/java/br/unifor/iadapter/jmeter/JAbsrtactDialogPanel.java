package br.unifor.iadapter.jmeter;

import java.awt.Dimension;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public abstract class JAbsrtactDialogPanel extends JPanel{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int minWidth = 0;

    public int getMinWidth() {
        return minWidth;
    }

    public void setMinWidth(int minWidth) {
        this.minWidth = minWidth;
    }

    protected void repack() {
        JDialog dlgParent = getAssociatedDialog();
        if(dlgParent != null) {
            Dimension newSize = dlgParent.getPreferredSize();
            if(newSize.width < minWidth) {
                newSize.width = minWidth;
            }
            dlgParent.setSize(newSize);
            dlgParent.validate();
        }
    }

    protected JDialog getAssociatedDialog() {
        return (JDialog)SwingUtilities.getWindowAncestor(this);
    }

}
