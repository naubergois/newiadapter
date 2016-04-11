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
import java.awt.Frame;
import java.awt.GraphicsEnvironment;

import javax.swing.ImageIcon;
import javax.swing.JDialog;

public class DialogFactory {

    public static JDialog getJDialogInstance(Frame owner, String title, boolean modal, JAbsrtactDialogPanel content, String imagePath) {
        if(!GraphicsEnvironment.isHeadless()) {
            JDialog ret = new JDialog(owner, title, modal);
            ret.add(content);
            ret.pack();
            Dimension size = ret.getPreferredSize();
            if(size.width < content.getMinWidth()) {
                size.width = content.getMinWidth();
            }
            ret.setSize(size);
            ret.validate();
            if(imagePath != null) {
                ImageIcon imageIcon = new ImageIcon(DialogFactory.class.getResource(imagePath));
                if(imageIcon != null) {
                    ret.setIconImage(imageIcon.getImage());
                }
            }
            return ret;
        } else {
            return null;
        }
    }

    public static void centerDialog(Frame parent, JDialog dialog) {
        if(parent != null && dialog != null) {
            dialog.setLocation(parent.getLocation().x + (parent.getSize().width - dialog.getSize().width) / 2,
                        parent.getLocation().y + (parent.getSize().height - dialog.getSize().height) / 2);
        }
    }
}
