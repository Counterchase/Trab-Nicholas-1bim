/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author santos
 */
public abstract class TableModelPadrao extends AbstractTableModel {
    protected List lista;
    protected String[] columnNames;
    
    public TableModelPadrao(List<?> lista, String[] columnNames) {
        this.lista = lista;
        this.columnNames = columnNames;
    }
    
    @Override
    public int getRowCount() {
        return lista.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    
    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
    
    public Object getRow(int row) {
        return lista.get(row);
    }
    
    public void insertRow(int row, Object obj) {
        lista.add(row, obj);
        fireTableRowsInserted(row, row);
    }
    
    public void addRow(Object obj) {
        insertRow(getRowCount(), obj);
    }

    public void removeConta(int row) {
        lista.remove(row);
        fireTableRowsDeleted(row, row);
    }
    
    @Override
    public boolean isCellEditable(int linha, int coluna) {
        return false;
    }
    
}
