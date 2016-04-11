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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.JTextField;

import org.apache.jmeter.gui.GuiPackage;

public class BrowseAction implements ActionListener {

    private final JTextField control;
    private boolean isDirectoryBrowse = false;
    private String lastPath = ".";

    public BrowseAction(JTextField filename) {
        control = filename;
    }

    public BrowseAction(JTextField filename, boolean isDirectoryBrowse) {
        control = filename;
        this.isDirectoryBrowse = isDirectoryBrowse;
    }

   
    public void actionPerformed(ActionEvent e) {
        JFileChooser chooser = getFileChooser();
        if (chooser != null) {
            if(GuiPackage.getInstance() != null) {
                int returnVal = chooser.showOpenDialog(GuiPackage.getInstance().getMainFrame());
                if(returnVal == JFileChooser.APPROVE_OPTION) {
                   control.setText(chooser.getSelectedFile().getPath());
                }
                lastPath = chooser.getCurrentDirectory().getPath();
            }
        }
    }

    protected JFileChooser getFileChooser() {
        JFileChooser ret = new JFileChooser(lastPath);
        if(isDirectoryBrowse) {
            ret.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        }

        return ret;
    }
}
