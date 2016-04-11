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
