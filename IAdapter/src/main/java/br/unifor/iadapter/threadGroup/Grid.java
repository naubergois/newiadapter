package br.unifor.iadapter.threadGroup;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import org.apache.jmeter.gui.util.PowerTableModel;
import org.apache.jmeter.gui.util.VerticalPanel;


public class Grid extends VerticalPanel {
    private JTable grid;
    private PowerTableModel tableModel;

    public Grid(String title, String[] columnIdentifiers, Class<?>[] columnClasses, Object[] defaultValues) {
        super();
        this.setBorder(BorderFactory.createTitledBorder(title));
        this.setPreferredSize(new Dimension(150, 150));

        JScrollPane scroll = new JScrollPane(createGrid(columnIdentifiers, columnClasses));
        scroll.setPreferredSize(scroll.getMinimumSize());
        this.add(scroll, BorderLayout.CENTER);
        this.add(new ButtonPanelAddCopyRemove(grid, tableModel, defaultValues), BorderLayout.SOUTH);

        grid.getTableHeader().setReorderingAllowed(false);
    }

    private JTable createGrid(String[] columnIdentifiers, Class<?>[] columnClasses) {
        grid = new JTable();
        tableModel = new PowerTableModel(columnIdentifiers, columnClasses);
        grid.setModel(tableModel);
        grid.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        grid.setMinimumSize(new Dimension(200, 100));

        return grid;
    }

    public PowerTableModel getModel() {
        return tableModel;
    }
}
