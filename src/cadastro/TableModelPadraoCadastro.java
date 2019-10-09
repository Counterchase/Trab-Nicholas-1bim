/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cadastro;

import java.util.List;
import javax.swing.table.AbstractTableModel;

public abstract class TableModelPadraoCadastro extends AbstractTableModel {

    protected String[] colunas;
    protected List linhas;

    public TableModelPadraoCadastro(List linhas) {
        setColunas(criarColunas());
        this.linhas = linhas;
    }

    protected abstract String[] criarColunas();

    public int getRowCount() {
        if (linhas != null) {
            return linhas.size();
        } else {
            return 0;
        }
    }

    public int getColumnCount() {
        return colunas.length;
    }

    public boolean isCellEditable(int row, int col) {
        return false;
    }

    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    public String getColumnName(int col) {
        return colunas[col];
    }

    public String[] getColunas() {
        return colunas;
    }

    public void setColunas(String[] colunas) {
        this.colunas = colunas;
    }

    public List getLinhas() {
        return linhas;
    }

    public void setLinhas(List linhas) {
        this.linhas = linhas;
        fireTableDataChanged();
    }
}
